package org.pizzeria.jsonReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.pizzeria.io.jsonReader.JsonFileReader;

public class JsonReaderTest {
    @Test
    void readFromResourcesTest() {
        JsonFileReader reader = new JsonFileReader("configt.json");
        assertEquals("{    \"startOrdersCount\": 10}", reader.toJsonString());
    }
    
    @Test
    void failTest() {
        assertThrows(NullPointerException.class, () -> new JsonFileReader("s.c"));
    }
    
}
