package org.prime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

/**
 * Testing class for message build-in lombok functions.
 */
public class MessageTest {

    /**
     * Creating message.
     */
    @Test
    void testCreateMessage() {
        String type = "REQUEST";
        List<Integer> data = Arrays.asList(2, 3, 5);

        Message message = new Message();
        message.setType(type);
        message.setData(data);

        assertEquals(type, message.getType());
        assertEquals(data, message.getData());
    }

    /**
     * Tests equals and hash code.
     */
    @Test
    void testEqualsAndHashCode() {
        Message message1 = new Message();
        message1.setType("RESULT");
        message1.setData(Arrays.asList(7, 11, 13));

        Message message2 = new Message();
        message2.setType("RESULT");
        message2.setData(Arrays.asList(7, 11, 13));

        assertEquals(message1, message2);
        assertEquals(message1.hashCode(), message2.hashCode());
    }

    /**
     * Test equals or not.
     */
    @Test
    void testNotEquals() {
        Message message1 = new Message();
        message1.setType("REQUEST");
        message1.setData(Arrays.asList(2, 3, 5));

        Message message2 = new Message();
        message2.setType("RESULT");
        message2.setData(Arrays.asList(7, 11, 13));

        assertNotEquals(message1, message2);
    }

    /**
     * Test casting to string.
     */
    @Test
    void testToString() {
        Message message = new Message();
        message.setType("REQUEST");
        message.setData(Arrays.asList(2, 3, 5));

        String expectedString = "Message(type=REQUEST, data=[2, 3, 5])";
        assertEquals(expectedString, message.toString());
    }

    /**
     * Test set and get data.
     */
    @Test
    void testDataModification() {
        Message message = new Message();
        message.setType("RESULT");
        List<Integer> data = Arrays.asList(7, 11, 13);
        message.setData(data);

        // Modify data list and check if the message data is also modified (if list is not copied)
        data.set(0, 17);
        assertEquals(Arrays.asList(17, 11, 13), message.getData());
    }
}
