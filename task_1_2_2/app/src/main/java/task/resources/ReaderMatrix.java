package task.resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;

import task.exceptions.IncorrectMatrixException;

public class ReaderMatrix {
    private List<List<String>> values;
    private String[] verticesName;

    public ReaderMatrix(String pathname) {
        readMatrixFromFile(pathname);
    }

    private void readMatrixFromFile(String pathname) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(pathname));
            verticesName = lines.get(0).split("\\s+");
            int rows = verticesName.length;
            matrix = new String[rows][rows];
            for (int i = 1; i <= rows; i++) {
                String[] currLine = lines.get(i).split("\\s+");
                for (int j = 0; j < rows; i++) {
                    matrix[i][j] = currLine[j];
                }
            }
        } catch (IOException e) {
            throw new IncorrectMatrixException("Cannot open file.");
        } catch (IndexOutOfBoundsException e) {
            throw new IncorrectMatrixException("The matrix is set incorrectly.");
        } catch (InvalidPathException e) {
            throw new IncorrectMatrixException("Incorrect path to file.");
        }
    }

    private E parseNumber(String value) {
        try {
            if (value.contains(".")) {
                return (E) Double.valueOf(value);
            } else {
                return (E) Integer.valueOf(value);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectMatrixException("Invalid number format: " + value);
        }
    }

    public String[][] getStringMatrix() {
        return matrix;
    }
    
    public String[] getVerticesName() {
        return verticesName;
    }
}
