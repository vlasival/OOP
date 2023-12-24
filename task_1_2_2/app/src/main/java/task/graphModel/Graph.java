package task.graphmodel;

import java.util.List;

/**
 * An interface representing a generic graph with vertices and edges.
 *
 * @param <V> the type of data stored in the vertices
 * @param <E> the type of weight associated with the edges (must extend Number)
 */
public interface Graph<V, E extends Number> {
    /**
     * Adds a new vertex with the specified data to the graph.
     *
     * @param data the data to be stored in the new vertex
     * @return the newly added vertex
     */
    Vertex<V> addVertex(V data);

    /**
     * Removes the specified vertex from the graph.
     *
     * @param node the vertex to be removed
     */
    void removeVertex(Vertex<V> node);

    /**
     * Changes the data of the specified vertex in the graph.
     *
     * @param node    the vertex whose data is to be changed
     * @param newName the new data to be assigned to the vertex
     */
    void changeVertex(Vertex<V> node, V newName);

    /**
     * Adds a new edge with the specified source, destination vertices, and weight to the graph.
     *
     * @param from   the source vertex of the new edge
     * @param to     the destination vertex of the new edge
     * @param weight the weight associated with the new edge
     * @return the newly added edge
     */
    Edge<V, E> addEdge(Vertex<V> from, Vertex<V> to, E weight);

    /**
     * Removes the specified edge from the graph.
     *
     * @param e the edge to be removed
     */
    void removeEdge(Edge<V, E> e);

    /**
     * Changes the weight of the specified edge in the graph.
     *
     * @param edge      the edge whose weight is to be changed
     * @param newWeight the new weight to be assigned to the edge
     */
    void changeEdge(Edge<V, E> edge, E newWeight);

    /**
     * Gets a list of all vertices in the graph.
     *
     * @return a list of vertices in the graph
     */
    List<Vertex<V>> getVertices();

    /**
     * Gets a list of all edges in the graph.
     *
     * @return a list of edges in the graph
     */
    List<Edge<V, E>> getEdges();

    /**
     * Gets a list of outgoing edges from the specified vertex in the graph.
     *
     * @param node the vertex for which to retrieve connected edges
     * @return a list of edges connected to the specified vertex
     */
    List<Edge<V, E>> getIncidentEdges(Vertex<V> node);

    
}
