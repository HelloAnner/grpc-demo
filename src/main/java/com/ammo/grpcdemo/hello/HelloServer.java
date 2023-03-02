package com.ammo.grpcdemo.hello;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class HelloServer {

    private final static int PORT = 50051;

    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(PORT).addService(new HelloIml()).build().start();
        System.out.println("servce start ...");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            HelloServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        final HelloServer helloServer = new HelloServer();
        helloServer.start();
        helloServer.blockUntilShutdown();
    }

    private static class HelloIml extends com.ammo.grpcdemo.hello.HelloGrpc.HelloImplBase {

        @Override
        public void sayHello(com.ammo.grpcdemo.hello.HelloRequest request, StreamObserver<com.ammo.grpcdemo.hello.HelloResponse> responseObserver) {
            System.out.println("service:" + request.getName());
            responseObserver.onNext(com.ammo.grpcdemo.hello.HelloResponse.newBuilder().setMessage("Hello: " + request.getName()).build());
            responseObserver.onCompleted();
        }
    }
}
