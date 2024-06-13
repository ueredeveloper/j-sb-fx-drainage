package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class InterferenceController implements Initializable {
	
	

	DocumentController documentController;
	TranslateTransition ttClose;
	
    @FXML
    private JFXButton btnClose;
	
	
	public InterferenceController(DocumentController documentController, TranslateTransition ttClose ) {
		this.documentController = documentController;
		this.ttClose = ttClose;
	}
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Fechamento do anchor pane
		btnClose.setOnAction(event -> this.ttClose.play());
		
	}

}
