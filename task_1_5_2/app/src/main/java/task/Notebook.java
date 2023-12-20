package task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The Notebook class represents a notebook that stores notes.
 */
class Notebook {
    private List<Note> notes;
    private String filename;

    /**
     * The Notebook constructor creates a new notebook and loads notes from a JSON file.
     *
     * @param filename The name of the JSON file from which the notes are loaded.
     */
    public Notebook(String filename) {
        this.filename = filename;
        this.notes = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Note>>(){}.getType();
                this.notes = gson.fromJson(new FileReader(file), type);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The getNotes method returns the list of notes in the notebook.
     *
     * @return The list of notes.
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     * The getFilename method returns the name of the file where the notes are stored.
     *
     * @return The name of the file.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * The add method adds a new note to the notebook and saves it to the file.
     *
     * @param title The title of the note.
     * @param content The content of the note.
     */
    public void add(String title, String content) {
        notes.add(new Note(title, content));
        save();
    }

    /**
     * The remove method removes a note with the specified title from the notebook and saves the changes to the file.
     *
     * @param title The title of the note to be removed.
     */
    public void remove(String title) {
        notes.removeIf(note -> note.getTitle().equals(title));
        save();
    }

    /**
     * The show method returns a string representation of all the notes in the notebook.
     *
     * @return A string representation of all the notes.
     */
    public String show() {
        notes.sort(Comparator.comparing(note -> {
            String tmp = ((Note) note).getDate();
            return Main.stringToLocalTime(tmp);
        }));

        StringBuilder str = new StringBuilder();
        for (Note note : notes) {
            str.append(note.toString() + "\n");
        }

        return str.toString();
    }

    /**
     * The show method returns a string representation of the notes that were created within the specified time period and contain the specified keywords.
     *
     * @param start The start of the time period.
     * @param end The end of the time period.
     * @param keywords An array of keywords.
     * @return A string representation of the matching notes.
     */
    public String show(LocalDateTime start, LocalDateTime end, String... keywords) {
        notes.sort(Comparator.comparing(note -> ((Note) note).getDate()));
        StringBuilder str = new StringBuilder();
        for (Note note : notes) {
            LocalDateTime currTime = Main.stringToLocalTime(note.getDate());
            if (currTime.isAfter(start) && currTime.isBefore(end)) {
                for (String keyword : keywords) {
                    if (note.getTitle().contains(keyword)) {
                        str.append(note.toString() + "\n");
                        break;
                    }
                }
            }
        }
        return str.toString();
    }

    public void save() {
        try {
            Gson gson = new Gson();
            File f = new File(filename);
            if (f.exists()) {
                f.delete();
            }
            FileWriter writer = new FileWriter(filename);
            gson.toJson(notes, writer);
            writer.close();
        } catch (IOException e) {
            System.err.println("Failed recording to JSON");
            e.printStackTrace();
        }
    }
}


