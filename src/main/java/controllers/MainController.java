package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import com.sun.javafx.webkit.WebConsoleListener;

@SuppressWarnings("restriction")
public class MainController implements Initializable {

	@FXML
	private AnchorPane apMain;

	@FXML
	private AnchorPane apTop;

	@FXML
	private AnchorPane apContent;

	@FXML
	private AnchorPane apMap;

	@FXML
	private AnchorPane apManager;

	@FXML
	private WebView wvMap;

	public AnchorPane getAnchorPaneContent() {
		return this.apContent;
	}

	public Pane getAnchorPaneManager() {
		return this.apManager;
	}

	public Pane getAnchorPaneMap() {
		return this.apMap;
	}

	@FXML
	private Button btnZoom;

	private JSObject doc;
	private WebEngine webEngine;
	public boolean ready;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

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
		
		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            public void changed(final ObservableValue<? extends Worker.State> observableValue,
                                final Worker.State oldState,
                                final Worker.State newState)
            {
                if (newState == Worker.State.SUCCEEDED)
                {
                    doc = (JSObject) webEngine.executeScript("window");
                    
                    doc.setMember("app", MainController.this);
                    
                    //doc.setMember("appShapeEndereco", GoogleMap.this);
                }
               
               // System.out.println(" initComunicantion funcionando");
            }
        });

	

		// mostrar erros no console
		WebConsoleListener.setDefaultListener((webView, message, lineNumber, sourceId) -> {
			System.out.println(message + "[at " + lineNumber + "]");
		});

		// Add a listener to the width property of the AnchorPane
		apContent.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double newWidth = newValue.doubleValue();
				apMap.setPrefWidth(newWidth / 2);
				apManager.setPrefWidth(newWidth / 2);
			}
		});

		// Torna as dimens�es do WebView (wvMap) semelhantes ao do pai (aapMap)
		apMap.widthProperty().addListener((observable, oldValue, newValue) -> {
			// setar prefwidth no mapa
			wvMap.setPrefWidth(newValue.doubleValue());
		});

		apMap.heightProperty().addListener((observable, oldValue, newValue) -> {
			wvMap.setPrefHeight(newValue.doubleValue());
		});

		loadNavigationBar();

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
		invokeJS("app.setZoom();");
	}

	private DocumentController docController;
	private AnchorPane apDocument; // Store the AnchorPane of Documents.fxml

	private void loadNavigationBar() {
		try {

			// Abrir a barra de navega��o

			FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/fxml/Navigation.fxml"));
			AnchorPane apNavBar = loader1.load();
			// acesso ao MainController dentro do NavBarController
			NavigationController navBarController = loader1.getController();
			navBarController.setMainController(this);

			apTop.getChildren().add(apNavBar);
			// seta apNavBar no lado direito da tela
			AnchorPane.setRightAnchor(apNavBar, 0.0);

			if (apDocument == null) {
				// Open the DocumentController
				FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/Documents.fxml"));
				apDocument = loader2.load();

				docController = loader2.getController();
				docController.setMainController(this);

				apManager.getChildren().add(apDocument);
			} else {
				// Close the DocumentController
				apManager.setVisible(!apDocument.isVisible());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
