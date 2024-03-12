package org.prime;

import java.io.Serializable;
import java.util.ArrayList;

class Message implements Serializable {
    private ArrayList<Integer> numbers;

    public Message(int count) {
        numbers = new ArrayList<>();
        final int prime = 1000000007;
        for (int i = 0; i < count; i++) {
            numbers.add(prime);
        }
    }

    public ArrayList<Integer> getNumbers() {
        return numbers;
    }
}