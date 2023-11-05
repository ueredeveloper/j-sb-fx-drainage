package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MainController implements Initializable {

	@FXML
    private AnchorPane apMain;

    @FXML
    private AnchorPane acTop;

    @FXML
    private AnchorPane apContent;

    @FXML
    private Pane pMap;

    @FXML
    private WebView wvMap;

    @FXML
    private Pane pManager;
   

	public AnchorPane getAnchorPaneContent () {
		return this.apContent;
	}
	public void setRightAnchorPaneContent(Pane pane) {
		AnchorPane.setRightAnchor(pane, 0.0);
	}
	public AnchorPane getAnchorPaneMain () {
		return this.apMain;
	}
	public Pane getPaneManager () {
		return this.pManager;
	}
	public Pane getMapPane () {
		return this.pMap;
	}
	
	double mapWidth = 0;
	
	public void setMapWidth (double width) {
		this.mapWidth = width;
	}
	public double getMapWidth () {
		return this.mapWidth;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		WebEngine webEngine = wvMap.getEngine();
		webEngine.load(getClass().getResource("/map/index.html").toExternalForm());

		// setar pManager à direita
		AnchorPane.setRightAnchor(pManager, 0.0);

		// Torna as dimensões do WebView (wvMap) semelhantes ao do pai (apMap)
		pMap.widthProperty().addListener((observable, oldValue, newValue) -> {
			// setar largura do mapa
			setMapWidth(newValue.doubleValue());
			// setar prefwidth no mapa
			wvMap.setPrefWidth(newValue.doubleValue());
			// setar prefWidth no pane pManager
			//pManager.setPrefWidth(newValue.doubleValue()/2);
			// direcionar pManager para a direita
			

		});

		pMap.heightProperty().addListener((observable, oldValue, newValue) -> {
			wvMap.setPrefHeight(newValue.doubleValue());
		});
		
		loadNavBar();

	}

	private void loadNavBar() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NavBar.fxml"));
			AnchorPane apNavBar = loader.load();
		
			// acesso ao MainController dentro do NavBarController
			NavBarController navBarController = loader.getController();
			navBarController.setMainController(this);
			

			acTop.getChildren().add(apNavBar);
			// seta apNavBar no lado direito da tela
			AnchorPane.setRightAnchor(apNavBar, 0.0);

			// Seta pane com barra de navegação (p do lado direito do pane (aacTop)
		/*	aacTop.widthProperty().addListener((observable, oldValue, newValue) -> {
				apNavBar.layoutXProperty().set(newValue.doubleValue() - apNavBar.getPrefWidth());

			});*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
