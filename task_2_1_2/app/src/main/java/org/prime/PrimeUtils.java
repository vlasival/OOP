package org.prime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PrimeUtils {

    public static final int BUFFER_SIZE = 1024; // Размер буфера в байтах
    private static final int usingBufferSize = BUFFER_SIZE / 4;

    /**
     * Метод читает файл с числами, разделенными пробелами, и делит их на блоки определенного размера.
     *
     * @param filePath путь к файлу с числами.
     * @return список Message, каждый из которых содержит блок чисел.
     * @throws IOException если происходит ошибка при чтении файла.
     */
    public static List<Message> generateMessage(String filePath) throws IOException {
        List<Message> messages = new ArrayList<>();
        try (InputStream inputStream = PrimeUtils.class.getClassLoader().getResourceAsStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            List<Integer> block = new ArrayList<>();
            int blockSizeInBytes = 0;
            char[] buffer = new char[usingBufferSize];
            StringBuilder leftover = new StringBuilder();

            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                leftover.append(buffer, 0, charsRead);

                String[] numberStrings = leftover.toString().split("\\s+");
                leftover.setLength(0); // clear stringBuilder

                for (int i = 0; i < numberStrings.length; i++) {
                    String numberString = numberStrings[i];
                    if (i == (numberStrings.length - 1)) {
                        leftover.append(numberString + " ");
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
