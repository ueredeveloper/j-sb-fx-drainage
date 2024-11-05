package controllers.views;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

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
	private JFXComboBox<Processo> cbProcess;
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

		processCbController = new ProcessComboBoxController(urlService, cbProcess);
		userComboBoxController = new UserComboBoxController(urlService, cbUser);
		attachmentCbController = new AttachmentComboBoxController(urlService, cbAttachment);

		if (process != null) {
			processCbController.fillAndSelectComboBoxProcess(process);
		}

		tcProcess.setCellValueFactory(new PropertyValueFactory<>("numero"));
		tcUser.setCellValueFactory(new PropertyValueFactory<>("usuario"));
		tcAttachment.setCellValueFactory(new PropertyValueFactory<>("anexo"));

		tableView.setItems(tableViewObsList);

		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			if (newSelection != null) {

				processCbController.fillAndSelectComboBoxProcess(newSelection);

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

			Processo object = cbProcess.selectionModelProperty().get().isEmpty() ? null
					: processCbController.getSelectedObject();

			if (object != null) {
				this.documentController.fillAndSelectComboBoxProcess(object);
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
		
		/*btnEdit.setOnAction(event -> {
			deleteProcess(event);
		});*/

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

	public void saveProcess(ActionEvent event) {

		try {

			ProcessoService service = new ProcessoService(urlService);

			Processo obsProcessList0 = processCbController.getSelectedObject();

			Anexo obsAttachList0 = attachmentCbController.getSelectedObject();

			Usuario obsUsList0 = userComboBoxController.getSelectedObject();

			// Anexar o processo principal (anexo) ao processo
			if (obsAttachList0 != null) {
				obsProcessList0.setAnexo(obsAttachList0);
				// obsProcessList0.set
			}
			if (obsUsList0 != null) {
				obsProcessList0.setUsuario(obsUsList0);
			}

			ServiceResponse<?> reponse = service.save(obsProcessList0);

			if (reponse.getResponseCode() == 201) {

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Objeto salvo com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);
				// Adiciona resposta na tabela
				Processo responseObject = new Gson().fromJson((String) reponse.getResponseBody(), Processo.class);
				// Adiciona com primeiro na lista
				tableView.getItems().add(0, responseObject);
				// Seleciona o objeto salvo na table view
				tableView.getSelectionModel().select(responseObject);

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
		cbProcess.getSelectionModel().clearSelection();
		cbProcess.setValue(null);

		cbAttachment.getSelectionModel().clearSelection();
		cbAttachment.setValue(null);

		cbUser.getSelectionModel().clearSelection();
		cbUser.setValue(null);

	}
}
