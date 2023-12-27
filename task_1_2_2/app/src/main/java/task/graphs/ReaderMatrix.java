package task.graphs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import task.exceptions.IncorrectMatrixException;
import task.graphmodel.Graph;
import task.graphmodel.Vertex;

/**
 * Utility class for reading matrices from files and populating graphs.
 */
public class ReaderMatrix {
    private static String[][] values;
    private static String[] verticesName;

    /**
     * Parses a string into a numeric value, either Integer or Double.
     *
     * @param value the string to parse
     * @return the numeric value
     * @throws IncorrectMatrixException if the string is not a valid number
     */
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

    /**
     * Reads a matrix from a file and populates the class variables.
     *
     * @param pathname the path to the file
     * @throws IncorrectMatrixException if there are issues reading the file or parsing the matrix
     */
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
            throw new IncorrectMatrixException("Cannot open file.", e);
        } catch (IndexOutOfBoundsException e) {
            throw new IncorrectMatrixException("The matrix is set incorrectly.", e);
        }
    }

    /**
     * Reads a matrix from a file, creates vertices in the graph, 
     * and adds edges based on the matrix.
     *
     * @param graph    the graph to populate
     * @param pathname the path to the file containing the matrix
     * @param <V>      the type of data stored in the vertices
     * @param <E>      the type of weight associated with the edges (must extend Number)
     * @throws IncorrectMatrixException if there are issues reading the file or parsing the matrix
     */
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
