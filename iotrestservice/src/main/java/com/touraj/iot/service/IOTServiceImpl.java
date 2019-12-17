package com.touraj.iot.service;

import com.touraj.iot.domain.IOTData;
import com.touraj.iot.repository.IOTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by toraj on 07/21/2018.
 */

@Service
public class IOTServiceImpl implements IOTService {

    @Autowired
    IOTRepository iotRepository;

    @Override
    public List<IOTData> findByDevicetype(String deviceType) {
        return iotRepository.findByDevicetype(deviceType);
    }

    @Override
    public double getMaxByDevicetype(String deviceType) {
        return iotRepository.getMaxByDevicetype(deviceType);
    }

    @Override
    public double getMinByDevicetype(String deviceType) {
        return iotRepository.getMinByDevicetype(deviceType);
    }

    @Override
    public double getMaxByDevicetypeGroup(List<String> deviceTypeGroup) {
        return iotRepository.getMaxByDevicetypeGroup(deviceTypeGroup);
    }

    @Override
    public double getAvgByDevicetype(String deviceType) {
        return iotRepository.getAvgByDevicetype(deviceType);
    }

    @Override
    public double getByMaxForGroupDeviceAndTimeFrame(String func, List<String> deviceTypeGroup, Date from, Date to) {
        return iotRepository.getByMaxForGroupDeviceAndTimeFrame(func, deviceTypeGroup, from, to);
    }

    @Override
    public double getByMinForGroupDeviceAndTimeFrame(String func, List<String> deviceTypeGroup, Date from, Date to) {
        return iotRepository.getByMinForGroupDeviceAndTimeFrame(func, deviceTypeGroup, from, to);
    }

    @Override
    public double getByAvgForGroupDeviceAndTimeFrame(String func, List<String> deviceTypeGroup, Date from, Date to) {
        return iotRepository.getByAvgForGroupDeviceAndTimeFrame(func, deviceTypeGroup, from, to);
    }

    @Override
    public List<IOTData> findByDevicetypeTimeRange(String devicetype, Date from, Date to) {
        return iotRepository.findByDevicetypeTimeRange(devicetype, from, to);
    }
}
