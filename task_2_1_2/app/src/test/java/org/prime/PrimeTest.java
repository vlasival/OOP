package org.prime;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class of main cross-net functionality.
 */
public class PrimeTest {
    private static final int SERVER_PORT = 54548;
    private static final String SERVER_HOST = "localhost";
    private static final String TEST_DATA_FILE = "t_test.txt";
    public static boolean result;

    private PrimeServer server;
    private Thread serverThread;
    private List<PrimeClient> clients;
    private List<Thread> clientThreads;
    private CountDownLatch latch;

    /**
     * Setup method. Preparing.
     */
    @BeforeEach
    void setUp() {
        latch = new CountDownLatch(4);
        server = new PrimeServer(SERVER_HOST, SERVER_PORT, TEST_DATA_FILE, PrimeTest.class);
        clients = new ArrayList<>();
        clientThreads = new ArrayList<>();
    }

    /**
     * Postparing method.
     */
    @AfterEach
    void tearDown() throws InterruptedException, IOException {
        if (server != null) {
            server.finishWorking(true);
        }
        if (serverThread != null && serverThread.isAlive()) {
            serverThread.interrupt();
            serverThread.join();
        }
        for (Thread clientThread : clientThreads) {
            if (clientThread != null && clientThread.isAlive()) {
                clientThread.interrupt();
                clientThread.join();
            }
        }
    }

    /**
     * Main method. Creates server and three clients and checks them work.
     *
     * @throws InterruptedException if I/O errors occured
     */
    @Test
    void mainFunctionalityTest() throws InterruptedException {
        serverThread = new Thread(() -> {
            try {
                PrimeTest.result = server.start();
            } catch (IOException e) {
                System.err.println("Couldn't start a server");
            }
            latch.countDown();
        });
        serverThread.start();

        for (int i = 0; i < 3; i++) {
            PrimeClient client = new PrimeClient(SERVER_HOST, SERVER_PORT);
            Thread clientThread = new Thread(() -> {
                try {
                    client.start();
                } catch (IOException e) {
                    System.err.println("Couldn't start a client");
                }
                latch.countDown();
            });
            clients.add(client);
            clientThreads.add(clientThread);
            clientThread.start();
        }

        // Добавить таймаут
        if (!latch.await(3, TimeUnit.SECONDS)) {
            System.err.println("Test timed out waiting for latch");
        }
        assertTrue(result);
    }
}
