package org.pizzeria.state;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class StateTest {

    private State state;
    private Order order; 

    @BeforeEach
    public void setUp() {
        state = new State(5, 10);
        order = new Order(0, "order");
    }

    @Test
    public void testGetOrders() {
        assertEquals(state.getOrders().size(), 0);
    }

    @Test
    public void testGetStorage() {
        assertEquals(state.getStorage().size(), 0);
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        String filename = "state.ser";
        State.serializeState(state, filename);
        State resultState = State.deserializeState(filename);
        assertEquals(resultState.getOrders().size(), state.getOrders().size());
        assertEquals(resultState.getStorage().size(), state.getStorage().size());
    }

    @AfterAll
    void removeFile() {
        Path filePath = Paths.get("state.ser");
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ignored) { } 
    }
}