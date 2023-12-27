package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.junit.jupiter.api.Test;
import task.exceptions.IncorrectMatrixException;
import task.graphmodel.Graph;
import task.graphs.AdjacencyMatrixGraph;
import task.graphs.ReaderMatrix;

public class ReadingFromFileTest {
    @Test
    void testFillGraphFromFileWithInvalidFile() {
        Graph<String, Integer> graph = new AdjacencyMatrixGraph<>();
        assertThrows(IncorrectMatrixException.class,
                () -> ReaderMatrix.fillGraphFromFile(graph, "nonexistent_file.txt"));
    }

    @Test
    void testFillGraphFromFileWithInvalidMatrix() throws IOException {
        String invalidMatrixContent = "0 1\n2";
        Path tempFilePath = Files.createTempFile("tempInvalidMatrixFile", ".txt");
        Files.write(tempFilePath, invalidMatrixContent.getBytes(), StandardOpenOption.WRITE);

        Graph<String, Integer> graph = new AdjacencyMatrixGraph<>();
        assertThrows(IncorrectMatrixException.class, 
                () -> ReaderMatrix.fillGraphFromFile(graph, tempFilePath.toString()));

        Files.deleteIfExists(tempFilePath);
    }

    @Test
    void testGraphConstructionFromFile() {
        Graph<String, Integer> graph = new AdjacencyMatrixGraph<>();
        String filePath = "src/test/resources/inAdjMatrix.txt";
        ReaderMatrix.fillGraphFromFile(graph, filePath);
        assertEquals(7, graph.getVertices().size());
        assertEquals(3, graph.getOutcomeEdges(graph.getVertices().get(0)).size());
        assertEquals(2, graph.getOutcomeEdges(graph.getVertices().get(1)).size());
        assertEquals(4, graph.getOutcomeEdges(graph.getVertices().get(2)).size());
        assertEquals(3, graph.getOutcomeEdges(graph.getVertices().get(3)).size());
        assertEquals(2, graph.getOutcomeEdges(graph.getVertices().get(4)).size());
        assertEquals(2, graph.getOutcomeEdges(graph.getVertices().get(5)).size());
        assertEquals(4, graph.getOutcomeEdges(graph.getVertices().get(6)).size());
    }

    @Test
    void testWrongValues() {
        Graph<String, Integer> graph = new AdjacencyMatrixGraph<>();
        String filePath = "src/test/resources/wrongValues.txt";
        assertThrows(IncorrectMatrixException.class, 
                    () -> ReaderMatrix.fillGraphFromFile(graph, filePath));
    }
}
