package org.pizzeria.jsonReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.pizzeria.io.jsonReader.JsonFileReader;

/**
 * Test class for JsonFileReader.
 */
public class JsonReaderTest {

    /**
     * Test method for reading from resources.
     */
    @Test
    void readFromResourcesTest() {
        JsonFileReader reader = new JsonFileReader("configt.json");
        assertEquals("{    \"startOrdersCount\": 10}", reader.toJsonString());
    }
    
    /**
     * Test method for failure case.
     */
    @Test
    void failTest() {
        assertThrows(NullPointerException.class, () -> new JsonFileReader("s.c"));
    }
    
}
