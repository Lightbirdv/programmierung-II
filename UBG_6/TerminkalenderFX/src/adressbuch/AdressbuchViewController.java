/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adressbuch;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * FXML Controller class
 *
 * @author hochschule
 */
public class AdressbuchViewController implements Initializable {

    @FXML
    private TextField searchField;
    private Adressbuch adressbuch;
    @FXML
    private TableView<Kontakt> tableView;
    @FXML
    private TableColumn<Kontakt, String> name;
    @FXML
    private TableColumn<Kontakt, String> telefon;
    @FXML
    private TableColumn<Kontakt, String> email;

    private ObservableList<Kontakt> data;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private Button addButton;
    /**
     * Initializes the controller class.
     * @param url
     * @throws DoppelterSchluesselException
     */
    public AdressbuchViewController() {
        try {
            this.adressbuch = new Adressbuch();
        } catch (UngueltigerSchluesselException ex) {
            Logger.getLogger(AdressbuchViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public AdressbuchViewController(Adressbuch adressbuch) {
        this.adressbuch = adressbuch;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            data = FXCollections.observableArrayList();
            configureTable();
            Kontakt[] initKontakte = adressbuch.getAlleKontakte();
            showKontakte(initKontakte);
            searchField.setOnAction((ActionEvent e) -> clearTableView());
            searchField.textProperty().addListener((e) -> filterList());
            addButton.setOnAction((ActionEvent e) -> createNewKontakt());
    }    
    
    private void filterList() {
        String praefix = searchField.getText().toLowerCase();
        Kontakt[] kontakte = adressbuch.searchKontakte(praefix);
        showKontakte(kontakte);
    }
    
    private void showKontakte(Kontakt[] kontakte) {
        clearTableView();
        data.addAll(Arrays.asList(kontakte));
        tableView.setItems(data);
    }

    private void clearTableView() {
        tableView.getItems().clear();
    }

    private void configureTable() {
        name.setCellValueFactory(new PropertyValueFactory<Kontakt, String>("name"));
        telefon.setCellValueFactory(new PropertyValueFactory<Kontakt, String>("telefon"));
        email.setCellValueFactory(new PropertyValueFactory<Kontakt, String>("email"));
        tableView.setEditable(true);
        name.setCellFactory(TextFieldTableCell.<Kontakt>forTableColumn());
        name.setOnEditCommit((e)->updateCell(e));
        telefon.setCellFactory(TextFieldTableCell.<Kontakt>forTableColumn());
        telefon.setOnEditCommit((e)->updateCell(e));
        email.setCellFactory(TextFieldTableCell.<Kontakt>forTableColumn());
        email.setOnEditCommit((e)->updateCell(e));
    }

    private void updateCell(TableColumn.CellEditEvent<Kontakt, String> e) {
        String alt = e.getOldValue();
        String neu = e.getNewValue();
        if (alt.equals(neu)) return;
        int index = e.getTablePosition().getRow();
        Kontakt k = tableView.getItems().get(index);
        if (e.getTablePosition().getColumn() == 0) k.setName(neu);
        if (e.getTablePosition().getColumn() == 1) k.setTelefon(neu);
        if (e.getTablePosition().getColumn() == 2) k.setEmail(neu);
        
        try {
            adressbuch.updateKontakt(alt, k);
        } catch (UngueltigerSchluesselException ex) {
            Logger.getLogger(AdressbuchViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        showKontakte(adressbuch.getAlleKontakte());
    }

    private void createNewKontakt() {
        if(nameField.getText().isEmpty() && phoneField.getText().isEmpty() && emailField.getText().isEmpty()) return;
        if(nameField.getText().isEmpty() && phoneField.getText().isEmpty()) {
            ViewHelper.showError("Beide Schl??sselargumente sind leer: Fehler!");
            emailField.clear();
            return;
        }
        try {
            adressbuch.addKontakt(new Kontakt(nameField.getText().trim().toLowerCase(),phoneField.getText().trim(),emailField.getText().trim().toLowerCase()));
            showKontakte(adressbuch.getAlleKontakte());
            nameField.clear();
            phoneField.clear();
            emailField.clear();
            return;
        }catch(Exception e) {
            ViewHelper.showError("Error occured: " + e);
        }
    }
}
