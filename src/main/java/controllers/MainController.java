package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

@SuppressWarnings("restriction")
public class MainController implements Initializable {
	
	

	@FXML
	private AnchorPane apMain;

	@FXML
	private AnchorPane apTop;

	@FXML
	private AnchorPane apContent;

	@FXML
	private AnchorPane apManager;

	public AnchorPane getAnchorPaneContent() {
		return this.apContent;
	}

	public Pane getAnchorPaneManager() {
		return this.apManager;
	}

	public MapController mapController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loadMapView();

		addApContentWidthListener();

		loadNavigationBar();

	}

	private DocumentController docController;
	private AnchorPane apDocument; // Store the AnchorPane of Documents.fxml

	private void loadNavigationBar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Navigation.fxml"));
            // Setting the custom controller factory for NavigationController
            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == NavigationController.class) {
                    return new NavigationController(this, mapController);
                } else {
                    try {
                        return controllerClass.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            AnchorPane apNavBar = loader.load();
            apTop.getChildren().add(apNavBar);
            AnchorPane.setRightAnchor(apNavBar, 0.0);

            if (apDocument == null) {
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/Documents.fxml"));
                apDocument = loader2.load();
                docController = loader2.getController();
                docController.setMainController(this);

                apManager.getChildren().add(apDocument);
                AnchorPane.setLeftAnchor(apDocument, 0.0);
                AnchorPane.setRightAnchor(apDocument, 0.0);
                
            } else {
                apManager.setVisible(!apDocument.isVisible());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private void loadMapView() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Map.fxml"));
			fxmlLoader.setControllerFactory(controllerClass -> {
				if (controllerClass == MapController.class) {
					mapController = new MapController(apContent);
					return mapController;
				} else {
					try {
						return controllerClass.getDeclaredConstructor().newInstance();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});
			Parent root = fxmlLoader.load();
			apContent.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	 private void addApContentWidthListener () {
	        apContent.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
	            double newWidth = newValue.doubleValue();
	            // A tela é dividida em três partes, uma para o mapa e as outras duas para o cadastro. O cadastro deve
	            //ser então newWidth/3 mais newWidth/3, com um pequeno ajuste, newWidth/2.75 em um dos cálculos.
	            apManager.setPrefWidth(newWidth*2/3);
	        });
	    }
}
