package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MainController implements Initializable {

	@FXML
	private WebView wvMap;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		WebEngine webEngine = wvMap.getEngine();
		webEngine.load(getClass().getResource("/map/index.html").toExternalForm());

	}

}
