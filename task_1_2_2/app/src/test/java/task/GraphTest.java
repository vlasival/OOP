package task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import task.graphmodel.Edge;
import task.graphmodel.Graph;
import task.graphmodel.Vertex;
import task.graphs.AdjacencyListGraph;
import task.graphs.AdjacencyMatrixGraph;
import task.graphs.IncedenceMatrixGraph;
import task.graphs.ReaderMatrix;

/**
 * Сlass tests the main methods described in the interface Graph.
 */
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

        List<Edge<String, Short>> incidentEdges = graph.getOutcomeEdges(vertexA);
        assertEquals(2, incidentEdges.size());
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testRemoveVertex(Graph<String, Integer> graph) {
        String filePath = "src/test/resources/inAdjMatrix.txt";
        ReaderMatrix.fillGraphFromFile(graph, filePath);
        assertEquals(7, graph.getVertices().size());
        Vertex<String> firstVertex = graph.getVertices().get(0);
        Vertex<String> lastVertex = graph.getVertices().get(5);
        graph.removeVertex(firstVertex);
        graph.removeVertex(lastVertex);
        assertEquals(5, graph.getVertices().size());
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testIncomeEdges(Graph<String, Integer> graph) {
        String filePath = "src/test/resources/inAdjMatrix.txt";
        ReaderMatrix.fillGraphFromFile(graph, filePath);
        List<Integer> expected = List.of(5, 12, 25);
        List<Integer> weights = new ArrayList<>();
        for (var i : graph.getIncomeEdges(graph.getVertices().get(0))) {
            weights.add(i.getWeight());
        }
        assertEquals(expected, weights);
    }
}
