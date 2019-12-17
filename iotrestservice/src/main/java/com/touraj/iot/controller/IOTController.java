package com.touraj.iot.controller;

import com.touraj.iot.domain.IOTData;
import com.touraj.iot.service.IOTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by toraj on 07/21/2018.
 */
@RestController
public class IOTController {

    @Autowired
    private IOTService iotService;

    @RequestMapping("/iotgetdata")
    public List<IOTData> iotGetData(@RequestParam(value="devicetype") String deviceType) {

        List<IOTData> iotDataList =  iotService.findByDevicetype(deviceType);

        return iotDataList;
    }

    @RequestMapping("/iotgetdata/max")
    public double iotGetDataMaxByDeviceType(@RequestParam(value="devicetype") String deviceType) {

        double maxValueForSpecificDevice =  iotService.getMaxByDevicetype(deviceType);
        return maxValueForSpecificDevice;
    }

    @RequestMapping("/iotgetdata/group/max")
    public double iotGetDataMaxByDeviceTypeGroup(@RequestParam(value="devicetypegroup") String devicetypegroup) {

        String[] devicetypes =  devicetypegroup.split(",");
        List<String> groupOfDevices = Arrays.asList(devicetypes);
        double maxValueForGroupOfDevices =  iotService.getMaxByDevicetypeGroup(groupOfDevices);
        return maxValueForGroupOfDevices;
    }

    @RequestMapping("/iotgetdata/timerange")
    public List<IOTData> iotGetDataTimeRange(@RequestParam(value="devicetype") String deviceType
            , @RequestParam(value="from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date from
            , @RequestParam(value="to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date to) {

        List<IOTData> iotDataList =  iotService.findByDevicetypeTimeRange(deviceType, from, to);
        return iotDataList;
    }

    @RequestMapping("/iotgetreadingsv2/{func}")
    public String iotGetReadingsv2(@RequestParam(value="devicetype", required = true) String deviceType
            , @PathVariable(value="func", required = true) String func
            , HttpServletResponse httpResponse
    ) {

        try {
        switch (func) {
            case "max": return String.valueOf(iotService.getMaxByDevicetype(deviceType));
            case "min": return String.valueOf(iotService.getMinByDevicetype(deviceType));
            case "avg": return String.valueOf(iotService.getAvgByDevicetype(deviceType));
            default: httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "Incorrect Function (Min/ Max/ Avg are Acceptable)";
        }

        } catch (Throwable t)
        {
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Critical Server Error! Contact Admin";
        }
    }

    @RequestMapping("/iotgetreadings/{func}")
    public String iotGetReadings(@RequestParam(value="devicetypes", required = true) String deviceTypes
            , @RequestParam(value="from", defaultValue = "1900-01-01", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date from
            , @RequestParam(value="to", defaultValue = "2200-01-01", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date to
            , @PathVariable(value="func", required = true) String func
            , HttpServletResponse httpResponse
    ) {

        try {
                String[] devicetypes =  deviceTypes.split(",");
                List<String> groupOfDevices = Arrays.asList(devicetypes);

            switch (func) {
                case "max": return String.valueOf(iotService.getByMaxForGroupDeviceAndTimeFrame(func, groupOfDevices, from, to));
                case "min": return String.valueOf(iotService.getByMinForGroupDeviceAndTimeFrame(func, groupOfDevices, from, to));
                case "avg": return String.valueOf(iotService.getByAvgForGroupDeviceAndTimeFrame(func, groupOfDevices, from, to));
                default: httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return "Incorrect Function (min/ max/ avg are Acceptable!)";
            }
        } catch (Throwable t)
        {
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Critical Server Error! Contact Admin";
        }
    }
}