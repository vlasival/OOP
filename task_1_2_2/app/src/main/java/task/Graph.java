
package task;

import java.util.List;

public interface Graph<V, E extends Number> {
    Vertex<V> addVertex(V data);
    void removeVertex(Vertex<V> node);
    void changeVertex(Vertex<V> node, V newName);
    Edge<V,E> addEdge(Vertex<V> from, Vertex<V> to, E weight);
    void removeEdge(Edge<V,E> e);
    void changeEdge(Edge<V,E> edge, E newWeight);
    List<Vertex<V>> getVertices();
    List<Edge<V,E>> getEdges(Vertex<V> node);
}
