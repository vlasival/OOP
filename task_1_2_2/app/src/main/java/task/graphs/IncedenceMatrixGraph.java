package task.graphs;

import java.util.ArrayList;
import java.util.List;

import task.graphModel.Edge;
import task.graphModel.Graph;
import task.graphModel.Vertex;

public class IncedenceMatrixGraph<V, E extends Number> implements Graph<V, E> {
    private List<List<Boolean>> incedenceMatrix;
    private List<Vertex<V>> vertices;
    private List<Edge<V,E>> edges;

    public IncedenceMatrixGraph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        incedenceMatrix = new ArrayList<>();
    }

    @Override
    public Vertex<V> addVertex(V data) {
        Vertex<V> newVertex = new Vertex<>(data);
        vertices.add(newVertex);
        incedenceMatrix.add(new ArrayList<>());
        return newVertex;
    }

    @Override
    public void removeVertex(Vertex<V> node) {
        if (!vertices.contains(node)) {
            System.err.println("Removing vertex doesn't exist.");
            return;
        }
        var index = vertices.indexOf(node);
        for (var i : edges) {
            if (i.getFrom().equals(node) || i.getTo().equals(node)) {
                removeEdge(i);
            }
        }
        incedenceMatrix.remove(index);
        vertices.remove(index);
    }

    @Override
    public void changeVertex(Vertex<V> node, V newName) {
        if (!vertices.contains(node)) {
            System.err.println("Removing vertex doesn't exist.");
            return;
        }
        node.setName(newName);
    }

    @Override
    public Edge<V,E> addEdge(Vertex<V> from, Vertex<V> to, E weight) {
        Edge<V,E> newEdge = new Edge<>(from, to, weight);
        edges.add(newEdge);
        for (int i = 0; i < incedenceMatrix.size(); i++) {
            incedenceMatrix.get(i).add(false);
        }
        var indexFrom = vertices.indexOf(from);
        int currLineSize = incedenceMatrix.get(indexFrom).size();
        incedenceMatrix.get(indexFrom).set(currLineSize - 1, true);
        return newEdge;
    }

    @Override
    public void removeEdge(Edge<V, E> edge) {
        if (!edges.contains(edge)) {
            System.err.println("Removing edge doesn't exist.");
            return;
        }
        var index = edges.indexOf(edge);
        for (int i = 0; i < incedenceMatrix.size(); i++) {
            incedenceMatrix.get(i).remove(index);
        }
        edges.remove(index);
    }

    @Override
    public void changeEdge(Edge<V, E> edge, E newWeight) {
        if (!edges.contains(edge)) {
            System.err.println("Changing edge doesn't exist.");
            return;
        }
        edge.setWeight(newWeight);
    }

    @Override
    public List<Vertex<V>> getVertices() {
        return vertices;
    }

    @Override
    public List<Edge<V, E>> getEdges() {
        return edges;
    }

    @Override
    public List<Edge<V, E>> getIncidentEdges(Vertex<V> node) {
        List<Edge<V,E>> listEges = new ArrayList<Edge<V,E>>();
        var index = vertices.indexOf(node);
        for (int i = 0; i < edges.size(); i++) {
            var tmp = incedenceMatrix.get(index).get(i);
            if (tmp) {
                listEges.add(edges.get(i));
            }
        }
        return listEges;
    }
}
