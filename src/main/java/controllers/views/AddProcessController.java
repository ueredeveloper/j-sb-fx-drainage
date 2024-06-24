package controllers.views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import controllers.DocumentController;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Anexo;
import models.Processo;
import models.Usuario;
import services.ProcessoService;
import utilities.URLUtility;

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
	private TableView<?> tvDocs;

	@FXML
	private TableColumn<?, ?> tcProcess;

	@FXML
	private TableColumn<?, ?> tcUser;

	@FXML
	private TableColumn<?, ?> tcAttachment;

	private String urlService;
	private TranslateTransition ttClose;
	JFXComboBox<Processo> docCbProcess;
	ObservableList<Processo> obsProcess;
	Processo process;

	ProcessComboBoxController processCbController;
	DocumentController documentController;

	public AddProcessController(DocumentController documentController, Processo process, String urlService, TranslateTransition ttClose) {
		this.documentController = documentController;
		this.process = process;
		this.urlService = URLUtility.getURLService();
		this.ttClose = ttClose;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		processCbController = new ProcessComboBoxController(urlService, cbProcess);
		new UserComboBoxController(urlService, cbUser);
		new AttachmentComboBoxController(urlService, cbAttachment);
		
		
		if (process!= null) {
			processCbController.fillAndSelectComboBoxProcess(process);
		}

		btnClose.setOnAction(e -> {
			ttClose.play();

			Processo object = cbProcess.selectionModelProperty().get().isEmpty() ? null
					: processCbController.getSelectedObject();

			if (object != null) {
				this.documentController.fillAndSelectComboBoxProcess(object);
			}

		});
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
	
	public void fillAndSelectComboBoxProcess (Processo process) {
		ObservableList<Processo> newObs = FXCollections.observableArrayList();
		cbProcess.setItems(newObs);

		

		newObs.add(0, process);

		// Atualizando o ComboBox para refletir a mudança
		// cbProcess.setItems(null);
		cbProcess.setItems(newObs);

		// Selecionando o novo item no ComboBox
		cbProcess.getSelectionModel().select(0);
	}

}
