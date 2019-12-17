package com.touraj.iotreceiver.repository;

import com.datastax.driver.core.Session;

/**
 * Created by toraj on 07/20/2018.
 */
public class KeyspaceRepository {

    private Session session;

    public KeyspaceRepository(Session session) {
        this.session = session;
    }

    public void createKeyspace(
            String keyspaceName, String replicationStrategy, int replicationFactor) {
        StringBuilder sb =
                new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
                        .append(keyspaceName).append(" WITH replication = {")
                        .append("'class':'").append(replicationStrategy)
                        .append("','replication_factor':").append(replicationFactor)
                        .append("};");

        String query = sb.toString();
        session.execute(query);
    }

    public void useKeyspace(String keyspace) {
        session.execute("USE " + keyspace);
    }

}
