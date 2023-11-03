package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MainController implements Initializable {

	@FXML
	private AnchorPane apMain;

	@FXML
	private AnchorPane apTopBar;

	@FXML
	private AnchorPane apMap;

	@FXML
	private WebView wvMap;

	public AnchorPane getAnchorPaneMap () {
		return apMap;
	}
	public AnchorPane getAnchorPaneMain () {
		return apMain;
	}
	public void setRightAnchor (AnchorPane ac, double value) {
		AnchorPane.setRightAnchor(ac, value);
	}
	
	public void setAnchorPositions (AnchorPane ac, double value) {
		AnchorPane.setTopAnchor(ac, 50.0);
		AnchorPane.setRightAnchor(ac, value);
		AnchorPane.setBottomAnchor(ac, value);
		AnchorPane.setLeftAnchor(ac, value);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		WebEngine webEngine = wvMap.getEngine();
		webEngine.load(getClass().getResource("/map/index.html").toExternalForm());

		loadNavBar();

		// Torna as dimensões do WebView (wvMap) semelhantes ao do pai (apMap)
		apMap.widthProperty().addListener((observable, oldValue, newValue) -> {
			wvMap.setPrefWidth(newValue.doubleValue());

		});

		apMap.heightProperty().addListener((observable, oldValue, newValue) -> {
			wvMap.setPrefHeight(newValue.doubleValue());
		});

	}

	private void loadNavBar() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NavBar.fxml"));
			AnchorPane apNavBar = loader.load();
		
			// acesso ao MainController dentro do NavBarController
			NavBarController navBarController = loader.getController();
			navBarController.setMainController(this);
			

			apTopBar.getChildren().add(apNavBar);
			// seta apNavBar no lado direito da tela
			setRightAnchor(apNavBar, 0.0);

			// Seta pane com barra de navegação (p do lado direito do pane (apTopBar)
		/*	apTopBar.widthProperty().addListener((observable, oldValue, newValue) -> {
				apNavBar.layoutXProperty().set(newValue.doubleValue() - apNavBar.getPrefWidth());

			});*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
