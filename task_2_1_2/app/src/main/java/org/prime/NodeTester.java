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
        this.message = new Message();
    }

    public int sendTestMessageToNode(InetAddress address) {
        final int timeout = 5000;
        int time = -1;
        try (Socket socket = new Socket(address, port);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            outputStream.writeObject(message);
            System.out.println("Message sent to node: " + address);

            socket.setSoTimeout(timeout);

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            int response = inputStream.readInt();
            System.out.println("Response from node: " + address + ", execution time: " + response + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return time;
    }
}
