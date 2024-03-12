package org.prime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Master {
    private static final int BROADCAST_PORT = 8888;
    private int port;
    private List<InetAddress> activeDevices;
    private List<Integer> executionTimes;

    public Master(int port) {
        this.port = port;
        this.activeDevices = new ArrayList<>();
        this.executionTimes = new ArrayList<>();
    }

    public List<InetAddress> getActiveDevices() {
        return activeDevices;
    }

    public void findAndTestNodes() throws IOException {
        Broadcaster broadcaster = new Broadcaster(BROADCAST_PORT);
        broadcaster.doBroadcast();

        NodeTester tester = new NodeTester(BROADCAST_PORT);
        for (InetAddress i : broadcaster.getActiveDevices()) {
            int execTime = tester.sendTestMessageToNode(i);
            if (execTime != -1) {
                activeDevices.add(i);
                executionTimes.add(execTime);
            }
        }
    }

    private List<List<Integer>> splitNumbersBetweenNodes(List<Integer> array) {
        List<List<Integer>> result = new ArrayList<>();
        int sumExecTime = executionTimes.stream().mapToInt(Integer::intValue).sum();
        int lastIndex = 0;
        for (int i = 0; i < activeDevices.size(); i++) {
            result.add(array.subList(lastIndex, lastIndex + (executionTimes.get(i) * array.size()) / sumExecTime - 1));
        }
        return result;
    }

    public boolean checkPrime(List<Integer> numbers) {
        List<List<Integer>> splittedArray = splitNumbersBetweenNodes(numbers);
        boolean flag = false;
        for (int i = 0; i < activeDevices.size(); i++) {
            try (Socket tcpSocket = new Socket(activeDevices.get(i), port)) {
                ObjectOutputStream outputStream = new ObjectOutputStream(tcpSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(tcpSocket.getInputStream());
                outputStream.writeObject(splittedArray.get(i));
                boolean res = (boolean) inputStream.readObject();
                if (res) {
                    flag = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (flag) {
                break;
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        final int prime = 1000000007;
        for (int i = 0; i < 1000; i++) {
            numbers.add(prime);
        }
        Master master = new Master(8888);
        boolean result = false;
        try {
            master.findAndTestNodes();
            result = master.checkPrime(numbers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
