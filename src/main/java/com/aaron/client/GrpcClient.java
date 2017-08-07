package com.aaron.client;

import com.aaron.grpc.HelloRequest;
import com.aaron.grpc.HelloResponse;
import com.aaron.grpc.HelloWorldServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;


/**
 * A simple client that requests a greeting from the {@link com.aaron.grpc.GrpcServer}.
 */
public class GrpcClient {
    private final ManagedChannel channel;
    private final HelloWorldServiceGrpc.HelloWorldServiceBlockingStub helloWorldServiceBlockingStub;

    public GrpcClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build();
        helloWorldServiceBlockingStub = HelloWorldServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }


    public void hello(String req) {
        try {
            HelloRequest request = HelloRequest.newBuilder()
                    .setReq(req)
                    .build();
            HelloResponse response = helloWorldServiceBlockingStub.hello(request);
            System.out.printf("Thread %-4s %-20s -> success: %s, res: %s\n", Thread.currentThread().getId(), "hello",
                    response.getRes());
        } catch (StatusRuntimeException e) {
            System.err.printf("hello failed: %s\n", e.getStatus());
        }
    }
}