package task.resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;

import task.exceptions.IncorrectMatrixException;
import task.graphModel.Graph;
import task.graphModel.Vertex;

public class ReaderMatrix {
    private static String[][] values;
    private static String[] verticesName;

    private static Number parseNumber(String value) {
        try {
            if (value.contains(".")) {
                return Double.valueOf(value);
            } else {
                return Integer.valueOf(value);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectMatrixException("Invalid number format: " + value);
        }
    }

    private static void readMatrixFromFile(String pathname) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(pathname));
            verticesName = lines.get(0).split("\\s+");
            int size = verticesName.length;
            values = new String[size][size];
            for (int i = 0; i < size; i++) {
                values[i] = lines.get(i + 1).split("\\s+");
            }
        } catch (IOException e) {
            throw new IncorrectMatrixException("Cannot open file.");
        } catch (IndexOutOfBoundsException e) {
            throw new IncorrectMatrixException("The matrix is set incorrectly.");
        } catch (InvalidPathException e) {
            throw new IncorrectMatrixException("Incorrect path to file.");
        }
    }

    public static <V, E extends Number> void fillGraphFromFile(Graph<V, E> graph, String pathname) {
        readMatrixFromFile(pathname);
        for (int i = 0; i < verticesName.length; i++) {
            graph.addVertex((V) verticesName[i]);
        }
        List<Vertex<V>> vertices = graph.getVertices();

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length; j++) {
                if (values[i][j].equals("n")) {
                    continue;
                }
                graph.addEdge(vertices.get(i), vertices.get(j), (E) parseNumber(values[i][j]));
            }
        }
    }
}
