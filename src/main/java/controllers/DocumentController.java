package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import controllers.views.DocumentViewController;
import enums.ToastType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.Documento;
import models.DocumentoTipo;
import models.Processo;
import services.DocumentService;
import services.DocumentoTipoService;
import services.ServiceResponse;

/**
 * Controlador para lidar com operações relacionadas aos documentos.
 */
public class DocumentController implements Initializable {
	
	// URL local para os recursos
	private String localUrl;
	// private String remoteUrl;

	/**
	 * Construtor da classe DocumentController.
	 */
	public DocumentController() {
		try {
			Properties prop = new Properties();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
			prop.load(inputStream);
			localUrl = prop.getProperty("local.url");
			// remoteUrl = prop.getProperty("remote.url");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private AnchorPane apContent;

	@FXML
	private JFXComboBox<DocumentoTipo> cbDocType;
		ObservableList<DocumentoTipo> obsListDocTypes = FXCollections.observableArrayList();

	@FXML
	private JFXTextField tfNumber;

	@FXML
	private JFXTextField tfNumberSEI;

	@FXML
    private JFXComboBox<Processo> cbMainProcess;
		ObservableList<Processo> obsMainProcess = FXCollections.observableArrayList();

    @FXML
    private JFXComboBox<Processo> cbProcess;
    	ObservableList<Processo> obsProcess = FXCollections.observableArrayList();

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnEdit;

	@FXML
	private JFXButton btnDelete;

	@FXML
	private JFXTextField tfSearch;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private TableView<Documento> tvDocs;
		ObservableList<Documento> obsListDocs = FXCollections.observableArrayList();

	@FXML
	private TableColumn<Documento, Integer> tcId;

	@FXML
	private TableColumn<Documento, String> tcNum;
	
	@FXML
	private TableColumn<Documento, String> tcMainProc;

	@FXML
	private TableColumn<Documento, String> tcProc;

	@FXML
	private TableColumn<Documento, String> tcNumSei;

	@FXML
	private JFXButton btnViews;

	@FXML
	private MainController mainController;

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	/**
	 * Inicializa o controlador e configura a interface.
	 *
	 * @param location
	 *            O URL para localização do recurso.
	 * @param resources
	 *            Os recursos utilizados pelo controlador.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Configura as colunas da TableView
		// Configura as ComboBoxes, TextFields e botões
		// Adiciona listeners aos componentes

		tcId.setCellValueFactory(new PropertyValueFactory<Documento, Integer>("docId"));
		tcNum.setCellValueFactory(new PropertyValueFactory<Documento, String>("docNumero"));
		tcNumSei.setCellValueFactory(new PropertyValueFactory<Documento, String>("docSEI"));

		AnchorPane.setRightAnchor(apContent, 0.0);
		AnchorPane.setLeftAnchor(apContent, 0.0);

		tvDocs.setItems(obsListDocs);

		obsListDocTypes = FXCollections.observableArrayList(fetchDocTypes());

		cbDocType.setItems(obsListDocTypes);
		cbDocType.setValue(null);

		tvDocs.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				// Atualizar componentes de acordo com o documento selecionado
				tfNumber.setText(newSelection.getDocNumero());
				tfNumberSEI.setText(String.valueOf(newSelection.getDocSEI()));

				// Atualiza ComboBox (Tipo de Documento) a partir do documento selecionado
				cbDocType.getSelectionModel().select(newSelection.getDocTipo());

			} else {
				// Se documento nulo limpa componentes
				tfNumber.clear();
				tfNumberSEI.clear();
				cbDocType.getSelectionModel().clearSelection();
			}
		});
		// Listener do TextField
		tfNumber.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// System.out.println("TextField changed from: " + oldValue + " to: " +
				// newValue);
			}
		});
		// Listener do TextField
		tfNumberSEI.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// System.out.println("TextField changed from: " + oldValue + " to: " +
				// newValue);
			}
		});
		// Expressão que aceita somente números
		String numericPattern = "^[0-9]*$";

		// Formata texto do TextField para número
		TextFormatter<String> textFormatter = new TextFormatter<String>(change -> {
			if (change.getControlNewText().matches(numericPattern)) {
				return change;
			} else {
				return null; // Reject the change (non-numeric input)
			}
		});
		tfNumberSEI.setTextFormatter(textFormatter);
		// main process
		obsMainProcess = FXCollections.observableArrayList(
				new Processo(1, "197.555./2015"),
				new Processo(2, "198.555.666/2012"));
		
		cbMainProcess.setItems(obsMainProcess);
		
		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(cbMainProcess,
				(typedText, itemToCompare) -> itemToCompare.getProcDescricao().toLowerCase().contains(typedText.toLowerCase()));
		

		cbMainProcess.setConverter(new StringConverter<Processo>() {

			@Override
			public String toString(Processo object) {
				return object != null ? object.getProcDescricao() : "";
			}

			@Override
			public Processo fromString(String string) {
				return cbMainProcess.getItems().stream().filter(object -> object.getProcDescricao().equals(string)).findFirst()
						.orElse(null);
			}

		});

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(cbMainProcess);
		
		
		// process
				obsProcess = FXCollections.observableArrayList(
						new Processo(1, "197.555./2015"),
						new Processo(2, "198.555.666/2012"));
				
				cbProcess.setItems(obsMainProcess);
				
				utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(cbProcess,
						(typedText, itemToCompare) -> itemToCompare.getProcDescricao().toLowerCase().contains(typedText.toLowerCase()));
				

				cbProcess.setConverter(new StringConverter<Processo>() {

					@Override
					public String toString(Processo object) {
						return object != null ? object.getProcDescricao() : "";
					}

					@Override
					public Processo fromString(String string) {
						return cbProcess.getItems().stream().filter(object -> object.getProcDescricao().equals(string)).findFirst()
								.orElse(null);
					}

				});

				utilities.FxUtilComboBoxSearchable.getComboBoxValue(cbProcess);
		
		
		btnViews.setOnAction(event -> showDocumentView());
		btnSave.setOnAction(event -> handleSave(event));
		btnSearch.setOnAction(event -> handleSearch(event));
		btnDelete.setOnAction(event -> handleDelete(event));
		btnEdit.setOnAction(event -> handleEdit(event));
	}

	/**
	 * Retorna o documento selecionado na TableView.
	 *
	 * @return O documento selecionado.
	 */

	public Documento getSelectedDocument() {
		Documento documento = tvDocs.getSelectionModel().getSelectedItem();
		return documento;
	}

	/**
	 * Exibe a visualização do documento selecionado em uma nova janela.
	 */
	public void showDocumentView() {
		try {
			Documento selectedDocument = tvDocs.getSelectionModel().getSelectedItem();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/views/DocumentView.fxml"));
			DocumentViewController docViewController = new DocumentViewController(selectedDocument);
			loader.setController(docViewController);

			Parent root = loader.load();

			Stage popupStage = new Stage();
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStage.setTitle("Edições e Diagramas");

			Scene scene = new Scene(root);
			popupStage.setScene(scene);

			popupStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Manipula a ação de pesquisa de documentos por parâmetro.
	 *
	 * @param event
	 *            O evento de ação associado à pesquisa.
	 */
	public void handleSearch(ActionEvent event) {

		try {

			DocumentService documentService = new DocumentService(localUrl);

			String keyword = tfSearch.getText();

			List<Documento> documentos = documentService.fetchByParam(keyword);

			// Create a list of Document objects
			obsListDocs.clear();
			obsListDocs.addAll(documentos);
			cbDocType.setValue(obsListDocTypes.get(0));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Manipula a ação de salvar um documento.
	 *
	 * @param event
	 *            O evento de ação associado à edição do documento.
	 */
	public void handleSave(ActionEvent event) {

		String text = tfNumberSEI.getText();

		// verjo que este m�todo � desnecess�rio pois o textfield j� est� aceitando
		// apenas n�meros
		int numeroSei = 0;
		try {
			numeroSei = Integer.parseInt(text);
			// Use the 'numeroSei' integer as needed
		} catch (NumberFormatException e) {
			// Handle the case where the input is not a valid integer
			System.out.println("Input is not a valid integer.");
		}

		try {

			DocumentService documentService = new DocumentService(localUrl);

			Documento reqDoc = new Documento(tfNumber.getText(), numeroSei, cbDocType.getValue());

			ServiceResponse<?> serviceResponse = documentService.save(reqDoc);

			if (serviceResponse.getResponseCode() == 201) {

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Documento salvo com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);
				// Adiciona resposta na tabela
				Documento responseDocumento = new Gson().fromJson((String) serviceResponse.getResponseBody(),
						Documento.class);
				// Adiciona com primeiro na lista
				tvDocs.getItems().add(0, responseDocumento);
				// Seleciona o objeto salvo na table view
				tvDocs.getSelectionModel().select(responseDocumento);

			} else {
				// adiconar alerta (Toast) de erro
				// System.out.println(serviceResponse.getResponseCode());
			}

		} catch (Exception e) {
			// adicionar Toast de erro
			e.printStackTrace();
		}

	}

	/**
	 * Manipula a ação de editar um documento existente.
	 *
	 * @param event
	 *            O evento de ação associado à edição do documento.
	 */
	public void handleEdit(ActionEvent event) {
		// Get the selected document from the TableView
		Documento selectedDoc = tvDocs.getSelectionModel().getSelectedItem();

		if (selectedDoc == null) {
			// Display an alert or toast message indicating that no document is selected
			System.out.println("No document selected for editing.");
			return;
		}

		// Retrieve values from text fields
		String updatedNumero = tfNumber.getText();
		String updatedNumeroSEI = tfNumberSEI.getText();

		DocumentoTipo updateDocumentoTipo = cbDocType.getValue();

		// converter para integer
		int updatedSei = 0;
		try {
			updatedSei = Integer.parseInt(updatedNumeroSEI);
		} catch (NumberFormatException e) {
			// Handle the case where the input is not a valid integer
			System.out.println("Input is not a valid integer for SEI.");
			// You may want to display a toast or alert here
			return;
		}

		// Edita objeto com novos valores
		selectedDoc.setDocNumero(updatedNumero);
		selectedDoc.setDocSEI(updatedSei);

		selectedDoc.setDocTipo(updateDocumentoTipo);

		try {
			DocumentService documentService = new DocumentService(localUrl);

			// Requisi��o de resposta de edi��o
			ServiceResponse<?> serviceResponse = documentService.update(selectedDoc);

			if (serviceResponse.getResponseCode() == 200) {
				// Alerta (Toast) de sucesso na edi��o
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Documento editado com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

				tvDocs.getItems().remove(selectedDoc);
				// Converte objeto editado para Json
				Documento responseDocumento = new Gson().fromJson((String) serviceResponse.getResponseBody(),
						Documento.class);
				// Adiciona objeto editado como primeiro �tem ma fila na table view
				tvDocs.getItems().add(0, responseDocumento);
				// Seleciona o objeto editado na table view
				tvDocs.getSelectionModel().select(responseDocumento);

			} else {
				// Display an error toast or alert
				// System.out.println(serviceResponse.getResponseCode());
			}

		} catch (Exception e) {
			// Display an error toast or alert
			e.printStackTrace();
		}
	}

	/**
	 * Manipula a ação de deletar um documento existente.
	 *
	 * @param event
	 *            O evento de ação associado à exclusão do documento.
	 */
	public void handleDelete(ActionEvent event) {

		Documento selectedDocumento = tvDocs.getSelectionModel().getSelectedItem();

		try {
			DocumentService documentService = new DocumentService(localUrl);

			ServiceResponse<?> serviceResponse = documentService.deleteById(selectedDocumento.getDocId());

			if (serviceResponse.getResponseCode() == 200) {

				// Informa sucesso em deletar
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Documento deletado com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

				// retira objecto da tabela de documentos tvDocs
				tvDocs.getItems().remove(selectedDocumento);

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

	/**
	 * Lista os tipos de documento disponíveis (Requerimento, Ofício, ...).
	 *
	 * @return Uma lista contendo os tipos de documento disponíveis.
	 */
	public List<DocumentoTipo> fetchDocTypes() {

		try {

			DocumentoTipoService dtService = new DocumentoTipoService(localUrl);

			List<DocumentoTipo> docTypes = dtService.fetchAll();

			return docTypes;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
