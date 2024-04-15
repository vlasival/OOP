package org.pizzeria.jsonReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.pizzeria.io.jsonReader.JsonFileReader;
import org.pizzeria.io.jsonReader.PizzeriaConfig;


/**
 * A unit test class for handling the Pizzeria's configuration.
 */
public class ConfigTest {
    /**
     * A unit test method that tests reading from a json config file
     * and asserts the configuration file parameters.
     */
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
        assertEquals(workingTime, 5000);
        assertEquals(startOrdersCount, 4);
    }
}
