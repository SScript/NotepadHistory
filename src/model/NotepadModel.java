package model;

import controller.NotepadController;
import javafx.fxml.FXML;

public class NotepadModel {

    @FXML
    public NotepadController notepadController;
    public String text;

public NotepadModel(NotepadController notepadController) {
    this.notepadController = notepadController;
}
    @FXML
    public void addText(String text) {
        System.out.println("adding text: " + text);

        notepadController.setText(text);
    }

}
