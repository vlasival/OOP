package org.prime;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class NodeTester {
    private int port;
    private Message message;

    public NodeTester(int port) {
        this.port = port;
        this.message = new Message(100);
    }

    public int sendTestMessageToNode(InetAddress address) {
        final int timeout = 5000;
        int time = -1;
        System.out.println("Starting test worker " + address);
        try (Socket socket = new Socket(address, port);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            outputStream.writeObject(message);
            System.out.println("Message sent to node: " + address);

            // socket.setSoTimeout(timeout);

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            time = (int) inputStream.readLong();
            System.out.println("Response from node: " + address + ", execution time: " + time + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return time;
    }
}
