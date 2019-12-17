package com.touraj.iotsender.domain;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by toraj on 07/21/2018.
 */
public class IOTData {

    private UUID id;

    private String deviceType;

    private String unitType;

    private double value;

    private Timestamp timeread;

    public IOTData() {
    }

    public IOTData(UUID id, String deviceType, String unitType, double value, Timestamp timeread) {
        this.id = id;
        this.deviceType = deviceType;
        this.unitType = unitType;
        this.value = value;
        this.timeread = timeread;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Timestamp getTimeread() {
        return timeread;
    }

    public void setTimeread(Timestamp timeread) {
        this.timeread = timeread;
    }
}
