/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TreeTest {
    @Test void appHasAGreeting() {
        Tree classUnderTest = new Tree();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }
}
