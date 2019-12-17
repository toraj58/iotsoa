package com.touraj.iotsender.Main;

/**
 * Created by toraj on 07/21/2018.
 */

import com.datastax.driver.core.utils.UUIDs;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.touraj.iotsender.common.LoadConfiguration;
import com.touraj.iotsender.domain.IOTData;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final int MIN_SENSOR_VALUE = 10;
    private static final int MAX_SENSOR_VALUE = 1000;
    private static final int SLEEP_IN_SECONDS = 1;

    public static void main(String[] argv) throws Exception {

        System.out.println("Starting IOT Sender...");

        String iotDeviceType = null;
        if (argv.length > 0) {
            iotDeviceType = argv[0];
        }

        if (iotDeviceType == null || iotDeviceType.trim().equals("")) {
            iotDeviceType = "default_iot_device";
        }

        new LoadConfiguration();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(LoadConfiguration.getRabbitmqIP());
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                    System.out.println("Shouting down and Cleaning Resources...");
                    try {
                        channel.close();
                        connection.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        boolean durable = false;
        channel.queueDeclare(LoadConfiguration.getQueueName(), durable, false, false, null);

        while (true) {

            TimeUnit.SECONDS.sleep(SLEEP_IN_SECONDS);

            int randomSensorValue = ThreadLocalRandom.current().nextInt(MIN_SENSOR_VALUE, MAX_SENSOR_VALUE);

            IOTData iotData = new IOTData(UUIDs.timeBased(), iotDeviceType, "defaultunit", randomSensorValue, new Timestamp(System.currentTimeMillis()));
            String message = new Gson().toJson(iotData);
            channel.basicPublish("", LoadConfiguration.getQueueName(), null, message.getBytes("UTF-8"));
            System.out.println("Message Sent :: " + message);
        }
    }
}