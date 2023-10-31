package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	Button btnSiginUp;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnSiginUp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					Node node = (Node) event.getSource();
					Stage stage = (Stage) node.getScene().getWindow();

					//stage.close();
					Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Main.fxml")));
					stage.setScene(scene);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
	}

}
