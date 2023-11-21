package task;

public class Grade {
    private String subject;
    private int mark;
    private boolean differentiated;

    public Grade(String subject, int mark, boolean differentiated) throws GradeException {
        this.subject = subject;
        this.differentiated = differentiated;
        this.setMark(mark);
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) throws GradeException {
        if (mark < 2 || mark > 5) {
            throw new GradeException("Mark must be between 2 and 5.\n");
        }
        if (differentiated) {
            this.mark = mark;
        } else {
            this.mark = mark > 2 ? 5 : 2;
        }
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isDifferentiated() {
        return differentiated;
    }

    public void setDifferentiated(boolean differentiated) {
        this.differentiated = differentiated;
    }
}
