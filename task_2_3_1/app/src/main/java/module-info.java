module org.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires transitive javafx.graphics;


    requires static lombok;
    requires javafx.base;

    opens org.snake.model to javafx.fxml;
    exports org.snake.model;
}
