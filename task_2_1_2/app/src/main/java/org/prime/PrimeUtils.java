package org.prime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PrimeUtils {

    public static List<Message> generateMessage(String filePath, int blockSize) throws IOException {
        List<Message> numberBlocks = new ArrayList<>();

        StringBuilder contentBuilder = new StringBuilder();
        try (InputStream inputStream 
                = PrimeUtils.class.getClassLoader().getResourceAsStream(filePath);
             BufferedReader reader 
                = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line);
            }
        }

        String content = contentBuilder.toString();
        String[] numberStrings = content.split("\\s+");

        List<Integer> numbers = new ArrayList<>();
        for (String numberString : numberStrings) {
            numbers.add(Integer.parseInt(numberString.trim()));
        }

        for (int i = 0; i < numbers.size(); i += blockSize) {
            List<Integer> block = new ArrayList<>();
            for (int j = i; j < Math.min(numbers.size(), i + blockSize); j++) {
                block.add(numbers.get(j));
            }
            Message messageBlock = new Message();
            messageBlock.setType("TASK");
            messageBlock.setData(block);
            numberBlocks.add(messageBlock);
        }

        return numberBlocks;
    }
}
