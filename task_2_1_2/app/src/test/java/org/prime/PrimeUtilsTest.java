package org.prime;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrimeUtilsTest {

    @Test
    void testGenerateMessage() throws IOException {
        List<Message> messages = PrimeUtils.generateMessage("test.txt");
        assertFalse(messages.isEmpty(), "Messages should not be empty.");
        for (Message message : messages) {
            assertEquals("TASK", message.getType(), "Message type should be 'TASK'.");
            assertNotNull(message.getData(), "Message data should not be null.");
        }
    }
}
