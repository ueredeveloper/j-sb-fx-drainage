package controllers.views;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.Set;

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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.ApiResponse;
import models.Documento;
import models.Processo;
import models.Usuario;
import services.ProcessoService;
import services.ServiceResponse;
import services.UsuarioService;
import utilities.CpfCnpjFormatter;
import utilities.URLUtility;

public class AddUserController implements Initializable {

	@FXML
	private JFXTextField tfName;

	@FXML
	AnchorPane apUserDocuments;

	@FXML
	private JFXComboBox<String> cbCpfCnpj;
	ObservableList<Usuario> obsCpfCnpj = FXCollections.observableArrayList();

	@FXML
	private TableView<Usuario> tableView;

	@FXML
	private TableColumn<Usuario, String> tcName;

	@FXML
	private TableColumn<Usuario, String> tcCpfCnpj;

	@FXML
	private JFXTextField tfSearch;

	@FXML
	private JFXButton btnNew, btnSave, btnEdit, btnDelete, btnSearch;

	@FXML
	private JFXButton btnClose;

	private String urlService;
	private TranslateTransition ttClose;

	Usuario object;

	ProcessComboBoxController processCbController;
	UserComboBoxController userComboBoxController;
	DocumentController documentController;

	ObservableList<Usuario> obsList = FXCollections.observableArrayList();

	UserComboBoxCpfCnpjController userCbController;

	public AddUserController(DocumentController documentController, Usuario object, String urlService,
			TranslateTransition ttClose) {
		this.documentController = documentController;
		this.object = object;
		this.urlService = URLUtility.getURLService();
		this.ttClose = ttClose;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		userCbController = new UserComboBoxCpfCnpjController(this.urlService, cbCpfCnpj);

		tcName.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nome"));
		tcCpfCnpj.setCellValueFactory(new PropertyValueFactory<Usuario, String>("cpfCnpj"));

		tcCpfCnpj.setCellFactory(column -> new TableCell<Usuario, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					try {
						setText(CpfCnpjFormatter.format(item));
					} catch (ParseException e) {
						setText(item); // Se der erro, mostra sem formatação
					}
				}
			}
		});

		tableView.setItems(obsList);

		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Usuario>() {
			@Override
			public void changed(ObservableValue<? extends Usuario> observable, Usuario oldValue, Usuario newValue) {
				if (newValue != null) {

					tfName.setText(newValue.getNome());

					Usuario selectedUser = newValue;
					// Se houver endereço relacionado com a interferência, preencher o combobox do
					// endereço
					if (selectedUser != null) {
						userCbController.fillAndSelectComboBox(selectedUser.getCpfCnpj().toString());
					}

					if (userDocumentsController != null) {
						userDocumentsController.updateUser(newValue);
					}

				}
			}
		});

		btnClose.setOnAction(e -> {
			ttClose.play();

			Usuario seletedObject = tableView.getSelectionModel().getSelectedItem();
			this.documentController.fillAndSelectComboBoxUser(seletedObject);

		});

		btnSearch.setOnAction(event -> searchByKeyword(event));
		

		/*
		 * Buscar apenas clicando no enter do teclado
		 */
		tfSearch.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER){
				btnSearch.fire();
			}
		});
		
		
		
		btnSave.setOnAction(event -> save(event));
		btnEdit.setOnAction(event -> update(event));
		btnDelete.setOnAction(event -> delete(event));
		btnNew.setOnAction(event -> clearAllComponents());

		openUserDocumentsController();

	}

	public void save(ActionEvent event) {

		Long id = object != null && object.getId() != null ? object.getId() : null;

		String nome = tfName.getText();

		String cpfCnpj = cbCpfCnpj.selectionModelProperty().get().isEmpty() ? null : cbCpfCnpj.getItems().get(0);

		// Remove todos os caracteres não numéricos da string
		cpfCnpj = cpfCnpj.replaceAll("\\D", "");

		// Se o logradouro não estiver preenchido não salvar
		if (nome == null || nome.isEmpty()) {

			// Informa salvamento com sucesso
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Insira o nome do usuário !!!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

		} else if (nome == null || nome.isEmpty()) {
			// Informa salvamento com sucesso
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um Cpf ou Cnpj !!!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
		}

		else {

			try {

				// DocumentService documentService = new DocumentService(localUrl);
				UsuarioService service = new UsuarioService(urlService);

				Usuario toSaveObject = new Usuario(nome, cpfCnpj);

				ServiceResponse<?> response = service.save(toSaveObject);

				// Caputura a mensagem do banco de dados e converte para o objeto solicitado, no
				// caso Usuario, além de status e mensagem.
				ApiResponse<Usuario> serviceResponseFromJava = ApiResponse
						.fromJson(response.getResponseBody().toString(), Usuario.class);

				if (!serviceResponseFromJava.getStatus().equals("erro")) {

					// Mensagem
					Node source = (Node) event.getSource();
					Stage ownerStage = (Stage) source.getScene().getWindow();
					String toastMsg = serviceResponseFromJava.getMensagem();
					utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

					// Adiciona resposta na tabela
					Usuario newObject = serviceResponseFromJava.getObject();

					// Remove o objeto com solicitado de salvamento ou id nulo, se estiver presente
					// na tableView
					tableView.getItems().removeIf(obj -> obj.getId() == null || obj.getId().equals(newObject.getId()));

					// Adiciona com primeiro na lista
					tableView.getItems().add(0, newObject);
					// Seleciona o objeto salvo na table view
					tableView.getSelectionModel().select(newObject);
					// Atualiza objeto vindo do DocumentController
					object = newObject;

				} else {
					Node source = (Node) event.getSource();
					Stage ownerStage = (Stage) source.getScene().getWindow();
					String toastMsg = serviceResponseFromJava.getMensagem();
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

		Usuario seletedObject = tableView.getSelectionModel().getSelectedItem();

		// Long id = seletedObject.getId();
		String nome = tfName.getText();
		String cpfCnpj = cbCpfCnpj.selectionModelProperty().get().isEmpty() ? null : cbCpfCnpj.getItems().get(0);

		// Remove todos os caracteres não numéricos da string
		cpfCnpj = cpfCnpj.replaceAll("\\D", "");

		if (nome.isEmpty() || nome == null) {
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Digite um nome ao editar!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
		} else if (cpfCnpj == null || cpfCnpj.isEmpty()) {
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um cpf ou cnpj ao editar!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

		} else {

			try {

				// DocumentService documentService = new DocumentService(localUrl);
				UsuarioService service = new UsuarioService(urlService);

				// Endereco endereco = new Endereco(id, logradouro, bairro, cidade, cep,
				// estado);

				seletedObject.setNome(nome);
				seletedObject.setCpfCnpj(cpfCnpj);

				ServiceResponse<?> response = service.update(seletedObject);

				// Caputura a mensagem do banco de dados e converte para o objeto solicitado, no
				// caso Usuario, além de status e mensagem.
				ApiResponse<Usuario> serviceResponseFromJava = ApiResponse
						.fromJson(response.getResponseBody().toString(), Usuario.class);

				if (!serviceResponseFromJava.getStatus().equals("erro")) {

					// Mensagem
					Node source = (Node) event.getSource();
					Stage ownerStage = (Stage) source.getScene().getWindow();
					String toastMsg = serviceResponseFromJava.getMensagem();
					utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

					// Adiciona resposta na tabela
					Usuario newObject = serviceResponseFromJava.getObject();

					// Remove objeto solicitado da tableView
					tableView.getItems().remove(seletedObject);
					// Adiciona objeto já editado como primeiro da lista.
					tableView.getItems().add(0, newObject);
					// Seleciona o objeto na table view
					tableView.getSelectionModel().select(newObject);

				} else {
					Node source = (Node) event.getSource();
					Stage ownerStage = (Stage) source.getScene().getWindow();
					String toastMsg = serviceResponseFromJava.getMensagem();
					utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

				}

			} catch (Exception e) {
				// adicionar Toast de erro
				e.printStackTrace();
			}

		}

	}

	public void delete(ActionEvent event) {

		Usuario selectedObject = tableView.getSelectionModel().getSelectedItem();

		try {
			UsuarioService service = new UsuarioService(urlService);

			ServiceResponse<?> serviceResponse = service.deleteById(selectedObject.getId());

			// Caputura a mensagem do banco de dados e converte para o objeto solicitado, no
			// caso Usuario, além de status e mensagem.
			ApiResponse<Usuario> serviceResponseFromJava = ApiResponse
					.fromJson(serviceResponse.getResponseBody().toString(), Usuario.class);

			if (!serviceResponseFromJava.getStatus().equals("erro")) {

				// Informa sucesso em deletar
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = serviceResponseFromJava.getMensagem();
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

				// retira objecto da tabela de documentos tvDocs
				tableView.getItems().remove(selectedObject);

			} else {

				// Informa erro em deletar
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = serviceResponseFromJava.getMensagem();
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void searchByKeyword(ActionEvent event) {

		userDocumentsController.updateUser(null);

		try {

			UsuarioService service = new UsuarioService(urlService);

			String keyword = tfSearch.getText();

			Set<Usuario> objects = service.listByName(keyword);

			// Create a list of Document objects
			obsList.clear();
			obsList.addAll(objects);

			// Limpa os inputs e comboboxes
			clearAllComponents();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para buscar processos e preencher o ComboBox
	public Set<Processo> fetchProcesses(String keyword) {

		try {
			ProcessoService service = new ProcessoService(urlService);

			Set<Processo> objects = service.fetchByKeyword(keyword);

			return objects;

		} catch (Exception e) {

		}
		return null;
	}

	public void fillAndSelectComboBoxProcess(Usuario process) {
		ObservableList<Usuario> newObsList = FXCollections.observableArrayList();

		newObsList.add(0, process);

	}

	public void clearAllComponents() {
		tfName.clear();
		cbCpfCnpj.getSelectionModel().clearSelection();

	}

	UserDocumentsController userDocumentsController;

	public void openUserDocumentsController() {

		// Carrega o arquivo FXML para o painel de edição
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/UserDocuments.fxml"));

		loader.setRoot(apUserDocuments);

		userDocumentsController = new UserDocumentsController(this);
		loader.setController(userDocumentsController);

		try {
			loader.load();
		} catch (IOException e) {
			System.out.println("erro na abertura do pane atendimento");
			e.printStackTrace();
		}

	}

	public Set<Documento> searchDocumentsByUser() {

		return null;

	}

}
