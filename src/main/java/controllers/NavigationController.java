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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import utilities.ResizeMap;
import utilities.SVGIconLoader;

public class NavigationController implements Initializable {

	@FXML
	private AnchorPane apContent;

    @FXML
    private StackPane stackLogo;

	@FXML
	private HBox hbNavigation;

    @FXML
    private Hyperlink hlRegistry;

    @FXML
    private Hyperlink hlMap;

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

		isDarkMode = !isDarkMode; // Toggle mode


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

		SVGPath logo = SVGIconLoader.getNavbarLogo(12);
		
		stackLogo.getChildren().add(logo);

        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/glacial-indifference.bold.otf"), 30);

        Label logoLabel = new Label("R E G G +");
        
        
        if (customFont != null) {
        	logoLabel.setFont(customFont);
        } else {
            System.out.println("Font not loaded!");
        }
	
        logoLabel.setStyle("-fx-font-size: 40px;-fx-text-fill: primary-color; -fx-padding: 40px 0 10px 0;");

		stackLogo.getChildren().add(logoLabel);

		// Retira o link com a stilização light ou dark, assim fica a estilização do
		// componente pai (MainController)
		apContent.getStylesheets().clear();

		hlRegistry.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				AnchorPane apMainContent = (AnchorPane) mainController.getAnchorPaneContent();
				AnchorPane apMapContent = (AnchorPane) mapController.getAnchorPaneMap();
				AnchorPane apManagerContent = (AnchorPane) mainController.getAnchorPaneManager();

				ResizeMap rm = new ResizeMap(apMainContent, apMapContent, apManagerContent);
				rm.resetMapSize();
				
				

				mapController.hideCoordConversor();


			}
		});

		hlMap.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AnchorPane apMainContent = (AnchorPane) mainController.getAnchorPaneContent();
				AnchorPane apMapContent = (AnchorPane) mapController.getAnchorPaneMap();
				AnchorPane apManagerContent = (AnchorPane) mainController.getAnchorPaneManager();

				ResizeMap rm = new ResizeMap(apMainContent, apMapContent, apManagerContent);
				rm.resizeMapToFullWidth();

				mapController.showCoordConversor();
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