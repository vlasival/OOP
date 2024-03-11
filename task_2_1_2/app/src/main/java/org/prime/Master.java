package org.prime;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class Master {
    private int port;
    private List<InetAddress> activeDevices;
    private List<Integer> executionTimes;

    public Master(int port) {
        this.port = port;
    }

    public List<InetAddress> getActiveDevices() {
        return activeDevices;
    }

    public void findAndTestNodes() throws IOException {
        Broadcaster broadcaster = new Broadcaster(port);
        broadcaster.doBroadcast();

        NodeTester tester = new NodeTester(port);
        for (InetAddress i : broadcaster.getActiveDevices()) {
            int execTime = tester.sendTestMessageToNode(i);
            if (execTime != -1) {
                activeDevices.add(i);
                executionTimes.add(execTime);
            }
        }
    }
}
