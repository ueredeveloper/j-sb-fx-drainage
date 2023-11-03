package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import utilities.ResizePaneAnimation;

public class MainController implements Initializable {

	@FXML
	AnchorPane apMain;
	@FXML
	Pane pMapAndDocs;

	@FXML
	private WebView wvMap;

	@FXML
	private JFXButton btnOpenListDocs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		WebEngine webEngine = wvMap.getEngine();
		webEngine.load(getClass().getResource("/map/index.html").toExternalForm());

		// Torna as dimensões do WebView (wvMap) semelhantes ao do pai (pMapAndDocs)

		ChangeListener<Number> widthListener = (observable, oldValue, newValue) -> {
			wvMap.setPrefWidth(newValue.doubleValue());
		};

		ChangeListener<Number> heightListener = (observable, oldValue, newValue) -> {
			wvMap.setPrefHeight(newValue.doubleValue());
		};
		/*
		 * pMapAndDocs.widthProperty().addListener((observable, oldValue, newValue) -> {
		 * wvMap.setPrefWidth(newValue.doubleValue()); });
		 * 
		 * pMapAndDocs.heightProperty().addListener((observable, oldValue, newValue) ->
		 * { wvMap.setPrefHeight(newValue.doubleValue()); });
		 */

		pMapAndDocs.widthProperty().addListener(widthListener);
		pMapAndDocs.heightProperty().addListener(heightListener);

		btnOpenListDocs.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("remove listener and setPrefwi");
				/*
				 * Pane pDocs = new Pane(); pDocs.setPrefSize(500.0, 500.0);
				 * pDocs.setPrefSize(500, 500); pDocs.setStyle("-fx-background-color: blue;");
				 * 
				 * apMain.getChildren().add(pDocs);
				 */
				
				if (pMapAndDocs.getParent() instanceof AnchorPane) {

					ResizePaneAnimation resizer = new ResizePaneAnimation();
					resizer.animateResize(pMapAndDocs);
				}

			}
		});

	}


}
