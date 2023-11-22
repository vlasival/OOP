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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + mark;
        result = prime * result + (differentiated ? 1231 : 1237);
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
        Grade other = (Grade) obj;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        if (mark != other.mark)
            return false;
        if (differentiated != other.differentiated)
            return false;
        return true;
    }
}
