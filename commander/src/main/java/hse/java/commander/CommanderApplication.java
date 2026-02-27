package hse.java.commander;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class CommanderApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxml_loader = new FXMLLoader(CommanderApplication.class.getResource("commander-ui.fxml"));
        Scene scene = new Scene(fxml_loader.load());
        var css = CommanderApplication.class.getResource("commander.css");

        if (css != null) scene.getStylesheets().add(css.toExternalForm());

        stage.setTitle("Commander");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }
}