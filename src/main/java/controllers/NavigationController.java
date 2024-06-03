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
	@FXML
	private MapController mapController;

	public NavigationController(MainController mainController, MapController mapController) {
		this.mainController = mainController;
		this.mapController = mapController;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnRegistration.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				AnchorPane apMainContent = (AnchorPane) mainController.getAnchorPaneContent();
				AnchorPane apMapContent = (AnchorPane) mapController.getAnchorPaneMap();
				AnchorPane apManagerContent = (AnchorPane) mainController.getAnchorPaneManager();

				ResizeMap rm = new ResizeMap(apMainContent, apMapContent, apManagerContent);
				rm.resetMapSize();

			}
		});

		btnMap.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AnchorPane apMainContent = (AnchorPane) mainController.getAnchorPaneContent();
				AnchorPane apMapContent = (AnchorPane) mapController.getAnchorPaneMap();
				AnchorPane apManagerContent = (AnchorPane) mainController.getAnchorPaneManager();

				ResizeMap rm = new ResizeMap(apMainContent, apMapContent, apManagerContent);
				rm.resizeMapToFullWidth();
			}
		});

	}

	public void loadDocuments() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Documents.fxml"));
			AnchorPane apManagerContent = (AnchorPane) mainController.getAnchorPaneManager();
			apManagerContent.getChildren().setAll((AnchorPane) loader.load());

			DocumentController docController = loader.getController();
			docController.setMainController(this.mainController);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
