package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MainController implements Initializable {

	@FXML
	AnchorPane apMain;

	@FXML
	Pane pTopBar;

	@FXML
	Pane pMapAndDocs;

	@FXML
	private WebView wvMap;

	@FXML
	private JFXButton btnOpenListDocs;
	
	@FXML
    private NavBarController navBarController;
	
	public Pane getPane () {
		return pMapAndDocs;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		WebEngine webEngine = wvMap.getEngine();
		webEngine.load(getClass().getResource("/map/index.html").toExternalForm());

		loadNavBar();

		// Torna as dimens�es do WebView (wvMap) semelhantes ao do pai (pMapAndDocs)

		pMapAndDocs.widthProperty().addListener((observable, oldValue, newValue) -> {
			wvMap.setPrefWidth(newValue.doubleValue());

		});

		pMapAndDocs.heightProperty().addListener((observable, oldValue, newValue) -> {
			wvMap.setPrefHeight(newValue.doubleValue());
		});

		

	}

	private void loadNavBar() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NavBar.fxml"));
			Pane pTopBarContent = loader.load();
			pTopBar.getChildren().add(pTopBarContent);

			// Setar pane com barra de navega��o do lado direito do pane (pTopBar)
			pTopBar.widthProperty().addListener((observable, oldValue, newValue) -> {
				pTopBarContent.layoutXProperty().set(newValue.doubleValue()-pTopBarContent.getPrefWidth());

			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
