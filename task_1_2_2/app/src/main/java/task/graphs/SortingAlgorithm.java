package task.graphs;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import task.graphModel.Edge;
import task.graphModel.Graph;
import task.graphModel.Vertex;

public class SortingAlgorithm<V, E extends Number> {
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
