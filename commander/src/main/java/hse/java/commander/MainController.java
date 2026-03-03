package hse.java.commander;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainController {

    @FXML
    public Button move;

    @FXML
    public Button delete;

    @FXML
    public Button createFolder;

    @FXML
    public Button createFile;

    @FXML
    public ListView<String> left;

    @FXML
    public ListView<String> right;

    private Path leftPath;
    private Path rightPath;

    private boolean inLeftSide = true;

    public void initialize() {
        leftPath = Paths.get(System.getProperty("user.home"));
        rightPath = Paths.get(System.getProperty("user.home"));

        showFiles(left, leftPath);
        showFiles(right, rightPath);

        left.setOnMouseClicked(event -> {
            inLeftSide = true;
            if (event.getClickCount() == 2) {
                int index = left.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    String name = left.getItems().get(index);
                    Path newPath = getPath(leftPath, name);
                    if (newPath != null) {
                        leftPath = newPath;
                        showFiles(left, leftPath);
                    }
                }
            }
        });

        right.setOnMouseClicked(event -> {
            inLeftSide = false;
            if (event.getClickCount() == 2) {
                int index = right.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    String name = right.getItems().get(index);
                    Path newPath = getPath(rightPath, name);
                    if (newPath != null) {
                        rightPath = newPath;
                        showFiles(right, rightPath);
                    }
                }
            }
        });

        move.setOnAction(event -> {
            int index = (inLeftSide ? left : right).getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                moveFile((inLeftSide ? left : right).getItems().get(index), inLeftSide ? leftPath : rightPath,
                        inLeftSide ? rightPath : leftPath);
            }
        });
        delete.setOnAction(event -> deleteFile());
        createFolder.setOnAction(event -> createFolder());
        createFile.setOnAction(event -> createFile());
    }

    private Path getPath(Path current, String file) {
        if (file.equals("..")) {
            return current.getParent();
        }
        String name;
        if (file.startsWith(">")) {
            name = file.substring(1);
        }
        else{
            name = file;
        }
        Path newPath = current.resolve(name);
        if (Files.isDirectory(newPath)) {
            return newPath;
        }
        return null;
    }

    private void showFiles(ListView<String> listView, Path dir) {
        listView.getItems().clear();
        if (dir.getParent() != null) {
            listView.getItems().add("..");
        }
        File[] all_files = dir.toFile().listFiles();
        if (all_files == null) return;
        for (File file : all_files) {
            if (file.isDirectory()) {
                listView.getItems().add(">" + file.getName());
            }
            else{
                listView.getItems().add(file.getName());
            }
        }
    }

    private void moveFile(String name, Path fromDir, Path toDir) {
        if (name.equals("..")) return;
        String newName = name.startsWith(">") ? name.substring(1) : name;
        try {
            Files.move(fromDir.resolve(newName), toDir.resolve(newName));
            showFiles(left, leftPath);
            showFiles(right, rightPath);
        } catch (IOException e) {
            System.out.println("Error while moving: " + e.getMessage());
        }
    }

    private void deleteRecursive(Path path) throws IOException {
        if (!Files.exists(path)) {
            return;
        }
        Files.walk(path)
                .sorted(java.util.Comparator.reverseOrder())
                .forEach(p -> {
                    try {
                        Files.delete(p);
                    } catch (IOException e) {
                        System.out.println("Error while deleting: " + p);
                    }
                });
    }

    private void deleteFile() {
        ListView<String> list = inLeftSide ? left : right;
        Path path = inLeftSide ? leftPath : rightPath;
        int index = list.getSelectionModel().getSelectedIndex();
        if (index >= 0){
            String files = list.getItems().get(index);
            if (files.equals("..")) return;

            String name = files.startsWith(">") ? files.substring(1) : files;
            Path pathToDelete = path.resolve(name);
            try {
                deleteRecursive(pathToDelete);
                showFiles(list, path);
            } catch (IOException e) {
                System.out.println("Error while deleting: " + e.getMessage());
            }
        }
    }

    private void createFile() {
        TextInputDialog textDialog = new TextInputDialog();
        textDialog.setTitle("New File :)");
        textDialog.setHeaderText("Enter name:");
        textDialog.showAndWait().ifPresent(name -> {
            Path path = inLeftSide ? leftPath : rightPath;
            Path newPath = path.resolve(name);
            try {
                Files.createFile(newPath);
                showFiles(inLeftSide ? left : right, path);
            } catch (IOException e) {
                System.out.println("Error while creating file: " + e.getMessage());
            }
        });
    }

    private void createFolder() {
        TextInputDialog textDialog = new TextInputDialog();
        textDialog.setTitle("New Folder ^_^");
        textDialog.setHeaderText("Enter name:");
        textDialog.showAndWait().ifPresent(name -> {
            Path path = inLeftSide ? leftPath : rightPath;
            Path newPath = path.resolve(name);
            try {
                Files.createDirectory(newPath);
                showFiles(inLeftSide ? left : right, path);
            } catch (IOException e) {
                System.out.println("Error while creating directory: " + e.getMessage());
            }
        });
    }
}
