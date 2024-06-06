package org.prime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Test class for single prime utils method.
 */
class PrimeUtilsTest {

    /**
     * Generate message from test file and check it's correct.
     *
     * @throws IOException if I/O errors occured
     */
    @Test
    void testGenerateMessage() throws IOException {
        List<Message> messages = PrimeUtils.generateMessage("t_test.txt", PrimeUtilsTest.class);
        assertFalse(messages.isEmpty());
        for (Message message : messages) {
            assertEquals("TASK", message.getType());
            assertNotNull(message.getData());
        }
    }
}
