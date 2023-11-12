
package task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class SubstringSearchTest {
    @Test
    public void chineeseTest() {
        List<Integer> expected = new ArrayList<>(Arrays.asList(79, 135, 153));
        assertEquals(expected, SubstringSearch.findSubstringIndices("chineesetest.txt", true, "传统医学"));
    }

    @Test
    public void russianTest() {
        List<Integer> expected = new ArrayList<>(Arrays.asList(18, 21, 24, 28, 63, 66));
        assertEquals(expected, SubstringSearch.findSubstringIndices("russiantest.txt", true, "бла"));
    }

    @Test
    public void simpleTest() {
        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 4, 13, 136, 145));
        assertEquals(expected, SubstringSearch.findSubstringIndices("simpletest.txt", true, "bla"));
    }

    @Test
    public void algorithmKmpTest() {
        List<Integer> expected = new ArrayList<>(Arrays.asList(60, 123, 316));
        assertEquals(expected, SubstringSearch.findSubstringIndices("kmptest.txt", true, "ababbaba"));
    }

    @Test
    public void errorTest() {
        SubstringSearch.findSubstringIndices("abcd.txt", true, "abcd");
    }

    @Test
    public void indicesToStringTest() {
        List<Long> list = new ArrayList<>();
        for (long i = 3; i < 1000; i*=3) {
            list.add(i);
        }
        String expected = "[3, 9, 27, 81, 243, 729]";
        assertEquals(expected, SubstringSearch.indicesToString(list));
    }

    @Test
    public void theBiggestTest() {
        String fileName = "large_file.txt";
        long fileSize = 1024L * 1024 * 1024 * 8; // 16 gb will be in total
        String res = "";

        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        
            long ind = 0;
            for (ind = 0; ind < fileSize; ind++) {
                writer.write('b');
            }
            writer.write('a');
            for (ind = 0; ind < fileSize; ind++) {
                writer.write('b');
            }
            writer.write('a');
        } catch (IOException e) {
            e.printStackTrace();
        } 

        List<Long> preres = SubstringSearch.findSubstringIndices("large_file.txt", false, "ba");
        res = SubstringSearch.indicesToString(preres);
        
        file.delete();
        List<Long> expected = new ArrayList<>();
        expected.add(1024L * 1024 * 1024 * 8 - 1);
        expected.add(1024L * 1024 * 1024 * 16);
        assertEquals(expected.toString(), res);
    }
}

