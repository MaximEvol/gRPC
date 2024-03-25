package org.example.client;

import com.example.grpc.RpcResponse;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientStreamObserver implements StreamObserver<RpcResponse> {

    private static final Logger logger = LoggerFactory.getLogger(ClientStreamObserver.class);

    private int currentValue = 0;

    @Override
    public void onNext(RpcResponse rpcResponse) {
        setCurrentValue(rpcResponse.getCurrentValue());
        logger.info("new value: {}", currentValue);
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
    }

    @Override
    public void onCompleted() {
        logger.info("request completed");
    }

    public synchronized void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public synchronized int getCurrentValue() {
        int result = currentValue;
        currentValue = 0;
        return result;
    }
}
