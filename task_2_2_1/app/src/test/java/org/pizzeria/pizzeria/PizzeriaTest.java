package org.pizzeria.pizzeria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.pizzeria.Pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;

@TestInstance(Lifecycle.PER_CLASS)
class PizzeriaTest {

    private Pizzeria pizzeria;

    /**
     * Set up the test environment by creating a new instance of the Pizzeria class.
     */
    @BeforeEach
    void setUp() {
        pizzeria = new Pizzeria("pizzconf.json");
    }

    /**
     * Test case for adding an order to the pizzeria.
     * It checks whether the order count is increased correctly after adding the order.
     *
     * @throws InterruptedException if the execution is interrupted
     */
    @Test
    void addOrderTest() throws InterruptedException {
        int initialOrderCount = pizzeria.getState().getOrders().size();
        pizzeria.addOrder(5);
        int newOrderCount = pizzeria.getState().getOrders().size();
        assertEquals(initialOrderCount + 5, newOrderCount);
    }

    /**
     * Test case for running the pizzeria.
     * It checks whether the storage is empty after running the pizzeria.
     */
    @Test
    void runTest() {
        pizzeria.run();
        assertTrue(pizzeria.getState().getStorage().isEmpty());
    }

    /**
     * Test case for getting the state of the pizzeria.
     * It checks whether the state is not null.
     */
    @Test
    void getStateTest() {
        assertNotNull(pizzeria.getState());
    }

    /**
     * Method to remove the file after all test cases are executed.
     * It deletes the serialized state file if it exists.
     */
    @AfterAll
    void removeFile() {
        Path filePath = Paths.get("state.ser");
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ignored) { } 
    }

}