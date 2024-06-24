package controllers.views;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXTextField;

import controllers.MapController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import models.Interferencia;

public class InterferenceTextFieldsController {
	
	
	

	public static InterferenceTextFieldsController getInstance() {
		return instance;
	}

	private Interferencia interferencia = new Interferencia();

	private String localUrl;
	private JFXTextField tfLatitude;
	private JFXTextField tfLongitude;
	private MapController mapController;
	

	public InterferenceTextFieldsController(String localUrl, JFXTextField tfLatitude, JFXTextField tfLongitude) {
		this.localUrl = localUrl;
		this.mapController = MapController.getInstance();
		this.tfLatitude = tfLatitude;
		this.tfLongitude = tfLongitude;
		instance = this;

		init();

	}

	private static InterferenceTextFieldsController instance;

	// Adicione os campos existentes e o construtor


	// Método privado para atualizar a latitude
	private void updateLatitude(String newValue) {

		try {
			if (newValue != null && !newValue.trim().isEmpty()) {
				Double latitude = Double.parseDouble(newValue);
				interferencia.setInterLatitude(latitude);
			} else {
				interferencia.setInterLatitude(null); // Set null if empty or null input
			}
		} catch (NumberFormatException e) {
			// Handle invalid number format
			interferencia.setInterLatitude(null); // Set null if input cannot be parsed
		}
		if (interferencia.getInterLatitude() != null && interferencia.getInterLongitude() != null) {

			mapController.handleAddMarker(convertObjectToJson(interferencia));
		}

	}

	// Método privado para atualizar a longitude
	private void updateLongitude(String newValue) {

		try {
			if (newValue != null && !newValue.trim().isEmpty()) {
				Double longitude = Double.parseDouble(newValue);
				interferencia.setInterLongitude(longitude);
			} else {
				interferencia.setInterLongitude(null); // Set null if empty or null input
			}
		} catch (NumberFormatException e) {
			// Handle invalid number format
			interferencia.setInterLongitude(null); // Set null if input cannot be parsed
		}

		if (interferencia.getInterLatitude() != null && interferencia.getInterLongitude() != null) {

			mapController.handleAddMarker(convertObjectToJson(interferencia));
		}
	}

	// Método para obter a instância de Interferencia com latitude e longitude
	public Interferencia getLatitudeLongitude() {
		return interferencia;
	}

	public void updateCoordinates(Interferencia interferencia) {

		System.out.println("Tf update coords " + interferencia.getInterLatitude() + tfLatitude.getText());

		tfLatitude.setText(String.valueOf(interferencia.getInterLatitude()));
		tfLongitude.setText(String.valueOf(interferencia.getInterLongitude()));

		// mapController.handleAddMarker(convertObjectToJson(interferencia));
	}

	private String convertObjectToJson(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

	public void init() {
		tfLatitude.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				updateLatitude(newValue);
			}
		});

		tfLongitude.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				updateLongitude(newValue);
			}
		});

	}
}
