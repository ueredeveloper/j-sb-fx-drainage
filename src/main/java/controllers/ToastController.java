package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import enums.ToastType;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ToastController implements Initializable {
	
	
	@FXML
	private StackPane spContainer;

	@FXML
	private Pane pToast;

	@FXML
	private Text textToast;

	@FXML
	private FontAwesomeIconView iconView;

	public void showToast(String toastMsg, ToastType toastType) {
		textToast.setText(toastMsg);
		pToast.getStyleClass().clear();
		pToast.getStyleClass().add("pane");
		pToast.getStyleClass().add(toastType.getStyleClass());
		iconView.setIcon(toastType.getIcon());

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		TranslateTransition tt = new TranslateTransition(new Duration(400.0), spContainer);
		System.out.println(spContainer.getTranslateY());
		tt.setToY(40.0);
		tt.play();

	}
}