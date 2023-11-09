package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginController implements Initializable {

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

	FontAwesomeIconView iconGoogle = new FontAwesomeIconView(FontAwesomeIcon.GOOGLE_PLUS);

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		iconGoogle.setSize("2em");
		iconGoogle.setId("icon-google");

		Label lblGoogle = new Label("Google");
		lblGoogle.setId("lbl-google");
		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(iconGoogle, lblGoogle);
		hbox.setAlignment(Pos.CENTER);

		btnSignUpGoogle.setGraphic(hbox);

		btnSignUp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					Node node = (Node) event.getSource();
					Stage stage = (Stage) node.getScene().getWindow();

					// stage.close();
					Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Main.fxml")));

					// Redimensionamento da do stage de acordo com as dimensões do monitor
					Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

					// Tela cheia
					stage.setX(primaryScreenBounds.getMinX());
					stage.setY(primaryScreenBounds.getMinY());
					stage.setWidth(primaryScreenBounds.getWidth());
					stage.setHeight(primaryScreenBounds.getHeight());
					// Minímo de tamanho e largura da tela principal
					stage.setMinHeight(668);
					stage.setMinWidth(1266);
					// stage.setHeight(668);
					// stage.setWidth(1266);

					// título
					stage.setTitle("SAD/DF - Geo - Cadastro");

					stage.setScene(scene);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
	}

}
