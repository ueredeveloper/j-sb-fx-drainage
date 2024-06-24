package controllers.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import enums.ToastType;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

	String urlService;
	TranslateTransition ttClose;
	Endereco endereco = new Endereco();
	JFXComboBox<Endereco> cbAddress;

	public AddAddressController(String urlServie, TranslateTransition ttClose, JFXComboBox<Endereco> cbAddress) {
		this.urlService = URLUtility.getURLService();
		this.ttClose = ttClose;
		this.cbAddress = cbAddress;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Retira o link com a stilização light ou dark, assim fica a estilização do
		// componente pai (MainController)
		apContainer.getStylesheets().clear();
		
		String userInput = cbAddress.getEditor().getText();
		

		//Endereco selectedEndereco = cbAddress.getSelectionModel().getSelectedItem();
		Endereco selectedEndereco = new Endereco(userInput);

		if (selectedEndereco != null) {
			tfStreet.setText(selectedEndereco.getEndLogradouro());
		}

		// Fechar tela
		btnClose.setOnAction(e -> {
			ttClose.play();

			updateComboBox(selectedEndereco);

		});

		btnAdd.setOnAction(event -> {
			handleSave(event);

		});

	}

	public void handleSave(ActionEvent event) {

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

			if (serviceResponse.getResponseCode() == 200 || serviceResponse.getResponseCode() == 201) {
				// Alerta (Toast) de sucesso na edi��o
				Node source = (Node) event.getSource();

				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Endereço salvo com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

				// Atualiza com endereço salvo no banco de dados, trazindo assim o Id também.
				Endereco response = new Gson().fromJson((String) serviceResponse.getResponseBody(), Endereco.class);

				endereco = response;

			} else {
				// Display an error toast or alert
				// System.out.println(serviceResponse.getResponseCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateComboBox(Endereco selectedEndereco) {

		//if (selectedEndereco == null) {

			System.out.println("if  null");

			selectedEndereco = new Endereco();

			selectedEndereco.setEndLogradouro(tfStreet.getText());
			selectedEndereco.setEndCidade(tfCity.getText());
			selectedEndereco.setEndCep(tfPostalCode.getText());
			selectedEndereco.setEndBairro(tfNeighborhood.getText());

			ObservableList<Endereco> newObs = FXCollections.observableArrayList();
			cbAddress.setItems(newObs);

			// Adicionando o novo Endereco como primeiro da lista
			// newObs.add(new Endereco());
			cbAddress.setItems(newObs);

			newObs.add(0, selectedEndereco);

			// Atualizando o ComboBox para refletir a mudança
			// cbAddress.setItems(null);
			cbAddress.setItems(newObs);

			// Selecionando o novo item no ComboBox
			cbAddress.getSelectionModel().select(0);
		//} 
	}

}
