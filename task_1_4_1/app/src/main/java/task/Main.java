package task;

public class Main {
    public static void main(String[] args) {
        try {
            GradeBook book = new GradeBook("Ivanov Ivan Ivanovich", 4);
            book.writeInGradeBook(1, "Math", 5, true);
            book.writeInGradeBook(1, "Rus", 3, true);
            book.writeInGradeBook(1, "Haskell", 3, false);
            book.writeInGradeBook(2, "SQL", 2, false);
            book.writeInGradeBook(4, "Diploma", 5, true);
            book.removeFromGradeBook(1, "Rus");
            System.out.println(book.averageMark());
            System.out.println(book.redDiploma());
            System.out.println(book.scholarship(1));
            System.out.println(book.toString());
        } catch (Exception e) {
            var message = e.getMessage();
            System.out.println(message);
        }
        
    }
}
