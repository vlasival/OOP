
package task;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Gradle book class implements methods to create, edit, remove electronic student grade book.
 * Also it has some methods for displaying additional information.
 */
public class GradeBook {
    /**
     * Array of marks pages.
     */
    private MarksPage[] pages;

    /**
     * Count of pages (semesters) in gradebook.
     */
    private int pageCount;

    /**
     * Student name in format: last name, first name, patronymic.
     */
    private String studentName;

    /**
     * Class constructor.
     *
     * @param studentName name in the format: first name, last name, patronymic.
     * @param pageCount count of pages in Grade Book. 
     * @throws GradeException special exception for GradeBook class. Contains information message.
     */
    public GradeBook(String studentName, int pageCount) throws GradeException {
        pages = new MarksPage[pageCount];
        for (int i = 0; i < pageCount; i++) {
            pages[i] = new MarksPage();
        }
        this.pageCount = pageCount;
        this.setStudentName(studentName);
    }

    /**
     * Getter for student name.
     *
     * @return full student name.
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Setter for student name. 
     *
     * @param studentName a name that will be checked to match the format.
     * @throws GradeException special exception for GradeBook class. Contains information message.
     */
    public void setStudentName(String studentName) throws GradeException {
        String[] nameParts = studentName.split(" ");
        if (nameParts.length != 3) {
            throw new GradeException("\"" + studentName + "\"" + 
                                            " is not supported in this record book.\n" + 
                                            "Please, Make sure your name is in the format:\n" + 
                                            "last name, first name, patronymic.\n");
        } 
        if (studentName.matches(".*\\d.*")) {
            throw new GradeException("\"" + studentName + "\"" +
                                            " contains numbers. Fix this.\n");
        }
        this.studentName = studentName;
    }

    /**
     * Method adds new entry to a GradeBook.
     *
     * @param currentSemester number of page (semester) in GradeBook. <br>
     *                          Must be between 1 and pageCount.
     * @param subject name of the subject.
     * @param mark grade for this subject.
     * @param differentiated boolean variable. True if mark is differentiated, false otherwise.
     * @throws GradeException special exception for GradeBook class. Contains information message.
     */
    public void writeInGradeBook(int currentSemester, 
                                String subject,
                                int mark,
                                boolean differentiated) throws GradeException {
        if (currentSemester < 1 || currentSemester > pageCount) {
            throw new GradeException("Semester must be between 1 and " + pageCount);
        }
        pages[currentSemester - 1].addNode(subject, mark, differentiated);
    }

    /**
     * Method removes a note from gradebook.
     *
     * @param currentSemester number of page (semester) in GradeBook.
     * @param subject name of the subject to remove.
     */
    public void removeFromGradeBook(int currentSemester, String subject) {
        var currSemGrades = pages[currentSemester - 1].getNotes();
        for (var i : currSemGrades) {
            if (i.getSubject() == subject) {
                currSemGrades.remove(i);
                return;
            }
        }
    }

    /**
     * Methods return ability to get increased scholarship.
     *
     * @param semester number of page (semester) in GradeBook.
     * @return true if is able to get increased scholarship this semester, false otherwise.
     */
    public boolean scholarship(int semester) {
        MarksPage page = pages[semester - 1];
        for (var i : page.getNotes()) {
            if (i.getMark() != 5) {
                return false;
            }
        }
        return true;
    } 

    /**
     * Methods return ability to get red diploma.
     *
     * @return true if is able to get red diploma, false otherwise.
     */
    public boolean redDiploma() {
        ArrayList<String> visited = new ArrayList<>();
        int goodMarks = 0;
        int countMarks = 0;
        boolean diplomWritten = false;
        for (int i = pageCount - 1; i >= 0; i--) {
            var temp = pages[i];
            for (var j : temp.getNotes()) {
                if (!visited.contains(j.getSubject())) {
                    if (j.getMark() <= 3) {
                        return false;
                    }
                    if (j.getSubject() == "Diploma") {
                        diplomWritten = true;
                        if (j.getMark() < 5) {
                            return false;
                        }
                    }
                    visited.add(j.getSubject());
                    if (j.getMark() == 5) {
                        goodMarks++;
                    }
                    countMarks++;
                }
            }
        }
        if (!diplomWritten) {
            return false;
        }
        float goodMarkPercent = (float) goodMarks / (float) countMarks;
        if (goodMarkPercent < 0.75) {
            return false;
        }
        return true;
    }

    /**
     * Methods return average grade for all semesters.
     *
     * @return float number of average grade.
     */
    public float averageMark() {
        float total = 0f;
        int countMarks = 0;
        for (int i = pageCount - 1; i >= 0; i--) {
            var temp = pages[i];
            for (var j : temp.getNotes()) {
                total += (float) j.getMark();
                countMarks++;
            }
        }
        return total / (float) countMarks;
    }

    /**
     * Overrided hashCode.
     *
     * @return hash of GradeBook object.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(pages);
        result = prime * result + pageCount;
        result = prime * result + ((studentName == null) ? 0 : studentName.hashCode());
        return result;
    }

    /**
     * Overrided equals.
     *
     * @param obj Object to compare.
     * @return true if GradeBooks are equals.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GradeBook other = (GradeBook) obj;
        if (!Arrays.equals(pages, other.pages))
            return false;
        if (pageCount != other.pageCount)
            return false;
        if (studentName == null) {
            if (other.studentName != null)
                return false;
        } else if (!studentName.equals(other.studentName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < pageCount; i++) {
            var currPage = pages[i];
            res.append("Semester number " + (i + 1) + "\n");
            for (var j : currPage.getNotes()) {
                res.append("    " + j.getSubject() + " " + j.getMark() + "\n");
            }
        }
        return res.toString();
    }
}

