package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import utilities.ResizePaneAnimation;

public class NavBarController implements Initializable {

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

				// Pane pMapAndDocs = mainController.getPane();

				// System.out.println(pMapAndDocs);

				if (mainController.getPane().getParent() instanceof AnchorPane) {

					ResizePaneAnimation resizer = new ResizePaneAnimation();
					resizer.animateResize(mainController.getPane());
				}

			}
		});

	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
		
	}

}
