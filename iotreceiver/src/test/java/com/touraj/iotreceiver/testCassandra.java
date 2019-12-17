package com.touraj.iotreceiver;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.touraj.iotreceiver.Domain.IOTData;
import com.touraj.iotreceiver.common.CassandraConnector;
import com.touraj.iotreceiver.repository.IOTDataRepository;
import com.touraj.iotreceiver.repository.KeyspaceRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by toraj on 07/20/2018.
 */
public class testCassandra {

    private KeyspaceRepository schemaRepository;
    private IOTDataRepository iotDataRepository;
    private Session session;
    private CassandraConnector client;
    final String KEYSPACE_NAME = "iottest";

    @Before
    public void connect() {
        client = new CassandraConnector();
        client.connect("localhost", 9042);
        this.session = client.getSession();
        schemaRepository = new KeyspaceRepository(session);
        schemaRepository.createKeyspace(KEYSPACE_NAME, "SimpleStrategy", 1);
        schemaRepository.useKeyspace(KEYSPACE_NAME);
        iotDataRepository = new IOTDataRepository(session);
    }

    @Test
    public void whenCreatingAKeyspace_thenCreated() {
        String keyspace = "testkeyspace";
        schemaRepository.createKeyspace(keyspace, "SimpleStrategy", 1);

        ResultSet result =
                session.execute("SELECT * FROM system_schema.keyspaces;");

        List<String> filteredKeyspaces = result.all()
                .stream()
                .filter(r -> r.getString(0).equals(keyspace.toLowerCase()))
                .map(r -> r.getString(0))
                .collect(Collectors.toList());

        assertEquals(filteredKeyspaces.size(), 1);
        assertTrue(filteredKeyspaces.get(0).equals(keyspace.toLowerCase()));
    }

    @Test
    public void whenAddingIOTData_thenIOTDATAExists() {
        iotDataRepository.createTable();

        IOTData iotData = new IOTData(UUIDs.timeBased(), "testdevice", "testunit", 4000, new Timestamp(System.currentTimeMillis()));
        iotDataRepository.insertIOTData(iotData);

        IOTData savedIOTData = iotDataRepository.selectIOTDataByDeviceType("testdevice");
        assertEquals(iotData.getDeviceType(), savedIOTData.getDeviceType());
    }

    @After
    public  void cleanup() {
        System.out.println("Cleaning Up!");
        client.close();
    }
}
