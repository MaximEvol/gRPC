package org.example.Server;

import com.example.grpc.RpcRequest;
import com.example.grpc.RpcResponse;
import com.example.grpc.ServiceGrpc;
import io.grpc.stub.StreamObserver;

public class ServiceImpl extends ServiceGrpc.ServiceImplBase {

    @Override
    public void greeting(RpcRequest request,
                         StreamObserver<RpcResponse> responseObserver) {

        int firstValue = request.getFirstValue();
        int lastValue = request.getLastValue();

        for (int val = firstValue + 2; val < lastValue; val++) {

            responseObserver.onNext(RpcResponse.newBuilder().setCurrentValue(val).build());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }

        responseObserver.onCompleted();
    }
}
