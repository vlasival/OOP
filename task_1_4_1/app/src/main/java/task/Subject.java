package task;

public class Subject {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private int mark;
    public int getMark() {
        return mark;
    }
    public boolean setMark(int mark) {
        if (mark < 2 || mark > 5) {
            return false;
        }
        this.mark = mark;
        return true;
    }

    
}
