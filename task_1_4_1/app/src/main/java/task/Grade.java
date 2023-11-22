package task;
/**
 * Class represents subject grade in gradebook.
 */
public class Grade {

    /**
     * Name of subject.
     */
    private String subject;

    /**
     * Grade for this subject.
     */
    private int mark;

    /**
     * Boolean variable. True if mark is differentiated, false otherwise.
     */
    private boolean differentiated;

    /**
     * Constructor of Grade class.
     *
     * @param subject        name of the subject.
     * @param mark           grade for this subject.
     * @param differentiated boolean variable. True if mark is differentiated, false otherwise.
     * @throws GradeException special exception for GradeBook class. Contains information message.
     */
    public Grade(String subject, int mark, boolean differentiated) throws GradeException {
        this.subject = subject;
        this.differentiated = differentiated;
        this.setMark(mark);
    }

    /**
     * Getter for mark.
     *
     * @return mark for subject.
     */
    public int getMark() {
        return mark;
    }

    /**
     * Setter for mark.
     *
     * @param mark mark.
     * @throws GradeException special exception for GradeBook class. Contains information message.
     */
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

    /**
     * Getter for subject.
     *
     * @return name of the subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Setter for subject.
     *
     * @param subject name of the subject.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Getter for differentiated.
     *
     * @return true if mark is differentiated, false otherwise.
     */
    public boolean isDifferentiated() {
        return differentiated;
    }

    /**
     * Setter for differentiated.
     *
     * @param differentiated boolean variable. True if mark is differentiated, false otherwise.
     */
    public void setDifferentiated(boolean differentiated) {
        this.differentiated = differentiated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + mark;
        result = prime * result + (differentiated ? 1231 : 1237);
        return result;
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
