package hse.java.commander;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

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
    public Button copy;

    @FXML
    public ListView<String> left;
    private Path rightDir;

    @FXML
    public ListView<String> right;
    private Path leftDir;

    public void initialize() {

        /* left -- откуда запускается программа
         right -- домашняя папка
        */

        leftDir = Paths.get("").toAbsolutePath();
        rightDir = Paths.get(System.getProperty("user.home"));

        refreshLeft();
        refreshRight();

        move.setOnMouseClicked(event -> {
            String selectedLeft = left.getSelectionModel().getSelectedItem();
            String selectedRight = right.getSelectionModel().getSelectedItem();

            if (selectedLeft != null && !selectedLeft.equals("..")) {

                if (selectedLeft.startsWith("[") && selectedLeft.endsWith("]")) {
                    selectedLeft = selectedLeft.substring(1, selectedLeft.length() - 1);
                }

                Path toMove = leftDir.resolve(selectedLeft);
                Path whereMove = rightDir.resolve(selectedLeft);

                try {
                    Files.move(toMove, whereMove);
                } catch (IOException e) {
                    try {
                        copyRecursive(toMove, whereMove);
                        deleteRecursive(toMove);
                    } catch (IOException again) {
                        again.printStackTrace();
                    }
                }

                refreshLeft();
                refreshRight();

            } else if (selectedRight != null && !selectedRight.equals("..")) {

                if (selectedRight.startsWith("[") && selectedRight.endsWith("]")) {
                    selectedRight = selectedRight.substring(1, selectedRight.length() - 1);
                }

                Path toMove = rightDir.resolve(selectedRight);
                Path whereMove = leftDir.resolve(selectedRight);

                try {
                    Files.move(toMove, whereMove);
                } catch (IOException e) {
                    try {
                        copyRecursive(toMove, whereMove);
                        deleteRecursive(toMove);
                    } catch (IOException again) {
                        again.printStackTrace();
                    }
                }

                refreshLeft();
                refreshRight();
            }
        });


        delete.setOnMouseClicked(mouseEvent -> {
            String selectedLeft = left.getSelectionModel().getSelectedItem();
            String selectedRight = right.getSelectionModel().getSelectedItem();

            if (selectedLeft != null && !selectedLeft.equals("..")) {

                if (selectedLeft.startsWith("[") && selectedLeft.endsWith("]")) {
                    selectedLeft = selectedLeft.substring(1, selectedLeft.length() - 1);
                }

                Path toDelete = leftDir.resolve(selectedLeft);

                try {
                    deleteRecursive(toDelete);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                refreshLeft();
                refreshRight();

            } else if (selectedRight != null && !selectedRight.equals("..")) {

                if (selectedRight.startsWith("[") && selectedRight.endsWith("]")) {
                    selectedRight = selectedRight.substring(1, selectedRight.length() - 1);
                }

                Path toDelete = rightDir.resolve(selectedRight);

                try {
                    deleteRecursive(toDelete);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                refreshLeft();
                refreshRight();
            }
        });

        copy.setOnMouseClicked(mouseEvent -> {
            String selectedLeft = left.getSelectionModel().getSelectedItem();
            String selectedRight = right.getSelectionModel().getSelectedItem();

            if (selectedLeft != null && !selectedLeft.equals("..")) {

                if (selectedLeft.startsWith("[") && selectedLeft.endsWith("]")) {
                    selectedLeft = selectedLeft.substring(1, selectedLeft.length() - 1);
                }

                Path toMove = leftDir.resolve(selectedLeft);
                Path whereMove = rightDir.resolve(selectedLeft);

                try {
                    copyRecursive(toMove, whereMove);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                refreshLeft();
                refreshRight();

            } else if (selectedRight != null && !selectedRight.equals("..")) {

                if (selectedRight.startsWith("[") && selectedRight.endsWith("]")) {
                    selectedRight = selectedRight.substring(1, selectedRight.length() - 1);
                }

                Path toMove = rightDir.resolve(selectedRight);
                Path whereMove = leftDir.resolve(selectedRight);

                try {
                    copyRecursive(toMove, whereMove);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                refreshLeft();
                refreshRight();
            }
        });



       left.setOnMouseClicked(event -> {
           if (event.getClickCount() == 2) {
               int index = left.getSelectionModel().getSelectedIndex();
               if (index >= 0) {
                   String name = left.getSelectionModel().getSelectedItem();

                   if (name.startsWith("[") && name.endsWith("]")) {
                       name = name.substring(1, name.length() - 1);
                   }

                   Path fullPath = leftDir.resolve(name);

                    if (name.equals("..")) {
                        leftDir = leftDir.getParent();
                        refreshLeft();

                   } else if (Files.isDirectory(fullPath)) {
                        leftDir = fullPath;
                        refreshLeft();
                   }
               }
           }
       });

        right.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                int index = right.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    String name = right.getSelectionModel().getSelectedItem();

                    if (name.startsWith("[") && name.endsWith("]")) {
                        name = name.substring(1, name.length() - 1);
                    }

                    Path fullPath = rightDir.resolve(name);

                    if (name.equals("..")) {
                        rightDir = rightDir.getParent();
                        refreshRight();

                    } else if (Files.isDirectory(fullPath)) {
                        rightDir = fullPath;
                        refreshRight();
                    }
                }
            }
        });
   }



   private void refreshLeft() {
        left.getItems().clear();

       File[] filesDeeper = leftDir.toFile().listFiles();

       if (leftDir.getParent() != null) {
           left.getItems().add("..");
       }

       for (File f: filesDeeper) {
           if (f.isDirectory()) {
               left.getItems().add('[' + f.getName() + ']');
           } else {
               left.getItems().add(f.getName());
           }
       }
   }

    private void refreshRight() {
        right.getItems().clear();

        File[] filesDeeper = rightDir.toFile().listFiles();

        if (rightDir.getParent() != null) {
            right.getItems().add("..");
        }

        for (File f: filesDeeper) {
            if (f.isDirectory()) {
                right.getItems().add('[' + f.getName() + ']');
            } else {
                right.getItems().add(f.getName());
            }
        }
    }

    private void deleteRecursive(Path toDelete) throws IOException{
        if (toDelete.toFile().isDirectory()) {
            File [] filesDeeper = toDelete.toFile().listFiles();
            if (filesDeeper != null) {
                for (File f : filesDeeper) {
                    deleteRecursive(f.toPath());
                }
            }
        }
        Files.delete(toDelete);
    }

    private void copyRecursive(Path toCopy, Path whereCopy) throws IOException{
        if (toCopy.toFile().isDirectory()) {
            Files.createDirectories(whereCopy);
            File [] filesDeeper = toCopy.toFile().listFiles();
            if (filesDeeper != null) {
                for (File f : filesDeeper) {
                    copyRecursive(f.toPath(), whereCopy.resolve(f.getName()));
                }
            }
        } else {
            Files.copy(toCopy, whereCopy);
        }
    }
}
