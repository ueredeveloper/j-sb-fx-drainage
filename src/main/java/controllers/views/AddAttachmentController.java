package controllers.views;

import java.net.URL;
import java.util.OptionalInt;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.IntStream;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import controllers.DocumentController;
import enums.ToastType;
import javafx.animation.TranslateTransition;
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
import models.Anexo;
import models.Processo;
import models.Usuario;
import services.AnexoService;
import services.ProcessoService;
import services.ServiceResponse;
import utilities.URLUtility;

public class AddAttachmentController implements Initializable {

	@FXML
	private JFXButton btnClose;

	@FXML
	private JFXComboBox<Anexo> cbAttachment;
	@FXML
	private JFXComboBox<Processo> cbProcess;
	@FXML
	private JFXComboBox<Usuario> cbUser;

	@FXML
	private JFXTextField tfSearch, tfAttachment;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private TableView<Anexo> tableView;
	ObservableList<Anexo> tableViewObsList = FXCollections.observableArrayList();

	@FXML
	private TableColumn<Processo, String> tcProcess;

	@FXML
	private TableColumn<Usuario, String> tcUser;

	@FXML
	private TableColumn<Anexo, String> tcAttachment;

	@FXML
	private JFXButton btnNew;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnEdit;

	@FXML
	private JFXButton btnDelete;

	private String urlService;
	private TranslateTransition ttClose;
	JFXComboBox<Anexo> docCbAttachment;
	ObservableList<Anexo> obsAttachemnt;
	Anexo attachment;

	DocumentController documentController;
	ProcessComboBoxController processComboBoxController;
	UserComboBoxController userComboBoxController;

	public AddAttachmentController(DocumentController documentController, Anexo attachment, String urlService,
			TranslateTransition ttClose) {
		this.documentController = documentController;
		this.attachment = attachment;
		this.urlService = URLUtility.getURLService();
		this.ttClose = ttClose;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		userComboBoxController = new UserComboBoxController(urlService, cbUser);
		processComboBoxController = new ProcessComboBoxController(urlService, cbProcess);

		tcAttachment.setCellValueFactory(new PropertyValueFactory<>("numero"));
		tcProcess.setCellValueFactory(new PropertyValueFactory<>("processo"));
		tcUser.setCellValueFactory(new PropertyValueFactory<>("usuario"));

		tableView.setItems(tableViewObsList);

		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			if (newSelection != null) {

				this.attachment = newSelection;

				// Preencher o text field para edição se precisar
				tfAttachment.setText(newSelection.getNumero());

				// Limpa o combobox processo e combobox usuario
				cbProcess.getSelectionModel().clearSelection();
				cbProcess.setValue(null);

				cbUser.getSelectionModel().clearSelection();
				cbUser.setValue(null);

			} else {
				clearAllComponents();
			}
		});

		btnClose.setOnAction(e -> {
			ttClose.play();

			// Preencher a tela de cadastro de documentos com o processo persistido
			// (salvamento ou edição)
			if (attachment != null) {
				this.documentController.fillAndSelectComboBoxAttachment(attachment);

			}

		});

		btnSearch.setOnAction(event -> {

			String keyword = tfSearch.getText();

			try {
				AnexoService service = new AnexoService(urlService);
				Set<Anexo> objects = service.fecthByKeyword(keyword);

				tableViewObsList.clear();
				tableViewObsList.addAll(objects);

			} catch (Exception e) {
				// Trate exceções adequadamente
				e.printStackTrace();
			}

		});

		btnNew.setOnAction(event -> {
			clearAllComponents();
		});

		btnSave.setOnAction(event -> {
			saveAttachment(event);
		});
		btnEdit.setOnAction(event -> {
			saveAttachment(event);
		});

		btnDelete.setOnAction(event -> {
			deleteAttachment(event);
		});
	}

	public void saveAttachment(ActionEvent event) {

		try {

			// Preencher com o número digitado pelo usuário, assim é possível editar o
			// número do processo
			if (this.attachment == null) {
				this.attachment = new Anexo();
				this.attachment.setNumero(tfAttachment.getText());
			} else {
				this.attachment.setNumero(tfAttachment.getText());
			}

			// Capturar as seleções dos outros atributos nos comboboxes
			Processo selectedProcess = processComboBoxController.getSelectedObject();

			Usuario selectedUser = userComboBoxController.getSelectedObject();

			// Anexar o processo principal (anexo) ao processo
			if (selectedProcess != null) {

				if (selectedUser != null) {
					selectedProcess.setUsuario(selectedUser);
				}

				this.attachment.getProcessos().add(selectedProcess);
			}

			// DocumentService documentService = new DocumentService(localUrl);
			AnexoService service = new AnexoService(urlService);

			ServiceResponse<?> response = service.save(this.attachment);

			System.out.println(response);

			if (response.getResponseCode() == 200) {

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Objeto salvo com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);
				// Adiciona resposta na tabela
				Anexo responseObject = new Gson().fromJson((String) response.getResponseBody(), Anexo.class);
				// Adiciona com primeiro na lista

				ObservableList<Anexo> items = tableView.getItems();
				// Remove o objeto da lista
				OptionalInt indexOpt = IntStream.range(0, items.size())
						.filter(i -> items.get(i).getId().equals(responseObject.getId())).findFirst();

				if (indexOpt.isPresent()) {
					// Se o objeto existe, substituí-lo pelo novo objeto
					items.set(indexOpt.getAsInt(), responseObject);
				} else {
					// Caso contrário, adicioná-lo no início da lista e selecioná-lo
					items.add(0, responseObject);
					tableView.getSelectionModel().select(responseObject);
				}

			} else {
				// adiconar alerta (Toast) de erro
				// System.out.println(serviceResponse.getResponseCode());

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Erro ao salvar o objeto !";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			}

		} catch (Exception e) {
			// adicionar Toast de erro
			e.printStackTrace();
		}

	}

	public void deleteAttachment(ActionEvent event) {

		Anexo selectedObject = tableView.getSelectionModel().getSelectedItem();

		try {
			AnexoService service = new AnexoService(urlService);

			ServiceResponse<?> serviceResponse = service.deleteById(selectedObject.getId());

			if (serviceResponse.getResponseCode() == 200) {

				// Informa sucesso em deletar
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Objeto deletado com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

				// retira objecto da tabela de documentos tvDocs
				tableView.getItems().remove(selectedObject);

			} else {
				// Informa erro em deletar
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Erro ao deletar objeto!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Método para buscar processos e preencher o ComboBox
	public Set<Processo> fetchProcessesByKeyword(String keyword) {

		try {
			ProcessoService service = new ProcessoService(urlService);

			Set<Processo> objects = service.fetchByKeyword(keyword);

			return objects;

		} catch (Exception e) {

		}
		return null;
	}

	public Set<Anexo> fethcAttachmentsByKeyword(String keyword) {

		try {
			AnexoService service = new AnexoService(urlService);

			Set<Anexo> objects = service.fecthByKeyword(keyword);

			return objects;

		} catch (Exception e) {

		}
		return null;
	}

	public void fillAndSelectComboBoxProcess(Processo process) {
		ObservableList<Processo> newObs = FXCollections.observableArrayList();
		cbProcess.setItems(newObs);

		newObs.add(0, process);

		// Atualizando o ComboBox para refletir a mudança
		// cbProcess.setItems(null);
		cbProcess.setItems(newObs);

		// Selecionando o novo item no ComboBox
		cbProcess.getSelectionModel().select(0);
	}

	public void clearAllComponents() {

		tfAttachment.clear();
		// Limpa o id do processo para que seja salvo novo processo
		this.attachment = new Anexo();

		cbProcess.getSelectionModel().clearSelection();
		cbProcess.setValue(null);

		cbUser.getSelectionModel().clearSelection();
		cbUser.setValue(null);

	}

}
