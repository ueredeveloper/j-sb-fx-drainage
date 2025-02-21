package controllers.views;

import com.jfoenix.controls.JFXTextField;

import controllers.MapController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import models.Interferencia;
import utilities.JsonConverter;

public class InterferenceTextFieldsController {

	private static InterferenceTextFieldsController instance;

	public static InterferenceTextFieldsController getInstance() {
		return instance;
	}

	private Interferencia interferencia = new Interferencia();

	private JFXTextField tfLatitude;
	private JFXTextField tfLongitude;
	private MapController mapController;

	String urlService;

	public InterferenceTextFieldsController(String urlService, JFXTextField tfLatitude, JFXTextField tfLongitude) {
		this.urlService = urlService;
		this.mapController = MapController.getInstance();
		this.tfLatitude = tfLatitude;
		this.tfLongitude = tfLongitude;
		instance = this;

		init();

	}

	// Adicione os campos existentes e o construtor

	// Método privado para atualizar a latitude
	private void updateLatitude(String newValue) {

		try {
			if (newValue != null && !newValue.trim().isEmpty()) {
				Double latitude = Double.parseDouble(newValue);
				interferencia.setLatitude(latitude);
			} else {
				interferencia.setLatitude(null); // Set null if empty or null input
			}
		} catch (NumberFormatException e) {
			// Handle invalid number format
			interferencia.setLatitude(null); // Set null if input cannot be parsed
		}
		if (interferencia.getLatitude() != null && interferencia.getLongitude() != null) {

			mapController.handleAddMarker(JsonConverter.convertObjectToJson(interferencia));
		}

	}

	// Método privado para atualizar a longitude
	private void updateLongitude(String newValue) {

		try {
			if (newValue != null && !newValue.trim().isEmpty()) {
				Double longitude = Double.parseDouble(newValue);
				interferencia.setLongitude(longitude);
			} else {
				interferencia.setLongitude(null); // Set null if empty or null input
			}
		} catch (NumberFormatException e) {
			// Handle invalid number format
			interferencia.setLongitude(null); // Set null if input cannot be parsed
		}

		if (!Double.isNaN(interferencia.getLatitude()) && !Double.isNaN(interferencia.getLatitude())) {

			mapController.handleAddMarker(JsonConverter.convertObjectToJson(interferencia));
		}
	}

	// Método para obter a instância de Interferencia com latitude e longitude
	public Interferencia getLatLng() {
		return interferencia;
	}

	public void updateCoordinates(Interferencia interferencia) {

		tfLatitude.setText(String.valueOf(interferencia.getLatitude()));
		tfLongitude.setText(String.valueOf(interferencia.getLongitude()));

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
