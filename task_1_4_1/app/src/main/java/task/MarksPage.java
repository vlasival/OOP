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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        for (var i : notes) {
            result = prime * result + i.hashCode();
        }
        return result;
    }

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
