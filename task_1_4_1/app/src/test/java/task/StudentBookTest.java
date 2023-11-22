package task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class StudentBookTest {
    GradeBook book;

    @BeforeEach
    void setData() {
        try {
            GradeBook book = new GradeBook("Vlasov Vlas Vlasovich", 4);
            book.writeInGradeBook(1, "Math", 5, true);
            book.writeInGradeBook(1, "Rus", 3, false);
            book.writeInGradeBook(1, "Discrete", 4, true);
            book.writeInGradeBook(1, "History", 4, true);
            book.writeInGradeBook(1, "Haskell", 5, true);
            book.writeInGradeBook(1, "Digital Platforms", 5, false);
            book.writeInGradeBook(1, "Imperative Programming", 5, true);
            book.writeInGradeBook(2, "Imperative Programming", 4, true);
            book.writeInGradeBook(2, "SQL", 2, false);
            book.writeInGradeBook(2, "Math", 4, true);
            book.writeInGradeBook(4, "Diploma", 5, true);
            book.writeInGradeBook(3, "Introdution to AI", 5, true);
            book.writeInGradeBook(3, "OOP", 5, true);
            book.writeInGradeBook(3, "Diffurs", 4, true);
            book.writeInGradeBook(3, "OSI", 5, true);
            book.writeInGradeBook(4, "Models", 4, true);
            book.writeInGradeBook(4, "Philosophy", 5, true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void setStudentNameTest() {
        try {
            book.setStudentName("Ivanov Ivan Ivanovich");
        } catch (GradeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getStudentNameTest() {
        assertEquals("Ivanov Ivan Ivanovich", book.getStudentName());
    }

    @Test
    public void writeInAndRemoveFromGradeBookTest() {
        var store = .clone();
        try {
            book.writeInGradeBook(3, "Fizra", 5, false);
            book.removeFromGradeBook(3, "Fizra");
        } catch (GradeException e) {
            System.out.println(e.getMessage());
        }
        assertEquals("Ivanov Ivan Ivanovich", book.);
    }

}
