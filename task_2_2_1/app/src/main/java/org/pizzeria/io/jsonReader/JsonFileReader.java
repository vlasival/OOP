package org.pizzeria.io.jsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Class reader of json.
 */
public class JsonFileReader {

    String jsonString;

    /**
     * Constructor.
     *
     * @param filename name of file
     */
    public JsonFileReader(String filename) {
        try {
            jsonString = readFromResources(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return json string.
     *
     * @return string
     */
    public String toJsonString() {
        return jsonString;
    }

    /**
     * Function to read json file from resources.
     *
     * @param filePath name of file
     * @return string with content of file
     * @throws IOException if cannot open a file
     */
    private static String readFromResources(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (InputStream inputStream 
                = JsonFileReader.class.getClassLoader().getResourceAsStream(filePath);
             BufferedReader reader 
                = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line);
            }
        } 
        return contentBuilder.toString();
    }
    
}
