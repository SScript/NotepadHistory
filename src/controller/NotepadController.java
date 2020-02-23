package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.NotepadModel;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TODO: finish all
 */

public class NotepadController implements Initializable {

    @FXML
    public TextArea textArea;
    @FXML
    public ListView<String> listView;
    @FXML
    private NotepadModel notepadModel;
    private static final String FILEPATH = ".\\files\\";
    public String currentFile;


    public void doSomething() throws IOException {
        notepadModel = new NotepadModel(this);
        notepadModel.addText(textArea.getText());
    }

    public void setText(String text) throws IOException {
        addDateToList();
        saveToFile();
        System.out.println("click on: " + listView.getSelectionModel().getSelectedItem());

    }


    private void loadFromFile() {
        FileReader fr = null;
        try {
            fr = new FileReader(FILEPATH + currentFile);

            try (BufferedReader br = new BufferedReader(fr)) {
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
//                sb.delete(sb.length() -1, sb.length());

                System.out.println("setting this to textarea: " + sb.toString());
                textArea.setText(sb.toString());
            } catch (Exception ex) {
                System.out.println("error loading file");
            }
        } catch (FileNotFoundException e) {
            System.out.println("nothing to load...");
        }
    }

    public void saveToFile() throws IOException {
        try (FileWriter fw = new FileWriter(FILEPATH + currentFile); BufferedWriter bw = new BufferedWriter(fw);) {
            System.out.println("Saving file to: " + FILEPATH + currentFile);
            bw.write(textArea.getText());
        } catch (Exception e) {
            System.out.println("Error saving file");
        }
    }

    private String getLatestFile() {
        // List got reversed, so it's the first file, but called last, as the file was last created
        return listView.getItems().get(0);
    }

    private void addDateToList() {
        if (listView.getItems().isEmpty()) {
            listView.getItems().add(getTodaysDate());
        } else {
            if (!listView.getItems().get(0).equals(getTodaysDate())) {
                System.out.println("todays date: " + getTodaysDate());
                listView.getItems().add(0, getTodaysDate());
            }
        }
    }

    public String getTodaysDate() {
        return new SimpleDateFormat("dd.MM.yyyy EEEE").format(new Date());
    }

    public void loadAllFiles(String path) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy EEEE");
        ArrayList<Date> list = new ArrayList<>();

        File f = new File(path);
        for (File file : f.listFiles()) {
            if (file.isFile()) {
                try {
                    list.add(sdf.parse(file.getName()));
                } catch (ParseException e) {
                    System.out.println("Could not parse the file " + file.getName());
                }
            }
        }

        Collections.sort(list);
        Collections.reverse(list);

        for (Date date : list) {
            listView.getItems().add(sdf.format(date));
        }
    }

    public void closeApplication() {
        System.out.println("exiting from menu (closeApplication method)");
        Platform.exit();
    }


    public void setLastFileColor() {

        listView.setCellFactory(lv -> new ListCell<String>() {
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    if (isLatest(item)) {
                        setTextFill(Color.valueOf("#CC6666"));
                    }
                } else {
                    setText(item);
                }
            }
        });


    }

    public boolean isLatest(String item) {
        return item.equals(getLatestFile());
    }

    public void loadFileFromList(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            saveToFile();
            currentFile = listView.getSelectionModel().getSelectedItem();
            loadFromFile();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
/**
 * TODO: some stuff to do
 */


        // load all the history into sidebar
        loadAllFiles(FILEPATH);

        // set current file as the latest one
        currentFile = getLatestFile();

        // if file was already created today
        if (getLatestFile().equals(getTodaysDate())) {
            loadFromFile();
        }

        // just a little prompt
        textArea.setPromptText("Start typing...");
        textArea.end();
    }
}
