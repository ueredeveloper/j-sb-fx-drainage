package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import utilities.ResizeMap;

public class NavigationController implements Initializable {

	@FXML
	private AnchorPane apContent;

	@FXML
	private HBox hbNavigation;

	@FXML
	private JFXButton btnRegistration;

	@FXML
	private JFXButton btnModels;

	@FXML
	private JFXButton btnMap;

	@FXML
	private MainController mainController;
	
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnRegistration.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AnchorPane apc = (AnchorPane) mainController.getAnchorPaneContent();
				AnchorPane apMap = (AnchorPane) mainController.getAnchorPaneMap();
				AnchorPane apManager = (AnchorPane) mainController.getAnchorPaneManager();

				ResizeMap rm = new ResizeMap(apc, apMap, apManager);
				rm.resetMapSize();
				
				loadDocuments();

			}
		});

		btnMap.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AnchorPane apc = (AnchorPane) mainController.getAnchorPaneContent();
				AnchorPane apMap = (AnchorPane) mainController.getAnchorPaneMap();
				AnchorPane apManager = (AnchorPane) mainController.getAnchorPaneManager();

				ResizeMap rm = new ResizeMap(apc, apMap, apManager);
				rm.resizeMapToFullWidth();
			}
		});

	}

	

	public void loadDocuments() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Documents.fxml"));
			AnchorPane apManager = (AnchorPane) mainController.getAnchorPaneManager();
			apManager.getChildren().setAll((AnchorPane) loader.load());

			DocumentController docCont = loader.getController();
			docCont.setMainController(this.mainController);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
