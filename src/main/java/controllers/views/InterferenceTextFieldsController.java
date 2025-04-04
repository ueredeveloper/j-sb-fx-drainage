package controllers.views;

import com.jfoenix.controls.JFXTextField;

import controllers.MapController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import models.Interferencia;
import utilities.JsonConverter;
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
	
	private MapController mapController;

	String urlService;
	
	MapListener listener;
	
	private TextFieldsListener textFieldsListener;

    public void setTextFieldsListener(TextFieldsListener listener) {
        this.textFieldsListener = listener;
    }
	
	
	public InterferenceTextFieldsController(String urlService, JFXTextField tfLatitude, JFXTextField tfLongitude) {
		this.urlService = urlService;
		this.mapController = MapController.getInstance();
		this.tfLatitude = tfLatitude;
		this.tfLongitude = tfLongitude;
		instance = this;

		//init();

	}

	// Método para obter a instância de Interferencia com latitude e longitude
	public Interferencia getLatLng() {
		return interferencia;
	}

	public void updateCoordinates(Interferencia interferencia) {

		tfLatitude.setText(String.valueOf(interferencia.getLatitude()));
		tfLongitude.setText(String.valueOf(interferencia.getLongitude()));

	}

	@FXML
	public void initialize() {
		
		tfLatitude.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				setCoordOnMap();
				
			}
		});

		tfLongitude.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				setCoordOnMap();
			}
		});
		
	}

	@Override
	public void setOnTextFieldsLatLng (Double latitude, Double longitude) {
		tfLatitude.setText(String.valueOf(latitude));
		tfLongitude.setText(String.valueOf(longitude));	
	}

    public void setCoordOnMap () {
		System.out.println("Interference text on send " + tfLatitude.getText() + tfLongitude.getText());
        try {
            double lat = Double.parseDouble(tfLatitude.getText());
            double lng = Double.parseDouble(tfLongitude.getText());

            if (textFieldsListener != null) {
                textFieldsListener.setOnMapCoords(lat, lng);
            }

        } catch (NumberFormatException e) {
            System.out.println("Coordenadas inválidas");
        }
    }


}
