package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StudentBookTest {
    GradeBook testBook = testData();

    GradeBook testData() {
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
            book.writeInGradeBook(4, "Models", 5, true);
            book.writeInGradeBook(4, "Philosophy", 5, true);
            return book;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        
    }

    @Test
    public void setAndGetStudentNameTest() {
        try {
            testBook.setStudentName("Ivanov Ivan Ivanovich");
        } catch (GradeException e) {
            System.out.println(e.getMessage());
        }
        assertEquals("Ivanov Ivan Ivanovich", testBook.getStudentName());
    }

    @Test
    public void setStudentIncorrectNameFormatExceptionTest() {
        try {
            testBook.setStudentName("Ivan Ivanovich");
        } catch (GradeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void setStudentDigitsInNameExceptionTest() {
        try {
            testBook.setStudentName("Ivanov I4an Ivanovich");
        } catch (GradeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void incorrectPageCountTest() {
        try {
            testBook.writeInGradeBook(10, null, 3, false);
        } catch (GradeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void incorrectMarktTest() {
        try {
            testBook.writeInGradeBook(3, null, 187, true);
        } catch (GradeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void writeInAndRemoveFromGradeBookTest() {
        var newBook = testData();
        try {
            testBook.writeInGradeBook(3, "Fizra", 5, false);
            testBook.removeFromGradeBook(3, "Fizra");
        } catch (GradeException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(newBook, testBook);
    }
    
    @Test
    public void scholarshipTest() {
        assertTrue(testBook.scholarship(4));
    }

    @Test
    public void redDiplomaTest() {
        assertFalse(testBook.redDiploma());
    }
    
    @Test
    public void redDiplomaGoodFinalTest() {
        GradeBook newBook = null;
        try {
            newBook = new GradeBook("Petrov Petr Petrovich", 1);
            newBook.writeInGradeBook(1, "Math", 5, true);
            newBook.writeInGradeBook(1, "Diploma", 5, true);
        } catch (GradeException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(newBook.redDiploma());
    }

    @Test
    public void averageMarkTest() {
        assertEquals(4.529412f, testBook.averageMark());
    }

    @Test
    public void hashCodeTest() {
        var newBook = testData();
        newBook.removeFromGradeBook(1, "Haskell");
        try {
            newBook.writeInGradeBook(1, "Haskell", 5, true);
        } catch (GradeException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(testBook.hashCode(), newBook.hashCode());
    }

    @Test
    public void toStringTest() {
        try {
            var newBook = new GradeBook("Palkin Igor Alexandrovich", 2);
            newBook.writeInGradeBook(1, "Math", 4, true);
            newBook.writeInGradeBook(2, "Rus", 4, true);
            newBook.writeInGradeBook(2, "Phis", 4, true);
        } catch (GradeException e) {
            System.out.println(e.getMessage());
        }
    }
}
