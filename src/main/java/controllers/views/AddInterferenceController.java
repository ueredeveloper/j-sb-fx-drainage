package controllers.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import models.Endereco;
import models.Interferencia;
import utilities.URLUtility;

public class AddInterferenceController implements Initializable {

	@FXML
	private AnchorPane apContainer;

	@FXML
	private JFXComboBox<?> cbInterferenceType;

	@FXML
	private JFXComboBox<?> cbGrant;

	@FXML
	private JFXComboBox<?> cbGrantSubtype;

	@FXML
	private JFXComboBox<?> cbSituation;

	@FXML
	private JFXComboBox<?> cbActType;

	@FXML
	private JFXComboBox<?> cbWatershedBasin;

	@FXML
	private JFXComboBox<?> cbHydrographicUnit;

	@FXML
	private AnchorPane apInterferenceType;
	
	@FXML
	private JFXButton btnClose;


	JFXTextField tfLatitude;
	JFXTextField tfLongitude;

	String urlService;
	TranslateTransition ttClose;
	Endereco endereco = new Endereco();
	JFXComboBox<Interferencia> cbAddress;

	public AddInterferenceController(String urlService, TranslateTransition ttClose, JFXTextField tfLatitude,
			JFXTextField tfLongitude) {
		this.urlService = URLUtility.getURLService();
		this.ttClose = ttClose;
		this.tfLatitude = tfLatitude;
		this.tfLongitude = tfLongitude;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Retira o link com a stilização light ou dark, assim fica a estilização do
		// componente pai (MainController)
		apContainer.getStylesheets().clear();
		
		// Fechar tela
		btnClose.setOnAction(e -> {
			ttClose.play();

			//updateComboBox(selectedEndereco);

		});

	}

	public void handleSave(ActionEvent event) {

		// String endLogradouro = tfStreet.getText();
		// String endBairro = tfNeighborhood.getText();
		// String endCidade = tfCity.getText();
		// String endCep = tfPostalCode.getText();

		// endereco.setEndLogradouro(endLogradouro);
		// endereco.setEndCidade(endCidade);
		// endereco.setEndCep(endCep);
		// endereco.setEndBairro(endBairro);

		// try {
		// EnderecoService endServ = new EnderecoService(urlService);
		//
		// // Requisi��o de resposta de edi��o
		// ServiceResponse<?> serviceResponse = endServ.save(endereco);
		//
		// if (serviceResponse.getResponseCode() == 200 ||
		// serviceResponse.getResponseCode() == 201) {
		// // Alerta (Toast) de sucesso na edi��o
		// Node source = (Node) event.getSource();
		//
		// Stage ownerStage = (Stage) source.getScene().getWindow();
		// String toastMsg = "Endereço salvo com sucesso!";
		// utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);
		//
		// // Atualiza com endereço salvo no banco de dados, trazindo assim o Id também.
		// Endereco response = new Gson().fromJson((String)
		// serviceResponse.getResponseBody(), Endereco.class);
		//
		// endereco = response;
		//
		// } else {
		// // Display an error toast or alert
		// // System.out.println(serviceResponse.getResponseCode());
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

	public void updateComboBox(Endereco selectedEndereco) {

		// if (selectedEndereco == null) {

		// System.out.println("if null");
		//
		// selectedEndereco = new Endereco();
		//
		// selectedEndereco.setEndLogradouro(tfStreet.getText());
		// selectedEndereco.setEndCidade(tfCity.getText());
		// selectedEndereco.setEndCep(tfPostalCode.getText());
		// selectedEndereco.setEndBairro(tfNeighborhood.getText());
		//
		// ObservableList<Endereco> newObs = FXCollections.observableArrayList();
		// cbAddress.setItems(newObs);
		//
		// // Adicionando o novo Endereco como primeiro da lista
		// // newObs.add(new Endereco());
		// cbAddress.setItems(newObs);
		//
		// newObs.add(0, selectedEndereco);
		//
		// // Atualizando o ComboBox para refletir a mudança
		// // cbAddress.setItems(null);
		// cbAddress.setItems(newObs);
		//
		// // Selecionando o novo item no ComboBox
		// cbAddress.getSelectionModel().select(0);
		// }
	}

}
