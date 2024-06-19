package controllers.views;

import com.jfoenix.controls.JFXTextField;

public class InterferenceTextFieldsController {

	String localUrl;

	private JFXTextField tfLatitude;
	private JFXTextField tfLongitude;

	public InterferenceTextFieldsController(String localUrl, JFXTextField tfLatitude, JFXTextField tfLongitude) {
		this.localUrl = localUrl;
		this.tfLatitude = tfLatitude;
		this.tfLongitude = tfLongitude;
	}

	// Método para inicializar o ComboBox
	public void initializeTextFields() {

	}

	// Método para buscar processos e preencher o ComboBox

}
