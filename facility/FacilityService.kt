package io.sibur.disquad.services.facility

import Api.Sibur.Disquad.Disquad.FacilityPacket.GetRequest
import Api.Sibur.Disquad.Disquad.FacilityPacket.GetResponse
import Api.Sibur.Disquad.FacilityServiceGrpc
import io.grpc.stub.StreamObserver
import io.sibur.disquad.services.facility.actions.GetAction
import org.lognet.springboot.grpc.GRpcService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@GRpcService
class FacilityService : FacilityServiceGrpc.FacilityServiceImplBase() {
    @Autowired
    lateinit var getAction: GetAction

    override fun get(request: GetRequest, responseObserver: StreamObserver<GetResponse>) {
        responseObserver.onNext(getAction.execute(request))
        responseObserver.onCompleted()
    }
}