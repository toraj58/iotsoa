package com.touraj.iot.repository;

import com.touraj.iot.domain.IOTData;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by toraj on 07/21/2018.
 */

@Repository
public interface IOTRepository extends CrudRepository<IOTData, String> {

    @Query("select * from iotdata where devicetype = ?0")
    List<IOTData> findByDevicetype(String devicetype);

    @Query("select max(value) from iotdata where devicetype = ?0")
    double getMaxByDevicetype(String deviceType);

    @Query("select min(value) from iotdata where devicetype = ?0")
    double getMinByDevicetype(String deviceType);

    @Query("select avg(value) from iotdata where devicetype = ?0")
    double getAvgByDevicetype(String deviceType);

    @Query("select max(value) from iotdata where devicetype in :devicetypes and timeread >= :from and timeread <= :to ALLOW FILTERING")
    double getByMaxForGroupDeviceAndTimeFrame(
            @Param("func") String func
            , @Param("devicetypes") List<String> deviceTypeGroup
            , @Param("from") Date from
            , @Param("to") Date to
    );

    @Query("select min(value) from iotdata where devicetype in :devicetypes and timeread >= :from and timeread <= :to ALLOW FILTERING")
    double getByMinForGroupDeviceAndTimeFrame(
            @Param("func") String func
            , @Param("devicetypes") List<String> deviceTypeGroup
            , @Param("from") Date from
            , @Param("to") Date to
    );

    @Query("select avg(value) from iotdata where devicetype in :devicetypes and timeread >= :from and timeread <= :to ALLOW FILTERING")
    double getByAvgForGroupDeviceAndTimeFrame(
            @Param("func") String func
            , @Param("devicetypes") List<String> deviceTypeGroup
            , @Param("from") Date from
            , @Param("to") Date to
    );

    @Query("select max(value) from iotdata where devicetype in :devicetypes")
    double getMaxByDevicetypeGroup(@Param("devicetypes") List<String> deviceTypeGroup);

    @Query("select * from iotdata where devicetype = ?0 and timeread >= ?1 and timeread <= ?2 ALLOW FILTERING")
    List<IOTData> findByDevicetypeTimeRange(String devicetype, Date from, Date to);
}