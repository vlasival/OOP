
package task;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * SubstringSearch class implements methods to find substring.
 */
public class SubstringSearch {

    /**
     * Main function that implements finding substring.
     *
     * @param filename file where to find substing.
     * @param pattern current substring.
     * @return List of indices of substring entries.
     */
    public static List<Long> findSubstringIndices(String filename, boolean useResourcesFolder, String pattern) {
        List<Long> indices = new ArrayList<>();
        int chunkSize = 1024 * 1024 * 10;
        
        try (BufferedReader reader =    useResourcesFolder ? 
                                        openFromResources(filename, chunkSize) :
                                        openFile(filename, chunkSize)) {
            if (reader == null) {
                throw new IOException();
            }
            char[] prevChunk = new char[chunkSize];
            int prevChunkSize = 0;
            int chunkCounter = 0;

            while (true) {
                char[] chunk = new char[chunkSize];
                int bytesRead = reader.read(chunk);
                if (bytesRead == -1) {
                    break;
                }

                int totalChunkSize = prevChunkSize + bytesRead;
                StringBuilder buffer = new StringBuilder(totalChunkSize);

                buffer.append(prevChunk, 0, prevChunkSize);
                buffer.append(chunk, 0, bytesRead);

                String data = buffer.toString();
                List<Long> partialIndices = findSubstring(data, pattern);

                for (long partialIndex : partialIndices) {
                    indices.add((long) partialIndex + (long) chunkCounter * (long) chunkSize - (long) prevChunkSize);
                }
                chunkCounter++;

                if (totalChunkSize >= pattern.length()) {
                    System.arraycopy(chunk, bytesRead - pattern.length() + 1, 
                                    prevChunk, 0, pattern.length() - 1);
                    prevChunkSize = pattern.length() - 1;
                } else {
                    System.arraycopy(chunk, 0, prevChunk, 0, bytesRead);
                    prevChunkSize = bytesRead;
                }
            }
        } catch (IOException e) {
            System.err.println("Error!");
            e.printStackTrace();
        }

        return indices;
    }

    /**
     * Opens file from resoueces.
     *
     * @param filename name of source file.
     * @param chunkSize size of one portion of reading data.
     * @return reader.
     */
    private static BufferedReader openFromResources(String filename, int chunkSize) {
        InputStream inputStream 
            = SubstringSearch.class.getClassLoader().getResourceAsStream(filename);
        
        if (inputStream != null) {
            InputStreamReader inputStreamReader 
                = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            return new BufferedReader(inputStreamReader, chunkSize);
        } else {
            System.out.println("Can't find: " + filename);
            return null;
        }
    }

    private static BufferedReader openFile(String filename, int chunkSize) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(
                fileInputStream, StandardCharsets.UTF_8
            );
            return new BufferedReader(inputStreamReader, chunkSize);
        } catch (Exception e) {
            System.err.println("File wasn't opened\n" + e);
            return null;
        }
    }

    /**
     * Method implements KMP-algorithm.
     *
     * @param text string where to find.
     * @param pattern substring.
     * @return List of indices of entries.
     */
    public static List<Long> findSubstring(String text, String pattern) {
        List<Long> indices = new ArrayList<>();
        int textLength = text.length();
        int patternLength = pattern.length();
        int[] lps = computeLpsArray(pattern);

        int textIndex = 0;
        int patternIndex = 0;

        while (textIndex < textLength) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                textIndex++;
                patternIndex++;
            }

            if (patternIndex == patternLength) {
                indices.add((long) (textIndex - patternIndex));
                patternIndex = lps[patternIndex - 1];
            } else if (textIndex < textLength 
                    && pattern.charAt(patternIndex) != text.charAt(textIndex)) {
                if (patternIndex != 0) {
                    patternIndex = lps[patternIndex - 1];
                } else {
                    textIndex++;
                }
            }
        }

        return indices;
    }

    /**
     * Method-helper that calculates KMP-algorithm coefficients.
     *
     * @param pattern substring.
     * @return coefficients.
     */
    private static int[] computeLpsArray(String pattern) {
        int patternLength = pattern.length();
        int[] lps = new int[patternLength];
        int length = 0;
        int i = 1;

        while (i < patternLength) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    /**
     * Entry point.
     *
     * @param args input arguments.
     */
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        String filename = "chineesetest.txt";
        String pattern = "传统医学";
        List<Long> indices = findSubstringIndices(filename, true, pattern);
        System.out.println(indicesToString(indices));
    }

    /**
     * Not overrided toString.
     *
     * @param indices List of indices
     * @return string of indices. 
     */
    public static String indicesToString(List<Long> indices) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < indices.size(); i++) {
            sb.append(indices.get(i));
            if (i < indices.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
