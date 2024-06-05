package org.prime;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PrimeServer {
    public static final String dataFile = "numbers.txt";

    private Selector selector;
    private ServerSocketChannel serverChannel;
    private Map<SocketChannel, ByteBuffer> clientBuffers = new HashMap<>();
    private Map<SocketChannel, Message> executingMessages = new HashMap<>();
    private List<Message> messageBlocks;
    private boolean working = true;

    public PrimeServer(String address, int port) {
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(address, port));
            System.out.println("Channel binded on ip " + address + " on port " + port);
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Channel registered");

            messageBlocks = PrimeUtils.generateMessage(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\n\nFailed to bind socket");
        }
        
    }

    public void start() throws IOException {
        while (working) {
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();
                if (key.isAcceptable()) {
                    System.out.println("Accepting client");
                    SocketChannel client = acceptClient(key);
                    System.out.println("Client " 
                        + client.getLocalAddress().toString() + " accepted");
                } else if (key.isReadable()) {
                    readClient(key);
                }
            }
        }
    }

    private SocketChannel acceptClient(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        clientBuffers.put(clientChannel, ByteBuffer.allocate(PrimeUtils.BUFFER_SIZE));
        return clientChannel;
    }

    private void removeClient(SocketChannel clientChannel) throws IOException {
        clientChannel.close();
        clientBuffers.remove(clientChannel);
    }

    private void removeClient(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        clientChannel.close();
        clientBuffers.remove(clientChannel);
    }

    private void readClient(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = clientBuffers.get(clientChannel);
        int bytesRead;
        bytesRead = clientChannel.read(buffer);

        // if client was disconnected
        if (bytesRead == -1) {
            removeClient(key);
            System.out.println("Client " + clientChannel.getLocalAddress().toString() 
                                + " disconnected.");
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

    private Message receiveMessageFromBuffer(ByteBuffer clientBuffer) throws IOException, 
                                                                            ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(clientBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Message message = (Message) objectInputStream.readObject();
        return message;
    }

    private void sendMessage(SocketChannel clientSocket, Message message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        byte[] data = byteArrayOutputStream.toByteArray();
        ByteBuffer buffer = ByteBuffer.wrap(data);
        try {
            clientSocket.write(buffer);
        } catch (ClosedChannelException e) {
            removeClient(clientSocket);
            return;
        }
    }

    private void requestTask(SocketChannel clientChannel) throws IOException {
        Message task;
        if (messageBlocks.size() > 0) {
            task = messageBlocks.removeFirst();
        } else {
            if (executingMessages.size() > 0) {
                messageBlocks.addAll(executingMessages.values());
                executingMessages.clear();
                task = messageBlocks.removeFirst();
            } else {
                finishWorking(true);
                return;
            }
        }
        executingMessages.put(clientChannel, task);
        sendMessage(clientChannel, task);
    }

    private void finishWorking(boolean result) throws IOException {
        for (SocketChannel client : clientBuffers.keySet()) {
            Message exitMessage = new Message();
            exitMessage.setType("EXIT");
            sendMessage(client, exitMessage);
            client.close();
        }
        clientBuffers.clear();

        if (result) {
            System.out.println("All numbers is prime.");
        } else {
            System.out.println("Numbers has non-prime number. Goodbye!");
        }
        working = false;
    }

    private void handleClientMessage(SocketChannel clientChannel, Message message) throws IOException {
        String messageType = message.getType();
        switch (messageType) {
            case "REQUEST":
                requestTask(clientChannel);
                break;
            case "RESULT":
                List<Integer> result = message.getData();
                if (result == null) {
                    finishWorking(false);
                }
                executingMessages.remove(clientChannel);
                requestTask(clientChannel);
                break;
            default:
                System.out.println("Undefined message type. Synchronize types with server.");
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        PrimeServer server = new PrimeServer("localhost", 5000);
        server.start();
    }
}
