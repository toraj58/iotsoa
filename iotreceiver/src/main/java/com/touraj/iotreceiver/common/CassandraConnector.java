package com.touraj.iotreceiver.common;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Created by toraj on 07/20/2018.
 */
public class CassandraConnector {

    private Cluster cluster;
    private Session session;

    public void connect(String node, Integer port) {
        Cluster.Builder builder = Cluster.builder().addContactPoint(node);
        if (port != null) {
            builder.withPort(port);
        }
        cluster = builder.build();
        session = cluster.connect();
    }

    public Session getSession() {
        return this.session;
    }

    public void close() {
        session.close();
        cluster.close();
    }
}