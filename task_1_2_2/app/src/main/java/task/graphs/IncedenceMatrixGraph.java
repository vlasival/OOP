package task.graphs;

import java.util.ArrayList;
import java.util.List;
import task.graphModel.Edge;
import task.graphModel.Graph;
import task.graphModel.Vertex;

/**
 * Implementation of the Graph interface using an incidence matrix representation.
 *
 * @param <V> the type of data stored in the vertices
 * @param <E> the type of weight associated with the edges (must extend Number)
 */
public class IncedenceMatrixGraph<V, E extends Number> implements Graph<V, E> {
    /**
     * 2D list to represent the incidence matrix.
     */
    private final List<List<Boolean>> incedenceMatrix;

    /**
     * List to store vertices in the graph.
     */
    private final List<Vertex<V>> vertices;

    /**
     * List to store edges in the graph.
     */
    private final List<Edge<V, E>> edges;

    /**
     * Constructs a new IncedenceMatrixGraph with empty lists for vertices, edges, and the incidence matrix.
     */
    public IncedenceMatrixGraph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        incedenceMatrix = new ArrayList<>();
    }

    /**
     * Adds a new vertex with the specified data to the graph.
     *
     * @param data the data to be stored in the new vertex
     * @return the newly added vertex
     */
    @Override
    public Vertex<V> addVertex(V data) {
        Vertex<V> newVertex = new Vertex<>(data);
        vertices.add(newVertex);
        incedenceMatrix.add(new ArrayList<>());
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
        var index = vertices.indexOf(node);
        for (var i : edges) {
            if (i.getFrom().equals(node) || i.getTo().equals(node)) {
                removeEdge(i);
            }
        }
        incedenceMatrix.remove(index);
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
            System.err.println("Removing vertex doesn't exist.");
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
        var index = edges.indexOf(edge);
        for (int i = 0; i < incedenceMatrix.size(); i++) {
            incedenceMatrix.get(i).remove(index);
        }
        edges.remove(index);
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
    public List<Edge<V, E>> getIncidentEdges(Vertex<V> node) {
        List<Edge<V, E>> listEges = new ArrayList<Edge<V, E>>();
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
