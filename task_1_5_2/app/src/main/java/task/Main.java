package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * The Main class is the entry point of the application.
 */
public class Main {

    // @Option(name="-add", usage="add note", metaVar="TITLE CONTENT")
    // private String[] add;

    // @Option(name="-rm", aliases="--remove", usage="remove note", metaVar="TITLE")
    // private String remove;

    // @Option(name="-show", usage="show notes")
    // private String[] show;

    /**
     * The main method is the entry point of the application.
     * It creates a new notebook, adds and removes notes, and displays the notes.
     *
     * @param args The command line arguments.
     */
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        Notebook notebook = new Notebook("app/src/main/java/task/notes.json");
        // notebook.add("1", "One");
        // notebook.add("2", "Two");
        // notebook.add("3", "Three");
        // notebook.add("4", "Four");
        // notebook.add("5", "Five");

        System.out.println(notebook.show());

        // notebook.remove("1");
        // notebook.remove("2");
        // notebook.remove("3");
        // notebook.remove("4");
        // notebook.remove("5");

        System.out.println(notebook.show(stringToLocalTime("20.12.2023 07:00"), 
                                        stringToLocalTime("20.12.2024 13:00"), 
                                        "1", "5", "4"));
    }

    /**
     * The stringToLocalTime method converts a string to a LocalDateTime.
     * It uses a DateTimeFormatter to parse the string.
     *
     * @param time The string to convert.
     * @return The LocalDateTime that represents the string, or null if the string cannot be parsed.
     */
    public static LocalDateTime stringToLocalTime(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            return LocalDateTime.parse(time, formatter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * The localTimeToString method converts a LocalDateTime to a string.
     * It uses a DateTimeFormatter to format the LocalDateTime.
     *
     * @param time The LocalDateTime to convert.
     * @return The string that represents the LocalDateTime, or null if the LocalDateTime cannot be formatted.
     */
    public static String localTimeToString(LocalDateTime time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            return time.format(formatter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}