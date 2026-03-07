package hse.java.commander;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainController {

    @FXML
    public ListView<String> left;

    @FXML
    public ListView<String> right;

    @FXML
    public Button move;

    @FXML
    public Button copy;

    @FXML
    public Button delete;

    private Path leftDir;
    private Path rightDir;

    private boolean leftAct;

    // for testing
    public void setInitialDirs(Path leftStart, Path rightStart) {
        this.leftDir = leftStart;
        this.rightDir = rightStart;
    }

    public void initialize() {
        move.setOnMouseClicked(event -> move());

        copy.setOnMouseClicked(event -> copy());

        delete.setOnMouseClicked(event -> delete());

        System.out.println(System.getProperty("user.home"));
        left.getItems().add("Kek");

        left.setOnMouseClicked(event -> {
            leftAct = true;
            if (event.getClickCount() == 2) {
                int index = left.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    left.getItems().set(index, "clicked");
                }
            }
        });
        right.setOnMouseClicked(event -> leftAct = false);
    }


    private void move() {
        String name = leftAct ? left.getSelectionModel().getSelectedItem() : right.getSelectionModel().getSelectedItem();

        Path source = leftAct ? leftDir.resolve(name) : rightDir.resolve(name);
        Path target = leftAct ? rightDir.resolve(name) : leftDir.resolve(name);

        try {
            Files.move(source,  target);
        } catch (IOException e) {}
    }

    private void copy() {
        String name = leftAct ? left.getSelectionModel().getSelectedItem() : right.getSelectionModel().getSelectedItem();

        Path source = leftAct ? leftDir.resolve(name) : rightDir.resolve(name);
        Path target = leftAct ? rightDir.resolve(name) : leftDir.resolve(name);

        try {
            Files.copy(source, target);
        } catch (IOException e) {}
    }

    private void delete() {
        String name = leftAct ? left.getSelectionModel().getSelectedItem() : right.getSelectionModel().getSelectedItem();

        Path source = leftAct ? leftDir.resolve(name) : rightDir.resolve(name);

        try {
            Files.delete(source);
        } catch (IOException e) {}
    }
}
