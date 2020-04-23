package scripts.pohlogstoplanks.graphics;


import com.allatori.annotations.DoNotRename;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.tribot.api.General;
import scripts.pohlogstoplanks.Constants;
import scripts.pohlogstoplanks.PohLogsToPlanks;

import java.net.URL;

import java.util.ResourceBundle;


/**
 * Credits to laniax for the base JavaFX GUI + Controller in his API located at https://github.com/Laniax/LanAPI/blob/master/core/gui/
 */

@DoNotRename
public class GUIController implements Initializable {
    private GUI gui;

    @DoNotRename @FXML
    private ComboBox plankType;

    @DoNotRename @FXML
    private ComboBox bankingLocation;


    @DoNotRename @FXML
    private void onStart() {
        String plankTypeString = (String) plankType.getValue();
        String bankingLocationString = (String) bankingLocation.getValue();

        switch (plankTypeString.toLowerCase()) {
            case "mahogany":
                PohLogsToPlanks.logType = "Mahogany logs";
                PohLogsToPlanks.logTypeNoted = 8836;
                PohLogsToPlanks.plankType = "Mahogany plank";
                break;
            case "teak":
                PohLogsToPlanks.logType = "Teak logs";
                PohLogsToPlanks.logTypeNoted = 6334;
                PohLogsToPlanks. plankType = "Teak plank";
                break;
            default:
                PohLogsToPlanks.logType = "Oak logs";
                PohLogsToPlanks.logTypeNoted = 1522;
                PohLogsToPlanks.plankType = "Oak plank";
        }

        switch (bankingLocationString.toLowerCase()) {
            case "camelot":
                PohLogsToPlanks.bank = Constants.CAMELOT_AREA;
                break;
            default:
                PohLogsToPlanks.bank = Constants.LUMBRIDGE_AREA;
        }


        this.getGUI().close();
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public GUI getGUI() {
        return this.gui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        plankType.getItems().add("Oak");
        plankType.getItems().add("Mahogany");
        plankType.getItems().add("Teak");
        plankType.getSelectionModel().selectFirst();;
        bankingLocation.getItems().add("Lumbridge");
        bankingLocation.getItems().add("Camelot");
        bankingLocation.getSelectionModel().selectFirst();
    }


}