import controller.NotepadController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NotepadHistory extends Application {

    public FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        loader = new FXMLLoader(getClass().getResource("view/notepad.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Notepad with History");

        NotepadController nc = loader.getController();
        nc.textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            nc.setLastFileColor();
        });


        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);
        primaryStage.show();

    }


    @Override
    public void stop() throws IOException {
        NotepadController nc = loader.getController();
        nc.saveToFile();
        System.out.println("App is closing, saving to " + nc.currentFile);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
