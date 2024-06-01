package controllers;

import java.applet.AppletContext;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

@SuppressWarnings("restriction")
public class MainController implements Initializable {

	@FXML
	private AnchorPane apMain;

	@FXML
	private AnchorPane apTop;

	@FXML
	private AnchorPane apContent;


	@FXML
	private AnchorPane apManager;


	public AnchorPane getAnchorPaneContent() {
		return this.apContent;
	}

	public Pane getAnchorPaneManager() {
		return this.apManager;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Map.fxml"));

			// Set the controller factory
			fxmlLoader.setControllerFactory(controllerClass -> {
				if (controllerClass == MapController.class) {
					MapController controller = new MapController(apContent);
					return controller;
				} else {
					try {
						return controllerClass.newInstance();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});

			Parent root = fxmlLoader.load();
			
			apContent.getChildren().add(root);
			// Add the root to your scene or parent node as needed
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Add a listener to the width property of the AnchorPane
		apContent.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double newWidth = newValue.doubleValue();
				//apMap.setPrefWidth(newWidth / 2);
				apManager.setPrefWidth(newWidth / 2);
			}
		});

		loadNavigationBar();

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
