package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotebookTest {
    Notebook notebook;
    
    @BeforeEach
    void prepare() {
        cleaning();
        notebook = new Notebook("notes.json");
        notebook.add("1", "One");
        notebook.add("2", "Two");
        notebook.add("3", "Three");
        notebook.add("4", "Four");
        notebook.add("5", "Five");
    }

    @AfterEach
    void cleaning() {
        File f = new File("notes.json");
        if (f.exists()) {
            f.delete();
        }
    }

    @Test
    void getNotesTest() {
        assertEquals(5, notebook.getNotes().size());
    }

    @Test
    void getFilenameTest() {
        assertEquals("notes.json", notebook.getFilename());
    }

    

    @Test
    void showWithArgumentsTest() {
        String expected = "1: One\n"
                        + "4: Four\n"
                        + "5: Five\n";
        LocalDateTime from = Main.stringToLocalTime("20.12.2023 07:00");
        LocalDateTime to = Main.stringToLocalTime("20.12.2024 13:00");
        assertEquals(expected, notebook.show(from, to, "1", "5", "4"));
    }

    @Test
    void showNoArgumentsTest() {
        String expected = "1: One\n"
                        + "2: Two\n"
                        + "3: Three\n"
                        + "4: Four\n"
                        + "5: Five\n";
        assertEquals(expected, notebook.show());
    }

    @Test
    void removeTest() {
        notebook.remove("4");
        String expected = "1: One\n"
                        + "2: Two\n"
                        + "3: Three\n"
                        + "5: Five\n";
        assertEquals(expected, notebook.show());
    }

    @Test
    void stringToLocalTimeTest() {
        LocalDateTime expected = LocalDateTime.of(2023, 12, 20, 7, 0, 0);
        assertEquals(expected, Main.stringToLocalTime("20.12.2023 07:00"));
    }

    @Test
    void localTimeToStringTest() {
        String expected = "20.12.2023 13:45";
        LocalDateTime test = LocalDateTime.of(2023, 12, 20, 13, 45, 0);
        assertEquals(expected, Main.localTimeToString(test));
    }

    @Test
    void getContentTest() {
        assertEquals("Two", notebook.getNotes().get(1).getContent());
    }

    // @Test
    // void loadFromExistsJson() {
    //     Notebook nb = new Notebook("notes.json");
    //     assertEquals(notebook.show(), nb.show());
    // }
}
