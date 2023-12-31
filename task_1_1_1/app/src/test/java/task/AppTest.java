
package task;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


/**
 * Это класс, реализующий тестирование метода heapsort в основном файле App.java
 */
public class AppTest {

    @Test
    public void testHeapSort() {
        int[] inputArray = {3, -11, 13, 7, 6, 7};
        int[] expectedArray = {-11, 3, 6, 7, 7, 13};
        App.heapSort(inputArray);
        assertArrayEquals(expectedArray, inputArray);
    }

    @Test
    public void testHeapSortEmptyArray() {
        int[] inputArray = {};
        int[] expectedArray = {};
        App.heapSort(inputArray);
        assertArrayEquals(expectedArray, inputArray);
    }

    @Test
    public void testHeapSortAlreadySortedArray() {
        int[] inputArray = {1, 2, 3, 4, 5};
        int[] expectedArray = {1, 2, 3, 4, 5};
        App.heapSort(inputArray);
        assertArrayEquals(expectedArray, inputArray);
    }

    @Test
    public void testHeapSortReverseSortedArray() {
        int[] inputArray = {5, 4, 3, 2, 1};
        int[] expectedArray = {1, 2, 3, 4, 5};
        App.heapSort(inputArray);
        assertArrayEquals(expectedArray, inputArray);
    }

    @Test
    public void testHeapSortCriticalValues() {
        int[] inputArray = {2147483647, 0, -2147483648};
        int[] expectedArray = {-2147483648, 0, 2147483647};
        App.heapSort(inputArray);
        assertArrayEquals(expectedArray, inputArray);
    }

    @Test
    public void testPrintArray() {
        App.printArray(new int[] {});
        assertTrue(true);
    }

    @Test
    public void testMain() {
        App.main(new String[] {});
        assertTrue(true);
    }
}

