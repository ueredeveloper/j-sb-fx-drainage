package controllers.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import controllers.DocumentController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

public class EditAddressController implements Initializable {
	
    @FXML
    private AnchorPane apContainer;

    @FXML
    private JFXTextField tfAddress;

    @FXML
    private JFXTextField tfCity;

    @FXML
    private JFXTextField tfZipCode;

    @FXML
    private WebView wvArea;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnClose;
    
    private DocumentController documentController;

	public EditAddressController(DocumentController documentController) {
		this.documentController = documentController;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Retira o link com a stilização light ou dark, assim fica a estilização do componente pai (MainController)
		apContainer.getStylesheets().clear();
		// Retira o link com a stilização light ou dark, assim fica a estilização do componente pai (MainController)
		apContainer.getStylesheets().clear();
		btnClose.setOnAction(e-> {this.documentController.closeEditAddress();});
		
	}

}
