package task.entryPoint1;

import java.io.IOException;

import task.graphmodel1.Graph;
import task.graphs.AdjacencyMatrixGraph;
import task.resources.ReaderMatrix;

/**
 * Main class.
 */
public class Main {
    /**
     * Entry point.
     *
     * @param args agruments
     * @throws IOException exception
     */
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) throws IOException {
        Graph<String, Integer> graph = new AdjacencyMatrixGraph<>();
        ReaderMatrix.fillGraphFromFile(graph, "app/src/main/java/task/resources/inAdjMatrix.txt");
        System.out.println(graph.getIncidentEdges(graph.getVertices().get(0)).size());
        System.out.println(graph.getIncidentEdges(graph.getVertices().get(1)).size());
        System.out.println(graph.getIncidentEdges(graph.getVertices().get(2)).size());
        System.out.println(graph.getIncidentEdges(graph.getVertices().get(3)).size());
        System.out.println(graph.getIncidentEdges(graph.getVertices().get(4)).size());
        System.out.println(graph.getIncidentEdges(graph.getVertices().get(5)).size());
        System.out.println(graph.getIncidentEdges(graph.getVertices().get(6)).size());
    }
}
