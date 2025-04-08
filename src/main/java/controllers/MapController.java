package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import com.sun.javafx.webkit.WebConsoleListener;

import controllers.views.CoordinateConversorController;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import utilities.MapListener;
import utilities.SVGIconLoader;
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
	WebView wvMap;

	@FXML
	private JFXButton btnRoadMap, btnSatelliteMap, btnTerrain, btnHybridMap;

	@FXML
	private Button btnCopyLat, btnCopyLng;

	@FXML
	private Label lblLatitude, lblLongitude;

	@FXML
	private Button btnLeaflet, btnMapBox, btnOPenLayers, btnOpenStreet;

	private JSObject doc;
	private WebEngine webEngine;
	public boolean ready;
	private AnchorPane apContent, apManager;

	// private static MapController instance;

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

	@SuppressWarnings("restriction")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

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

		webEngine = wvMap.getEngine();

		webEngine.setJavaScriptEnabled(true);
		webEngine.setOnAlert(event -> System.out.println("Alert: " + event.getData()));
		webEngine.setOnError(event -> System.err.println("Error: " + event.getMessage()));

		// Set User-Agent to mimic a modern browser
		webEngine.setUserAgent(
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

		/*
		 * try { new SimpleWebServer(); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */

		// webEngine.load("http://localhost:3000");

		loadMap("open-layers");

		ready = false;

		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
			public void changed(final ObservableValue<? extends Worker.State> observableValue,
					final Worker.State oldState, final Worker.State newState) {

				if (newState == Worker.State.SUCCEEDED) {
					ready = true;
				}
			}
		});

		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
			public void changed(final ObservableValue<? extends Worker.State> observableValue,
					final Worker.State oldState, final Worker.State newState) {
				if (newState == Worker.State.SUCCEEDED) {
					doc = (JSObject) webEngine.executeScript("window");

					doc.setMember("app", MapController.this);

					// doc.setMember("appShapeEndereco", GoogleMap.this);
				}

			}
		});

		// mostrar erros no console
		WebConsoleListener.setDefaultListener((webView, message, lineNumber, sourceId) -> {
			System.out.println(message + "[at " + lineNumber + "]");
		});

		// Torna as dimens�es do WebView (wvMap) semelhantes ao do pai (aapMap)
		apMap.widthProperty().addListener((observable, oldValue, newValue) -> {
			// setar prefwidth no mapa
			wvMap.setPrefWidth(newValue.doubleValue());
		});

		apMap.heightProperty().addListener((observable, oldValue, newValue) -> {
			wvMap.setPrefHeight(newValue.doubleValue());
		});

		btnRoadMap.setOnAction(event -> handleRoadMap(event));
		btnSatelliteMap.setOnAction(event -> handleSatelliteMap(event));
		btnTerrain.setOnAction(event -> handleTerrainMap(event));
		btnHybridMap.setOnAction(event -> handleHybridMap(event));

		btnCopyLat.setOnAction(evet -> copyToClipboard("Latitude"));
		btnCopyLng.setOnAction(evet -> copyToClipboard("Longitude"));

		// Set SVG icons
		btnLeaflet.setGraphic(SVGIconLoader.getLeafletIcon(84));
		btnMapBox.setGraphic(SVGIconLoader.getMapBoxIcon(5));
		btnOPenLayers.setGraphic(SVGIconLoader.getOpenLayersIcon(10));
		btnOpenStreet.setGraphic(SVGIconLoader.getOpenStreetIcon(10));

		btnLeaflet.setOnAction(evet -> loadMap("leaflet-map"));
		btnMapBox.setOnAction(evet -> loadMap("map-box"));
		btnOPenLayers.setOnAction(evet -> loadMap("open-layers"));
		btnOpenStreet.setOnAction(evet -> loadMap("open-street"));

	}

	public void setLatLng() {
		System.out.println("set lat lng");
	}

	private void invokeJS(final String str) {

		if (ready) {
			try {
				doc.eval(str);
			} catch (JSException js) {
				System.out.println("nao ready execao de leitura javascript " + js);
			}
		} else {

			webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
				@Override
				public void changed(final ObservableValue<? extends Worker.State> observableValue,
						final Worker.State oldState, final Worker.State newState) {
					if (newState == Worker.State.SUCCEEDED) {
						doc.eval(str);

					}

					System.out.println(" invokeJS funcionando");
				}
			});

		}
	}

	private void handleRoadMap(ActionEvent event) {
		invokeJS("setMapType('road');");

	}

	private void handleSatelliteMap(ActionEvent event) {
		invokeJS("setMapType('satellite');");

	}

	private void handleTerrainMap(ActionEvent event) {
		invokeJS("setMapType('terrain');");

	}

	private void handleHybridMap(ActionEvent event) {
		invokeJS("setMapType('hybrid');");

	}

	public void sendCoordinates(String coords) {

		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(coords, JsonObject.class);
		Double latitude = jsonObject.get("lat").getAsDouble();
		Double longitude = jsonObject.get("lng").getAsDouble();

		for (MapListener listener : listeners) {
			listener.setOnTextFieldsLatLng(latitude, longitude);
		}

	}

	public void handleAddMarker(String json) {
		System.out.println("addMarker(" + json + ");");
		invokeJS("addMarker(" + json + ");");
	}

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

		lblLatitude.setText(lat);
		lblLongitude.setText(lng);

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

	private void loadMap(String type) {

		String mapFile;

		if (type.equalsIgnoreCase("leaflet-map")) {
			mapFile = "/html/map/leaflet/index.html";
		} else if (type.equalsIgnoreCase("map-box")) {
			mapFile = "/html/map/map-box/index.html";
		} else if (type.equalsIgnoreCase("open-layers")) {
			mapFile = "/html/map/open-layers/index.html";
		} else if (type.equalsIgnoreCase("gmaps-api")) {
			mapFile = "/html/map/gmaps/index.html";
		} else {
			mapFile = "/html/map/open-street/index.html";
		}

		URL mapUrl = getClass().getResource(mapFile);
		if (mapUrl != null) {

			webEngine.load(mapUrl.toExternalForm());

			ready = false;

			webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
				public void changed(final ObservableValue<? extends Worker.State> observableValue,
						final Worker.State oldState, final Worker.State newState) {

					if (newState == Worker.State.SUCCEEDED) {
						ready = true;
					}
				}
			});

			webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
				public void changed(final ObservableValue<? extends Worker.State> observableValue,
						final Worker.State oldState, final Worker.State newState) {
					if (newState == Worker.State.SUCCEEDED) {
						doc = (JSObject) webEngine.executeScript("window");

						doc.setMember("app", MapController.this);
					}

				}
			});

		} else {
			System.err.println("Map file not found: " + mapFile);
		}
	}

	@Override
	public void addMarker(double latitude, double longitude) {

		String json = "{lat:" + latitude + ",lng:" + longitude + "}";

		System.out.println("map controller, addMarker, json:" + json);

		invokeJS("addMarker(" + json + ");");

	}

}
