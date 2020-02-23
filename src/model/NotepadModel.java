package model;

import controller.NotepadController;
import javafx.fxml.FXML;

import java.io.IOException;

public class NotepadModel {

    @FXML
    public NotepadController notepadController;
    public String text;

    public NotepadModel(NotepadController notepadController) {
        this.notepadController = notepadController;
    }

    public void addText(String text) throws IOException {
        System.out.println("adding text: " + text);

        notepadController.setText(text);
    }

}
