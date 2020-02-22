package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import model.NotepadModel;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class NotepadController implements Initializable {

    @FXML
    public TextArea textArea;
    @FXML
    public ListView<String> listView;
    @FXML
    NotepadModel notepadModel;


    public void doSomething() {
        notepadModel = new NotepadModel(this);
        notepadModel.addText(textArea.getText());
    }

    public void setText(String text) {
        textArea.appendText(text + "\n");
        addDateToList();
    }

    private void addDateToList() {
        if (listView.getItems().isEmpty()) {
                listView.getItems().add(getTodaysDate());
        } else {
            if (!listView.getItems().get(listView.getItems().size() - 1).equals(getTodaysDate())) {
                System.out.println("getLastIndex: " + listView.getItems().get(listView.getItems().size() - 1));
                System.out.println("todays date: " + getTodaysDate());
                listView.getItems().add(getTodaysDate());
            }
        }
    }
    // new comment

    public String getTodaysDate() {
        return new SimpleDateFormat("dd.MM.yyyy EEEE").format(new Date());
    }

    public void initialize(URL location, ResourceBundle resources) {
        textArea.setPromptText("Start typing...");
    }
}
