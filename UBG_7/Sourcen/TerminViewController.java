package terminplaner;

import adressbuch.Kontakt;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class für die EditView, in der sich ein Termin editieren bzw.
 * neu Erstellen oder nur ansehen lässt.
 *
 * @author beuth
 */
public class TerminViewController implements Initializable {

    @FXML
    Label titel;
    @FXML
    DatePicker datum;
    @FXML
    TextField von;
    @FXML
    TextField bis;
    @FXML
    Label error;
    @FXML
    TextArea text;
    @FXML
    ListView<Kontakt> teilnehmerliste;
    @FXML
    Button addTeilnehmer;
    @FXML
    Button cancel;
    @FXML
    Button save;

    private Termin termin;
    private PlanerViewController controller;

    public TerminViewController(Termin termin, PlanerViewController view) {
        this.termin = termin;
        this.controller = view;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Kontakt> items = FXCollections.observableArrayList();
        teilnehmerliste.setItems(items);

        // Hier kommt Ihr Code hinein
    }

    /**
     * Initialisiert die GUI-Elemente des Editors für das Anlegen eines neuen
     * Termins.
     */
    private void initNewTermin() {

    }

    /**
     * Initialisiert die GUI-Elemente des Editors für das Editieren eines
     * Termins.
     */
    private void initUpdateTermin() {

    }

    /**
     * Initialisiert die GUI-Elemente des Editors für das Anzeigen eines fremden
     * Termins.
     */
    private void initShowTermin() {

    }

    /**
     * Wird aufgerufen wenn der Save/Update Button gedrueckt wurde. Termininfos
     * aus den Eingabefeldern werden in den Termin gefuellt (beim Editieren
     * eines existierenden Termins) oder es wird ein neuer Termin mit den Infos
     * erzeugt und dieser dem Controller gemeldet. Ist der Termin ungueltig,
     * z.B. wegen der von/bis Angaben, so wird der Fehler im Fenster angezeigt.
     */
    private void saveTermin() {

    }

    private void close() {
        Stage window = (Stage) cancel.getScene().getWindow();
        window.close();
    }

    /**
     * Erstellt fuer den text ein Objekt vom Typ LocalTime mit der Zeit, die im
     * Text angegeben ist. Std und Min koennen hier mit . oder : getrennt sein.
     * Im Fehlerfall wird dieser im Fenster angezeigt.
     *
     * @param text Text aus den Zeiteingabefeldern.
     * @return das LocalTime Objekt mit der Zeiteinstellung aus dem text
     */
    private LocalTime getTime(String text) {
        LocalTime time = null;

        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("HH.mm");
        try {
            time = LocalTime.parse(text, f1);
        } catch (DateTimeParseException ex1) {
            try {
                time = LocalTime.parse(text, f2);
            } catch (DateTimeParseException ex2) {
                error.setText("Die Zeiten bitte als hh:mm oder hh.mm angeben!");
            }
        }
        return time;
    }

    /**
     * Wird aufgerufen, wenn der addTeilnehmerButton gedrueckt wurde. Hier wird
     * das Auswahlfenster mit den Kontakten angezeigt.
     */
    private void showKontakte() {

    }

}
