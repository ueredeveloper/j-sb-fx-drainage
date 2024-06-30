package controllers.views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import controllers.DocumentController;
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
import models.Processo;
import services.EnderecoService;
import services.ProcessoService;
import services.ServiceResponse;
import utilities.URLUtility;

public class AddAddressController implements Initializable {

	
	@FXML
	private JFXButton btnClose;

	@FXML
	private JFXComboBox<Endereco> cbAddress;

	@FXML
	private JFXTextField tfNeighborhood;

	@FXML
	private JFXTextField tfZipCode;

	@FXML
	private JFXTextField tfCity;

	@FXML
	private JFXTextField tfArea;

	@FXML
	private JFXComboBox<String> cbState;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tcAddress.setCellValueFactory(new PropertyValueFactory<Endereco, String>("endLogradouro"));
		tcNeighborhood.setCellValueFactory(new PropertyValueFactory<Endereco, String>("endCep"));
		tcCity.setCellValueFactory(new PropertyValueFactory<Endereco, String>("endCidade"));
		tcCity.setCellValueFactory(new PropertyValueFactory<Endereco, String>("endCidade"));
		tcState.setCellValueFactory(new PropertyValueFactory<Endereco, String>("endEstado"));

		tableView.setItems(obsList);

		// Add listener to tableView selection
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Endereco>() {
			@Override
			public void changed(ObservableValue<? extends Endereco> observable, Endereco oldValue, Endereco newValue) {
				if (newValue != null) {
					// Perform actions with the selected Endereco object

					tfNeighborhood.setText(newValue.getEndBairro());
					tfZipCode.setText(newValue.getEndCep());
					tfCity.setText(newValue.getEndCidade());
					// tfArea.setText(newValue.getEndArea());
					cbState.setValue(newValue.getEndEstado());
					cbAddress.setValue(newValue);
					
					// Selecionar o object dentro do combobox
					addressCbController.fillAndSelectComboBox(newValue);
				}
			}
		});

		cbState.getItems().addAll("DF", "SP");

		addressCbController = new AddressComboBoxController(urlService, cbAddress);

		if (object != null) {
			addressCbController.fillAndSelectComboBox(object);
		}

		btnClose.setOnAction(e -> {
			ttClose.play();

			Endereco object = cbAddress.selectionModelProperty().get().isEmpty() ? null
					: addressCbController.getSelectedObject();
			
			if (object != null) {
				this.documentController.fillAndSelectComboBoxAddress(object);
			}

		});

		btnSave.setOnAction(event -> save(event));
		btnUpdate.setOnAction(event -> update(event));
		btnDelete.setOnAction(event -> delete(event));
		btnNew.setOnAction(e -> clearAllComponents());
		btnSearch.setOnAction(event -> handleSearchByKeyword(event));
		

	}

	// Método para buscar processos e preencher o ComboBox
	public List<Processo> fetchProcesses(String keyword) {

		try {
			ProcessoService service = new ProcessoService(urlService);

			List<Processo> list = service.fetchProcesses(keyword);

			return list;

		} catch (Exception e) {

		}
		return null;
	}

	public void fillAndSelectComboBoxAddress(Endereco object) {
		ObservableList<Endereco> newObsList = FXCollections.observableArrayList();
		cbAddress.setItems(newObsList);

		newObsList.add(0, object);

		// Atualizando o ComboBox para refletir a mudança
		// cbProcess.setItems(null);
		cbAddress.setItems(newObsList);

		// Selecionando o novo item no ComboBox
		cbAddress.getSelectionModel().select(0);
	}

	public void save(ActionEvent event) {

		Endereco object = cbAddress.selectionModelProperty().get().isEmpty() ? null
				: addressCbController.getSelectedObject();

		if (object != null) {

			String bairro = tfNeighborhood.getText();
			String cidade = tfCity.getText();
			String cep = tfZipCode.getText();
			String estado = cbState.getValue();

			// Se objeto sem Id, salva, senão edita.
			if (object.getEndId() == null) {

				try {

					// DocumentService documentService = new DocumentService(localUrl);
					EnderecoService enderecoService = new EnderecoService(urlService);

					Endereco endereco = new Endereco(object.getEndLogradouro(), bairro, cidade, cep, estado);
					ServiceResponse<?> service = enderecoService.save(endereco);

					if (service.getResponseCode() == 201) {

						// Mensagem
						Node source = (Node) event.getSource();
						Stage ownerStage = (Stage) source.getScene().getWindow();
						String toastMsg = "Sucesso!";
						utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

						// Adiciona resposta na tabela
						Endereco newEndereco = new Gson().fromJson((String) service.getResponseBody(), Endereco.class);
						// Adiciona com primeiro na lista
						tableView.getItems().add(0, newEndereco);
						// Seleciona o objeto salvo na table view
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

			} else {

			}

			// Alerta de logradouro vazio
		} else {
			// Informa salvamento com sucesso
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "O logradouro não pode estar vazio";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
		}

	}

	public void update(ActionEvent event) {

		Endereco object = cbAddress.selectionModelProperty().get().isEmpty() ? null
				: addressCbController.getSelectedObject();

		if (object.getEndId() != null) {

			Long id = object.getEndId();
			String logradouro = object.getEndLogradouro();
			String bairro = tfNeighborhood.getText();
			String cidade = tfCity.getText();
			String cep = tfZipCode.getText();
			String estado = cbState.getValue();

			try {

				// DocumentService documentService = new DocumentService(localUrl);
				EnderecoService enderecoService = new EnderecoService(urlService);

				Endereco endereco = new Endereco(id, logradouro, bairro, cidade, cep, estado);
				ServiceResponse<?> service = enderecoService.update(endereco);

				if (service.getResponseCode() == 200) {

					// Mensagem
					Node source = (Node) event.getSource();
					Stage ownerStage = (Stage) source.getScene().getWindow();
					String toastMsg = "Sucesso!";
					utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

					// Adiciona resposta na tabela
					Endereco newEndereco = new Gson().fromJson((String) service.getResponseBody(), Endereco.class);
					// Adiciona com primeiro na lista
					tableView.getItems().add(0, newEndereco);
					// Seleciona o objeto salvo na table view
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

		} else {
			// Mensagem
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um endereço!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
		}

	}

	public void handleSearchByKeyword(ActionEvent event) {

		try {

			clearAllComponents();

			EnderecoService service = new EnderecoService(urlService);

			String keyword = tfSearch.getText();

			List<Endereco> enderecos = service.fetchAddressByKeyword(keyword);

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

			ServiceResponse<?> serviceResponse = service.deleteById(selected.getEndId());
			
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
				String toastMsg = "Erro ao deletar documento!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void clearAllComponents() {
		cbAddress.getSelectionModel().clearSelection();
		tfNeighborhood.clear();
		tfCity.clear();
		tfZipCode.clear();
		tfArea.clear();
		cbState.getSelectionModel().clearSelection();
	}

}
