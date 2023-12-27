package task.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import task.graphmodel.Edge;
import task.graphmodel.Graph;
import task.graphmodel.Vertex;

/**
 * Implementation of the Graph interface using an adjacency matrix representation.
 *
 * @param <V> the type of data stored in the vertices
 * @param <E> the type of weight associated with the edges (must extend Number)
 */
public class AdjacencyMatrixGraph<V, E extends Number> implements Graph<V, E> {
    /**
     * List to store vertices in the graph.
     */
    private final List<Vertex<V>> vertices;

    /**
     * List to store edges in the graph.
     */
    private final List<Edge<V, E>> edges;

    /**
     * 2D list to represent the adjacency matrix.
     */
    private final List<List<E>> adjacencyMatrix;

    /**
     * Constructs a new AdjacencyMatrixGraph with empty lists 
     * for vertices, edges, and the adjacency matrix.
     */
    public AdjacencyMatrixGraph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        adjacencyMatrix = new ArrayList<>();
    }

    /**
     * Helper method to resize the adjacency matrix to the specified size.
     *
     * @param newSize the new size for the adjacency matrix
     */
    private void upscaleMatrix(int newSize) {
        for (int i = adjacencyMatrix.size(); i < newSize; i++) {
            adjacencyMatrix.add(new ArrayList<E>());
        }
        for (int i = 0; i < adjacencyMatrix.size(); i++) {
            while (adjacencyMatrix.get(i).size() < newSize) {
                adjacencyMatrix.get(i).add(null);
            }
        }
    }

    /**
     * Adds a new vertex with the specified data to the graph.
     *
     * @param data the data to be stored in the new vertex
     * @return the newly added vertex
     */
    @Override
    public Vertex<V> addVertex(V data) {
        Vertex<V> newVertex = new Vertex<V>(data);
        vertices.add(newVertex);
        upscaleMatrix(vertices.size());
        return newVertex;
    }

    /**
     * Removes the specified vertex from the graph.
     *
     * @param node the vertex to be removed
     */
    @Override
    public void removeVertex(Vertex<V> node) {
        if (!vertices.contains(node)) {
            System.err.println("Removing vertex doesn't exist.");
            return;
        }
        int index = vertices.indexOf(node);
        for (var i : getOutcomeEdges(node)) {
            removeEdge(i);
        }
        for (var i : getIncomeEdges(node)) {
            removeEdge(i);
        }
        adjacencyMatrix.remove(index);
        for (int i = 0; i < adjacencyMatrix.size(); i++) {
            adjacencyMatrix.get(i).remove(index);
        }
        vertices.remove(index);
    }

    /**
     * Changes the data of the specified vertex in the graph.
     *
     * @param node    the vertex whose data is to be changed
     * @param newName the new data to be assigned to the vertex
     */
    @Override
    public void changeVertex(Vertex<V> node, V newName) {
        if (!vertices.contains(node)) {
            System.err.println("Changing vertex doesn't exist.");
            return;
        }
        node.setName(newName);
    }

    /**
     * Adds a new edge with the specified source, destination vertices, and weight to the graph.
     *
     * @param from   the source vertex of the new edge
     * @param to     the destination vertex of the new edge
     * @param weight the weight associated with the new edge
     * @return the newly added edge
     */
    @Override
    public Edge<V, E> addEdge(Vertex<V> from, Vertex<V> to, E weight) {
        upscaleMatrix(vertices.size());
        if (!vertices.contains(from) || !vertices.contains(to)) {
            System.err.println("Edge's vertices don't exist.");
            return null;
        }
        Edge<V, E> newEdge = new Edge<>(from, to, weight);
        edges.add(newEdge);
        int indexFrom = vertices.indexOf(from);
        int indexTo = vertices.indexOf(to);
        adjacencyMatrix.get(indexFrom).set(indexTo, weight);
        return newEdge;
    }

    /**
     * Removes the specified edge from the graph.
     *
     * @param edge the edge to be removed
     */
    @Override
    public void removeEdge(Edge<V, E> edge) {
        if (!edges.contains(edge)) {
            System.err.println("Removing edge doesn't exist.");
            return;
        }
        int from = vertices.indexOf(edge.getFrom());
        int to = vertices.indexOf(edge.getTo());
        adjacencyMatrix.get(from).set(to, null);
        edges.remove(edge);
    }

    /**
     * Changes the weight of the specified edge in the graph.
     *
     * @param edge      the edge whose weight is to be changed
     * @param newWeight the new weight to be assigned to the edge
     */
    @Override
    public void changeEdge(Edge<V, E> edge, E newWeight) {
        if (!edges.contains(edge)) {
            System.err.println("Changing edge doesn't exist.");
            return;
        }
        edge.setWeight(newWeight);
        int from = vertices.indexOf(edge.getFrom());
        int to = vertices.indexOf(edge.getTo());
        adjacencyMatrix.get(from).set(to, newWeight);
    }

    /**
     * Gets a list of all vertices in the graph.
     *
     * @return a list of vertices in the graph
     */
    @Override
    public List<Vertex<V>> getVertices() {
        return vertices;
    }

    /**
     * Gets a list of all edges in the graph.
     *
     * @return a list of edges in the graph
     */
    @Override
    public List<Edge<V, E>> getEdges() {
        return edges;
    }

    /**
     * Gets a list of outgoing edges from the specified vertex in the graph.
     *
     * @param node the vertex for which to retrieve connected edges
     * @return a list of edges connected to the specified vertex
     */
    @Override
    public List<Edge<V, E>> getOutcomeEdges(Vertex<V> node) {
        return edges.stream()
                .filter(edge -> edge.getFrom().equals(node))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets a list of incoming edges from the specified vertex in the graph.
     *
     * @param node the vertex for which to retrieve connected edges
     * @return a list of edges connected to the specified vertex
     */
    @Override
    public List<Edge<V, E>> getIncomeEdges(Vertex<V> node) {
        return edges.stream()
                .filter(edge -> edge.getTo().equals(node))
                .collect(Collectors.toList());
    }
}