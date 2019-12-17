package com.touraj.iotreceiver.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by toraj on 07/22/2018.
 */
public class LoadConfiguration {

    private static String queueName;
    private static String cassandraKeySpace;
    private static String cassandraIP;
    private static String rabbitmqIP;
    private static String cassandraPort;

    private static final String configFileName = "config.properties";

    public LoadConfiguration() {
        readAndLoadConfigs();
    }

    private void readAndLoadConfigs()
    {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = getClass().getClassLoader().getResourceAsStream(configFileName);

            prop.load(input);

            setQueueName(prop.getProperty("queuename"));
            setCassandraKeySpace(prop.getProperty("cassandrakeyspace"));
            setCassandraIP(prop.getProperty("cassandraip"));
            setRabbitmqIP(prop.getProperty("rabbitmqip"));
            setCassandraPort(prop.getProperty("cassandraport"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getQueueName() {
        return queueName;
    }

    public static void setQueueName(String queueName) {
        LoadConfiguration.queueName = queueName;
    }

    public static String getCassandraKeySpace() {
        return cassandraKeySpace;
    }

    public static void setCassandraKeySpace(String cassandraKeySpace) {
        LoadConfiguration.cassandraKeySpace = cassandraKeySpace;
    }

    public static String getCassandraIP() {
        return cassandraIP;
    }

    public static void setCassandraIP(String cassandraIP) {
        LoadConfiguration.cassandraIP = cassandraIP;
    }

    public static String getRabbitmqIP() {
        return rabbitmqIP;
    }

    public static void setRabbitmqIP(String rabbitmqIP) {
        LoadConfiguration.rabbitmqIP = rabbitmqIP;
    }

    public static String getCassandraPort() {
        return cassandraPort;
    }

    public static void setCassandraPort(String cassandraPort) {
        LoadConfiguration.cassandraPort = cassandraPort;
    }

    public static String getConfigFileName() {
        return configFileName;
    }
}
