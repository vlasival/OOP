package task;

import java.time.LocalDateTime;

/**
 * The Note class represents a note with a title, content, and date.
 */
class Note {
    private String title;
    private String content;
    private String date;

    /**
     * The Note constructor creates a new note with the specified title and content, and sets the date to the current date and time.
     *
     * @param title The title of the note.
     * @param content The content of the note.
     */
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.date = Main.localTimeToString(LocalDateTime.now());
    }

    /**
     * The getTitle method returns the title of the note.
     *
     * @return The title of the note.
     */
    public String getTitle() {
        return title;
    }


    /**
     * The getContent method returns the content of the note.
     *
     * @return The content of the note.
     */
    public String getContent() {
        return content;
    }

    /**
     * The getDate method returns the date of the note.
     *
     * @return The date of the note.
     */
    public String getDate() {
        return date;
    }

    /**
     * The toString method returns a string representation of the note.
     *
     * @return A string representation of the note.
     */
    @Override
    public String toString() {
        return title + ": " + content + "";
    }
}