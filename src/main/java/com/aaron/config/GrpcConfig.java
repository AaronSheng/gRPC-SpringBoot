package com.aaron.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Aaron Sheng on 7/31/17.
 */
@Configuration
public class GrpcConfig {
    @Value("${grpc.server.port}")
    private Integer port;

    @Bean
    public Integer grpcPort() {
        return port;
    }
}
