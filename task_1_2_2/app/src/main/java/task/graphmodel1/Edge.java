package task.graphmodel1;

/**
 * Represents an edge in a graph connecting two vertices with a specified weight.
 *
 * @param <V> the type of data stored in the vertices
 * @param <E> the type of weight associated with the edge (must extend Number)
 */
public class Edge<V, E extends Number> {
    private Vertex<V> from;
    private Vertex<V> to;
    private E weight;

    /**
     * Constructs a new edge with the specified source and destination vertices and weight.
     *
     * @param from   the source vertex
     * @param to     the destination vertex
     * @param weight the weight associated with the edge
     */
    public Edge(Vertex<V> from, Vertex<V> to, E weight) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    /**
     * Gets the weight of the edge.
     *
     * @return the weight of the edge
     */
    public E getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the edge.
     *
     * @param weight the new weight to be set
     */
    public void setWeight(E weight) {
        this.weight = weight;
    }

    /**
     * Gets the source vertex of the edge.
     *
     * @return the source vertex
     */
    public Vertex<V> getFrom() {
        return from;
    }

    /**
     * Sets the source vertex of the edge.
     *
     * @param from the new source vertex to be set
     */
    public void setFrom(Vertex<V> from) {
        this.from = from;
    }

    /**
     * Gets the destination vertex of the edge.
     *
     * @return the destination vertex
     */
    public Vertex<V> getTo() {
        return to;
    }

    /**
     * Sets the destination vertex of the edge.
     *
     * @param to the new destination vertex to be set
     */
    public void setTo(Vertex<V> to) {
        this.to = to;
    }
}
