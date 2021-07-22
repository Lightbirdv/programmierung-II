package terminplaner;

import adressbuch.AdressbuchSelectViewController;
import adressbuch.Kontakt;
import adressbuch.ViewHelper;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
        if(termin == null && controller != null)
            this.initNewTermin();
        if(termin !=null && controller != null)
            this.initUpdateTermin();
        if(termin != null && controller == null)
            this.initShowTermin();
        
        addTeilnehmer.setOnAction((ActionEvent e) -> showKontakte());
        save.setOnAction((ActionEvent e) -> {
            try {
                saveTermin();
            } catch (UngueltigerTerminException ex) {
                this.error.setText("Das Startdatum muss vor dem Ende-Datum liegen!");
            }
        });
        cancel.setOnAction((ActionEvent e) -> close());
    }

    /**
     * Initialisiert die GUI-Elemente des Editors für das Anlegen eines neuen
     * Termins.
     */
    private void initNewTermin() {
        this.titel.setText("Termineditor");
        this.datum.setValue(controller.getSelectedDate());
        this.save.setText("Speichern");
    }

    /**
     * Initialisiert die GUI-Elemente des Editors für das Editieren eines
     * Termins.
     */
    private void initUpdateTermin() {
        this.titel.setText("Termin von " + termin.getBesitzer().getName());
        this.datum.setValue(termin.getDatum());
        this.addTeilnehmer.setVisible(false);
        this.von.setText(termin.getVon().toString());
        this.bis.setText(termin.getBis().toString());
        this.text.setText(termin.getText());
        ObservableList<Kontakt> teilnehmerOAL = FXCollections.observableArrayList();
        teilnehmerOAL.addAll(termin.getTeilnehmer());
        this.teilnehmerliste.setItems(teilnehmerOAL);
        this.save.setText("Aktualisieren");
    }

    /**
     * Initialisiert die GUI-Elemente des Editors für das Anzeigen eines fremden
     * Termins.
     */
    private void initShowTermin() {
        this.titel.setText("Termin von " + termin.getBesitzer().getName());
        this.datum.setValue(termin.getDatum());
        this.addTeilnehmer.setVisible(false);
        this.von.setText(termin.getVon().toString());
        this.bis.setText(termin.getBis().toString());
        this.text.setText(termin.getText());
        ObservableList<Kontakt> teilnehmerOAL = FXCollections.observableArrayList();
        teilnehmerOAL.addAll(termin.getTeilnehmer());
        this.teilnehmerliste.setItems(teilnehmerOAL);
        this.save.setText("Save");
        this.save.setDisable(true);
    }

    /**
     * Wird aufgerufen wenn der Save/Update Button gedrueckt wurde. Termininfos
     * aus den Eingabefeldern werden in den Termin gefuellt (beim Editieren
     * eines existierenden Termins) oder es wird ein neuer Termin mit den Infos
     * erzeugt und dieser dem Controller gemeldet. Ist der Termin ungueltig,
     * z.B. wegen der von/bis Angaben, so wird der Fehler im Fenster angezeigt.
     */
    private void saveTermin() throws UngueltigerTerminException {
        // Neuer Termin
        
        if(termin == null) {
            Termin t = new Termin(text.getText(),datum.getValue(),this.getTime(von.getText()),this.getTime(bis.getText()));
            ObservableList<Kontakt> teilnehmerOAL = this.teilnehmerliste.getItems();
            teilnehmerOAL.forEach(k -> {
                t.addTeilnehmer(k);
            });
            controller.processTermin(t);
            this.close();
        }
        if(termin != null) {
            Termin t = termin.getCopy();
            t.setDatum(datum.getValue());
            t.setVonBis(this.getTime(von.getText()),this.getTime(bis.getText()));
            t.setText(text.getText());
            ObservableList<Kontakt> teilnehmerOAL = this.teilnehmerliste.getItems();
            teilnehmerOAL.forEach(k -> {
                t.addTeilnehmer(k);
            });
            controller.processTermin(t);
            this.close();
        }
        
        
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
        AdressbuchSelectViewController asvc = new AdressbuchSelectViewController(controller.getAdressbuch(), this);
        ViewHelper.showView(asvc, getClass().getResource("../adressbuch/adressbuchView.fxml"));
    }
    
    public void addTeilnehmer(Kontakt k) {
        this.teilnehmerliste.getItems().add(k);
    }

}
