package org.prime;

import java.net.DatagramSocket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class Broadcaster {
    private int port;
    private List<InetAddress> activeDevices;

    public Broadcaster(int port) {
        this.port = port;
    }

    public List<InetAddress> getActiveDevices() {
        return activeDevices;
    }
    
    public void doBroadcast() throws IOException {
        activeDevices = new ArrayList<>();

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            socket.setSoTimeout(3000);

            byte[] sendData = InetAddress.getLocalHost().getHostAddress().getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData,
                    sendData.length,
                    InetAddress.getByName("255.255.255.255"),
                    port);
            socket.send(sendPacket);
            System.out.println("Broadcast sent");

            synchronized (socket) {
                while (true) {
                    byte[] receiveData = new byte[32];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);

                    InetAddress deviceAddress = receivePacket.getAddress();
                    if (!activeDevices.contains(deviceAddress)) {
                        activeDevices.add(deviceAddress);
                        System.out.println("Response from: " + deviceAddress);
                    }
                }
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Timeout reached. Broadcasting stopped.");
        } 
    }
    
}
