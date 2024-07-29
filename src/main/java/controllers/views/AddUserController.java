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

public class AddUserController implements Initializable {
	

	

    @FXML
    private JFXButton btnClose;


    @FXML
    private JFXComboBox<Usuario> cbUser;

    @FXML
    private JFXComboBox<String> cbCpfCnpj;

    @FXML
    private TableColumn<?, ?> tcAttachment;

    @FXML
    private TableColumn<?, ?> tcUser;

    @FXML
    private TableColumn<?, ?> tcProcess;

    @FXML
    private JFXTextField tfSearch;

    @FXML
    private JFXButton btnSearch;

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

	Usuario object;

	ProcessComboBoxController processCbController;
	UserComboBoxController userComboBoxController;
	DocumentController documentController;

	public AddUserController(DocumentController documentController, Usuario object, String urlService, TranslateTransition ttClose) {
		this.documentController = documentController;
		this.object = object;
		this.urlService = URLUtility.getURLService();
		this.ttClose = ttClose;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		// mudar para cpf processCbController = new ProcessComboBoxController(urlService, cbProcess);
		new UserComboBoxController(urlService, cbUser);
		userComboBoxController = new UserComboBoxController(urlService, cbUser);
		
		
		if (object!= null) {
			userComboBoxController.fillAndSelectComboBox(object);
		}

		btnClose.setOnAction(e -> {
			ttClose.play();

			Usuario object = cbUser.selectionModelProperty().get().isEmpty() ? null
					: userComboBoxController.getSelectedObject();

			if (object != null) {
				this.documentController.fillAndSelectComboBoxUser(object);
			}

		});
	}

	// Método para buscar processos e preencher o ComboBox
	public List<Processo> fetchProcesses(String keyword) {

		try {
			ProcessoService service = new ProcessoService(urlService);

			List<Processo> list = service.fetchByKeyword(keyword);

			return list;

		} catch (Exception e) {

		}
		return null;
	}
	
	public void fillAndSelectComboBoxProcess (Usuario process) {
		ObservableList<Usuario> newObsList = FXCollections.observableArrayList();
		cbUser.setItems(newObsList);

		newObsList.add(0, process);

		// Atualizando o ComboBox para refletir a mudança
		// cbProcess.setItems(null);
		cbUser.setItems(newObsList);

		// Selecionando o novo item no ComboBox
		cbUser.getSelectionModel().select(0);
	}

}
