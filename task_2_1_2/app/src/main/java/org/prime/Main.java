package org.prime;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Master server = new Master(12345);
        try {
            server.findAndTestNodes();
            var res = server.getActiveDevices();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
