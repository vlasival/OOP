package task;

import java.io.IOException;

public class Main {
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) throws IOException {
        var adjMatrix = ReadMatrixFromFile.readMatrix("task_1_2_2/app/src/main/java/task/inAdjMatrix.txt");
    }
}
