package task.entryPoint;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import task.graphModel.Edge;
import task.graphModel.Graph;
import task.graphModel.Vertex;
import task.graphs.AdjacencyListGraph;
import task.graphs.AdjacencyMatrixGraph;
import task.graphs.IncedenceMatrixGraph;
import task.graphs.SortingAlgorithm;
import task.resources.ReaderMatrix;



public class Main {
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
