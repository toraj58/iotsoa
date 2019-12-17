package com.touraj.iotreceiver.main;

import com.touraj.iotreceiver.common.AMQPConsumer;
import com.touraj.iotreceiver.common.LoadConfiguration;

/**
 * Created by toraj on 07/20/2018.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Starting IOT Receiver...");

        try {
            new LoadConfiguration();

            AMQPConsumer amqpConsumer = new AMQPConsumer();
            amqpConsumer.startConsumer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
