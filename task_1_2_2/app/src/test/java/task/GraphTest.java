package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import task.exceptions.IncorrectMatrixException;
import task.graphModel.Edge;
import task.graphModel.Graph;
import task.graphModel.Vertex;
import task.graphs.AdjacencyListGraph;
import task.graphs.AdjacencyMatrixGraph;
import task.graphs.IncedenceMatrixGraph;
import task.graphs.SortingAlgorithm;
import task.resources.ReaderMatrix;

class GraphTest {
    static class GraphsArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(new AdjacencyListGraph<String, Double>()),
                    Arguments.of(new IncedenceMatrixGraph<String, Double>()),
                    Arguments.of(new AdjacencyMatrixGraph<String, Double>())
            );
        }
    }
    
    @Test
    void testEdgeGetWeight() {
        Vertex<String> from = new Vertex<>("A");
        Vertex<String> to = new Vertex<>("B");
        Edge<String, Double> edge = new Edge<>(from, to, 5.0);

        assertEquals(5.0, edge.getWeight());
    }

    @Test
    void testEdgeSetWeight() {
        Vertex<String> from = new Vertex<>("A");
        Vertex<String> to = new Vertex<>("B");
        Edge<String, Double> edge = new Edge<>(from, to, 5.0);

        edge.setWeight(10.0);

        assertEquals(10.0, edge.getWeight());
    }

    @Test
    void testEdgeGetFrom() {
        Vertex<String> from = new Vertex<>("A");
        Vertex<String> to = new Vertex<>("B");
        Edge<String, Double> edge = new Edge<>(from, to, 5.0);

        assertEquals(from, edge.getFrom());
    }

    @Test
    void testEdgeSetFrom() {
        Vertex<String> from = new Vertex<>("A");
        Vertex<String> to = new Vertex<>("B");
        Edge<String, Double> edge = new Edge<>(from, to, 5.0);

        Vertex<String> newFrom = new Vertex<>("C");
        edge.setFrom(newFrom);

        assertEquals(newFrom, edge.getFrom());
    }

    @Test
    void testEdgeGetTo() {
        Vertex<String> from = new Vertex<>("A");
        Vertex<String> to = new Vertex<>("B");
        Edge<String, Double> edge = new Edge<>(from, to, 5.0);

        assertEquals(to, edge.getTo());
    }

    @Test
    void testEdgeSetTo() {
        Vertex<String> from = new Vertex<>("A");
        Vertex<String> to = new Vertex<>("B");
        Edge<String, Double> edge = new Edge<>(from, to, 5.0);

        Vertex<String> newTo = new Vertex<>("D");
        edge.setTo(newTo);

        assertEquals(newTo, edge.getTo());
    }

    @Test
    void testVertexGetName() {
        Vertex<String> vertex = new Vertex<>("A");

        assertEquals("A", vertex.getName());
    }

    @Test
    void testVertexSetName() {
        Vertex<String> vertex = new Vertex<>("A");
        vertex.setName("B");

        assertEquals("B", vertex.getName());
    }

    @Test
    void testVertexConstructor() {
        Vertex<Integer> vertex = new Vertex<>(42);

        assertEquals(42, vertex.getName());
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testGraphAddVertex(Graph<String, Double> graph) {
        graph.addVertex("A");
        graph.addVertex("B");
        List<Vertex<String>> vertices = graph.getVertices();

        assertEquals(2, vertices.size());
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testGraphChangeVertex(Graph<String, Double> graph) {
        Vertex<String> vertexA = graph.addVertex("A");
        graph.changeVertex(vertexA, "NewName");

        List<Vertex<String>> vertices = graph.getVertices();
        assertEquals(1, vertices.size());
        assertEquals("NewName", vertices.get(0).getName());
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testGraphAddEdge(Graph<String, Integer> graph) {
        Vertex<String> vertexA = graph.addVertex("A");
        Vertex<String> vertexB = graph.addVertex("B");
        graph.addEdge(vertexA, vertexB, 2);

        List<Edge<String, Integer>> edges = graph.getEdges();
        assertEquals(1, edges.size());
        assertEquals(vertexA, edges.get(0).getFrom());
        assertEquals(vertexB, edges.get(0).getTo());
        assertEquals(2, edges.get(0).getWeight());
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testGraphRemoveEdge(Graph<String, Double> graph) {
        Vertex<String> vertexA = graph.addVertex("A");
        Vertex<String> vertexB = graph.addVertex("B");
        Edge<String, Double> edge = graph.addEdge(vertexA, vertexB, 2d);
        graph.removeEdge(edge);

        List<Edge<String, Double>> edges = graph.getEdges();
        assertEquals(0, edges.size());
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testGraphChangeEdge(Graph<String, Integer> graph) {
        Vertex<String> vertexA = graph.addVertex("A");
        Vertex<String> vertexB = graph.addVertex("B");
        Edge<String, Integer> edge = graph.addEdge(vertexA, vertexB, 2);
        graph.changeEdge(edge, 5);

        List<Edge<String, Integer>> edges = graph.getEdges();
        assertEquals(1, edges.size());
        assertEquals(5, edges.get(0).getWeight());
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testGraphGetIncidentEdges(Graph<String, Short> graph) {
        Vertex<String> vertexA = graph.addVertex("A");
        Vertex<String> vertexB = graph.addVertex("B");
        Vertex<String> vertexC = graph.addVertex("C");
        graph.addEdge(vertexA, vertexB, (short) 2);
        graph.addEdge(vertexA, vertexC, (short) 3);

        List<Edge<String, Short>> incidentEdges = graph.getIncidentEdges(vertexA);
        assertEquals(2, incidentEdges.size());
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testDijkstra(Graph<String, Integer> graph) {
        Vertex<String> vertexA = graph.addVertex("A");
        Vertex<String> vertexB = graph.addVertex("B");
        Vertex<String> vertexC = graph.addVertex("C");
        graph.addEdge(vertexA, vertexB, 1);
        graph.addEdge(vertexA, vertexC, 2);
        graph.addEdge(vertexB, vertexC, 3);

        Map<Vertex<String>, Double> distances = SortingAlgorithm.dijkstra(graph, vertexA);

        assertEquals(0.0, distances.get(vertexA));
        assertEquals(1.0, distances.get(vertexB));
        assertEquals(2.0, distances.get(vertexC));
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testBellmanFord(Graph<String, Integer> graph) {
        Vertex<String> vertexA = graph.addVertex("A");
        Vertex<String> vertexB = graph.addVertex("B");
        Vertex<String> vertexC = graph.addVertex("C");
        graph.addEdge(vertexA, vertexB, 1);
        graph.addEdge(vertexB, vertexC, 2);
        graph.addEdge(vertexC, vertexA, -2);

        Map<Vertex<String>, Double> distances = SortingAlgorithm.bellmanFord(graph, vertexA);

        assertEquals(0.0, distances.get(vertexA));
        assertEquals(1.0, distances.get(vertexB));
        assertEquals(3.0, distances.get(vertexC));
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testBellmanFordWithNegativeCycle(Graph<String, Integer> graph) {
        Vertex<String> vertexA = graph.addVertex("A");
        Vertex<String> vertexB = graph.addVertex("B");
        Vertex<String> vertexC = graph.addVertex("C");
        graph.addEdge(vertexA, vertexB, 1);
        graph.addEdge(vertexB, vertexC, -4);
        graph.addEdge(vertexC, vertexA, 2);

        Map<Vertex<String>, Double> distances = SortingAlgorithm.bellmanFord(graph, vertexA);

        assertNull(distances);
    }

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
        // Создаем экземпляр графа
        Graph<String, Integer> graph = new AdjacencyMatrixGraph<>();

        // Путь к файлу в ресурсах тестов
        String filePath = "src/test/resources/inAdjMatrix.txt";

        // Заполняем граф из файла
        ReaderMatrix.fillGraphFromFile(graph, filePath);

        // Проверяем вершины
        assertEquals(7, graph.getVertices().size());

        // Проверяем ребра и веса
        assertEquals(3, graph.getIncidentEdges(graph.getVertices().get(0)).size());
        assertEquals(2, graph.getIncidentEdges(graph.getVertices().get(1)).size());
        assertEquals(4, graph.getIncidentEdges(graph.getVertices().get(2)).size());
        assertEquals(3, graph.getIncidentEdges(graph.getVertices().get(3)).size());
        assertEquals(2, graph.getIncidentEdges(graph.getVertices().get(4)).size());
        assertEquals(2, graph.getIncidentEdges(graph.getVertices().get(5)).size());
        assertEquals(4, graph.getIncidentEdges(graph.getVertices().get(6)).size());
    }
}
