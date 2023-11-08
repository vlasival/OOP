package task;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrixGraph<V, E extends Number> implements Graph<V, E> {
    private List<Vertex<V>> vertices;
    private ArrayList<ArrayList<E>> adjacencyMatrix;

    public AdjacencyMatrixGraph(String[][] adjacencyMatrix) {
        this.vertices = new ArrayList<>();
        this.adjacencyMatrix = parseStringMatrix(adjacencyMatrix);
    }

    private ArrayList<ArrayList<E>> parseStringMatrix (String[][] matrix) {
        int len = matrix[0].length;
        ArrayList<ArrayList<E>> resultMatrix = new ArrayList<>();
        for (int i = 1; i < len; i++) {
            vertices.add(matrix[0][i]);
        }
        for (int i = 1; i < len; i++) {
            for (int j = 1; j < len; j++) {
                resultMatrix[i - 1][j - 1] = Integer.parseInt(matrix[i][j]);
            }
        }
        return resultMatrix;
    }

    // public E[][] getAdjacencyMatrix() {
    //     return adjacencyMatrix;
    // }

    @Override
    public Vertex<V> addVertex(V data) {
        var newVertex = new Vertex<V>(data);
        vertices.add(newVertex);
        adjacencyMatrix.add(new ArrayList<E>(vertices.size()));
        for (var i : adjacencyMatrix) {
            i.ensureCapacity(vertices.size());
        }
        return newVertex;
    }

    @Override
    public void removeVertex(Vertex<V> node) {
        var index = vertices.indexOf(node);
        vertices.remove(index);
        adjacencyMatrix.remove(index);
        for (var i : adjacencyMatrix) {
            i.remove(index);
        }
    }

    @Override
    public void changeVertex(Vertex<V> node, V newName) {
        node.setName(newName);
    }

    @Override
    public Edge<V,E> addEdge(Vertex<V> from, Vertex<V> to, E weight) {
        var newEdge = new Edge<>(from, to, weight);
        var indexFrom = vertices.indexOf(from);
        var indexTo = vertices.indexOf(to);
        adjacencyMatrix.get(indexFrom).set(indexTo, weight);
        return newEdge;
    }

    @Override
    public void removeEdge(Edge<V,E> e) {
        var indexFrom = vertices.indexOf(e.getFrom());
        var indexTo = vertices.indexOf(e.getTo());
        adjacencyMatrix.get(indexFrom).set(indexTo, null);
    }

    @Override
    public void changeEdge(Edge<V,E> edge, E newWeight) {
        edge.setWeight(newWeight);
    }

    @Override
    public List<Vertex<V>> getVertices() {
        return vertices;
    }

    @Override
    public List<Edge<V,E>> getEdges(Vertex<V> node) {
        List<Edge<V,E>> listEdges = new ArrayList<>();
        var index = vertices.indexOf(node);
        for (int i = 0; i < vertices.size(); i++) {
            var tmp = adjacencyMatrix.get(index).get(i);
            if (tmp != null) {
                listEdges.add(new Edge<V,E>(node, vertices.get(i), tmp));
            }
        }
        return listEdges;
    }
    
}
