package task.graphs;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import task.graphModel.Edge;
import task.graphModel.Graph;
import task.graphModel.Vertex;

/**
 * Sorting algorithms for weighted graphs.
 *
 * @param <V> the type of data stored in the vertices
 * @param <E> the type of weight associated with the edges (must extend Number)
 */
public class SortingAlgorithm<V, E extends Number> {

    /**
     * Applies Dijkstra's algorithm to find the shortest paths from a source vertex to all other vertices in a graph.
     *
     * @param graph  the graph to analyze
     * @param source the source vertex
     * @return a map of vertices to their respective shortest distances from the source
     */
    public static <V, E extends Number> Map<Vertex<V>, Double> dijkstra(Graph<V, E> graph, Vertex<V> source) {
        Map<Vertex<V>, Double> distances = new HashMap<>();
        PriorityQueue<Vertex<V>> minHeap = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        
        distances.put(source, 0.0);
        minHeap.add(source);

        while (!minHeap.isEmpty()) {
            Vertex<V> current = minHeap.poll();

            for (Edge<V, E> edge : graph.getIncidentEdges(current)) {
                Vertex<V> neighbor = edge.getTo();
                double newDistance = distances.get(current) + edge.getWeight().doubleValue();

                if (!distances.containsKey(neighbor) || newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    minHeap.add(neighbor);
                }
            }
        }

        return distances;
    }

    /**
     * Applies the Bellman-Ford algorithm to find the shortest paths from a source vertex to all other vertices in a graph.
     *
     * @param graph  the graph to analyze
     * @param source the source vertex
     * @return a map of vertices to their respective shortest distances from the source, or null if a negative cycle is detected
     */
    public static <V, E extends Number> Map<Vertex<V>, Double> bellmanFord(Graph<V, E> graph, Vertex<V> source) {
        Map<Vertex<V>, Double> distances = new HashMap<>();
        distances.put(source, 0.0);

        for (int i = 0; i < graph.getVertices().size() - 1; i++) {
            for (Edge<V, E> edge : graph.getEdges()) {
                Vertex<V> from = edge.getFrom();
                Vertex<V> to = edge.getTo();
                if (distances.get(from) == null) {
                    continue;
                }
                double newDistance = distances.get(from) + edge.getWeight().doubleValue();

                if (!distances.containsKey(to) || newDistance < distances.get(to)) {
                    distances.put(to, newDistance);
                }
            }
        }

        for (Edge<V, E> edge : graph.getEdges()) {
            Vertex<V> from = edge.getFrom();
            Vertex<V> to = edge.getTo();
            if (distances.get(from) == null) {
                distances.put(from, Double.POSITIVE_INFINITY);
                continue;
            }
            double newDistance = distances.get(from) + edge.getWeight().doubleValue();

            if (newDistance < distances.get(to)) {
                System.err.println("Graph contains a negative cycle.");
                return null;
            }
        }

        return distances;
    }
}
