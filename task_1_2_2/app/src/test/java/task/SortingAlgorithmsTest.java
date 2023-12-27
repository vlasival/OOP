package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import task.graphmodel.Graph;
import task.graphmodel.Vertex;
import task.graphs.AdjacencyListGraph;
import task.graphs.AdjacencyMatrixGraph;
import task.graphs.IncedenceMatrixGraph;
import task.graphs.ReaderMatrix;
import task.graphs.SortingAlgorithms;

/**
 * Class tests graph's sorting algorithms.
 */
public class SortingAlgorithmsTest {

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
    void testDijkstra(Graph<String, Integer> graph) {
        Vertex<String> vertexA = graph.addVertex("A");
        Vertex<String> vertexB = graph.addVertex("B");
        Vertex<String> vertexC = graph.addVertex("C");
        graph.addEdge(vertexA, vertexB, 1);
        graph.addEdge(vertexA, vertexC, 2);
        graph.addEdge(vertexB, vertexC, 3);

        Map<Vertex<String>, Double> distances = SortingAlgorithms.dijkstra(graph, vertexA);

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

        Map<Vertex<String>, Double> distances = SortingAlgorithms.bellmanFord(graph, vertexA);

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

        Map<Vertex<String>, Double> distances = SortingAlgorithms.bellmanFord(graph, vertexA);

        assertNull(distances);
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testBellmanFordFromFile(Graph<String, Integer> graph) {
        String filePath = "src/test/resources/inAdjMatrix.txt";
        ReaderMatrix.fillGraphFromFile(graph, filePath);
        graph.addEdge(graph.addVertex("Z"), graph.getVertices().get(0), 60);
        var distances = SortingAlgorithms.bellmanFord(graph, graph.getVertices().get(0));
        Set<Double> setDist = new HashSet<>(distances.values());
        Set<Double> expected = new HashSet<>(Set.of(19.0, 18.0, 23.0, 12.0, 14.0, 
                                                    0.0, 5.0, Double.POSITIVE_INFINITY));
        assertEquals(expected, setDist);
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testEqualsBellmanAndDijkstra(Graph<String, Integer> graph) {
        String filePath = "src/test/resources/inAdjMatrix.txt";
        ReaderMatrix.fillGraphFromFile(graph, filePath);
        Vertex<String> startVertex = graph.getVertices().get(0);
        Map<Vertex<String>, Double> dij = SortingAlgorithms.dijkstra(graph, startVertex);
        Map<Vertex<String>, Double> bell = SortingAlgorithms.bellmanFord(graph, startVertex);
        assertEquals(dij, bell);
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testReadDoubleValuesFromFile(Graph<Integer, Double> graph) {
        String filePath = "src/test/resources/doubleValuesAdjMatrix.txt";
        ReaderMatrix.fillGraphFromFile(graph, filePath);
        Double weight = graph.getEdges().get(0).getWeight();
        assertEquals(1.4, weight);
    }   
}
