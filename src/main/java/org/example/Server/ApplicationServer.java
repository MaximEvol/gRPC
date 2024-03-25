package org.example.Server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ApplicationServer {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServer.class);

    public static final int SERVER_PORT = 8080;

    public static void main( String[] args ) throws IOException, InterruptedException {

        Server server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(new ServiceImpl())
                .build();

        server.start();
        logger.info("Server started...");
        server.awaitTermination();
    }
}
