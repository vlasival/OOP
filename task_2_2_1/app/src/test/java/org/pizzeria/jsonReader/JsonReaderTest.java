package org.pizzeria.jsonReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.pizzeria.io.jsonReader.JsonFileReader;

public class JsonReaderTest {
    @Test
    void mainTest() {
        JsonFileReader reader = new JsonFileReader("config.json");
        assertEquals("{    \"startOrdersCount\": 10}", reader.toJsonString());
    }
}
