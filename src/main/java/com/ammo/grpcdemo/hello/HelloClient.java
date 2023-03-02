package com.ammo.grpcdemo.hello;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class HelloClient {
    private final ManagedChannel channel;
    private final com.ammo.grpcdemo.hello.HelloGrpc.HelloBlockingStub blockingStub;

    public HelloClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        blockingStub = com.ammo.grpcdemo.hello.HelloGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String sayHello(String name) {
        com.ammo.grpcdemo.hello.HelloRequest request = com.ammo.grpcdemo.hello.HelloRequest.newBuilder().setName(name).build();
        com.ammo.grpcdemo.hello.HelloResponse response = blockingStub.sayHello(request);
        return response.getMessage();
    }

    public static void main(String[] args) throws InterruptedException {
        HelloClient client = new HelloClient("localhost", 50051);
        String content = client.sayHello("java client");
        System.out.println(content);
        client.shutdown();
    }
}
