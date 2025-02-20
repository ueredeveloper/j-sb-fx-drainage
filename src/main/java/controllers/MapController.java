package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import com.sun.javafx.webkit.WebConsoleListener;

import controllers.views.AddInterferenceController;
import controllers.views.CoordinateConversorController;
import controllers.views.InterferenceTextFieldsController;
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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import models.Interferencia;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

/**
 * Controlador para a interface de mapa.
 */
public class MapController implements Initializable {

	@FXML
	private AnchorPane apMap, apCoordConversor;

	@FXML
	WebView wvMap;

	@FXML
	private JFXButton btnZoomPlus;

	@FXML
	private JFXButton btnStreet;

	@FXML
	private JFXButton btnSatellite;

	@FXML
	private JFXButton btnHybrid;

	@FXML
	private JFXButton btnZoomMinus;

	@FXML
	private Button btnCopyLat, btnCopyLng;

	@FXML
	private Label lblLatitude, lblLongitude;

	private JSObject doc;
	private WebEngine webEngine;
	public boolean ready;
	private AnchorPane apContent;

	private static MapController instance;

	public static MapController getInstance() {
		return instance;
	}

	public MapController(AnchorPane apContent) {
		this.apContent = apContent;
		instance = this; // Define a instância no construtor
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
				apMap.setPrefWidth(newWidth / 2.99);
				// apManager.setPrefWidth(newWidth / 2);
			}
		});

		webEngine = wvMap.getEngine();

		webEngine.load(getClass().getResource("/html/map/index.html").toExternalForm());

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

		/* apMap.getChildren().add(btnZoom); */

		btnZoomPlus.setOnAction(event -> handleZoomPlus(event));
		btnZoomMinus.setOnAction(event -> handleZoomMinus(event));
		btnStreet.setOnAction(event -> handleStreetMap(event));
		btnSatellite.setOnAction(event -> handleSatelliteMap(event));
		btnHybrid.setOnAction(event -> handleHybridMap(event));

		btnCopyLat.setOnAction(evet -> copyToClipboard("Latitude"));
		btnCopyLng.setOnAction(evet -> copyToClipboard("Longitude"));
		

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

					// System.out.println(" invokeJS funcionando");
				}
			});

		}
	}

	private void handleZoomPlus(ActionEvent event) {
		invokeJS("setMapZoomPlus()");
	}

	private void handleZoomMinus(ActionEvent event) {
		invokeJS("setMapZoomMinus()");
	}

	private void handleStreetMap(ActionEvent event) {
		invokeJS("setMapLayer(streetLayer);");

	}

	private void handleSatelliteMap(ActionEvent event) {
		invokeJS("setMapLayer(satelliteLayer);");

	}

	private void handleHybridMap(ActionEvent event) {
		invokeJS("setMapLayer(hybridLayer);");

	}

	public void printCoords(String coords) {

		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(coords, JsonObject.class);
		Double interLatitude = jsonObject.get("lat").getAsDouble();
		Double interLongitude = jsonObject.get("lng").getAsDouble();

		Interferencia interferencia = new Interferencia(interLatitude, interLongitude);

		InterferenceTextFieldsController interferenceController = InterferenceTextFieldsController.getInstance();
		if (interferenceController != null) {
			interferenceController.updateCoordinates(interferencia);
		} else {
			System.out.println("InterferenceTextFieldsController instance is null!");
		}
		AddInterferenceController addInterController = AddInterferenceController.getInstance();
		if (addInterController != null) {
			addInterController.updateCoordinates(interferencia);
		} else {
			System.out.println("Add Interference Controller instance is null!");
		}

	}

	public void handleAddMarker(String json) {
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
			conversorPane.setTranslateY(-apCoordConversor.getHeight());

			apCoordConversor.getChildren().setAll(conversorPane);
			AnchorPane.setLeftAnchor(conversorPane, 0.0);
			AnchorPane.setRightAnchor(conversorPane, 0.0);

			// Animate from top to bottom
			TranslateTransition transition = new TranslateTransition(Duration.millis(500), conversorPane);
			transition.setToY(-25);
			transition.play();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void hideCoordConversor() {
		if (conversorPane != null) {
			TranslateTransition transition = new TranslateTransition(Duration.millis(200), conversorPane);
			transition.setToY(apCoordConversor.getHeight()); // Move up
			transition.setOnFinished(event -> apCoordConversor.getChildren().remove(conversorPane)); // Remove after
																										// animation
			transition.play();
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

}
