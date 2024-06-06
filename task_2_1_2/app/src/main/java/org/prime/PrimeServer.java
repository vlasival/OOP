package org.prime;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * PrimeServer класс, который реализует сервер для распределенной проверки чисел на простоту.
 */
public class PrimeServer {
    private Selector selector;
    private ServerSocketChannel serverChannel;
    private Map<SocketChannel, ByteBuffer> clientBuffers = new HashMap<>();
    private Map<SocketChannel, Message> executingMessages = new HashMap<>();
    private Deque<Message> messageBlocks;
    private boolean working = true;
    private boolean result;

    /**
     * PrimeServer constructor.
     *
     * @param address server address
     * @param port server port
     * @param dataFile file contains numbers
     * @param resourcesClass class to find resources folder
     */
    public PrimeServer(String address, int port, String dataFile, Class<?> resourcesClass) {
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(address, port));
            serverChannel.configureBlocking(false);
            serverChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Channel bound and configured on IP \'" + address 
                                                    + "\' and port " + port);
            messageBlocks = new LinkedList<>(PrimeUtils.generateMessage(dataFile, resourcesClass));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to bind socket");
        }
    }

    /**
     * Start of server.
     *
     * @throws IOException if an I/O error occurs
     */
    public synchronized boolean start() throws IOException {
        while (working) {
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();
                if (key.isAcceptable()) {
                    acceptClient(key);
                } else if (key.isReadable()) {
                    readClient(key);
                }
            }
            checkConnectionAndRefreshTasks();
        }
        return result;
    }

    /**
     * Client accepting.
     *
     * @param key SelectionKey for accepting client
     * @throws IOException if an I/O error occurs
     */
    private void acceptClient(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        clientBuffers.put(clientChannel, ByteBuffer.allocate(PrimeUtils.BUFFER_SIZE));
        System.out.println("Client accepted: " + clientChannel.getRemoteAddress());
    }

    /**
     * Deleting client method.
     *
     * @param clientChannel client channel (socket)
     * @throws IOException if an I/O error occurs
     */
    private void removeClient(SocketChannel clientChannel) throws IOException {
        clientChannel.close();
        clientBuffers.remove(clientChannel);
        executingMessages.remove(clientChannel);
    }

    /**
     * Send to client signal to end working.
     *
     * @param client client who sent
     * @throws IOException if an I/O error occurs
     */
    private void freeWorker(SocketChannel client) throws IOException {
        Message exitMessage = new Message();
        exitMessage.setType("EXIT");
        sendMessage(client, exitMessage);
        removeClient(client);
    }

    /**
     * Read data from client.
     *
     * @param key SelectionKey for reading client data
     * @throws IOException if an I/O error occurs
     */
    private void readClient(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = clientBuffers.get(clientChannel);
        int bytesRead = clientChannel.read(buffer);

        if (bytesRead == -1) {
            removeClient(clientChannel);
            return;
        }

        buffer.flip();
        try {
            Message message = receiveMessageFromBuffer(buffer);
            handleClientMessage(clientChannel, message);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        buffer.clear();
    }

    /**
     * Extract message from buffer.
     *
     * @param clientBuffer client's buffer
     * @return extracted message
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if buffer data cannot be casted to message
     */
    private Message receiveMessageFromBuffer(ByteBuffer clientBuffer) throws IOException, 
                                                                ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(clientBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Message) objectInputStream.readObject();
    }

    /**
     * Sending data to client.
     *
     * @param clientSocket client channel (socket)
     * @param message message to send
     * @throws IOException if an I/O error occurs
     */
    private void sendMessage(SocketChannel clientSocket, Message message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        byte[] data = byteArrayOutputStream.toByteArray();
        ByteBuffer buffer = ByteBuffer.wrap(data);
        clientSocket.write(buffer);
    }

    /**
     * Task request from client.
     *
     * @param clientChannel client channel (socket)
     * @throws IOException if an I/O error occurs
     */
    private void requestTask(SocketChannel clientChannel) throws IOException {
        Message task;
        if (!messageBlocks.isEmpty()) {
            task = messageBlocks.pollFirst();
            executingMessages.put(clientChannel, task);
            sendMessage(clientChannel, task);
        } else {
            if (!executingMessages.isEmpty()) {
                // freeWorker(clientChannel);
                return;
            } else {
                finishWorking(true);
            }
        }
    }

    /**
     * Checks conditions to finish a work.
     *
     * @throws IOException if an I/O error occurs
     */
    private void checkConnectionAndRefreshTasks() throws IOException {
        for (var message : executingMessages.entrySet()) {
            if (!message.getKey().isConnected()) {
                messageBlocks.add(message.getValue());
            }
        }
        if (messageBlocks.isEmpty()) {
            finishWorking(true);
        }
    }

    /**
     * Stoping server's work.
     *
     * @param result result of checking, true if all numbers are prime
     * @throws IOException if an I/O error occurs
     */
    public synchronized void finishWorking(boolean result) throws IOException {
        for (var client : new ArrayList<>(clientBuffers.keySet())) {
            if (client.isOpen()) {
                freeWorker(client);
            }
        }
        working = false;
        this.result = result;

        if (selector.isOpen()) {
            selector.close();
        }
        if (serverChannel.isOpen()) {
            serverChannel.close();
        }
    }

    /**
     * Client's message processing.
     *
     * @param clientChannel client channel (socket)
     * @param message received message
     * @throws IOException if an I/O error occurs
     */
    private void handleClientMessage(SocketChannel clientChannel, Message message) 
                                                                throws IOException {
        String messageType = message.getType();
        switch (messageType) {
            case "REQUEST":
                requestTask(clientChannel);
                break;
            case "RESULT":
                List<Integer> result = message.getData();
                if (result == null) {
                    finishWorking(false);
                } else {
                    executingMessages.remove(clientChannel);
                    requestTask(clientChannel);
                }
                break;
            default:
                System.out.println("Undefined message type. Synchronize types with server.");
                break;
        }
    }

    /**
     * Entry point to start a server.
     *
     * @param args command line arguments
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        PrimeServer server = new PrimeServer("localhost", 5000, "numbers.txt", PrimeUtils.class);
        boolean res = server.start();
        System.out.println(res);
    }
}
