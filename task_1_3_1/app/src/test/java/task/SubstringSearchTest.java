
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
        List<Long> expected = Arrays.asList(79L, 135L, 153L);
        assertEquals(expected, SubstringSearch.findSubstringIndices("chineesetest.txt", 
                                                                    true, 
                                                                    "传统医学"));
    }

    @Test
    public void russianTest() {
        List<Long> expected = Arrays.asList(18L, 21L, 24L, 28L, 63L, 66L);
        assertEquals(expected, SubstringSearch.findSubstringIndices("russiantest.txt", 
                                                                    true, 
                                                                    "бла"));
    }

    @Test
    public void simpleTest() {
        List<Long> expected = Arrays.asList(1L, 4L, 13L, 136L, 145L);
        assertEquals(expected, SubstringSearch.findSubstringIndices("simpletest.txt", 
                                                                    true, 
                                                                    "bla"));
    }

    @Test
    public void algorithmKmpTest() {
        List<Long> expected = Arrays.asList(60L, 123L, 316L);
        assertEquals(expected, SubstringSearch.findSubstringIndices("kmptest.txt", 
                                                                    true, 
                                                                    "ababbaba"));
    }

    @Test
    public void errorTest() {
        SubstringSearch.findSubstringIndices("abcd.txt", 
                                            true, 
                                            "abcd");
    }

    @Test
    public void indicesToStringTest() {
        List<Long> list = new ArrayList<>();
        for (long i = 3; i < 1000; i *= 3) {
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

        List<Long> preres = SubstringSearch.findSubstringIndices("large_file.txt", 
                                                                false, 
                                                                "ba");
        res = SubstringSearch.indicesToString(preres);
        
        file.delete();
        List<Long> expected = new ArrayList<>();
        expected.add(fileSize - 1);
        expected.add(fileSize * 2);
        assertEquals(expected.toString(), res);
    }
}

