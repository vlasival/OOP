package task.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import task.graphModel.Edge;
import task.graphModel.Graph;
import task.graphModel.Vertex;

public class AdjacencyMatrixGraph<V, E extends Number> implements Graph<V, E> {
    private List<Vertex<V>> vertices;
    private List<Edge<V,E>> edges;
    private List<List<E>> adjacencyMatrix;

    public AdjacencyMatrixGraph(int size) {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        adjacencyMatrix = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            adjacencyMatrix.add(new ArrayList<E>());
            for (int j = 0; j < size; j++) {
                adjacencyMatrix.get(i).add(null);
            }
        }
    }

    @Override
    public void addVertex(V data) {
        var newVertex = new Vertex<V>(data);
        vertices.add(newVertex);
        adjacencyMatrix.add(new ArrayList<E>());
    }

    @Override
    public void removeVertex(Vertex<V> node) {
        if (!vertices.contains(node)) {
            System.err.println("Removing vertex doesn't exist.");
            return;
        }
        int index = vertices.indexOf(node);
        vertices.remove(index);
        for (var i : edges) {
            if (i.getFrom().equals(node) || i.getTo().equals(node)) {
                removeEdge(i);
            }
        }
        adjacencyMatrix.remove(index);
    }

    @Override
    public void changeVertex(Vertex<V> node, V newName) {
        if (!vertices.contains(node)) {
            System.err.println("Changing vertex doesn't exist.");
            return;
        }
        node.setName(newName);
    }

    @Override
    public void addEdge(Vertex<V> from, Vertex<V> to, E weight) {
        if (!vertices.contains(from) || !vertices.contains(to)) {
            System.err.println("Edge's vertices don't exist.");
            return;
        }
        var newEdge = new Edge<>(from, to, weight);
        edges.add(newEdge);
        adjacencyMatrix.get(vertices.indexOf(from)).set(vertices.indexOf(to), weight);
    }

    @Override
    public void removeEdge(Edge<V,E> edge) {
        if (!edges.contains(edge)) {
            System.err.println("Removing edge doesn't exist.");
            return;
        }
        edges.remove(edge);
        int from = vertices.indexOf(edge.getFrom());
        int to = vertices.indexOf(edge.getTo());
        adjacencyMatrix.get(from).set(to, null);
    }

    @Override
    public void changeEdge(Edge<V,E> edge, E newWeight) {
        if (!edges.contains(edge)) {
            System.err.println("Changing edge doesn't exist.");
            return;
        }
        edge.setWeight(newWeight);
        int from = vertices.indexOf(edge.getFrom());
        int to = vertices.indexOf(edge.getTo());
        adjacencyMatrix.get(from).set(to, newWeight);
    }

    @Override
    public List<Vertex<V>> getVertices() {
        return vertices;
    }

    @Override
    public List<Edge<V, E>> getIncidentEdges(Vertex<V> node) {
        return edges.stream()
                .filter(edge -> edge.getFrom().equals(node))
                .collect(Collectors.toList());
    }
    
}