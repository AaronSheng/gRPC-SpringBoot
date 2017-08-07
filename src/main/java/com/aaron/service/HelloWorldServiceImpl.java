package com.aaron.service;

import com.aaron.grpc.HelloRequest;
import com.aaron.grpc.HelloResponse;
import com.aaron.grpc.HelloWorldServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

/**
 * Created by Aaron Sheng on 2017/8/7.
 */
@Service
public class HelloWorldServiceImpl implements HelloWorldServiceGrpc.HelloWorldService {

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse helloResponse = HelloResponse.newBuilder().setRes("hello world!").build();
        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }
}
