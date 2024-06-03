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

public class PrimeClient {
    private SocketChannel socket;
    private ByteBuffer buffer;

    public PrimeClient(String address, int port) throws IOException {
        socket = SocketChannel.open(new InetSocketAddress(address, port));
        System.out.println("Chanell binded on ip " + address + " on port " + port);
        socket.configureBlocking(false);
        System.out.println("Chanell configured");
        buffer = ByteBuffer.allocate(256);
    }

    public void start() throws IOException {
        // ready to work notification to server
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
                            System.out.println("Unknown message type. Syncronize types with server");
                            break;
                    }
                } catch (ClassNotFoundException e) {
                    if (requestAttempts >= 3) {
                        System.out.println("Too many attempts to receive message. Aborting...");
                        return;
                    }
                    System.out.println("Error to cast received message from server to Message class.");
                    System.out.println("Requesting task.");
                    sendMessage(createMessage("REQUEST", null));
                    requestAttempts++;
                }
                buffer.clear();
            }
        }
    }

    private void handleTask(List<Integer> nums) throws IOException {
        for (Integer number : nums) {
            if (!isPrime(number)) {
                sendMessage(createMessage("RESULT", null));
                return;
            }
        }
        sendMessage(createMessage("RESULT", new ArrayList<>()));
    }

    private boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    private Message createMessage(String type, List<Integer> data) {
        Message message = new Message();
        message.setType(type);
        message.setData(data);
        return message;
    }

    private Message readMessageFromBuffer() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Message message = (Message) objectInputStream.readObject();
        return message;
    }

    private void sendMessage(Message message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        byte[] data = byteArrayOutputStream.toByteArray();
        ByteBuffer buffer = ByteBuffer.wrap(data);
        socket.write(buffer);
    }

    public static void main(String[] args) throws IOException {
        PrimeClient client = new PrimeClient("localhost", 5000);
        client.start();
    }
}
