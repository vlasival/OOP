package task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadMatrixFromFile {
    public static String[][] readMatrix(String filePath) throws IOException {
        String[][] matrix = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(" ");

                if (matrix == null) {
                    matrix = new String[values.length][values.length];
                }
                
                for (int col = 0; col < values.length; col++) {
                    matrix[row][col] = values[col];
                }

                row++;
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return matrix;
    }
}
