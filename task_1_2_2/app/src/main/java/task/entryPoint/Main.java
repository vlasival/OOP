package task.entryPoint;

import java.io.IOException;

import task.graphModel.Graph;
import task.graphs.IncedenceMatrixGraph;
import task.resources.ReaderMatrix;



public class Main {
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) throws IOException {
        Graph<String, Integer> graph = new IncedenceMatrixGraph<>();
        ReaderMatrix.fillGraphFromFile(graph, "app/src/main/java/task/resources/inAdjMatrix.txt");
        var e = graph.getIncidentEdges(graph.getVertices().get(6));
        for (var i : e) {
            System.out.println(i.getWeight());
        }
    }
}
