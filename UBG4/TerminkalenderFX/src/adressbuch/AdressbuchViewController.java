/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adressbuch;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author hochschule
 */
public class AdressbuchViewController implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    private TextArea textArea;
    private Adressbuch adressbuch;

    /**
     * Initializes the controller class.
     * @param url
     * @throws DoppelterSchluesselException
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            adressbuch = new Adressbuch();
            //showKontakte(adressbuch.getAlleKontakte());
            searchField.setOnAction((ActionEvent e) -> clearTextArea());
            searchField.textProperty().addListener((e) -> filterList());
        } catch (UngueltigerSchluesselException ex) {
            Logger.getLogger(AdressbuchViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void filterList() {
        String praefix = searchField.getText();
        Kontakt[] kontakte = adressbuch.searchKontakte(praefix);
        showKontakte(kontakte);
    }
    
    private void showKontakte(Kontakt[] kontakte) {
        textArea.clear();
        for (int k=0; k<kontakte.length; k++) {
            textArea.appendText(kontakte[k].toString());
            textArea.appendText("\n");
        }
    }

    private void clearTextArea() {
        textArea.clear();
    }
    
}
