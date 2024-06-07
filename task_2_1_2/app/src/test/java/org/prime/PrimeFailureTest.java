package org.prime;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Test;

/**
 * Class tests what happened if one of client was lost.
 */
public class PrimeFailureTest {
    public static boolean result;

    /**
     * Interrupts one of thread and checks if everything going ok.
     *
     * @throws InterruptedException if I/O errors occured
     */
    @Test
    void unexpectedClientInterruptTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(4);

        PrimeServer server = new PrimeServer("localhost", 55439, "t_test.txt", PrimeTest.class);
        List<PrimeClient> clients = new ArrayList<>();

        Thread serverThread = new Thread(() -> {
            try {
                PrimeFailureTest.result = server.start();
                latch.countDown();
            } catch (IOException e) {
                System.err.println("Couldn't start a server");
            }
        });
        serverThread.start();

        List<Thread> clientsThreads = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            PrimeClient client = new PrimeClient("localhost", 55439);
            Thread clientThread = new Thread(() -> {
                try {
                    client.start();
                    latch.countDown();
                } catch (IOException e) {
                    System.err.println("Couldn't start a client");
                }
            });
            clients.add(client);
            clientsThreads.add(clientThread);
            clientThread.start();
            if (i == 2) {
                clientThread.interrupt();
                latch.countDown();
            }
        }
        
        for (var client : clientsThreads) {
            try {
                client.join();
            } catch (InterruptedException e) {
                System.err.println("Everything is going on plan");
            }
        }
        serverThread.join();

        latch.await();
        assertTrue(result);
    }
}
