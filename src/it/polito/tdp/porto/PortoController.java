package it.polito.tdp.porto;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Stat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PortoController {
	Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtMatricolaStudente;

	@FXML
	private TextArea txtResult;

	@FXML
	void doFrequenzaRiviste(ActionEvent event) {
		int totRiv=0;
		for(Stat s: model.getFrequenze()){
			txtResult.appendText("Nr riviste con "+s.getNrPubb()+" pubblicazioni :"+s.getNrRiv()+"\n");
			totRiv+=s.getNrRiv();
		}
		
		txtResult.appendText(totRiv+" ");
	}

	@FXML
	void doVisualizzaRiviste(ActionEvent event) {
		
		txtResult.appendText(model.getMinSequenza().toString());
	}

	@FXML
	void initialize() {
		assert txtMatricolaStudente != null : "fx:id=\"txtMatricolaStudente\" was not injected: check your FXML file 'DidatticaGestionale.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DidatticaGestionale.fxml'.";

	}

	public void setModel(Model model) {
		this.model=model;
		
	}

}
