package task;

/**
 * Main class to demonstrate the functionality of the GradeBook system.
 */
public class Main {
    /**
     * Main method to run the GradeBook demonstration.
     *
     * @param args Command line arguments (not used in this context).
     */
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        try {
            GradeBook newBook = new GradeBook("Petrov Petr Petrovich", 1);
            newBook.writeInGradeBook(1, "Math", 5, true);
            newBook.writeInGradeBook(1, "Rus", 4, true);
            newBook.writeInGradeBook(1, "Diploma", 5, true);
            System.out.println(newBook.toString());
            System.out.println("Average Mark: " + newBook.averageMark());
            System.out.println("Red Diploma Eligibility: " + newBook.redDiploma());
            System.out.println("Scholarship Eligibility for Semester 1: " + newBook.scholarship(1));

        } catch (Exception e) {
            var message = e.getMessage();
            System.out.println(message);
        }
    }
}
