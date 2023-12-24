package task.graphModel;

/**
 * Represents a vertex in a graph.
 *
 * @param <T> the type of data stored in the vertex
 */
public class Vertex<T> {
    /**
     * The name of the vertex.
     */
    private T name;

    /**
     * Constructs a new vertex with the specified name.
     *
     * @param name the name of the vertex
     */
    public Vertex(T name) {
        this.name = name;
    }

    /**
     * Gets the name of the vertex.
     *
     * @return the name of the vertex
     */
    public T getName() {
        return name;
    }

    /**
     * Sets the name of the vertex.
     *
     * @param name the new name to be assigned to the vertex
     */
    public void setName(T name) {
        this.name = name;
    }
}
