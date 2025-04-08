package controllers.views;

import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import models.Interferencia;
import utilities.MapListener;
import utilities.TextFieldsListener;

public class InterferenceTextFieldsController implements MapListener {

	private static InterferenceTextFieldsController instance;

	public static InterferenceTextFieldsController getInstance() {
		return instance;
	}

	private Interferencia interferencia = new Interferencia();

	@FXML
	private JFXTextField tfLatitude;
	@FXML
	private JFXTextField tfLongitude;

	String urlService;

	MapListener listener;

	private TextFieldsListener textFieldsListener;

	public void setTextFieldsListener(TextFieldsListener listener) {
		this.textFieldsListener = listener;
	}

	public InterferenceTextFieldsController(String urlService, JFXTextField tfLatitude, JFXTextField tfLongitude) {
		this.urlService = urlService;
		this.tfLatitude = tfLatitude;
		this.tfLongitude = tfLongitude;
		instance = this;

		initialize();

	}

	public void initialize() {
		tfLatitude.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				addMarker();

			}
		});

		tfLongitude.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				addMarker();
			}
		});
	}

	// Método para obter a instância de Interferencia com latitude e longitude
	public Interferencia getLatLng() {
		return interferencia;
	}

	public void updateCoordinates(Interferencia interferencia) {

		tfLatitude.setText(String.valueOf(interferencia.getLatitude()));
		tfLongitude.setText(String.valueOf(interferencia.getLongitude()));

	}

	@Override
	public void setOnTextFieldsLatLng(Double latitude, Double longitude) {
		tfLatitude.setText(String.valueOf(latitude));
		tfLongitude.setText(String.valueOf(longitude));
	}

	public void addMarker() {
		try {
			double lat = Double.parseDouble(tfLatitude.getText());
			double lng = Double.parseDouble(tfLongitude.getText());

			if (textFieldsListener != null) {
				textFieldsListener.addMarker(lat, lng);
			}

		} catch (NumberFormatException e) {
			System.out.println("Coordenadas inválidas");
		}
	}

}
