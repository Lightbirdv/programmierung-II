package adressbuch;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Die Klasse ViewHelper besitzt statische Methoden für das Anzeigen von GUI-Elementen
 * in einen zusaetzlichen Fenster.
 * @author beuth
 */
public class ViewHelper {

    /**
     * Oeffnet ein Fenster in dem die Fehlermeldung angezeigt wird.
     * @param error String mit dem anzuzeigenden Fehler
     */
    public static void showError(String error) {
        Alert alert = new Alert(Alert.AlertType.WARNING, error);
        alert.setTitle("Fehler");
        alert.setHeaderText("Es ist folgender Fehler aufgetreten:");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Fenster wieder geschlossen.");
        }
    }

    /**
     * Laedt eine neue Scene in einer Stage mit dem übergebenen Controller und 
     * dem übergebenen FXML-Dokument
     * @param controller Der für die GUI zu verwendende Controller
     * @param location   Die URL der FXML-Datei mit der GUI-Beschreibung
     */
    public static void showView(Initializable controller, URL location) {
        Stage editStage = new Stage(StageStyle.UTILITY);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setController(controller);
        try {
            Pane myPane = (Pane) fxmlLoader.load();
            Scene myScene = new Scene(myPane);
            editStage.setScene(myScene);
            //editStage.initModality(Modality.WINDOW_MODAL);
            editStage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
