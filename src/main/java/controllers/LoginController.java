package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import utilities.VersionUtil;

/**
 * Controlador para a interface de login.
 */
public class LoginController implements Initializable {
	
	
	
	// Injetando elementos do arquivo FXML
	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Label createAccountLabel;

	@FXML
	private Pane greenPane;

	@FXML
	private Label loginLabel;

	@FXML
	private Label emailLabel;

	@FXML
	private JFXTextField tfSignInEmail;

	@FXML
	private JFXButton btnSignUp2;

	@FXML
	private Label passwordLabel;

	@FXML
	private JFXTextField tfSignInPassword;

	@FXML
	private Label emailLabel2;

	@FXML
	private JFXTextField tfSignUpEmail;

	@FXML
	private JFXButton btnSignUp;

	@FXML
	private Label passwordLabel2;

	@FXML
	private JFXTextField tfSiginUpPassWord;

	@FXML
	private JFXButton btnSignUpGoogle;

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		// Configurando a ação do botão de SignUp
		btnSignUp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					Node node = (Node) event.getSource();
					Stage stage = (Stage) node.getScene().getWindow();

					// Carregando a cena principal
					Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Main.fxml")));

					// Redimensionando o stage de acordo com as dimensões do monitor
					Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

					// Configurando o stage para tela cheia
					stage.setX(primaryScreenBounds.getMinX());
					stage.setY(primaryScreenBounds.getMinY());
					stage.setWidth(primaryScreenBounds.getWidth());
					stage.setHeight(primaryScreenBounds.getHeight());

					
					// Configurando o tamanho mínimo do stage
					//stage.setHeight(1080);
					//stage.setWidth(1920);
					
					String version = VersionUtil.getVersion();
					 
					// Configurando o título da janela
					stage.setTitle("REGG+ - Registro e Criação de Documentos | Versão: " + version);

					// Definindo a cena no stage
					stage.setScene(scene);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}