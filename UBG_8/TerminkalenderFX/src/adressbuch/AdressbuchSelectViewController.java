/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adressbuch;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import terminplaner.TerminViewController;

/**
 *
 * @author vicol
 */
public class AdressbuchSelectViewController extends AdressbuchViewController {
    
    private TerminViewController controller;
    
    /**
    * @param adressbuch && @param controller um den ausgewählten Kontakt zu übergeben
    * Konstruktor mit TerminViewController um den ausgewählten Kontakt zu übergeben
    */
    public AdressbuchSelectViewController (Adressbuch adressbuch, TerminViewController controller) {
        this.adressbuch = adressbuch;
        this.controller = controller;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            data = FXCollections.observableArrayList();
            configureTable();
            Kontakt[] initKontakte = adressbuch.getAlleKontakte();
            showKontakte(initKontakte);
            searchField.setOnAction((ActionEvent e) -> clearTableView());
            searchField.textProperty().addListener((e) -> filterList());
            addButton.setOnAction((ActionEvent e) -> addKontakt());
            nameField.setVisible(false);
            phoneField.setVisible(false);
            emailField.setVisible(false);
    } 

    private void addKontakt() {
        Kontakt k = tableView.getSelectionModel().getSelectedItem();
        System.out.println(k.toString());
        this.controller.addTeilnehmer(k);
        this.close();
    }
    
    private void close() {
        Stage window = (Stage) addButton.getScene().getWindow();
        window.close();
    }
}
