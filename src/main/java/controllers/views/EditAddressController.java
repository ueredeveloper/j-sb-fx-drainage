package controllers.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import controllers.DocumentController;
import enums.ToastType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.Documento;
import models.Endereco;
import services.EnderecoService;
import services.ServiceResponse;
import utilities.URLUtility;

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

	String urlService;
	Endereco endereco;

	public EditAddressController(DocumentController documentController) {
		this.documentController = documentController;
		this.urlService = URLUtility.getURLService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Retira o link com a stilização light ou dark, assim fica a estilização do
		// componente pai (MainController)
		apContainer.getStylesheets().clear();
		// Retira o link com a stilização light ou dark, assim fica a estilização do
		// componente pai (MainController)
		apContainer.getStylesheets().clear();
		
		
		this.endereco = this.documentController.getDocAddress();
		
		tfAddress.setText(endereco.getEndLogradouro());
		tfCity.setText(endereco.getEndCidade());
		tfZipCode.setText(endereco.getEndCEP());
		
		
		btnClose.setOnAction(e -> {
			this.documentController.closeEditAddress();
		});

		btnEdit.setOnAction(event -> {
			handleEdit(event);
		});

	}

	public void handleEdit(ActionEvent event) {


		String endLogradouro = tfAddress.getText();
		String endCidade = tfCity.getText();
		String endCep = tfZipCode.getText();

		endereco.setEndLogradouro(endLogradouro);
		endereco.setEndCidade(endCidade);
		endereco.setEndCEP(endCep);

		try {
			EnderecoService endServ = new EnderecoService(urlService);

			// Requisi��o de resposta de edi��o
			ServiceResponse<?> serviceResponse = endServ.update(endereco);
			if (serviceResponse.getResponseCode() == 200) {
				// Alerta (Toast) de sucesso na edi��o
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Endereço editado com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);
				
				// é preciso voltar com o endereço editado e atualizar a lista de documentos

				/*
				 * tvDocs.getItems().remove(selectedDoc); // Converte objeto editado para Json
				 * Documento responseDocumento = new Gson().fromJson((String)
				 * serviceResponse.getResponseBody(), Documento.class); // Adiciona objeto
				 * editado como primeiro �tem ma fila na table view tvDocs.getItems().add(0,
				 * responseDocumento); // Seleciona o objeto editado na table view
				 * tvDocs.getSelectionModel().select(responseDocumento);
				 */

			} else {
				// Display an error toast or alert
				// System.out.println(serviceResponse.getResponseCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
