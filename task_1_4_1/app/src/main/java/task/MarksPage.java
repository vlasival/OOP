package task;

import java.util.ArrayList;

public class MarksPage {
    ArrayList<Grade> notes;

    public MarksPage() {
        notes = new ArrayList<>();
    }
    
    public void addNode(String subject, int mark, boolean differentiated) throws GradeException {
        Grade newNote = new Grade(subject, mark, differentiated);
        notes.add(newNote);
    }

    public ArrayList<Grade> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Grade> notes) {
        this.notes = notes;
    }

}
