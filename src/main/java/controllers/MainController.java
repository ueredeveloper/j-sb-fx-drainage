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
		System.out.println("MainController initialized");
		/*
		 *  webView = new WebView();
	        webEngine = webView.getEngine();
	        webView.setPrefWidth(1900);
	        webView.setPrefHeight(710);
	        webEngine.load(getClass().getResource("/html/mapas/Principal/index.html").toExternalForm()); // originalMap
		 */

		WebEngine webEngine = wvMap.getEngine();
		//FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modelosHTML/ModelosHTML.fxml"));
		// Load an HTML page that includes Google Maps with your API key
		webEngine.load(getClass().getResource("/map/index.html").toExternalForm());

		/*
		 * Scene scene = new Scene(webView, 800, 600); stage.setScene(scene);
		 * stage.setTitle("Google Maps in JavaFX"); 
		 * stage.show();
		 */

	}

}
