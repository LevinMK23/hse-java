package hse.java.commander;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.lang.Throwable;
import java.nio.file.*;
import java.util.stream.Stream;

public class MainController {

    @FXML
    public ListView<String> left;

    @FXML
    public ListView<String> right;

    @FXML
    public Button copy;

    @FXML
    public Button move;

    @FXML
    public Button delete;

    private Path leftPath;
    private Path rightPath;
    private ListView<String> Panel;

    public void initialize() {
        leftPath = Paths.get(System.getProperty("user.dir"));
        rightPath = Paths.get(System.getProperty("user.dir"));

        Panel = left;
        dir(left, leftPath);
        dir(right, rightPath);

        left.setOnMouseClicked(e -> {
            Panel = left;
            if (e.getClickCount() == 2) {
                open(left);
            }
        });

        right.setOnMouseClicked(e -> {
            Panel = right;
            if (e.getClickCount() == 2) {
                open(right);
            }
        });
    }

    private void dir(ListView<String> panel, Path path) {
        panel.getItems().clear();
        panel.getItems().add("...");
        try (Stream<Path> f = Files.list(path)) {
            f.forEach(p -> panel.getItems().add(p.getFileName().toString()));
        } catch (Throwable e) {}
    }

    private void update() {
        dir(left, leftPath);
        dir(right, rightPath);
    }

    public void setInitialDirs(Path leftDir, Path rightDir) {
        leftPath = leftDir;
        rightPath = rightDir;
        dir(left, leftPath);
        dir(right, rightPath);
    }

    private void open(ListView<String> panel) {
        String name = panel.getSelectionModel().getSelectedItem();
        if (name == null) return;

        Path curr = panel == left ? leftPath : rightPath;

        if (name.equals("...")) {
            Path par = curr.getParent();
            if (par != null) {
                if (panel == left) {
                    leftPath = par;
                    dir(left, leftPath);
                } else {
                    rightPath = par;
                    dir(right, rightPath);
                }
            }
            return;
        }

        Path touch = curr.resolve(name);
        if (Files.isDirectory(touch)) {
            if (panel == left) {
                leftPath = touch;
                dir(left, leftPath);
            } else {
                rightPath = touch;
                dir(right, rightPath);
            }
        }
    }

    @FXML
    public void copy() {
        if (Panel == null) return;

        String name = Panel.getSelectionModel().getSelectedItem();
        if (name == null || name.equals("...")) return;

        Path srcPath = (Panel == left) ? leftPath : rightPath;
        Path dstPath = (Panel == left) ? rightPath : leftPath;

        Path src = srcPath.resolve(name);
        Path dst = dstPath.resolve(name);

        try {
            Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
        } catch (Throwable e) {}

        update();
    }

    @FXML
    public void move() {
        if (Panel == null) return;

        String name = Panel.getSelectionModel().getSelectedItem();
        if (name == null || name.equals("...")) return;

        Path srcPath = (Panel == left) ? leftPath : rightPath;
        Path dstPath = (Panel == left) ? rightPath : leftPath;

        Path src = srcPath.resolve(name);
        Path dst = dstPath.resolve(name);

        try {
            Files.move(src, dst, StandardCopyOption.REPLACE_EXISTING);
        } catch (Throwable e) {}
        update();
    }

    @FXML
    public void delete() {
        if (Panel == null) return;

        String name = Panel.getSelectionModel().getSelectedItem();
        if (name == null || name.equals("...")) return;

        Path dir = (Panel == left) ? leftPath : rightPath;
        Path file = dir.resolve(name);

        try {
            Files.deleteIfExists(file);
        } catch (Throwable e) {}
        update();
    }
}