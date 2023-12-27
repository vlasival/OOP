package task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import task.graphmodel.Edge;
import task.graphmodel.Vertex;

/**
 * Сlass tests methods of graph components.
 */
class GraphModelTest {
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
}
