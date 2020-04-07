package scripts.fletcherino.graphics;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.allatori.annotations.DoNotRename;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.tribot.api.General;
import org.tribot.util.Util;
import scripts.API.Node;
import scripts.fletcherino.Task;

import javax.xml.soap.Text;


/**
 * Credits to laniax for the base JavaFX GUI + Controller in his API located at https://github.com/Laniax/LanAPI/blob/master/core/gui/
 */

@DoNotRename
public class GUIController implements Initializable {
    String itemToDoOptions1AndOptions3[] = {
            "Shortbow",
            "Shafts",
            "Javelin",
            "Stock",
            "Longbow",
            "Oak shortbow",
            "Oak shafts",
            "Oak stock",
            "Oak longbow",
            "Oak shield",
            "Willow shortbow",
            "Willow shafts",
            "Willow longbow",
            "Willow shield",
            "Maple shortbow",
            "Maple shafts",
            "Maple stock",
            "Maple longbow",
            "Maple shield",
            "Yew shortbow",
            "Yew shafts",
            "Yew stock",
            "Yew longbow",
            "Yew shield",
            "Magic shortbow",
            "Magic shafts",
            "Magic longbow",
            "Magic shield",
    };
    String itemToDoOptions2[] = {
            "Shortbow (u)",
            "Longbow (u)",
            "Oak shortbow (u)",
            "Oak longbow (u)",
            "Willow shortbow (u)",
            "Willow longbow (u)",
            "Maple shortbow (u)",
            "Maple longbow (u)",
            "Yew shortbow (u)",
            "Yew longbow (u)",
            "Magic shortbow (u)",
            "Magic longbow (u)",
    };
    public static Map<String,String> mymap = new HashMap<>();
    public static ArrayList<Task> tasks = new ArrayList<>();

    private GUI gui;

    @FXML
    private ComboBox thingToDo;

    @FXML
    private ComboBox itemToDo;

    @FXML
    private ComboBox selectProfile;

    @FXML
    private TextField upToLevel;

    @FXML
    private TextField suppliesToFletch;

    @FXML
    private TextField nameProfile;
//
//    @FXML
//    private Button addTask;
//
    @FXML
    private ListView tasksList;

//    @FXML
//    private Button removeTask;
//
//    @FXML
//    private Button saveTasks;
//
//    @FXML
//    private Button loadTasks;

    @FXML
    private void selectProfileOnChange() {

    }



    @FXML
    private void startScriptPressed() {
        for (int i = 0; i < tasksList.getItems().size(); i++) {
            Task task = new Task(tasksList.getItems().get(i).toString());
            tasks.add(task);
        }

        this.getGUI().close();
    }

    @FXML
    private void addTaskPressed(){
        String thingToDoFinalValue = (String) thingToDo.getValue();
        String itemToDoFinalValue = (String) itemToDo.getValue();
        int levelToTrainTo = Integer.parseInt(upToLevel.getText());
        int numSuppliesToFletch = Integer.parseInt(suppliesToFletch.getText());
        String itemToAddToList = thingToDoFinalValue + ":" + itemToDoFinalValue + ":" + Integer.toString(levelToTrainTo) + ":" + Integer.toString(numSuppliesToFletch);
        tasksList.getItems().add(itemToAddToList);
    }

    @FXML
    private void removeTaskPressed(){
        tasksList.getItems().remove(tasksList.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void saveTasksPressed(){
        String content = "";
        for (int i = 0; i < tasksList.getItems().size(); i++) {
            content += tasksList.getItems().get(i).toString() + "/";
        }
        try {
            File myObj = new File(Util.getWorkingDirectory().getAbsolutePath(), nameProfile.getText() + ".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter(Util.getWorkingDirectory().getAbsolutePath() + "\\" + nameProfile.getText() + ".txt");
            myWriter.write(content);
            myWriter.close();
            General.println("Successfully saved profile settings.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @FXML
    private void loadTasksPressed(){
        tasksList.getItems().clear();
        String nameProfileChosen = (String) selectProfile.getValue();
        try {
            String data = new String(Files.readAllBytes(Paths.get(Util.getWorkingDirectory().getAbsolutePath() + "\\" + nameProfileChosen + ".txt")));
            String dataSplit[] = data.split("/");

            for (String task: dataSplit) {
                tasksList.getItems().add(task);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @FXML
    private void thingToDoOnChange() {
        String newValue = (String) thingToDo.getValue();

        if (newValue.equalsIgnoreCase("Cut")) {
            itemToDoOption1AndOption3();
        } else if (newValue.equalsIgnoreCase("String") || newValue.equalsIgnoreCase("Cut & String")) {
            itemToDoOption2();
        }
    }

    private void itemToDoOption1AndOption3() {
        itemToDo.getItems().clear();
        for (String item: itemToDoOptions1AndOptions3) {
            itemToDo.getItems().add(item);

        }
        itemToDo.getSelectionModel().selectFirst();

    }
    private void itemToDoOption2() {
        itemToDo.getItems().clear();
        for (String item: itemToDoOptions2) {
            itemToDo.getItems().add(item);
        }
        itemToDo.getSelectionModel().selectFirst();

    }

    @FXML
    private void itemToDoOnChange() {}

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public GUI getGUI() {
        return this.gui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 5; i++) {
            mymap.put(itemToDoOptions1AndOptions3[i], "Logs");
        }
        for (int i = 5; i < 10; i++) {
            mymap.put(itemToDoOptions1AndOptions3[i], "Oak logs");
        }
        for (int i = 10; i < 14; i++) {
            mymap.put(itemToDoOptions1AndOptions3[i], "Willow logs");
        }
        for (int i = 14; i < 19; i++) {
            mymap.put(itemToDoOptions1AndOptions3[i], "Maple logs");
        }
        for (int i = 19; i < 24; i++) {
            mymap.put(itemToDoOptions1AndOptions3[i], "Yew logs");
        }
        for (int i = 24; i < 28; i++) {
            mymap.put(itemToDoOptions1AndOptions3[i], "Magic logs");
        }

        thingToDo.getItems().add("Cut");
        thingToDo.getItems().add("String");
        thingToDo.getSelectionModel().selectFirst();
        itemToDoOption1AndOption3();
        upToLevel.setText("-1");
        suppliesToFletch.setText("-1");

        File directory= new File(Util.getWorkingDirectory().getAbsolutePath());
        for (File file : directory.listFiles())
        {
            if (FilenameUtils.getExtension(file.getName()).equals("txt"))
            {
                selectProfile.getItems().add(file.getName().substring(0, file.getName().lastIndexOf('.')));
            }
        }
    }


}