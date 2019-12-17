package com.touraj.iot.config;

import com.touraj.iot.service.IOTService;
import com.touraj.iot.service.IOTServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by toraj on 07/21/2018.
 */

@Configuration
public class Config {

    @Bean
    public IOTService iotService()
    {
        return new IOTServiceImpl();
    }

}
