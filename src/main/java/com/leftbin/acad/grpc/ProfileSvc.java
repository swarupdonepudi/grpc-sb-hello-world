package com.leftbin.acad.grpc;

import com.cfc.lib.protos.user.IdentityProfile;
import com.cfc.lib.protos.user.UserProfileSvcGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.security.access.annotation.Secured;

@GRpcService
@Secured("SCOPE_profile")
public class ProfileSvc extends UserProfileSvcGrpc.UserProfileSvcImplBase {

    @Override
    public void authenticate(Empty request, StreamObserver<IdentityProfile> responseObserver) {
        responseObserver.onNext(IdentityProfile.newBuilder().build());
        responseObserver.onCompleted();
    }
}
