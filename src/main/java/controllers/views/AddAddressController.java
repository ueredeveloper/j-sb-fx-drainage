package controllers.views;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import controllers.DocumentController;
import enums.StaticData;
import enums.ToastType;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Endereco;
import models.Estado;
import services.EnderecoService;
import services.ServiceResponse;
import utilities.URLUtility;

public class AddAddressController implements Initializable {

	@FXML
	private JFXButton btnClose;

	@FXML
	private JFXTextField tfAddress;

	@FXML
	private JFXTextField tfNeighborhood;

	@FXML
	private JFXTextField tfZipCode;

	@FXML
	private JFXTextField tfCity;

	@FXML
	private JFXTextField tfArea;

	@FXML
	private JFXComboBox<Estado> cbState;

	@FXML
	private TableView<Endereco> tableView;

	@FXML
	private TableColumn<Endereco, String> tcAddress;

	@FXML
	private TableColumn<Endereco, String> tcNeighborhood;

	@FXML
	private TableColumn<Endereco, String> tcCity;

	@FXML
	private TableColumn<Endereco, String> tcState;

	@FXML
	private JFXTextField tfSearch;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private JFXButton btnNew;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnUpdate;

	@FXML
	private JFXButton btnDelete;

	private String urlService;
	private TranslateTransition ttClose;

	Endereco object;

	ProcessComboBoxController processCbController;
	UserComboBoxController userComboBoxController;
	AddressComboBoxController addressCbController;
	DocumentController documentController;

	ObservableList<Endereco> obsList = FXCollections.observableArrayList();

	public AddAddressController(DocumentController documentController, Endereco object, String urlService,
			TranslateTransition ttClose) {
		this.documentController = documentController;
		this.object = object;
		this.urlService = URLUtility.getURLService();
		this.ttClose = ttClose;

	}

	ObservableList<Estado> obsListState;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		obsListState = StaticData.INSTANCE.getStates();
		cbState.setItems(obsListState);

		tcAddress.setCellValueFactory(new PropertyValueFactory<Endereco, String>("logradouro"));
		tcNeighborhood.setCellValueFactory(new PropertyValueFactory<Endereco, String>("bairro"));
		tcCity.setCellValueFactory(new PropertyValueFactory<Endereco, String>("cidade"));
		tcState.setCellValueFactory(new PropertyValueFactory<Endereco, String>("estado"));

		tableView.setItems(obsList);

		// Add listener to tableView selection
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Endereco>() {
			@Override
			public void changed(ObservableValue<? extends Endereco> observable, Endereco oldValue, Endereco newValue) {
				if (newValue != null) {
					// Perform actions with the selected Endereco object

					tfAddress.setText(newValue.getLogradouro());
					tfNeighborhood.setText(newValue.getBairro());
					tfZipCode.setText(newValue.getCep());
					tfCity.setText(newValue.getCidade());
					// tfArea.setText(newValue.getEndArea());
					Estado selectedType = newValue.getEstado();
					cbState.getSelectionModel().select(selectedType);

				}
			}
		});

		btnClose.setOnAction(e -> {
			ttClose.play();

			Endereco seletedObject = tableView.getSelectionModel().getSelectedItem();
			this.documentController.fillAndSelectComboBoxAddress(seletedObject);

		});

		btnSave.setOnAction(event -> save(event));
		btnUpdate.setOnAction(event -> update(event));
		btnDelete.setOnAction(event -> delete(event));
		btnNew.setOnAction(e -> clearAllComponents());
		btnSearch.setOnAction(event -> searchByKeyword(event));

		if (object != null) {
			obsList.clear();
			obsList.add(object);
			tableView.getSelectionModel().select(object);
		} else {
			clearAllComponents();
		}

	}

	public void save(ActionEvent event) {

		Long id = object != null && object.getId() != null ? object.getId() : null;

		String logradouro = tfAddress.getText();
		String bairro = tfNeighborhood.getText();
		String cidade = tfCity.getText();
		String cep = tfZipCode.getText();
		Estado estado = cbState.getValue();

		// Se o logradouro não estiver preenchido não salvar
		if (logradouro == null || logradouro.isEmpty()) {

			// Informa salvamento com sucesso
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "O Logradouro deve estar preenchido !!!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

		} else {

			try {

				// DocumentService documentService = new DocumentService(localUrl);
				EnderecoService enderecoService = new EnderecoService(urlService);

				Endereco endereco = new Endereco(id, logradouro, bairro, cidade, cep, estado);
				ServiceResponse<?> service = enderecoService.save(endereco);

				if (service.getResponseCode() == 201) {

					// Mensagem
					Node source = (Node) event.getSource();
					Stage ownerStage = (Stage) source.getScene().getWindow();
					String toastMsg = "Sucesso!";
					utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

					// Adiciona resposta na tabela
					Endereco newEndereco = new Gson().fromJson((String) service.getResponseBody(), Endereco.class);

					// Remove o objeto com solicitado de salvamento ou id nulo, se estiver presente
					// na tableView
					tableView.getItems()
							.removeIf(end -> end.getId() == null || end.getId().equals(newEndereco.getId()));

					// Adiciona com primeiro na lista
					tableView.getItems().add(0, newEndereco);
					// Seleciona o objeto salvo na table view
					tableView.getSelectionModel().select(newEndereco);
					// Atualiza objeto vindo do DocumentController
					object = newEndereco;

				} else {
					Node source = (Node) event.getSource();
					Stage ownerStage = (Stage) source.getScene().getWindow();
					String toastMsg = "Erro! + " + service.getResponseCode();
					utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

				}

			} catch (Exception e) {
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Erro! + " + e.getMessage();
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
				e.printStackTrace();
			}
		}

	}

	public void update(ActionEvent event) {

		/*
		 * Endereco object = cbAddress.selectionModelProperty().get().isEmpty() ? null :
		 * addressCbController.getSelectedObject();
		 */

		Endereco seletedObject = tableView.getSelectionModel().getSelectedItem();

		// Long id = seletedObject.getId();
		String logradouro = tfAddress.getText();
		String bairro = tfNeighborhood.getText();
		String cidade = tfCity.getText();
		String cep = tfZipCode.getText();
		Estado estado = cbState.getValue();

		try {

			// DocumentService documentService = new DocumentService(localUrl);
			EnderecoService enderecoService = new EnderecoService(urlService);

			// Endereco endereco = new Endereco(id, logradouro, bairro, cidade, cep,
			// estado);

			Endereco endereco = new Endereco();

			endereco.setId(seletedObject.getId());
			endereco.setLogradouro(logradouro);
			endereco.setBairro(bairro);
			endereco.setCidade(cidade);
			endereco.setCep(cep);
			endereco.setEstado(estado);

			ServiceResponse<?> service = enderecoService.update(endereco);

			if (service.getResponseCode() == 200) {

				// Mensagem
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

				// Adiciona resposta na tabela
				Endereco newEndereco = new Gson().fromJson((String) service.getResponseBody(), Endereco.class);

				// Remove objeto solicitado da tableView
				tableView.getItems().remove(seletedObject);
				// Adiciona objeto já editado como primeiro da lista.
				tableView.getItems().add(0, newEndereco);
				// Seleciona o objeto na table view
				tableView.getSelectionModel().select(newEndereco);

			} else {
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Erro!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			}

		} catch (Exception e) {
			// adicionar Toast de erro
			e.printStackTrace();
		}

	}

	public void searchByKeyword(ActionEvent event) {

		try {

			clearAllComponents();

			EnderecoService service = new EnderecoService(urlService);

			String keyword = tfSearch.getText();

			Set<Endereco> enderecos = service.fetchAddressByKeyword(keyword);

			// Create a list of Document objects
			obsList.clear();
			obsList.addAll(enderecos);
			// cbDocType.setValue(obsDocumentTypes.get(0));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(ActionEvent event) {
		Endereco selected = tableView.getSelectionModel().getSelectedItem();

		try {
			EnderecoService service = new EnderecoService(urlService);

			ServiceResponse<?> serviceResponse = service.deleteById(selected.getId());

			if (serviceResponse.getResponseCode() == 200) {

				// Informa sucesso em deletar
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Documento deletado com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

				// retira objecto da tabela de documentos tvDocs
				tableView.getItems().remove(selected);

			} else {
				// Informa erro em deletar
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Erro ao deletar! " + serviceResponse.getResponseBody();
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Erro ao deletar! " + e.getMessage();
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
		}

	}

	public void clearAllComponents() {

		object = null;

		tfAddress.clear();
		tfNeighborhood.clear();
		tfCity.clear();
		tfCity.setText("Brasília");
		tfZipCode.clear();
		tfArea.clear();
		cbState.getSelectionModel().clearSelection();
		Optional<Estado> estadoDF = obsListState.stream().filter(estado -> "DF".equals(estado.getDescricao()))
				.findFirst();

		cbState.getSelectionModel().select(estadoDF.orElse(null));
	}

}
