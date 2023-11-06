package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private AnchorPane apTop;

    @FXML
    private AnchorPane apContent;

    @FXML
    private AnchorPane apMap;

    @FXML
    private AnchorPane apManager;
    
    @FXML
    private WebView wvMap;
    
	public AnchorPane getAnchorPaneContent () {
		return this.apContent;
	}
	public Pane getAnchorPaneManager () {
		return this.apManager;
	}
	public Pane getAnchorPaneMap () {
		return this.apMap;
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		WebEngine webEngine = wvMap.getEngine();
		webEngine.load(getClass().getResource("/map/index.html").toExternalForm());

		// Add a listener to the width property of the AnchorPane
        apContent.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double newWidth = newValue.doubleValue();
                apMap.setPrefWidth(newWidth / 2);
                apManager.setPrefWidth(newWidth / 2);
            }
        });

		// Torna as dimensões do WebView (wvMap) semelhantes ao do pai (aapMap)
		apMap.widthProperty().addListener((observable, oldValue, newValue) -> {
			// setar prefwidth no mapa
			wvMap.setPrefWidth(newValue.doubleValue());
		});

		apMap.heightProperty().addListener((observable, oldValue, newValue) -> {
			wvMap.setPrefHeight(newValue.doubleValue());
		});
		
		loadNavigationBar();
		

	}

	private void loadNavigationBar () {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Navigation.fxml"));
			AnchorPane apNavBar = loader.load();
		
			// acesso ao MainController dentro do NavBarController
			NavigationController navBarController = loader.getController();
			navBarController.setMainController(this);
			

			apTop.getChildren().add(apNavBar);
			// seta apNavBar no lado direito da tela
			AnchorPane.setRightAnchor(apNavBar, 0.0);
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
