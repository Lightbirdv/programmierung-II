package terminplaner;

import adressbuch.Adressbuch;
import adressbuch.UngueltigerSchluesselException;
import adressbuch.AdressbuchViewController;
import adressbuch.ViewHelper;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
        addButton = new Button();
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
        termine.getItems().add(laden);
        termine.getItems().add(speichern);
        kontakte.getItems().add(bearbeiten);
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
        this.terminListe.setOnEditCommit((e)-> editTermin());
    }
    
    private void addTermin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void showTermine() {
        this.terminListe.getItems().clear();
        this.terminData.addAll(planer.getTermineTag(getSelectedDate()));
        terminListe.setItems(this.terminData);
    }

    private void editKontakte() throws IOException {
        AdressbuchViewController avc = new AdressbuchViewController(adressen);
        ViewHelper.showView(avc, getClass().getResource("../adressbuch/adressbuchView.fxml"));
    }

    private void saveTermine() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadTermine() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void editTermin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Adressbuch getAdressbuch() {
        return this.adressen;
    }
    
    public LocalDate getSelectedDate() {
        return this.date.getValue();
    } 
    
    
}
