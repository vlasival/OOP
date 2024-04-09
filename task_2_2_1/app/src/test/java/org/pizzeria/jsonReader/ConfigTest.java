package org.pizzeria.jsonReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.pizzeria.io.jsonReader.JsonFileReader;
import org.pizzeria.io.jsonReader.PizzeriaConfig;

import com.google.gson.Gson;

public class ConfigTest {
    @Test
    void mainTest() {
        JsonFileReader reader = new JsonFileReader("pizzconf.json");
        Gson gson = new Gson();
        PizzeriaConfig config = gson.fromJson(reader.toJsonString(), PizzeriaConfig.class);
        int storageSize = config.getStorageSize();
        int orderListSize = config.getOrderListSize();
        long workingTime = config.getWorkingTime();
        int startOrdersCount = config.getStartOrdersCount();
        assertEquals(storageSize, 10);
        assertEquals(orderListSize, 50);
        assertEquals(workingTime, 10000);
        assertEquals(startOrdersCount, 10);
    }
}
