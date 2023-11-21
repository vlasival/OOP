
package task;

import java.util.ArrayList;

public class GradeBook {
    private MarksPage[] pages;
    private int pageCount;
    private String studentName;

    public GradeBook(String name, int pageCount) throws GradeException {
        pages = new MarksPage[pageCount];
        for (var i : pages) {
            i = new MarksPage();
        }
        this.pageCount = pageCount;
        this.setStudentName(name);
    }

    public String getStudentName() {
        return studentName;
    }

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

    public void writeInGradeBook(int currentSemester, 
                                String subject,
                                int mark,
                                boolean differentiated) throws GradeException {
        if (currentSemester < 0 || currentSemester > pageCount) {
            throw new GradeException("Semester must be between 1 and " + pageCount);
        }
        pages[currentSemester - 1].addNode(subject, mark, differentiated);
    }

    public boolean scholarship(int semester) {
        MarksPage page = pages[semester - 1];
        for (var i : page.getNotes()) {
            if (i.getMark() != 5) {
                return false;
            }
        }
        return true;
    } 

    public boolean redDiploma() {
        ArrayList<String> visited = new ArrayList<>();
        float sumMarks = 0f;
        int countMarks = 0;
        for (int i = pageCount - 1; i >= 0; i--) {
            var temp = pages[i];
            for (var j : temp.getNotes()) {
                if (!visited.contains(j.getSubject())) {
                    if (j.getMark() <= 3) {
                        return false;
                    }
                    
                    visited.add(j.getSubject());
                    sumMarks += (float) j.getMark();
                    countMarks++;
                }
            }
        }

    }
}

