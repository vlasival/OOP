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

    @BeforeEach
    void setUp() {
        pizzeria = new Pizzeria("pizzconf.json");
    }

    @Test
    void addOrderTest() throws InterruptedException {
        int initialOrderCount = pizzeria.getState().getOrders().size();
        pizzeria.addOrder(5);
        int newOrderCount = pizzeria.getState().getOrders().size();
        assertEquals(initialOrderCount + 5, newOrderCount);
    }

    @Test
    void runTest() {
        pizzeria.run();
        assertTrue(pizzeria.getState().getStorage().isEmpty());
    }

    @Test
    void getStateTest() {
        assertNotNull(pizzeria.getState());
    }

    @AfterAll
    void removeFile() {
        Path filePath = Paths.get("state.ser");
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ignored) { } 
    }

}