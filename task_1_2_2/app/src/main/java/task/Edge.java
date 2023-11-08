
package task;

public class Edge<V, E extends Number> {
    private Vertex<V> from;
    private Vertex<V> to;
    private E weight;

    public Edge(Vertex<V> from, Vertex<V> to, E weight) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    public E getWeight() {
        return weight;
    }

    public void setWeight(E weight) {
        this.weight = weight;
    }

    public Vertex<V> getFrom() {
        return from;
    }

    public void setFrom(Vertex<V> from) {
        this.from = from;
    }

    public Vertex<V> getTo() {
        return to;
    }

    public void setTo(Vertex<V> to) {
        this.to = to;
    } 
}
