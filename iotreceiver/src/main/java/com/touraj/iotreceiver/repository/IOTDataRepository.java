package com.touraj.iotreceiver.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.touraj.iotreceiver.Domain.IOTData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toraj on 07/20/2018.
 */
public class IOTDataRepository {

    private static final String TABLE_NAME = "iotdata";

    private Session session;

    public IOTDataRepository(Session session) {
        this.session = session;
    }

    public void createTable() {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("id uuid, ")
                .append("devicetype text,")
                .append("unittype text,")
                .append("value double,")
                .append("timeread timestamp,")
                .append("PRIMARY KEY ((devicetype),id)")
                .append(");");

        final String query = sb.toString();
        session.execute(query);
    }

    public void insertIOTData(IOTData iotData) {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME)
                .append("(id, devicetype, unittype, value, timeread) ")
                .append("VALUES (")
                .append(iotData.getId())
                .append(", '")
                .append(iotData.getDeviceType())
                .append("', '")
                .append(iotData.getUnitType())
                .append("', ")
                .append(iotData.getValue())
                .append(", '")
                .append(iotData.getTimeread())
                .append("');");

        final String query = sb.toString();
        session.execute(query);
    }

    // Touraj :: I have Written this Method Only for Test Purpose
    public IOTData selectIOTDataByDeviceType(String devicetype) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME).append(" WHERE devicetype = '").append(devicetype).append("';");

        final String query = sb.toString();
        ResultSet rs = session.execute(query);
        List<IOTData> iotDatas = new ArrayList<IOTData>();

        for (Row r : rs) {
            IOTData s = new IOTData(r.getUUID("id"), r.getString("devicetype"), r.getString("unittype"), r.getDouble("value"), null);
            iotDatas.add(s);
        }
        return iotDatas.get(0);
    }
}