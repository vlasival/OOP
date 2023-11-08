
package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SubstringSearchTest {
    @Test
    public void chineeseTest() {
        List<Integer> expected = new ArrayList<>(Arrays.asList(79, 135, 153));
        assertEquals(expected, SubstringSearch.findSubstringIndices("chineesetest.txt", "传统医学"));
    }

    @Test
    public void russianTest() {
        List<Integer> expected = new ArrayList<>(Arrays.asList(18, 21, 24, 28, 63, 66));
        assertEquals(expected, SubstringSearch.findSubstringIndices("russiantest.txt", "бла"));
    }

    @Test
    public void simpleTest() {
        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 4, 13, 136, 145));
        assertEquals(expected, SubstringSearch.findSubstringIndices("simpletest.txt", "bla"));
    }

    @Test
    public void algorithmKMPTest() {
        List<Integer> expected = new ArrayList<>(Arrays.asList(60, 123, 316));
        assertEquals(expected, SubstringSearch.findSubstringIndices("kmptest.txt", "ababbaba"));
    }

    @Test
    public void errorTest() {
        SubstringSearch.findSubstringIndices("abcd.txt", "abcd");
    }

    // @Test
    // public void theBiggestTest() {
    //     String fileName = "large_file.txt";
    //     long fileSize = 128L;
    //     boolean state = false;

    //     File file = new File(fileName);
    //     try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
    //         long bytesWritten = 0;
    //         while (bytesWritten < fileSize) {
    //             writer.write('b');
    //             bytesWritten++;
    //         }
    //         writer.write('a');
    //         bytesWritten = 0;
    //         while (bytesWritten < fileSize) {
    //             writer.write('b');
    //             bytesWritten++;
    //         }

    //         List<Integer> res = SubstringSearch.findSubstringIndices("large_file.txt", "bab");
    //         state = res.equals(Arrays.asList(fileSize));

    //         if (file.delete()) {
    //             System.out.println("File deleted succsesfully.");
    //         } else {
    //             System.out.println("File wasn't deleted.");
    //         }

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     assertTrue(state);
    // }
}

