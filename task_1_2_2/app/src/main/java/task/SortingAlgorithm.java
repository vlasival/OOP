import java.util.List;

public class SortingAlgorithm<V, E extends Number> {
    public void findShortestPaths(Graph<V, E> graph, Vertex<V> source) {
        List<Vertex<V>> vertices = graph.getVertices();
        List<Edge<V, E>> edges = graph.getEdges(source);

        // Инициализация расстояний до всех вершин, начальная вершина имеет расстояние 0, остальные - бесконечность
        for (Vertex<V> vertex : vertices) {
            if (vertex.equals(source)) {
                vertex.setDistance(0);
            } else {
                vertex.setDistance(Double.POSITIVE_INFINITY);
            }
        }

        // Релаксация рёбер многократно (|V| - 1) раз
        for (int i = 0; i < vertices.size() - 1; i++) {
            for (Edge<V, E> edge : edges) {
                Vertex<V> from = edge.getFrom();
                Vertex<V> to = edge.getTo();
                E weight = edge.getWeight();

                if (from.getDistance() + weight.doubleValue() < to.getDistance()) {
                    to.setDistance(from.getDistance() + weight.doubleValue());
                    to.setPredecessor(from);
                }
            }
        }

        // Проверка на наличие отрицательных циклов
        for (Edge<V, E> edge : edges) {
            Vertex<V> from = edge.getFrom();
            Vertex<V> to = edge.getTo();
            E weight = edge.getWeight();

            if (from.getDistance() + weight.doubleValue() < to.getDistance()) {
                throw new RuntimeException("Граф содержит отрицательный цикл");
            }
        }
    }
}
