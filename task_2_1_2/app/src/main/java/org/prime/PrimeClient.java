package org.prime;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * PrimeClient class that implements a client for distributed checking of numbers for primality.
 */
public class PrimeClient {

    private SocketChannel socket;
    private ByteBuffer buffer;

    /**
     * PrimeClient constructor.
     *
     * @param address server address
     * @param port    server port
     */
    public PrimeClient(String address, int port) {
        try {
            socket = SocketChannel.open(new InetSocketAddress(address, port));
            socket.configureBlocking(false);
            buffer = ByteBuffer.allocate(PrimeUtils.BUFFER_SIZE);
            System.out.println("Channel bound and configured on IP \'" + address 
                                                        + "\' and port " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to bind socket");
        }
    }

    /**
     * Client launch.
     *
     * @throws IOException if an I/O error occurs
     */
    public void start() throws IOException {
        sendMessage(createMessage("REQUEST", null));
        int requestAttempts = 0;

        while (true) {
            buffer.clear();
            int bytesRead = socket.read(buffer);
            if (bytesRead == -1) {
                System.out.println("Server is down. Shutting down...");
                return;
            }
            if (bytesRead > 0) {
                buffer.flip();
                try {
                    Message message = readMessageFromBuffer();
                    String messageType = message.getType();
                    switch (messageType) {
                        case "TASK":
                            handleTask(message.getData());
                            requestAttempts = 0;
                            break;
                        case "EXIT":
                            System.out.println("Tasks done. Exiting program...");
                            return;
                        default:
                            System.out.println("Unknown message type. Synchronize types.");
                            break;
                    }
                } catch (ClassNotFoundException e) {
                    if (requestAttempts >= 3) {
                        System.out.println("Too many attempts to receive message. Aborting...");
                        return;
                    }
                    System.out.println("Error casting received message to Message class.");
                    sendMessage(createMessage("REQUEST", null));
                    requestAttempts++;
                }
                buffer.clear();
            }

            // Sleep for a short duration to prevent busy-waiting
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Client thread interrupted. Exiting...");
                return;
            }
        }
    }

    /**
     * Creating a message to send in network.
     *
     * @param type message type
     * @param data message data
     * @return message
     */
    private Message createMessage(String type, List<Integer> data) {
        Message message = new Message();
        message.setType(type);
        message.setData(data);
        return message;
    }

    /**
     * Sending message to the server method.
     *
     * @param message message to send
     * @throws IOException if an I/O error occurs
     */
    private void sendMessage(Message message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        byte[] data = byteArrayOutputStream.toByteArray();
        ByteBuffer buffer = ByteBuffer.wrap(data);
        socket.write(buffer);
    }

    /**
     * Read a message from client buffer.
     *
     * @return readed message
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if class of buffer data don't cast to message class
     */
    private Message readMessageFromBuffer() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Message) objectInputStream.readObject();
    }

    /**
     * List primality checking.
     *
     * @param nums list of numbers to check
     * @throws IOException if an I/O error occurs
     */
    private void handleTask(List<Integer> nums) throws IOException {
        for (Integer number : nums) {
            if (!isPrime(number)) {
                sendMessage(createMessage("RESULT", null));
                return;
            }
        }
        sendMessage(createMessage("RESULT", new ArrayList<>()));
    }

    /**
     * Number primality checking.
     *
     * @param number number to check
     * @return true if prime, false otherwise
     */
    public boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Entry point to start a client.
     *
     * @param args command line args
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        PrimeClient client = new PrimeClient("localhost", 5000);
        client.start();
    }
}
