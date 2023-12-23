
package task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraph<V, E extends Number> implements Graph<V, E> {
    Map<Vertex<V>, ArrayList<Edge<V,E>>> adjacencyMap;

    public AdjacencyListGraph() {
        adjacencyMap = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(V data) {
        var newVertex = new Vertex<>(data);
        adjacencyMap.put(newVertex, new ArrayList<Edge<V,E>>());
        return newVertex;
    }

    @Override
    public void removeVertex(Vertex<V> node) {
        adjacencyMap.remove(node);
        for (var i : adjacencyMap.entrySet()) {
            for (var j : i.getValue()) {
                if (j.getTo() == node) {
                    adjacencyMap.get(i.getKey()).remove(j);
                }
            }
        }
    }

    @Override
    public void changeVertex(Vertex<V> node, V newName) {
        node.setName(newName);
    }

    @Override
    public Edge<V, E> addEdge(Vertex<V> from, Vertex<V> to, E weight) {
        var newEdge = new Edge<>(from, to, weight);
        adjacencyMap.get(from).add(newEdge);
        return newEdge;
    }

    @Override
    public void removeEdge(Edge<V, E> edge) {
        for (var i : adjacencyMap.entrySet()) {
            if (i.getValue().contains(edge)) {
                i.getValue().remove(edge);
                break;
            }
        }
    }

    @Override
    public void changeEdge(Edge<V, E> edge, E newWeight) {
        edge.setWeight(newWeight);
    }

    @Override
    public List<Vertex<V>> getVertices() {
        var listVertices = new ArrayList<Vertex<V>>();
        for (var i : adjacencyMap.keySet()) {
            listVertices.add(i);
        }
        return listVertices;
    }

    @Override
    public List<Edge<V, E>> getEdges(Vertex<V> node) {
        return adjacencyMap.get(node);
    }
}
