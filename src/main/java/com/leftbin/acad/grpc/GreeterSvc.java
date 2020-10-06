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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;

@GRpcService
public class GreeterSvc extends GreeterGrpc.GreeterImplBase {
    Logger l = LoggerFactory.getLogger(GreeterSvc.class);
    @Override
    public void sayHello(GreeterOuterClass.HelloRequest request, StreamObserver<GreeterOuterClass.HelloReply> responseObserver) {
        super.sayHello(request, responseObserver);
    }

    @Override
    @Secured("SCOPE_profile")
    public void sayAuthHello(Empty request, StreamObserver<GreeterOuterClass.HelloReply> responseObserver) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            l.info("auth is null");
        } else if(auth instanceof JwtAuthenticationToken){
            Map<String, Object> tokenAttributes = JwtAuthenticationToken.class.cast(auth).getTokenAttributes();
            tokenAttributes.forEach((k,v)->l.info("key: {}, val: {}", k, v));
        }
        responseObserver.onNext(GreeterOuterClass.HelloReply.newBuilder().build());
        responseObserver.onCompleted();
    }
}
