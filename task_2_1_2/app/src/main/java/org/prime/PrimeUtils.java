package org.prime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contains data preparation methods.
 */
public class PrimeUtils {

    public static final int BUFFER_SIZE = 1024; // Размер буфера в байтах
    private static final int usingBufferSize = BUFFER_SIZE / 4;

    /**
     * Method reads file with numbers, separated by spaces 
     * and splits it on data blocks fixed size.
     *
     * @param filePath path to file in resources
     * @return list of separated messages
     * @throws IOException if an I/O error occurs
     */
    public static List<Message> generateMessage(String filePath, Class<?> resourcesClass) 
                                                                    throws IOException {
        List<Message> messages = new ArrayList<>();
        try (InputStream inputStream 
                = resourcesClass.getClassLoader().getResourceAsStream(filePath);
            BufferedReader reader 
                = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            List<Integer> block = new ArrayList<>();
            int blockSizeInBytes = 0;
            char[] buffer = new char[usingBufferSize];
            StringBuilder leftover = new StringBuilder();

            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                leftover.append(buffer, 0, charsRead);
                String leftoverString = leftover.toString();
                String offset = "";
                if (leftoverString.charAt(leftoverString.length() - 1) == ' ') {
                    offset = " ";
                }

                String[] numberStrings = leftoverString.split("\\s+");
                leftover.setLength(0); // clear stringBuilder

                for (int i = 0; i < numberStrings.length; i++) {
                    String numberString = numberStrings[i];
                    if (i == (numberStrings.length - 1)) {
                        leftover.append(numberString + offset);
                    } else {
                        int number = Integer.parseInt(numberString.trim());
                        int numberSize = numberString.getBytes(StandardCharsets.UTF_8).length;

                        if (blockSizeInBytes + numberSize > usingBufferSize) {
                            Message messageBlock = new Message();
                            messageBlock.setType("TASK");
                            messageBlock.setData(new ArrayList<>(block));
                            messages.add(messageBlock);

                            block.clear();
                            blockSizeInBytes = 0;
                        }

                        block.add(number);
                        blockSizeInBytes += numberSize + 1;
                    }
                }
            }

            if (leftover.length() > 0) {
                String[] numberStrings = leftover.toString().split("\\s+");
                for (String numberString : numberStrings) {
                    int number = Integer.parseInt(numberString.trim());
                    int numberSize = numberString.getBytes(StandardCharsets.UTF_8).length;

                    if (blockSizeInBytes + numberSize > usingBufferSize) {
                        Message messageBlock = new Message();
                        messageBlock.setType("TASK");
                        messageBlock.setData(new ArrayList<>(block));
                        messages.add(messageBlock);

                        block.clear();
                        blockSizeInBytes = 0;
                    }

                    block.add(number);
                    blockSizeInBytes += numberSize + 1;
                }
            }

            if (!block.isEmpty()) {
                Message messageBlock = new Message();
                messageBlock.setType("TASK");
                messageBlock.setData(new ArrayList<>(block));
                messages.add(messageBlock);
            }
        }

        return messages;
    }
}
