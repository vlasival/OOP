
package task.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import task.exceptions.GraphOperationException;
import task.graphModel.Edge;
import task.graphModel.Graph;
import task.graphModel.Vertex;

public class AdjacencyListGraph<V, E extends Number> implements Graph<V, E> {
    Map<Vertex<V>, ArrayList<Edge<V,E>>> adjacencyMap;

    public AdjacencyListGraph() {
        adjacencyMap = new HashMap<>();
    }

    @Override
    public void addVertex(V data) {
        var newVertex = new Vertex<>(data);
        adjacencyMap.put(newVertex, new ArrayList<Edge<V,E>>());
    }

    @Override
    public void removeVertex(Vertex<V> node) {
        if (!adjacencyMap.keySet().contains(node)) {
            throw new GraphOperationException("Removing vertex doesn't exist.");
        }
        adjacencyMap.remove(node);
        adjacencyMap.values()
            .forEach(edges -> edges.removeIf(edge -> edge.getFrom().equals(node) 
                                            || edge.getTo().equals(node)));
    }

    @Override
    public void changeVertex(Vertex<V> node, V newName) {
        if (!adjacencyMap.keySet().contains(node)) {
            throw new GraphOperationException("Changing vertex doesn't exist.");
        }
        node.setName(newName);
    }

    @Override
    public void addEdge(Vertex<V> from, Vertex<V> to, E weight) {
        Edge<V,E> newEdge = new Edge<>(from, to, weight);
        adjacencyMap.get(from).add(newEdge);
    }

    @Override
    public void removeEdge(Edge<V, E> edge) {
        if (!adjacencyMap.keySet().contains(edge.getFrom())) {
            throw new GraphOperationException("Removing edge doesn't exist.");
        }
        adjacencyMap.get(edge.getFrom()).remove(edge);
    }

    @Override
    public void changeEdge(Edge<V, E> edge, E newWeight) {
        if (!adjacencyMap.keySet().contains(edge.getFrom())) {
            throw new GraphOperationException("Changing edge doesn't exist.");
        }
        edge.setWeight(newWeight);
    }

    @Override
    public List<Vertex<V>> getVertices() {
        return new ArrayList<>(adjacencyMap.keySet());
    }

    @Override
    public List<Edge<V, E>> getIncidentEdges(Vertex<V> node) {
        return adjacencyMap.values().stream()
                .flatMap(list -> list.stream())
                .filter(edge -> edge.getTo().equals(node))
                .collect(Collectors.toList());
    }
}
