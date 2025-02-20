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
import services.ProcessoService;
import services.ServiceResponse;

public class AddProcessController implements Initializable {

	@FXML
	private JFXButton btnClose;

	@FXML
	private JFXTextField tfProcess;
	@FXML
	private JFXComboBox<Usuario> cbUser;
	@FXML
	private JFXComboBox<Anexo> cbAttachment;

	@FXML
	private JFXTextField tfSearch;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private TableView<Processo> tableView;
	ObservableList<Processo> tableViewObsList = FXCollections.observableArrayList();

	@FXML
	private TableColumn<Processo, String> tcProcess;

	@FXML
	private TableColumn<Processo, String> tcUser;

	@FXML
	private TableColumn<Processo, String> tcAttachment;

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
	JFXComboBox<Processo> docCbProcess;
	ObservableList<Processo> obsProcess;
	Processo process;

	ProcessComboBoxController processCbController;
	AttachmentComboBoxController attachmentCbController;
	UserComboBoxController userComboBoxController;
	DocumentController documentController;

	public AddProcessController(DocumentController documentController, Processo process, String urlService,
			TranslateTransition ttClose) {
		this.documentController = documentController;
		this.process = process;
		this.urlService = urlService;
		this.ttClose = ttClose;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		userComboBoxController = new UserComboBoxController(urlService, cbUser);
		attachmentCbController = new AttachmentComboBoxController(urlService, cbAttachment);

		if (process != null) {
			fillProcessAttributes(process);
		}

		tcProcess.setCellValueFactory(new PropertyValueFactory<>("numero"));
		tcUser.setCellValueFactory(new PropertyValueFactory<>("usuario"));
		tcAttachment.setCellValueFactory(new PropertyValueFactory<>("anexo"));

		tableView.setItems(tableViewObsList);

		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			if (newSelection != null) {

				this.process = newSelection;

				// Preencher o text field para edição se precisar
				tfProcess.setText(newSelection.getNumero());

				// Preencher os comboboxes
				if (newSelection.getAnexo() != null) {
					attachmentCbController.fillAndSelectComboBox(newSelection.getAnexo());
				} else {
					attachmentCbController.fillAndSelectComboBox(null);
				}

				if (newSelection.getUsuario() != null) {
					userComboBoxController.fillAndSelectComboBox(newSelection.getUsuario());
				} else {
					userComboBoxController.fillAndSelectComboBox(null);
				}

			} else {
				clearAllComponents();
			}
		});

		btnClose.setOnAction(e -> {
			ttClose.play();

			// Preencher a tela de cadastro de documentos com o processo persistido
			// (salvamento ou edição)
			if (process != null) {
				this.documentController.fillAndSelectComboBoxProcess(process);

				if (process.getAnexo() != null) {
					this.documentController.fillAndSelectComboBoxAttachment(process.getAnexo());
				}
			}

		});

		btnSearch.setOnAction(event -> {

			String keyword = tfSearch.getText();

			try {
				ProcessoService service = new ProcessoService(urlService);
				Set<Processo> objects = service.fetchByKeyword(keyword);

				System.out.println(objects.size());

				tableViewObsList.clear();
				tableViewObsList.addAll(objects);

			} catch (Exception e) {
				// Trate exceções adequadamente
				e.printStackTrace();
			}

		});

		btnSave.setOnAction(event -> {
			saveProcess(event);
		});
		btnNew.setOnAction(event -> {
			clearAllComponents();
		});
		btnDelete.setOnAction(event -> {
			deleteProcess(event);
		});

		btnEdit.setOnAction(event -> {
			update(event);
		});

	}

	public void fillProcessAttributes(Processo process) {

		tfProcess.setText(process.getNumero());

		if (process.getUsuario() != null) {
			this.userComboBoxController.fillAndSelectComboBox(process.getUsuario());
		}

		if (process.getAnexo() != null) {
			this.attachmentCbController.fillAndSelectComboBox(process.getAnexo());
		}

	}

	public void saveProcess(ActionEvent event) {

		try {

			ProcessoService service = new ProcessoService(urlService);

			// Preencher com o número digitado pelo usuário, assim é possível editar o
			// número do processo
			if (this.process == null) {
				this.process = new Processo();
				this.process.setNumero(tfProcess.getText());
			} else {
				this.process.setNumero(tfProcess.getText());
			}

			// Capturar as seleções dos outros atributos nos comboboxes
			Anexo obsAttachList0 = attachmentCbController.getSelectedObject();

			Usuario obsUsList0 = userComboBoxController.getSelectedObject();

			// Anexar o processo principal (anexo) ao processo
			if (obsAttachList0 == null) {

				// this.process.setAnexo(obsAttachList0);
				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Selecione o processo principal!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

				return;
			} else if (obsUsList0 == null) {
				// this.process.setUsuario(obsUsList0);

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Selecione um usuário!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

				return;

			} else if (obsUsList0.getCpfCnpj() == null || obsUsList0.getCpfCnpj().isEmpty()) {
				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Selecione um usuário com cpf/cnpj cadastrado!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

				return;
			}

			this.process.setAnexo(obsAttachList0);
			this.process.setUsuario(obsUsList0);

			ServiceResponse<?> reponse = service.save(this.process);

			if (reponse.getResponseCode() == 201) {

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Objeto salvo com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);
				// Adiciona resposta na tabela
				Processo responseObject = new Gson().fromJson((String) reponse.getResponseBody(), Processo.class);
				// Adiciona com primeiro na lista

				ObservableList<Processo> items = tableView.getItems();
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

	public void update(ActionEvent event) {

		try {

			ProcessoService service = new ProcessoService(urlService);

			this.process = tableView.getSelectionModel().getSelectedItem();

			if (this.process == null) {

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Objeto editado com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

			} else {

				// Capturar as seleções dos outros atributos nos comboboxes
				Anexo obsAttachList0 = attachmentCbController.getSelectedObject();

				Usuario obsUsList0 = userComboBoxController.getSelectedObject();

				// Anexar o processo principal (anexo) ao processo
				if (obsAttachList0 == null) {

					// this.process.setAnexo(obsAttachList0);
					// Informa salvamento com sucesso
					Node source = (Node) event.getSource();
					Stage ownerStage = (Stage) source.getScene().getWindow();
					String toastMsg = "Selecione o processo principal!";
					utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

					return;
				}
				if (obsUsList0 == null) {
					// this.process.setUsuario(obsUsList0);

					// Informa salvamento com sucesso
					Node source = (Node) event.getSource();
					Stage ownerStage = (Stage) source.getScene().getWindow();
					String toastMsg = "Selecione um usuário!";
					utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

					return;

				}

				ServiceResponse<?> reponse = service.update(this.process);

				if (reponse.getResponseCode() == 200) {

					// Informa salvamento com sucesso
					Node source = (Node) event.getSource();
					Stage ownerStage = (Stage) source.getScene().getWindow();
					String toastMsg = "Objeto editado com sucesso!";
					utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);
					// Adiciona resposta na tabela
					Processo responseObject = new Gson().fromJson((String) reponse.getResponseBody(), Processo.class);
					// Adiciona com primeiro na lista

					ObservableList<Processo> items = tableView.getItems();
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

			}

		} catch (Exception e) {
			// adicionar Toast de erro
			e.printStackTrace();
		}

	}

	public void deleteProcess(ActionEvent event) {

		Processo selectedObject = tableView.getSelectionModel().getSelectedItem();

		try {
			ProcessoService service = new ProcessoService(urlService);

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

	public void clearAllComponents() {

		tfProcess.clear();
		// Limpa o id do processo para que seja salvo novo processo
		this.process = new Processo();

		cbAttachment.getSelectionModel().clearSelection();
		cbAttachment.setValue(null);

		cbUser.getSelectionModel().clearSelection();
		cbUser.setValue(null);

	}

}
