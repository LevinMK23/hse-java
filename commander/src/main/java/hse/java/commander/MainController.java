package hse.java.commander;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class MainController {
  @FXML public ListView<String> left;

  @FXML public ListView<String> right;

  @FXML public Button copy;

  @FXML public Button move;

  @FXML public Button delete;

  private Path leftDir;
  private Path rightDir;
  private ListView<String> activePanel;

  public void setInitialDirs(Path leftStart, Path rightStart) {
    this.leftDir = leftStart;
    this.rightDir = rightStart;
    updatePanel(left, leftDir);
    updatePanel(right, rightDir);
  }

  public void initialize() {
    left.setOnMouseClicked(event -> {
      activePanel = left;
      if (event.getClickCount() == 2) {
        navigatePanel(left, leftDir, true);
      }
    });

    right.setOnMouseClicked(event -> {
      activePanel = right;
      if (event.getClickCount() == 2) {
        navigatePanel(right, rightDir, false);
      }
    });

    copy.setOnAction(event -> copyFile());
    move.setOnAction(event -> moveFile());
    delete.setOnAction(event -> deleteFile());
  }

  private void updatePanel(ListView<String> panel, Path dir) {
    panel.getItems().clear();
    panel.getItems().add("...");
    File[] files = dir.toFile().listFiles();
    if (files != null) {
      for (File file : files) {
        panel.getItems().add(file.getName());
      }
    }
  }

  private void navigatePanel(ListView<String> panel, Path currentDir,
                             boolean isLeft) {
    String selected = panel.getSelectionModel().getSelectedItem();
    if (selected == null)
      return;

    if (selected.equals("...")) {
      Path parent = currentDir.getParent();
      if (parent != null) {
        if (isLeft) {
          leftDir = parent;
          updatePanel(left, leftDir);
        } else {
          rightDir = parent;
          updatePanel(right, rightDir);
        }
      }
    } else {
      Path newPath = currentDir.resolve(selected);
      if (Files.isDirectory(newPath)) {
        if (isLeft) {
          leftDir = newPath;
          updatePanel(left, leftDir);
        } else {
          rightDir = newPath;
          updatePanel(right, rightDir);
        }
      }
    }
  }

  private void copyFile() {
    if (activePanel == null)
      return;
    String selected = activePanel.getSelectionModel().getSelectedItem();
    if (selected == null || selected.equals("..."))
      return;

    Path sourceDir = (activePanel == left) ? leftDir : rightDir;
    Path targetDir = (activePanel == left) ? rightDir : leftDir;
    ListView<String> targetPanel = (activePanel == left) ? right : left;

    Path source = sourceDir.resolve(selected);
    Path target = targetDir.resolve(selected);

    try {
      Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
      updatePanel(targetPanel, targetDir);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void moveFile() {
    if (activePanel == null)
      return;
    String selected = activePanel.getSelectionModel().getSelectedItem();
    if (selected == null || selected.equals("..."))
      return;

    Path sourceDir = (activePanel == left) ? leftDir : rightDir;
    Path targetDir = (activePanel == left) ? rightDir : leftDir;
    ListView<String> targetPanel = (activePanel == left) ? right : left;

    Path source = sourceDir.resolve(selected);
    Path target = targetDir.resolve(selected);

    try {
      Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
      updatePanel(activePanel, sourceDir);
      updatePanel(targetPanel, targetDir);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void deleteFile() {
    if (activePanel == null)
      return;
    String selected = activePanel.getSelectionModel().getSelectedItem();
    if (selected == null || selected.equals("..."))
      return;

    Path currentDir = (activePanel == left) ? leftDir : rightDir;
    Path toDelete = currentDir.resolve(selected);

    try {
      Files.deleteIfExists(toDelete);
      updatePanel(activePanel, currentDir);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
