package com.touraj.iot.service;

import com.touraj.iot.domain.IOTData;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by toraj on 07/21/2018.
 */
public interface IOTService {

    List<IOTData> findByDevicetype(String deviceType);

    double getMaxByDevicetype(String deviceType);

    double getMinByDevicetype(String deviceType);

    double getAvgByDevicetype(String deviceType);

    double getByMaxForGroupDeviceAndTimeFrame(
            String func
            , List<String> deviceTypeGroup
            , Date from
            , Date to
    );

    double getByMinForGroupDeviceAndTimeFrame(
            String func
            , List<String> deviceTypeGroup
            , Date from
            , Date to
    );

    double getByAvgForGroupDeviceAndTimeFrame(
            String func
            , List<String> deviceTypeGroup
            , Date from
            , Date to
    );

    double getMaxByDevicetypeGroup(List<String> deviceTypeGroup);

    List<IOTData> findByDevicetypeTimeRange(String devicetype, Date from, Date to);
}

