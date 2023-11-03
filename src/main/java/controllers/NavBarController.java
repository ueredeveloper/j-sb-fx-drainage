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
				if (mainController.getAnchorPane().getParent() instanceof AnchorPane) {

					ResizePaneAnimation resizer = new ResizePaneAnimation();
					resizer.animateResize(mainController.getAnchorPane());
				}

				loadListDocuments();

			}
		});

	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;

	}

	private void loadListDocuments() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ListDocuments.fxml"));

			AnchorPane apListDocuments = loader.load();

			mainController.getAnchorPane().getChildren().add(apListDocuments);
			mainController.setRightAnchor(apListDocuments, -700.0);
			//AnchorPane.setRightAnchor(apListDocuments, 0.0);

			// pDocuments.setLayoutX(mainController.getPane().getWidth() -
			// pDocuments.getPrefWidth());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
