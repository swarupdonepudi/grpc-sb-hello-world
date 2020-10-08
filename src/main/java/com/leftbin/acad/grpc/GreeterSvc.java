package com.leftbin.acad.grpc;

import com.google.protobuf.Empty;
import io.grpc.examples.GreeterGrpc;
import io.grpc.examples.GreeterOuterClass;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@GRpcService
public class GreeterSvc extends GreeterGrpc.GreeterImplBase {
    Logger l = LoggerFactory.getLogger(GreeterSvc.class);

    @Override
    public void sayHello(GreeterOuterClass.HelloRequest request, StreamObserver<GreeterOuterClass.HelloReply> responseObserver) {
        responseObserver.onNext(GreeterOuterClass.HelloReply.newBuilder().build());
        responseObserver.onCompleted();
    }
    //topics = "#{'${app.kafka.order.sink.topic}'}",

    @Override
    @Secured({"SCOPE_profiler:read"})
    public void sayAuthHello(Empty request, StreamObserver<GreeterOuterClass.HelloReply> responseObserver) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            l.info("auth is null");
        } else {
            l.info("swarup: authoritites: {}, name: {}, creds: {}", auth.getAuthorities(),auth.getName(), auth.getCredentials().toString());
        }
        responseObserver.onNext(GreeterOuterClass.HelloReply.newBuilder().build());
        responseObserver.onCompleted();
    }
}
