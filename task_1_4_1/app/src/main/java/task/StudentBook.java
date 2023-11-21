
package task;

import java.util.ArrayList;

public class StudentBook {
    private ArrayList<MarkPage> pages = new ArrayList<>();
    private int currentSemester;
    private String studentName;

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        String[] nameParts = studentName.split(" ");
        // checking that the string doesn't contain numbers and comprices 3 words.
        if (nameParts.length != 3) {
            throw new 
        }
        if (studentName.matches(".*\\d.*")) {

        }
        this.studentName = studentName;
    }
    public int getCurrentSemester() {
        return currentSemester;
    }
    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }
    

}

