package nl.rdb.spring_boot_examples.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GUILauncher extends Application {

    @Override
    public void start(Stage stage) {
        Button btn1 = new Button("Say, Hello World");
        btn1.setOnAction(arg0 -> {
            log.info("hello world");
        });
        StackPane root = new StackPane();
        root.getChildren().add(btn1);
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("First JavaFX Application");
        stage.setScene(scene);
        stage.show();
    }

    public void run(String[] args) {
        launch(args);
    }
}
