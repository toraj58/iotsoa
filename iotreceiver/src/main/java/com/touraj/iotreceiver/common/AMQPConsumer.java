package com.touraj.iotreceiver.common;

import com.datastax.driver.core.Session;
import com.google.gson.Gson;
import com.rabbitmq.client.*;
import com.touraj.iotreceiver.Domain.IOTData;
import com.touraj.iotreceiver.repository.IOTDataRepository;
import com.touraj.iotreceiver.repository.KeyspaceRepository;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by toraj on 07/20/2018.
 */
public class AMQPConsumer {

    private KeyspaceRepository schemaRepository;
    private IOTDataRepository iotDataRepository;
    private Session session;
    private CassandraConnector client;

    public void startConsumer() {

        // Touraj : My Workers work base on Fair Dispatch Algorithm

        try {
            final Channel channel = createConnectionAndChannel();

            boolean durable = false;
            channel.queueDeclare(LoadConfiguration.getQueueName(), durable, false, false, null);
            System.out.println("Waiting for messages.");
            System.out.println("To exit please press CTRL+C");

            //Touraj :: Fair Dispatch Enabling Algorithm
            int prefetchCount = 1;
            channel.basicQos(prefetchCount);

            Gson gson = new Gson();

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");

                    System.out.println("Received :: " + message);

                    connectToCassandra();

                    //Touraj:: Create Cassandra Table if Not Exist!
                    iotDataRepository.createTable();

                    //Touraj: I use Gson for Marshaling/ Unmarshaling data to DTO objects as Domain model

                    IOTData iotData = gson.fromJson(message, IOTData.class);

                    iotDataRepository.insertIOTData(iotData);

                    //Touraj ::  Manually Acknowledge the Producer :: good for Fair dispatch Algorithm
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };

            // Touraj :: I disable Auto Ack in order to use Fair Dispatch Algorithm
            // for Distributing work load between workers in a Fair Manner
            boolean autoAck = false;

            channel.basicConsume(LoadConfiguration.getQueueName(), autoAck, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void connectToCassandra() {
        client = new CassandraConnector();
        client.connect(LoadConfiguration.getCassandraIP(), Integer.valueOf(LoadConfiguration.getCassandraPort()));
        session = client.getSession();
        schemaRepository = new KeyspaceRepository(session);

        schemaRepository.createKeyspace(LoadConfiguration.getCassandraKeySpace(), "SimpleStrategy", 1);
        schemaRepository.useKeyspace(LoadConfiguration.getCassandraKeySpace());
        iotDataRepository = new IOTDataRepository(session);
    }

    private Channel createConnectionAndChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(LoadConfiguration.getRabbitmqIP());
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }
}
