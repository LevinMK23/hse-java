package hse.java.commander;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class MainController {
    @FXML public ListView<FileEntry> left, right;
    @FXML public TextField leftPathField, rightPathField;
    private Path leftCurrent, rightCurrent;

    @FXML public void refreshBoth() { loadDirectory(leftCurrent, true); loadDirectory(rightCurrent, false); }
    @FXML public void openLeftPath() { openPathFromField(leftPathField, true); }
    @FXML public void openRightPath() { openPathFromField(rightPathField, false); }
    @FXML public void upLeftDirectory() { moveUp(true); }
    @FXML public void upRightDirectory() { moveUp(false); }
    @FXML public void copyLeftToRight() { copySelected(left, rightCurrent, false); }
    @FXML public void copyRightToLeft() { copySelected(right, leftCurrent, true); }
    @FXML public void deleteLeftSelected() { deleteSelected(left, true); }
    @FXML public void deleteRightSelected() { deleteSelected(right, false); }
    @FXML public void createLeftDirectory() { createDirectory(true); }
    @FXML public void createRightDirectory() { createDirectory(false); }

    public void initialize() {
        Path home = Path.of(System.getProperty("user.home"));
        leftCurrent = home;
        rightCurrent = home;
        configureListView(left, true);
        configureListView(right, false);
        refreshBoth();
    }

    private void configureListView(ListView<FileEntry> listView, boolean isLeft) {
        listView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(FileEntry item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.displayName());
            }
        });
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) openSelected(listView, isLeft);
        });
    }

    private void openSelected(ListView<FileEntry> listView, boolean isLeft) {
        FileEntry selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.directory()) loadDirectory(selected.path(), isLeft);
    }

    private void openPathFromField(TextField field, boolean isLeft) {
        String rawPath = field.getText();
        if (rawPath == null || rawPath.isBlank()) return;
        loadDirectory(Path.of(rawPath).toAbsolutePath().normalize(), isLeft);
    }

    private void moveUp(boolean isLeft) {
        Path parent = current(isLeft).getParent();
        if (parent != null) loadDirectory(parent, isLeft);
    }

    private void loadDirectory(Path dir, boolean isLeft) {
        Path normalized = dir.toAbsolutePath().normalize();
        if (!Files.isDirectory(normalized)) {
            showError("Папка не найдена =( ", normalized.toString());
            return;
        }

        List<FileEntry> items = new ArrayList<>();
        Path parent = normalized.getParent();
        if (parent != null) items.add(new FileEntry(parent, "..", true));

        try (Stream<Path> fileStream = Files.list(normalized)) {
            items.addAll(fileStream
                    .sorted(Comparator.comparing((Path p) -> !Files.isDirectory(p))
                            .thenComparing(p -> p.getFileName().toString().toLowerCase()))
                    .map(p -> new FileEntry(p, p.getFileName().toString(), Files.isDirectory(p)))
                    .toList());
        } catch (IOException e) {
            showError("Не удалось прочитать папку =(", e.getMessage());
            return;
        }

        setCurrent(isLeft, normalized);
        pathField(isLeft).setText(normalized.toString());
        listView(isLeft).getItems().setAll(items);
    }

    private void copySelected(ListView<FileEntry> sourceList, Path targetDir, boolean clearLeftSelection) {
        FileEntry selected = sourceList.getSelectionModel().getSelectedItem();
        if (selected == null || "..".equals(selected.displayName())) return;

        Path source = selected.path();
        Path target = targetDir.resolve(source.getFileName());
        try {
            if (selected.directory()) {
                copyDirectory(source, target);
            } else {
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
            }
            refreshBoth();
            (clearLeftSelection ? left : right).getSelectionModel().clearSelection();
        } catch (IOException e) {
            showError("Ошибка копировния =(", e.getMessage());
        }
    }

    private void deleteSelected(ListView<FileEntry> listView, boolean isLeft) {
        FileEntry selected = listView.getSelectionModel().getSelectedItem();
        if (selected == null || "..".equals(selected.displayName())) return;

        try {
            deleteRecursively(selected.path());
            loadDirectory(current(isLeft), isLeft);
        } catch (IOException e) {
            showError("Ошибка удаления =(", e.getMessage());
        }
    }

    private static void copyDirectory(Path source, Path target) throws IOException {
        try (Stream<Path> paths = Files.walk(source)) {
            paths.forEach(path -> {
                Path dest = target.resolve(source.relativize(path));
                try {
                    if (Files.isDirectory(path)) {
                        Files.createDirectories(dest);
                    } else {
                        Files.copy(path, dest, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                    }
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        } catch (UncheckedIOException e) {
            throw e.getCause();
        }
    }

    private static void deleteRecursively(Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            Files.deleteIfExists(path);
            return;
        }
        try (Stream<Path> paths = Files.walk(path)) {
            paths.sorted(Comparator.reverseOrder()).forEach(p -> {
                try {
                    Files.deleteIfExists(p);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        } catch (UncheckedIOException e) {
            throw e.getCause();
        }
    }

    private void createDirectory(boolean isLeft) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Создание папки");
        dialog.setHeaderText("Введите имя новой папки");
        dialog.setContentText("Имя:");
        dialog.showAndWait().ifPresent(name -> {
            Path base = current(isLeft);
            try {
                Files.createDirectory(base.resolve(name.trim()).normalize());
                loadDirectory(base, isLeft);
            } catch (IOException | RuntimeException e) {
                showError("Ошибка создания папки =(", e.getMessage());
            }
        });
    }

    private Path current(boolean isLeft) { return isLeft ? leftCurrent : rightCurrent; }
    private void setCurrent(boolean isLeft, Path value) { if (isLeft) leftCurrent = value; else rightCurrent = value; }
    private ListView<FileEntry> listView(boolean isLeft) { return isLeft ? left : right; }
    private TextField pathField(boolean isLeft) { return isLeft ? leftPathField : rightPathField; }

    private void showError(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public record FileEntry(Path path, String displayName, boolean directory) {}
}
