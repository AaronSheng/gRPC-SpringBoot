package com.aaron.grpc;

import com.aaron.service.HelloWorldServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by Aaron Sheng on 7/31/17.
 */
@Component
public class GrpcServer {
    private static final Logger logger = LogManager.getLogger(GrpcServer.class);

    @Resource(name = "grpcPort")
    private Integer grpcPort;
    @Autowired
    private HelloWorldServiceImpl helloWorldService;

    private Server server;

    @PostConstruct
    public void startServer() {
        try {
            start();
        } catch (Exception e) {
            logger.error("GrpcServer startServer fail!");
        }
    }

    public void start() throws IOException, InterruptedException {
        server = ServerBuilder.forPort(grpcPort)
                .addService(HelloWorldServiceGrpc.bindService(helloWorldService))
                .build()
                .start();
        logger.info("Server start listening on " + grpcPort);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                GrpcServer.this.stop();
                GrpcServer.logger.info("Server shutdown");
            }
        });

        server.awaitTermination();
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }
}
