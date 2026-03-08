package hse.java.commander;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

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

        refresh(left, leftDir);
        refresh(right, rightDir);
    }

    private void refresh(ListView<String> part, Path dir) {
        part.getItems().clear();

        try {
            if (dir.getParent() != null) {
                part.getItems().add("..");
            }

            Files.list(dir).map(p -> p.getFileName().toString()).forEach(part.getItems()::add);
        } catch (IOException e) {}


        //part.getItems().addAll(dir.toFile().list());
    }

    public void initialize() {
        move.setOnMouseClicked(event -> move());

        copy.setOnMouseClicked(event -> copy());

        delete.setOnMouseClicked(event -> delete());

        System.out.println(System.getProperty("user.home"));

        left.setOnMouseClicked(event -> {
            leftAct = true;
            if (event.getClickCount() == 2) {
                open(left);
            }
        });
        right.setOnMouseClicked(event -> {
            leftAct = false;
            if (event.getClickCount() == 2) {
                open(right);
            }
        });
    }


    private void move() {
        String name = leftAct ? left.getSelectionModel().getSelectedItem() : right.getSelectionModel().getSelectedItem();

        Path source = leftAct ? leftDir.resolve(name) : rightDir.resolve(name);
        Path target = leftAct ? rightDir.resolve(name) : leftDir.resolve(name);

        try {
            Files.move(source,  target);
            refresh(left, leftDir);
            refresh(right, rightDir);
        } catch (IOException e) {}
    }

    private void copy() {
        String name = leftAct ? left.getSelectionModel().getSelectedItem() : right.getSelectionModel().getSelectedItem();

        Path source = leftAct ? leftDir.resolve(name) : rightDir.resolve(name);
        Path target = leftAct ? rightDir.resolve(name) : leftDir.resolve(name);

        try {
            Files.copy(source, target);
            refresh(left, leftDir);
            refresh(right, rightDir);
        } catch (IOException e) {}
    }

    private void delete() {
        String name = leftAct ? left.getSelectionModel().getSelectedItem() : right.getSelectionModel().getSelectedItem();

        Path source = leftAct ? leftDir.resolve(name) : rightDir.resolve(name);

        try {
            Files.delete(source);
            refresh(left, leftDir);
            refresh(right, rightDir);
        } catch (IOException e) {}
    }

    private void open(ListView<String> part) {
        String name = part.getSelectionModel().getSelectedItem();
        Path dir = leftAct ? leftDir : rightDir;

        if (name == null) {
            return;
        }

        if (name.equals("..")) {
            dir = dir.getParent();
        }
        else {
            Path fullPath = dir.resolve(name);
            if (Files.isDirectory(fullPath)) {
                dir = fullPath;
            }
            else {
                return;
            }
        }

        if (part == left) {
            leftDir = dir;
            refresh(left, leftDir);
        }
        else {
            rightDir = dir;
            refresh(right, rightDir);
        }
    }
}
