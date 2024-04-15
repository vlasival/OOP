package org.pizzeria.state;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * Javadoc for the StateTest class.
 */
@TestInstance(Lifecycle.PER_CLASS)
public class StateTest {

    private State state;
    @SuppressWarnings("unused")
    private Order order; 

    /**
     * Sets up the state and order before each test method execution.
     */
    @BeforeEach
    public void setUp() {
        state = new State(5, 10);
        order = new Order(0, "order");
    }

     /**
     * Test case to check the size of orders.
     */
    @Test
    public void testGetOrders() {
        assertEquals(state.getOrders().size(), 0);
    }

    /**
     * Test case to check the size of storage.
     */
    @Test
    public void testGetStorage() {
        assertEquals(state.getStorage().size(), 0);
    }

    /**
     * Test case for serialization and deserialization.
     *
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the class is not found during deserialization.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        String filename = "state.ser";
        State.serializeState(state, filename);
        State resultState = State.deserializeState(filename);
        assertEquals(resultState.getOrders().size(), state.getOrders().size());
        assertEquals(resultState.getStorage().size(), state.getStorage().size());
    }

    /**
     * Method to remove the file after all test methods have executed.
     */
    @AfterAll
    void removeFile() {
        Path filePath = Paths.get("state.ser");
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ignored) { } 
    }
}