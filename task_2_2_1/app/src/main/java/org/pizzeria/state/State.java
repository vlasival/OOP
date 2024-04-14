package org.pizzeria.state;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.pizzeria.customQueue.BlockingQueue;

/**
 * Class representing state of the pizzeria ordering system.
 * It contains queues for orders and storage.
 */
public class State implements Serializable {

    private static final long serialVersionUID = 1L;
    private BlockingQueue<Order> orders;
    private BlockingQueue<Order> storage;

    /**
     * Constructor for State class.
     *
     * @param storageSize size of storage queue.
     * @param queueSize   size of orders queue.
     */
    public State(int storageSize, int queueSize) {
        orders = new BlockingQueue<Order>(queueSize);
        storage = new BlockingQueue<Order>(storageSize);
    }

    /**
     * Getter for orders queue.
     */
    public BlockingQueue<Order> getOrders() {
        return orders;
    }

    /**
     * Getter for storage queue.
     */
    public BlockingQueue<Order> getStorage() {
        return storage;
    }

    /**
     * Serializator.
     *
     * @param state current state
     * @param filename name of file to safe in
     * @throws IOException throws if cannot open a file
     */
    public static void serializeState(State state, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(state);
        }
    }

    /**
     * Deserializator.
     *
     * @param filename name of file to load from
     * @return state from file
     * @throws IOException throws if cannot open a file
     * @throws ClassNotFoundException throws if cannot link to class
     */
    public static State deserializeState(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (State) in.readObject();
        }
    }
}
