package hse.java.commander;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MainController {

    @FXML
    private ListView<PathEntry> left;

    @FXML
    private ListView<PathEntry> right;

    @FXML
    private Button left_to_right;

    @FXML
    private Button right_to_left;

    @FXML
    private Button rename;

    @FXML
    private Button remove;

    @FXML
    private Button refresh;

    private Path root_dir;
    private Path left_dir;
    private Path right_dir;

    private boolean left_active = true;
    private String css_url;

    public void initialize() {
        root_dir = FileOperations.detectProjectRoot();
        left_dir = root_dir;
        right_dir = root_dir;

        var css = MainController.class.getResource("commander.css");
        css_url = css == null ? null : css.toExternalForm();

        left.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                left_active = true;
                if (event.getClickCount() != 2) return;
                openSelected(true);
            }
        });

        right.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                left_active = false;
                if (event.getClickCount() != 2) return;
                openSelected(false);
            }
        });

        left_to_right.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                move(true);
            }
        });

        right_to_left.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                move(false);
            }
        });

        rename.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                renameSelected();
            }
        });

        remove.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                removeSelected();
            }
        });

        refresh.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                refreshBoth();
            }
        });

        refreshBoth();
    }

    private void refreshBoth() {
        refreshPanel(true);
        refreshPanel(false);
    }

    private void refreshPanel(boolean is_left) {
        Path dir = is_left ? left_dir : right_dir;
        ListView<PathEntry> view = is_left ? left : right;

        try {
            List<PathEntry> entries = FileOperations.list(dir, root_dir);
            view.getItems().setAll(entries);
        } catch (IOException e) {
            view.getItems().clear();
        }
    }

    private void openSelected(boolean is_left) {
        ListView<PathEntry> view = is_left ? left : right;
        PathEntry selected = view.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (selected.isParent() || selected.isDirectory()) {
            if (is_left) left_dir = selected.path();
            else right_dir = selected.path();
            refreshPanel(is_left);
            return;
        }

        previewFile(selected.path());
    }

    private void move(boolean left_to_right_move) {
        ListView<PathEntry> from_view = left_to_right_move ? left : right;
        Path from_dir = left_to_right_move ? left_dir : right_dir;
        Path to_dir = left_to_right_move ? right_dir : left_dir;

        PathEntry selected = from_view.getSelectionModel().getSelectedItem();
        if (selected == null || selected.isParent()) return;

        Path source = selected.path();
        Path destination = to_dir.resolve(source.getFileName()).normalize();

        if (Files.exists(destination) && isCancelled("Заменить существующий файл?", destination.toString())) return;

        try {
            FileOperations.move(source, destination);
            refreshBoth();
        } catch (IOException e) {
            error("Не удалось перенести", e.getMessage());
        }
    }

    private void renameSelected() {
        ListView<PathEntry> view = left_active ? left : right;
        PathEntry selected = view.getSelectionModel().getSelectedItem();
        if (selected == null || selected.isParent()) return;

        Path path = selected.path();
        String current_name = path.getFileName().toString();

        TextInputDialog dialog = new TextInputDialog(current_name);
        dialog.setTitle("Переименовать");
        dialog.setHeaderText("Переименовать");
        dialog.setContentText("Новое имя:");
        applyDialogTheme(dialog.getDialogPane());

        String new_name = dialog.showAndWait().orElse("").trim();
        if (new_name.isEmpty() || new_name.equals(current_name)) return;

        if (new_name.indexOf('/') >= 0 || new_name.indexOf('\\') >= 0) {
            error("Некорректное имя", "Имя не должно содержать разделители пути");
            return;
        }

        Path destination = path.getParent().resolve(new_name).normalize();
        if (Files.exists(destination) && isCancelled("Заменить существующий файл?", destination.toString())) return;

        try {
            FileOperations.rename(path, new_name);
            refreshBoth();
        } catch (IOException e) {
            error("Не удалось переименовать", e.getMessage());
        }
    }

    private void removeSelected() {
        ListView<PathEntry> view = left_active ? left : right;
        PathEntry selected = view.getSelectionModel().getSelectedItem();
        if (selected == null || selected.isParent()) return;

        Path path = selected.path();
        if (isCancelled("Удалить выбранный объект?", path.toString())) return;

        try {
            FileOperations.deleteRecursively(path);
            refreshBoth();
        } catch (IOException e) {
            error("Не удалось удалить", e.getMessage());
        }
    }

    private void previewFile(Path path) {
        String content;
        try {
            content = Files.readString(path);
        } catch (IOException e) {
            error("Не удалось прочитать файл", e.getMessage());
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Просмотр");
        alert.setHeaderText(path.getFileName() == null ? path.toString() : path.getFileName().toString());

        TextArea area = new TextArea(content);
        area.setEditable(false);
        area.setWrapText(false);
        area.setPrefColumnCount(80);
        area.setPrefRowCount(25);

        alert.getDialogPane().setContent(area);
        applyDialogTheme(alert.getDialogPane());
        alert.getButtonTypes().setAll(new ButtonType("Закрыть", ButtonData.CANCEL_CLOSE));
        alert.showAndWait();
    }

    private boolean isCancelled(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(header);
        alert.setContentText(content);
        applyDialogTheme(alert.getDialogPane());

        ButtonType ok = new ButtonType("ОК", ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Отмена", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(ok, cancel);

        return alert.showAndWait().orElse(cancel) == cancel;
    }

    private void error(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message == null ? "" : message);
        applyDialogTheme(alert.getDialogPane());
        alert.showAndWait();
    }

    private void applyDialogTheme(DialogPane pane) {
        if (css_url == null || pane.getStylesheets().contains(css_url)) return;
        pane.getStylesheets().add(css_url);
    }
}