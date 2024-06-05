package org.prime;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class PrimeServerTest {

    private static PrimeServer server;
    private static ExecutorService serverExecutor;

    @BeforeAll
    static void setup() throws IOException {
        serverExecutor = Executors.newSingleThreadExecutor();
        server = new PrimeServer("localhost", 5000);
        serverExecutor.submit(() -> {
            try {
                server.start();
            } catch (IOException e) {
                fail("Server failed to start.");
            }
        });
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.finishWorking(true);
        serverExecutor.shutdown();
        try {
            if (!serverExecutor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                serverExecutor.shutdownNow();
            } 
        } catch (InterruptedException e) {
            serverExecutor.shutdownNow();
        }
    }

    @Test
    void testServerInitialization() {
        assertNotNull(server, "Server should be initialized.");
    }

    @Test
    void testServerClientCommunication() throws IOException {
        ExecutorService clientExecutor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            clientExecutor.submit(() -> {
                try {
                    PrimeClient client = new PrimeClient("localhost", 5000);
                    client.start();
                } catch (IOException e) {
                    fail("Client failed to start.");
                }
            });
        }
        clientExecutor.shutdown();
        try {
            if (!clientExecutor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                clientExecutor.shutdownNow();
            } 
        } catch (InterruptedException e) {
            clientExecutor.shutdownNow();
        }
    }
}
