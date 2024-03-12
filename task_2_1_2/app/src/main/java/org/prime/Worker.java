package org.prime;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Worker {
    private static final int BROADCAST_PORT = 8888;
    private int port;
    private int processors;
    private InetAddress serverAddress;

    public Worker(int port) {
        this.port = port;
        this.processors = Runtime.getRuntime().availableProcessors();
    }

    public void listenBroadcast() {
        try (DatagramSocket socket = new DatagramSocket(BROADCAST_PORT)) {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            // Ожидаем бродкаст от сервера
            socket.receive(receivePacket);
            this.serverAddress = receivePacket.getAddress();
            int serverPort = receivePacket.getPort();
            System.out.println("Received broadcast from server: " + serverAddress);

            // Отправляем ответ о готовности к работе
            String readyMessage = "Ready";
            byte[] readyData = readyMessage.getBytes();
            DatagramPacket readyPacket = new DatagramPacket(readyData, readyData.length, serverAddress, serverPort);
            socket.send(readyPacket);
            System.out.println("Sent readiness message to server");

            // Устанавливаем соединение по TCP с сервером для дальнейшего обмена данными
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void establishingConnection() {
        if (serverAddress == null) {
            System.err.println("Server address unknown. Run broadcast");
        }

        try (Socket tcpSocket = new Socket(serverAddress, port)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(tcpSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(tcpSocket.getInputStream());

            runTestTask(inputStream, outputStream);

            runMainTask(inputStream, outputStream);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private long runTestTask(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws ClassNotFoundException, IOException {
        List<Integer> testArray = (List<Integer>) inputStream.readObject();
        ThreadChecker checker = new ThreadChecker(processors);
        long startTime = System.currentTimeMillis();
        checker.hasNonPrime(testArray);
        long endTime = System.currentTimeMillis();
        long difference = endTime - startTime;
        outputStream.writeLong(difference);
        return difference;
    }

    private void runMainTask(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws ClassNotFoundException, IOException {
        List<Integer> array = (List<Integer>) inputStream.readObject();
        ThreadChecker checker = new ThreadChecker(processors);
        boolean result = checker.hasNonPrime(array);
        outputStream.writeBoolean(result);
    }

    public static void main(String[] args) {
        Worker worker = new Worker(8888);
        worker.listenBroadcast();
        worker.establishingConnection();
    }
}
