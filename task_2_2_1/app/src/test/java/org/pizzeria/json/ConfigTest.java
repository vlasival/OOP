package org.pizzeria.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.pizzeria.io.json.JsonFileReader;
import org.pizzeria.io.json.PizzeriaConfig;


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
        assertEquals(storageSize, 10);
        int orderListSize = config.getOrderListSize();
        assertEquals(orderListSize, 50);
        long workingTime = config.getWorkingTime();
        assertEquals(workingTime, 5000);
        int startOrdersCount = config.getStartOrdersCount();
        assertEquals(startOrdersCount, 4);
    }
}
