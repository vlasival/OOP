package org.prime;

import java.io.Serializable;
import java.util.ArrayList;

class Message implements Serializable {
    private ArrayList<Integer> numbers;

    public Message() {
        final int prime = 1000000007;
        for (int i = 0; i < 100; i++) {
            numbers.add(prime);
        }
    }

    public ArrayList<Integer> getNumbers() {
        return numbers;
    }
}