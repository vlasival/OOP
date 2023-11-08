
package task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SubstringSearch {

    /**
     * @param filename
     * @param pattern
     * @return
     */
    public static List<Integer> findSubstringIndices(String filename, String pattern) {
        List<Integer> indices = new ArrayList<>();
        int chunkSize = 1024 * 1024 * 10;
        
        try (BufferedReader reader = openFromResources(filename, chunkSize)) {
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
                List<Integer> partialIndices = findSubstring(data, pattern);

                for (int partialIndex : partialIndices) {
                    indices.add(partialIndex + chunkCounter * chunkSize - prevChunkSize);
                }
                chunkCounter++;

                if (totalChunkSize >= pattern.length()) {
                    System.arraycopy(chunk, bytesRead - pattern.length() + 1, prevChunk, 0, pattern.length() - 1);
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
     * @param filename
     * @param chunkSize
     * @return
     */
    protected static BufferedReader openFromResources(String filename, int chunkSize) {
        InputStream inputStream = SubstringSearch.class.getClassLoader().getResourceAsStream(filename);
        
        if (inputStream != null) {
            InputStreamReader inputStreamReader 
                = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            return new BufferedReader(inputStreamReader, chunkSize);
        } else {
            System.out.println("Can't find: " + filename);
            return null;
        }
    }

    public static List<Integer> findSubstring(String text, String pattern) {
        List<Integer> indices = new ArrayList<>();
        int textLength = text.length();
        int patternLength = pattern.length();
        int[] lps = computeLPSArray(pattern);

        int textIndex = 0;
        int patternIndex = 0;

        while (textIndex < textLength) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                textIndex++;
                patternIndex++;
            }

            if (patternIndex == patternLength) {
                indices.add(textIndex - patternIndex);
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

    private static int[] computeLPSArray(String pattern) {
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

    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        String filename = "chineesetest.txt";
        String pattern = "传统医学";
        List<Integer> indices = findSubstringIndices(filename, pattern);
        System.out.println(indicesToString(indices));
    }

    @ExcludeFromJacocoGeneratedReport
    public static String indicesToString(List<Integer> indices) {
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
