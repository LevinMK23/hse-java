package hse.java.commander;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    public ListView<Path> left;

    @FXML
    public ListView<Path> right;

    @FXML
    public Button leftBack;

    @FXML
    public Button rightBack;

    @FXML
    public Button move;

    private Path leftCurPath;
    private Path rightCurPath;

    private boolean leftSelected;

    public void initialize() {
        leftCurPath = Paths.get(System.getProperty("user.home"));
        rightCurPath = leftCurPath;

        showPanel(left);
        showPanel(right);

        updateLeft();
        updateRight();

        leftBack.setOnAction(e -> goParent(left));
        rightBack.setOnAction(e -> goParent(right));

        move.setOnAction(e -> moveSelected());

        left.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                goSelected(left);
            }
        });
        right.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                goSelected(right);
            }
        });

        left.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                leftSelected = true;
                right.getSelectionModel().clearSelection();
            }
        });
        right.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                leftSelected = false;
                left.getSelectionModel().clearSelection();
            }
        });
    }

    private void showPanel(ListView<Path> paths) {
        paths.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Path item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String name = item.getFileName().toString();
                    if (Files.isDirectory(item)) {
                        name += "/";
                    }
                    setText(name);
                }
            }
        });
    }

    private void updateLeft() {
        updatePanel(left, leftCurPath);
    }

    private void updateRight() {
        updatePanel(right, rightCurPath);
    }

    private void updatePanel(ListView<Path> paths, Path dir) {
        try {
            ObservableList<Path> items = Files.list(dir).collect(Collectors.toCollection(FXCollections::observableArrayList));
            paths.setItems(items);
        } catch(IOException e) {
            alert("Ошибка (highly likely permission denied)");
            goParent(paths);
        }
    }

    private void goParent(ListView<Path> paths) {
        Path curPath = (paths == left) ? leftCurPath : rightCurPath;
        Path parent = curPath.getParent();
        goFolder(paths, parent);
    }

    private void goSelected(ListView<Path> paths) {
        Path selected = paths.getSelectionModel().getSelectedItem();
        goFolder(paths, selected);
    }

    private void moveSelected() {
        Path source = leftSelected ? left.getSelectionModel().getSelectedItem() : right.getSelectionModel().getSelectedItem();
        Path targetDir = leftSelected ? rightCurPath : leftCurPath;

        if (!Files.isDirectory(targetDir)) {
            alert("NO DIR HUH");
            return;
        }

        Path target = targetDir.resolve(source.getFileName());

        if (target.equals(source)) {
            return;
        }

        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            updateLeft();
            updateRight();
        } catch(IOException e) {
            try {
                recCopy(source, target);
                recDel(source);
                updateLeft();
                updateRight();
            } catch(IOException e2) {
                alert("CAN't MOVE");
            }
        }
    }

    private void recCopy(Path source, Path target) throws IOException {
        if (Files.isDirectory(source)) {
            Files.createDirectories(target);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {
                for (Path entry : stream) {
                    recCopy(entry, target.resolve(entry.getFileName()));
                }
            }
        } else {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void recDel(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                for (Path entry : stream) {
                    recDel(entry);
                }
            }
        }
        Files.delete(path);
    }

    private void goFolder(ListView<Path> paths, Path folder) {
        if (Files.isDirectory(folder)) {
            if (paths == left) {
                leftCurPath = folder;
                updateLeft();
            } else {
                rightCurPath = folder;
                updateRight();
            }
        }
    }

    private void alert(String name) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(name);
        alert.setHeaderText(null);
        alert.setContentText("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        alert.showAndWait();
    }

}
