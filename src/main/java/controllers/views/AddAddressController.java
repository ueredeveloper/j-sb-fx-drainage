package controllers.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import controllers.DocumentController;
import enums.ToastType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Endereco;
import services.EnderecoService;
import services.ServiceResponse;
import utilities.URLUtility;

public class AddAddressController implements Initializable {

	@FXML
	private AnchorPane apContainer;

	@FXML
	private JFXButton btnAdd;

	@FXML
	private JFXButton btnClose;

	@FXML
	private JFXTextField tfStreet;

	@FXML
	private JFXTextField tfNeighborhood;

	@FXML
	private JFXTextField tfCity;

	@FXML
	private JFXTextField tfPostalCode;

	@FXML
	private JFXComboBox<?> cbState;

	@FXML
	private JFXComboBox<?> cbAdministrativeRegion;

	@FXML
	private JFXTextField tfArea;

	private DocumentController documentController;

	String urlService;

	public AddAddressController(DocumentController documentController) {
		this.documentController = documentController;
		this.urlService = URLUtility.getURLService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Retira o link com a stilização light ou dark, assim fica a estilização do
		// componente pai (MainController)
		apContainer.getStylesheets().clear();

		btnClose.setOnAction(e -> {
			this.documentController.closeAddAddress();
		});

		btnAdd.setOnAction(event -> {
			handleAdd(event);
		});

	}

	public void handleAdd(ActionEvent event) {
		
		Endereco endereco = new Endereco();

		String endLogradouro = tfStreet.getText();
		String endBairro = tfNeighborhood.getText();
		String endCidade = tfCity.getText();
		String endCep = tfPostalCode.getText();

		endereco.setEndLogradouro(endLogradouro);
		endereco.setEndCidade(endCidade);
		endereco.setEndCep(endCep);
		endereco.setEndBairro(endBairro);

		try {
			EnderecoService endServ = new EnderecoService(urlService);

			// Requisi��o de resposta de edi��o
			ServiceResponse<?> serviceResponse = endServ.save(endereco);
			if (serviceResponse.getResponseCode() == 200) {
				// Alerta (Toast) de sucesso na edi��o
				Node source = (Node) event.getSource();
				
				System.out.println(source);
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
