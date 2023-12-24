
package task.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import task.graphmodel.Edge;
import task.graphmodel.Graph;
import task.graphmodel.Vertex;

/**
 * Implementation of the Graph interface using an adjacency list representation.
 *
 * @param <V> the type of data stored in the vertices
 * @param <E> the type of weight associated with the edges (must extend Number)
 */
public class AdjacencyListGraph<V, E extends Number> implements Graph<V, E> {
    /**
     * A map that stores vertices as keys and their corresponding outgoing edges as values.
     */
    Map<Vertex<V>, ArrayList<Edge<V, E>>> adjacencyMap;

    /**
     * A list to store all edges in the graph.
     */
    List<Edge<V, E>> edges;

    /**
     * Constructs a new AdjacencyListGraph with an empty adjacency map and an empty list of edges.
     */
    public AdjacencyListGraph() {
        adjacencyMap = new HashMap<>();
        edges = new ArrayList<>();
    }

    /**
     * Adds a new vertex with the specified data to the graph.
     *
     * @param data the data to be stored in the new vertex
     * @return the newly added vertex
     */
    @Override
    public Vertex<V> addVertex(V data) {
        var newVertex = new Vertex<>(data);
        adjacencyMap.put(newVertex, new ArrayList<Edge<V, E>>());
        return newVertex;
    }

    /**
     * Removes the specified vertex from the graph.
     *
     * @param node the vertex to be removed
     */
    @Override
    public void removeVertex(Vertex<V> node) {
        if (!adjacencyMap.keySet().contains(node)) {
            System.err.println("Removing vertex doesn't exist.");
            return;
        }
        adjacencyMap.remove(node);
        adjacencyMap.values()
            .forEach(edges -> edges.removeIf(edge -> edge.getFrom().equals(node) 
                                            || edge.getTo().equals(node)));
    }

    /**
     * Changes the data of the specified vertex in the graph.
     *
     * @param node    the vertex whose data is to be changed
     * @param newName the new data to be assigned to the vertex
     */
    @Override
    public void changeVertex(Vertex<V> node, V newName) {
        if (!adjacencyMap.keySet().contains(node)) {
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
        Edge<V, E> newEdge = new Edge<>(from, to, weight);
        adjacencyMap.get(from).add(newEdge);
        edges.add(newEdge);
        return newEdge;
    }

    /**
     * Removes the specified edge from the graph.
     *
     * @param edge the edge to be removed
     */
    @Override
    public void removeEdge(Edge<V, E> edge) {
        if (!adjacencyMap.keySet().contains(edge.getFrom())) {
            System.err.println("Removing edge doesn't exist.");
            return;
        }
        adjacencyMap.get(edge.getFrom()).remove(edge);
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
        if (!adjacencyMap.keySet().contains(edge.getFrom())) {
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
        return new ArrayList<>(adjacencyMap.keySet());
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
        return adjacencyMap.values().stream()
                .flatMap(list -> list.stream())
                .filter(edge -> edge.getFrom().equals(node))
                .collect(Collectors.toList());
    }
}
