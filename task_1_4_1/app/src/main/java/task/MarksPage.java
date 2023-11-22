package task;

import java.util.ArrayList;

/**
 * Represents a page in the gradebook containing subject grades. 
 * Provides methods to add and retrieve grades.
 */
public class MarksPage {
    /**
     * A list of subject grades.
     */
    private ArrayList<Grade> notes;

    /**
     * Constructs a MarksPage object with an empty list of grades.
     */
    public MarksPage() {
        notes = new ArrayList<>();
    }

    /**
     * Adds a new grade entry to the gradebook.
     *
     * @param subject         The name of the subject.
     * @param mark            The grade for this subject.
     * @param differentiated  True if the grading is differentiated, false otherwise.
     * @throws GradeException Special exception for the GradeBook class containing an informative message.
     */
    public void addNote(String subject, int mark, boolean differentiated) throws GradeException {
        Grade newNote = new Grade(subject, mark, differentiated);
        notes.add(newNote);
    }

    /**
     * Retrieves the list of grades on this page.
     *
     * @return The list of grades.
     */
    public ArrayList<Grade> getNotes() {
        return notes;
    }

    /**
     * Sets the list of grades for this page.
     *
     * @param notes The list of grades to set.
     */
    public void setNotes(ArrayList<Grade> notes) {
        this.notes = notes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        for (var i : notes) {
            result += i.hashCode();
        }
        return result * prime;
    }

    /**
     * {@inheritDoc}
     */
    @ExcludeFromJacocoGeneratedReport
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MarksPage other = (MarksPage) obj;
        if (notes == null) {
            if (other.notes != null)
                return false;
        } else if (this.hashCode() != other.hashCode()) {
            return false;
        }
        return true;
    }
}
