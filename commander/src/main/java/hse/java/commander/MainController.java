package hse.java.commander;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class MainController {

    @FXML
    public Button move;

    @FXML
    public Button rename;

    @FXML
    public ListView<String> left;

    @FXML
    public ListView<String> right;

    private File leftDir;
    private File rightDir;

    public void initialize() {
        File home = new File(System.getProperty("user.home"));
        leftDir = home;
        rightDir = home;

        loadDir(left, leftDir);
        loadDir(right, rightDir);

        left.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) navigate(left, true);
        });

        right.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) navigate(right, false);
        });

        move.setOnMouseClicked(e -> onMove());
        rename.setOnMouseClicked(e -> onRename());
    }

    private void loadDir(ListView<String> list, File dir) {
        list.getItems().clear();
        list.getItems().add("..");
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (!f.isHidden()) {
                    list.getItems().add(f.isDirectory() ? "[DIR] " + f.getName() : f.getName());
                }
            }
        }
    }

    private void navigate(ListView<String> list, boolean isLeft) {
        String selected = list.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        File currentDir = isLeft ? leftDir : rightDir;
        File target;

        if (selected.equals("..")) {
            target = currentDir.getParentFile();
            if (target == null) return;
        } else if (selected.startsWith("[DIR] ")) {
            target = new File(currentDir, selected.substring(6));
        } else {
            return;
        }

        if (isLeft) leftDir = target;
        else rightDir = target;

        loadDir(list, target);
    }

    private void onMove() {
        String leftSelected = left.getSelectionModel().getSelectedItem();
        String rightSelected = right.getSelectionModel().getSelectedItem();

        if (leftSelected != null && !leftSelected.equals("..")) {
            moveFile(leftSelected, leftDir, rightDir, left, right);
        } else if (rightSelected != null && !rightSelected.equals("..")) {
            moveFile(rightSelected, rightDir, leftDir, right, left);
        }
    }

    private void onRename() {
        String leftSelected = left.getSelectionModel().getSelectedItem();
        String rightSelected = right.getSelectionModel().getSelectedItem();

        String selected;
        ListView<String> activeList;
        File activeDir;

        if (leftSelected != null && !leftSelected.equals("..")) {
            selected = leftSelected;
            activeList = left;
            activeDir = leftDir;
        } else if (rightSelected != null && !rightSelected.equals("..")) {
            selected = rightSelected;
            activeList = right;
            activeDir = rightDir;
        } else {
            return;
        }

        String oldName = selected.startsWith("[DIR] ") ? selected.substring(6) : selected;

        TextInputDialog dialog = new TextInputDialog(oldName);
        dialog.setTitle("Rename");
        dialog.setHeaderText(null);
        dialog.setContentText("New name:");
        dialog.showAndWait().ifPresent(newName -> {
            if (newName.isBlank() || newName.equals(oldName)) return;
            File from = new File(activeDir, oldName);
            File to = new File(activeDir, newName);
            if (from.renameTo(to)) {
                loadDir(activeList, activeDir);
            }
        });
    }

    private void moveFile(
		String item,
		File fromDir,
		File toDir,
        ListView<String> fromList,
		ListView<String> toList) {
        String name = item.startsWith("[DIR] ") ? item.substring(6) : item;
        Path source = new File(fromDir, name).toPath();
        Path target = new File(toDir, name).toPath();
        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            loadDir(fromList, fromDir);
            loadDir(toList, toDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
