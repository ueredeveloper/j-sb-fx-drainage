package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import com.sothawo.mapjfx.Configuration;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapType;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import com.sothawo.mapjfx.event.MapViewEvent;

import controllers.views.CoordinateConversorController;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import utilities.MapListener;
import utilities.TextFieldsListener;

/**
 * Controlador para a interface de mapa.
 */
public class MapController implements Initializable, TextFieldsListener {

	@FXML
	private AnchorPane apMap, apCopyCoords;

	@FXML
	private BorderPane bpCoordsConversor;

	@FXML
	private Button btnCopyLat, btnCopyLng, btnSendCoords;

	@FXML
	private Label lblLatitude, lblLongitude;

	private AnchorPane apContent, apManager;

	@FXML
	private BorderPane bpMap;
	// Coordenada inical - Adasa
	private final Coordinate BONN = new Coordinate(-15.775024, -47.940286);

	private MapView mapView;

	private final Set<MapListener> listeners = new HashSet<>();

	public void addMapClickListener(MapListener listener) {
		listeners.add(listener);
	}

	public MapController(AnchorPane apContent, AnchorPane apManager) {
		this.apContent = apContent;
		this.apManager = apManager;
		// instance = this; // Define a instância no construtor
	}

	public AnchorPane getAnchorPaneMap() {
		return this.apMap;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		mapView = new MapView();

		mapView.initialize(Configuration.builder().showZoomControls(true).build());

		// Wait until map is ready
		mapView.initializedProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal) {
				// Adiciona tipo de mapa, zoom e centraliza
				mapView.setMapType(MapType.OSM);
				mapView.setZoom(12);
				mapView.setCenter(BONN);

				// Adiciona marcador inicial
				addMarkerAt(BONN);

				mapView.addEventHandler(MapViewEvent.MAP_CLICKED, event -> {
					Coordinate clickedCoord = event.getCoordinate().normalize();
					// Adiciona marcador no ponto clicado
					addMarkerAt(clickedCoord);

					Double lat = clickedCoord.getLatitude();
					Double lng = clickedCoord.getLongitude();
					// Envia coordenada para os textfields
					sendCoordinates(lat, lng);

				});
			}
		});

		bpMap.setCenter(mapView);

		// Add a listener to the width property of the AnchorPane
		apContent.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double newWidth = newValue.doubleValue();
				if (apManager.isVisible()) {
					apMap.setPrefWidth(newWidth / 4.99);
				}
				// apManager.setPrefWidth(newWidth * 2 / 2.5);
			}
		});

		btnCopyLat.setOnAction(e -> {
			copyToClipboard("Latitude");
		});
		btnCopyLng.setOnAction(e -> {
			copyToClipboard("Longitude");
		});
		btnSendCoords.setOnAction(e -> {
			sendCoordinates(Double.parseDouble(lblLatitude.getText()), Double.parseDouble(lblLongitude.getText()));
		});

	}

	// Captura todos marcadores para remover quando preciso
	Set<Marker> markers = new HashSet<Marker>();

	
	@Override
	public void addMarkerAt(Coordinate coordinate) {

		Marker marker = Marker.createProvided(Marker.Provided.BLUE).setPosition(coordinate).setVisible(true);

		markers.add(marker);

		markers.forEach((m) -> mapView.removeMarker(m));
		// Adiciona marcador
		mapView.addMarker(marker);
		// Centraliza o mapa de acordo com o marcador
		mapView.setCenter(marker.getPosition());
	}

	public void sendCoordinates(Double lat, Double lng) {
		// Formata as coordenadas para ter 6 números depois do ponto
		String latFormatted = String.format(Locale.US, "%.6f", lat);
		String lngFormatted = String.format(Locale.US, "%.6f", lng);

		for (MapListener listener : listeners) {
			listener.setOnTextFieldsLatLng(latFormatted, lngFormatted);
		}

	}

	/*
	 * public void handleAddMarker(String json) { // invokeJS("addMarker(" + json +
	 * ");"); }
	 */

	CoordinateConversorController coordController;
	private AnchorPane conversorPane; // Store the pane reference

	public void showCoordConversor() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/CoordinateConversor.fxml"));
			conversorPane = loader.load();

			// Get the controller from the loader
			coordController = loader.getController();
			coordController.setMapController(this); // Set MapController

			// Set initial position above the visible area
			conversorPane.setTranslateY(-bpCoordsConversor.getHeight());

			bpCoordsConversor.setCenter(conversorPane);
			AnchorPane.setLeftAnchor(conversorPane, 0.0);
			AnchorPane.setRightAnchor(conversorPane, 0.0);

			// Animate from top to bottom
			TranslateTransition transition = new TranslateTransition(Duration.millis(500), conversorPane);
			transition.setToY(-25);
			transition.play();

			apCopyCoords.setVisible(false);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void hideCoordConversor() {
		if (conversorPane != null) {
			TranslateTransition transition = new TranslateTransition(Duration.millis(200), conversorPane);
			transition.setToY(bpCoordsConversor.getHeight()); // Move up
			transition.setOnFinished(event -> bpCoordsConversor.getChildren().remove(conversorPane)); // Remove after
																										// animation
			transition.play();

			apCopyCoords.setVisible(true);
		}
	}

	public void setCoordinates(String lat, String lng) {
		
		// Formata as coordenadas para ter 6 números depois do ponto
		String latFormatted = String.format(Locale.US, "%.6f", Double.parseDouble(lat));
		String lngFormatted = String.format(Locale.US, "%.6f", Double.parseDouble(lng));

		lblLatitude.setText(latFormatted);
		lblLongitude.setText(lngFormatted);

	}

	public void copyToClipboard(String coord) {

		if (coord.equals("Latitude")) {

			if (lblLatitude != null && lblLatitude.getText() != null) {
				Clipboard clipboard = Clipboard.getSystemClipboard();
				ClipboardContent content = new ClipboardContent();
				content.putString(lblLatitude.getText());
				clipboard.setContent(content);
			}

		} else {
			if (lblLongitude != null && lblLongitude.getText() != null) {
				Clipboard clipboard = Clipboard.getSystemClipboard();
				ClipboardContent content = new ClipboardContent();
				content.putString(lblLongitude.getText());
				clipboard.setContent(content);
			}
		}

	}

}
