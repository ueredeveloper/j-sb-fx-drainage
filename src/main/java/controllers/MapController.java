package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.javafx.webkit.WebConsoleListener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

/**
 * Controlador para a interface de mapa.
 */
public class MapController implements Initializable {

	@FXML
	WebView wvMap;

	@FXML
	private Button btnZoom;

	private JSObject doc;
	private WebEngine webEngine;
	public boolean ready;

	@SuppressWarnings("restriction")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Map Controller Inicializado");

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

		/*
		 * // Torna as dimens�es do WebView (wvMap) semelhantes ao do pai (aapMap)
		 * apMap.widthProperty().addListener((observable, oldValue, newValue) -> { //
		 * setar prefwidth no mapa wvMap.setPrefWidth(newValue.doubleValue()); });
		 * 
		 * apMap.heightProperty().addListener((observable, oldValue, newValue) -> {
		 * wvMap.setPrefHeight(newValue.doubleValue()); });
		 */

		btnZoom.setOnAction(this::handleZoomButtonClick);

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

	private void handleZoomButtonClick(ActionEvent event) {
		// Call setZoom method
		setZoom();
	}

	public void setZoom() {
		invokeJS("console.log('hello world')");
	}

	public void printCoords(String coords) {
		System.out.println(coords);
	}

}
