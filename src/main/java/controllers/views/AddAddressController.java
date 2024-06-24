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
import models.Endereco;
import models.Processo;
import models.Usuario;
import services.ProcessoService;
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

	Endereco object;

	ProcessComboBoxController processCbController;
	UserComboBoxController userComboBoxController;
	AddressComboBoxController addressCbController;
	DocumentController documentController;

	public AddAddressController(DocumentController documentController, Endereco object, String urlService,
			TranslateTransition ttClose) {
		this.documentController = documentController;
		this.object = object;
		this.urlService = URLUtility.getURLService();
		this.ttClose = ttClose;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// mudar para cpf processCbController = new
		// ProcessComboBoxController(urlService, cbProcess);
		// new UserComboBoxController(urlService, cbAddress);
		// userComboBoxController = new UserComboBoxController(urlService, cbAddress);

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

}
