package org.prime;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrimeServerClient {
    PrimeServer server;
    PrimeClient client;
    Thread serverThread;
    Thread clientThread;

    @BeforeEach
    void setUp() {
        server = new PrimeServer("localhost", 5000);
        client = new PrimeClient("localhost", 5000);
        serverThread = new Thread(() -> {
            try {
                server.start();
            } catch (IOException e) {
                System.out.println("Error occured in starting server");
            }
        });
        clientThread = new Thread(() -> {
            try {
                client.start();
            } catch (IOException e) {
                System.out.println("Error occured in starting client");
            }
        });
    }

    @Test
    void mainCheckTest() {
        serverThread.start();
        clientThread.start();
        
    }
}
