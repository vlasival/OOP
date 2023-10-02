package task;

/**
 * Main class. This class contains the entry point of the application.
 */
public class Main {
    /**
     * The main entry point of the application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        Polynomial p1 = new Polynomial(new int[] {0, 1, 2, 0, 1});
        Polynomial p2 = new Polynomial(new int[] {-3, -2, 0, 1, 4});
        System.out.println(p1.minus(p2));
    }
}
