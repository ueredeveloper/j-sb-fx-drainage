package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import com.sun.javafx.webkit.WebConsoleListener;

import controllers.views.InterferenceTextFieldsController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import models.Interferencia;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

/**
 * Controlador para a interface de mapa.
 */
public class MapController implements Initializable {

	

	@FXML
	private AnchorPane apMap;

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

				// System.out.println(" initComunicantion funcionando");
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

	}

	public void handleAddMarker(String json) {
		invokeJS("addMarker(" + json + ");");
	}

}
