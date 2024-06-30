package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
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
	private FontAwesomeIconView iconLightDark;

	private boolean isDarkMode = false;

	@FXML
	private MainController mainController;

	@FXML
	private MapController mapController;

	public NavigationController(MainController mainController, MapController mapController) {
		this.mainController = mainController;
		this.mapController = mapController;
	}

	@FXML
	private void toggleMode(MouseEvent event) {

		System.out.println("ICON");
		Node source = (Node) event.getSource();
		Scene scene = source.getScene();

		isDarkMode = !isDarkMode; // Toggle mode

		System.out.println(isDarkMode);

		if (isDarkMode) {
			// mainController.applyDarkMode();
			iconLightDark.setGlyphName("MOON_ALT");
		} else {
			// mainController.applyLightMode();
			iconLightDark.setGlyphName("SUN_ALT");
		}
	}

	/*
	 * private void applyDarkMode(Scene scene) { // Load the root-dark.css file
	 * scene.getStylesheets().clear();
	 * scene.getStylesheets().add(getClass().getResource("/fxml/css/root-dark.css").
	 * toExternalForm());
	 * 
	 * // Change icon to indicate light mode iconLightDark.setGlyphName("MOON_ALT");
	 * }
	 * 
	 * private void applyLightMode(Scene scene) { // Load the root-light.css file
	 * scene.getStylesheets().clear();
	 * scene.getStylesheets().add(getClass().getResource("/fxml/css/root-light.css")
	 * .toExternalForm());
	 * 
	 * // Change icon to indicate dark mode iconLightDark.setGlyphName("SUN_ALT"); }
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Retira o link com a stilização light ou dark, assim fica a estilização do
		// componente pai (MainController)
		apContent.getStylesheets().clear();

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