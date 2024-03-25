package org.example.client;

import com.example.grpc.RpcRequest;
import com.example.grpc.ServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumbersClient {

    private static final Logger logger = LoggerFactory.getLogger(NumbersClient.class);

    private static final String SERVER_HOST = "localhost";

    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();
        logger.info("numbers Client is starting...");

        ServiceGrpc.ServiceStub stub = ServiceGrpc.newStub(channel);

        int firstValue = 0;
        int lastValue = 30;

        RpcRequest request = RpcRequest
                .newBuilder()
                .setFirstValue(firstValue)
                .setLastValue(lastValue)
                .build();

        int currentValue = 0;

        ClientStreamObserver clientService = new ClientStreamObserver();

        stub.greeting(request, clientService);

        for (int i = 0; i < 50; i++) {
            currentValue = currentValue + clientService.getCurrentValue() + 1;
            logger.info("currentValue: {}", currentValue);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.info(e.getMessage(), e);
            }
        }

        channel.shutdownNow();
        logger.info("request completed");
    }
}
