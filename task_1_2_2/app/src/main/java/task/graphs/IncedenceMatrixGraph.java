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

    public IncedenceMatrixGraph(int vertexNumber, int edgeNumber) {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        incedenceMatrix = new ArrayList<>();
    }

    @Override
    public void addVertex(V data) {
        var newVertex = new Vertex<>(data);
        vertices.add(newVertex);
        incedenceMatrix.add(new ArrayList<>());
    }

    @Override
    public void removeVertex(Vertex<V> node) {
        var index = vertices.indexOf(node);
        for (int i = 0; i < edges.size(); i++) {
            if (incedenceMatrix.get(index).get(i) != null) {
                removeEdge(edges.get(i));
            }
        }
        incedenceMatrix.remove(index);
        vertices.remove(index);
    }

    @Override
    public void changeVertex(Vertex<V> node, V newName) {
        node.setName(newName);
    }

    @Override
    public void addEdge(Vertex<V> from, Vertex<V> to, E weight) {
        var newEdge = new Edge<>(from, to, weight);
        edges.add(newEdge);
        var indexFrom = vertices.indexOf(from);
        var indexTo = vertices.indexOf(to);
        for (int i = 0; i < incedenceMatrix.size(); i++) {
            if (i == indexFrom) {
                incedenceMatrix.get(i).add(true);
            } else if (i == indexTo) {
                incedenceMatrix.get(i).add(false);
            } else {
                incedenceMatrix.get(i).add(null);
            }
        }
    }

    @Override
    public void removeEdge(Edge<V, E> edge) {
        var index = edges.indexOf(edge);
        for (int i = 0; i < incedenceMatrix.size(); i++) {
            incedenceMatrix.get(i).remove(index);
        }
        edges.remove(index);
    }

    @Override
    public void changeEdge(Edge<V, E> edge, E newWeight) {
        edge.setWeight(newWeight);
    }

    @Override
    public List<Vertex<V>> getVertices() {
        return vertices;
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
