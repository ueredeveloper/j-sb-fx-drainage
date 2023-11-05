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
import javafx.scene.layout.Pane;
import utilities.ResizePaneAnimation;

public class NavBarController implements Initializable {

	@FXML
	private AnchorPane apNavBar;

	@FXML
	private HBox hbNavBar;

	@FXML
	private JFXButton btnCadastro;

	@FXML
	private JFXButton btnModelos;

	@FXML
	private JFXButton btnMapa;

	@FXML
	private MainController mainController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("nav bar controller ");

		btnCadastro.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {


				// Redimensiona o mapa através do acMap no MainController (Pai)
				if (mainController.getAnchorPaneContent().getParent() instanceof AnchorPane) {

					double width = mainController.getMapWidth();
					Pane paneToResize = mainController.getMapPane();
                    ResizePaneAnimation resizer = new ResizePaneAnimation(width);
                    resizer.animateResize(paneToResize);
				}

				loadPaneManager();

			}
		});

	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;

	}

	private void loadPaneManager () {
		System.out.println("load pane manager ");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Documents.fxml"));

			Pane paneManager = mainController.getPaneManager();
			
			Pane paneDocuments = new Pane();
			
			paneDocuments = loader.load();
			
			//apDocuments.setPrefWidth(mainController.getAnchorPaneMain().getPrefWidth()/2);
			//paneDocuments.setId("pd");
		paneManager.getChildren().add(paneDocuments);
			// setar apDocuments à direita
			//mainController.setAnchorPositions(apDocuments, 0.0);
			//AnchorPane.setRightAnchor(apDocuments, 0.0);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
