package terminplaner;

import adressbuch.Adressbuch;
import adressbuch.UngueltigerSchluesselException;
import adressbuch.AdressbuchViewController;
import adressbuch.ViewHelper;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * FXML Controller class fuer die Terminplaner-Hauptansicht.
 *
 * @author beuth
 */
public class PlanerViewController implements Initializable {

    private Terminplaner planer;
    private Adressbuch adressen;

    @FXML
    private Label titel;
    @FXML
    private MenuBar menuBar;
    @FXML
    private DatePicker date;
    @FXML
    private Button addButton;
    @FXML
    private ListView<Termin> terminListe;

    private Menu termine;
    private Menu kontakte;
    private MenuItem laden;
    private MenuItem speichern;
    private MenuItem bearbeiten;
    private MenuItem kladen;
    private MenuItem kspeichern;
    
    private ObservableList<Termin> terminData;
    
    public PlanerViewController() {
      
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            adressen = new Adressbuch();
            planer = new Terminplaner(adressen.getKontakt("john"));
        } catch (UngueltigerSchluesselException ex) {
            Logger.getLogger(PlanerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        date = new DatePicker(LocalDate.now());
        date.setOnAction((ActionEvent e) -> showTermine());
        addButton.setOnAction((ActionEvent e) -> addTermin());
        configureMenu();
        configureList();
        showTermine();
    }

    private void configureMenu() {
     
        termine = menuBar.getMenus().get(0);
        kontakte = menuBar.getMenus().get(1);
        laden = new MenuItem("Laden");
        speichern = new MenuItem("Speichern");
        bearbeiten = new MenuItem("Bearbeiten");
        kladen = new MenuItem("Laden");
        kspeichern = new MenuItem("Speichern");
        termine.getItems().add(laden);
        termine.getItems().add(speichern);
        kontakte.getItems().add(bearbeiten);
        kontakte.getItems().add(kladen);
        kontakte.getItems().add(kspeichern);
        kladen.setOnAction((ActionEvent e) -> loadKontakte());
        kspeichern.setOnAction((ActionEvent e) -> saveKontakte());
        date.setOnAction((ActionEvent e) -> handleInputDate());
        laden.setOnAction((ActionEvent e) -> loadTermine());
        speichern.setOnAction((ActionEvent e) -> saveTermine());
        bearbeiten.setOnAction((ActionEvent e) -> {
            try {
                editKontakte();
            } catch (IOException ex) {
                Logger.getLogger(PlanerViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void configureList() {
        terminData = FXCollections.observableArrayList();
        this.terminListe.setItems(terminData);
        this.terminListe.setOnMouseClicked((e)-> editTermin());
    }
    
    private void addTermin() {
        TerminViewController tvc = new TerminViewController(null, this);
        ViewHelper.showView(tvc, getClass().getResource("../terminplaner/terminView.fxml"));
    }

    private void showTermine() {
        this.terminListe.getItems().clear();
        if(planer.getTermineTag(getSelectedDate()) == null) {
            System.out.println("list is empty");
        } else {
            this.terminData.addAll(planer.getTermineTag(getSelectedDate()));
        }
        terminListe.setItems(this.terminData);
    }

    private void editKontakte() throws IOException {
        AdressbuchViewController avc = new AdressbuchViewController(adressen);
        ViewHelper.showView(avc, getClass().getResource("../adressbuch/adressbuchView.fxml"));
    }

    private void saveTermine() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Save File");
            chooser.setInitialFileName("Termine.ser");
            chooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Save Files", "*.ser"));
            File selection = chooser.showSaveDialog(null);
            this.planer.save(selection);
        } catch (IOException ex) {
            ViewHelper.showError("Der Terminkalender konnte nicht gespeichert werden: " + ex);
        }
    }

    private void loadTermine() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open File");
            File selection = chooser.showOpenDialog(null);
            this.planer.load(selection);
            this.date.setValue(LocalDate.now());
            this.showTermine();
        } catch (IOException ex) {
            ViewHelper.showError("Der Terminkalender konnte nicht geladen werden: " + ex);
        } catch (ClassNotFoundException | TerminUeberschneidungException ex) {
            Logger.getLogger(PlanerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void handleInputDate() {
        LocalDate d = this.date.getValue();
        System.out.println(d);
        this.date.setValue(d);
        this.showTermine();
    }
    
    private void editTermin() {
        Termin t = terminListe.getSelectionModel().getSelectedItem();
        if(this.planer.updateErlaubt(t)){
            TerminViewController tvc = new TerminViewController(t, this);
            ViewHelper.showView(tvc, getClass().getResource("../terminplaner/terminView.fxml"));
        } else {
            TerminViewController tvc = new TerminViewController(t, null);
            ViewHelper.showView(tvc, getClass().getResource("../terminplaner/terminView.fxml"));
        }
        System.out.println(t);
    }

    public Adressbuch getAdressbuch() {
        return this.adressen;
    }
    
    public LocalDate getSelectedDate() {
        return this.date.getValue();
    } 
    
    public void processTermin(Termin t) throws TerminUeberschneidungException {
        this.planer.setTermin(t);
        this.showTermine();
    }

    private void loadKontakte() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open File");
            File selection = chooser.showOpenDialog(null);
            this.adressen.load(selection);
        } catch (IOException ex) {
            ViewHelper.showError("Die Datei konnte nicht geladen werden: " + ex);
        }
    }

    private void saveKontakte() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Save File");
            chooser.setInitialFileName("Kontakte.ser");
            chooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Save Files", "*.ser"));
            File selection = chooser.showSaveDialog(null);
            this.adressen.save(selection);
        } catch (IOException ex) {
            ViewHelper.showError("Die Datei konnte nicht gespeichert werden: " + ex);
        }
    }
    
}
