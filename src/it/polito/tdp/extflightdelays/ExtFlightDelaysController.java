/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import it.polito.tdp.extflightdelays.model.Rotta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="compagnieMinimo"
    private TextField compagnieMinimo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="btnAeroportiConnessi"
    private Button btnAeroportiConnessi; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoDestinazione"
    private ComboBox<Airport> cmbBoxAeroportoDestinazione; // Value injected by FXMLLoader

    @FXML // fx:id="numeroTratteTxtInput"
    private TextField numeroTratteTxtInput; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaItinerario"
    private Button btnCercaItinerario; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	txtResult.clear();
    	cmbBoxAeroportoPartenza.getItems().clear();
    	cmbBoxAeroportoDestinazione.getItems().clear();
    	try {
    	int x = Integer.parseInt(compagnieMinimo.getText());
    	if (x == 0) {
    		txtResult.appendText("Inserire un numero");
    	}
    	
    	this.model.creaGrafo(x);
    	
    	cmbBoxAeroportoPartenza.getItems().addAll(this.model.getAer());
    	cmbBoxAeroportoDestinazione.getItems().addAll(this.model.getAer());
    	
    	} catch (NumberFormatException e ) {
    		txtResult.appendText("Inserire un numero");
    	} catch (RuntimeException e ) {
    		txtResult.appendText("ERRORE NEL DB");
    	}
    	
    	
    	
    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	
    	try {
    	Airport a = cmbBoxAeroportoPartenza.getValue();
    	
    	if (a == null) {
    		txtResult.appendText("Scegliere un aeroporto!");
    	}
    	
    	List<Rotta> connessi = this.model.getConnessi(a);
    	
    	for (Rotta temp: connessi) {
    		txtResult.appendText(temp.getA2()+" "+temp.getVoli()+"\n");
    	}
    	}catch (RuntimeException e ) {
    		txtResult.appendText("ERRORE NEL DB");
    	}
    	
    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	
    	try {
    	
    	Airport a1 = cmbBoxAeroportoPartenza.getValue();
    	Airport a2 = cmbBoxAeroportoDestinazione.getValue();
    	int t = Integer.parseInt(numeroTratteTxtInput.getText());
    	
    	if (a1==null || a2==null) {
    		txtResult.appendText("Selezionare aeroporto");
    	}
    	
    	if (t == 0) {
    		txtResult.appendText("DEVI INSERIRE UN NUMERO VALIDO");
    	}
    	
    	List<Airport> tratte = this.model.cercaItinerario(a1,a2,t);
    	
    	txtResult.appendText("\n"+"Il percorso e':\n");
    	
    	for (Airport r: tratte) {
    		txtResult.appendText(r+"\n");
    	}
    	
    	txtResult.appendText("\n"+this.model.getTot());
    	

    	}catch (NumberFormatException e ) {
    		txtResult.appendText("Inserire un numero"+"\n");
    	}
    	catch (RuntimeException e ) {
    		txtResult.appendText("ERRORE NEL DB"+"\n");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert compagnieMinimo != null : "fx:id=\"compagnieMinimo\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoDestinazione != null : "fx:id=\"cmbBoxAeroportoDestinazione\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroTratteTxtInput != null : "fx:id=\"numeroTratteTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }
    
    
    public void setModel(Model model) {
  		this.model = model;
  		
  	}
}
