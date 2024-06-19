package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import controllers.views.AddAddressController;
import controllers.views.AddressComboBoxController;
import controllers.views.AttachmentComboBoxController;
import controllers.views.DocumentViewController;
import controllers.views.EditAddressController;
import controllers.views.InterferenceTextFieldsController;
import controllers.views.ProcessComboBoxController;
import controllers.views.UserComboBoxController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import models.Anexo;
import models.Documento;
import models.DocumentoTipo;
import models.Endereco;
import models.Processo;
import models.Usuario;
import services.DocumentService;
import services.DocumentoTipoService;
import services.ServiceResponse;
import utilities.URLUtility;

/**
 * Controlador para lidar com operações relacionadas aos documentos.
 */
public class DocumentController implements Initializable {

	@FXML
	private ComboBox<Documento> cbDocument;

	private String localUrl;

	public DocumentController() {
		this.localUrl = URLUtility.getURLService();
	}

	@FXML
	private AnchorPane apContent;

	@FXML
	private JFXComboBox<DocumentoTipo> cbDocType;
	ObservableList<DocumentoTipo> obsDocumentTypes = FXCollections.observableArrayList();

	@FXML
	private JFXTextField tfNumber;

	@FXML
	private JFXTextField tfNumberSei;

	@FXML
	private JFXComboBox<Processo> cbProcess;
	ObservableList<Processo> obsProcess = FXCollections.observableArrayList();
	@FXML
	private JFXComboBox<Endereco> cbAddress;
	ObservableList<Endereco> obsAddress = FXCollections.observableArrayList();

	@FXML
	private JFXComboBox<Anexo> cbAttachment;
	ObservableList<Anexo> obsAnexo = FXCollections.observableArrayList();

	@FXML
	private JFXComboBox<Usuario> cbUser;

	@FXML
	private JFXTextField tfLatitude;

	@FXML
	private JFXTextField tfLongitude;

	@FXML
	private JFXButton btnNew;

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
	private TableColumn<Documento, String> tcTipo;

	@FXML
	private TableColumn<Documento, String> tcNum;

	@FXML
	private TableColumn<Documento, String> tcNumSei;

	@FXML
	private TableColumn<Documento, String> tcProc;

	@FXML
	private TableColumn<Documento, String> tcAddress;

	@FXML
	private TableColumn<Documento, String> tcActions;

	@FXML
	private JFXButton btnViews;

	@FXML
	private FontAwesomeIconView btnAddress;

	@FXML
	private JFXButton btnAddInterference;

	@FXML
	private JFXButton btnAddUser;

	@FXML
	private JFXButton btnAddProcess;

	@FXML
	private MainController mainController;

	AnchorPane apEditAddress, apAddAddress;

	public Endereco getDocAddress() {
		// Get the selected document from the TableView
		Endereco address = tvDocs.getSelectionModel().getSelectedItem().getDocEndereco();

		return address;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	private AddressComboBoxController addressCbController;
	private ProcessComboBoxController processCbController;
	private AttachmentComboBoxController attachmentCbController;
	private UserComboBoxController userCbController;

	private InterferenceTextFieldsController interferenceTfController;

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

		// Retira o link com a stilização light ou dark, assim fica a estilização do
		// componente pai (MainController)
		apContent.getStylesheets().clear();

		tcTipo.setCellValueFactory(cellData -> cellData.getValue().getProperty(Documento::getDocTipoDescricao));
		tcNum.setCellValueFactory(new PropertyValueFactory<Documento, String>("docNumero"));
		tcNumSei.setCellValueFactory(new PropertyValueFactory<Documento, String>("docSei"));
		tcProc.setCellValueFactory(cellData -> cellData.getValue().getProperty(Documento::getDocProcessoProcNumero));
		tcAddress.setCellValueFactory(cellData -> cellData.getValue().getProperty(Documento::getDocEnderecoLogradouro));

		Callback<TableColumn<Documento, String>, TableCell<Documento, String>> cellFactory = new Callback<TableColumn<Documento, String>, TableCell<Documento, String>>() {
			@Override
			public TableCell<Documento, String> call(final TableColumn<Documento, String> param) {
				final TableCell<Documento, String> cell = new TableCell<Documento, String>() {

					JFXComboBox<String> cbDocsAttributes = new JFXComboBox<>(
							FXCollections.observableArrayList("Tipo", "Documento", "Processo", "Endereço"));

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {

							// Id utilizado para os testes JUnit
							cbDocsAttributes.setId("cbDocsAttributes");
							// Action
							cbDocsAttributes.setOnAction(e -> openApEditAddress());

							setGraphic(cbDocsAttributes);
							setText(null);
						}
					}
				};
				return cell;
			}
		};

		tcActions.setCellFactory(cellFactory);

		AnchorPane.setRightAnchor(apContent, 0.0);
		AnchorPane.setLeftAnchor(apContent, 50.0);

		tvDocs.setItems(obsListDocs);

		obsDocumentTypes = FXCollections.observableArrayList(fetchDocumentTypes());

		cbDocType.setItems(obsDocumentTypes);
		cbDocType.setValue(null);

		tvDocs.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			if (newSelection != null) {

				System.out.println(newSelection.getDocTipo());

				// Atualiza ComboBox (Tipo de Documento) a partir do documento selecionado
				cbDocType.getSelectionModel().select(newSelection.getDocTipo());
				// Atualizar componentes de acordo com o documento selecionado
				tfNumber.setText(newSelection.getDocNumero());
				tfNumberSei.setText(String.valueOf(newSelection.getDocSei()));
				cbProcess.getSelectionModel().select(newSelection.getDocProcesso());
				cbAddress.getSelectionModel().select(newSelection.getDocEndereco());

			} else {

				clearAllComponents();

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
		tfNumberSei.textProperty().addListener(new ChangeListener<String>() {
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
		tfNumberSei.setTextFormatter(textFormatter);

		addressCbController = new AddressComboBoxController(localUrl, cbAddress);
		addressCbController.initializeComboBox();

		processCbController = new ProcessComboBoxController(localUrl, cbProcess);
		processCbController.initializeComboBox();

		attachmentCbController = new AttachmentComboBoxController(localUrl, cbAttachment);
		attachmentCbController.init();

		interferenceTfController = new InterferenceTextFieldsController(localUrl, tfLatitude, tfLongitude);
		interferenceTfController.initializeTextFields();

		userCbController = new UserComboBoxController(localUrl, cbUser);
		userCbController.init();

		btnNew.setOnAction(e -> clearAllComponents());
		btnViews.setOnAction(event -> showDocumentView());
		btnSave.setOnAction(event -> handleSave(event));
		btnSearch.setOnAction(event -> handleSearch(event));
		btnDelete.setOnAction(event -> handleDelete(event));
		btnEdit.setOnAction(event -> handleEdit(event));

		btnAddress.setOnMouseClicked(evert -> openAddAddress());

		// btnAddress.setOnAction(e -> openAddAddress());
	}

	/**
	 * Abre o painel de edição do endereço. Cria um novo AnchorPane e carrega um
	 * arquivo FXML para exibir o painel de edição. Realiza uma transição de
	 * tradução para exibir o painel na tela.
	 */
	public void openApEditAddress() {

		// Cria um novo AnchorPane
		apEditAddress = new AnchorPane();

		// Carrega o arquivo FXML para o painel de edição
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/EditAddress.fxml"));

		loader.setRoot(apEditAddress);
		loader.setController(new EditAddressController(this));

		try {
			loader.load();
		} catch (IOException e) {
			System.out.println("erro na abertura do pane atendimento");
			e.printStackTrace();
		}
		// Adiciona o painel de edição ao conteúdo atual
		apContent.getChildren().add(apEditAddress);
		// Define a translação inicial para exibir o painel na tela
		apEditAddress.setTranslateX(400.0);
		TranslateTransition tt = new TranslateTransition(new Duration(300), apEditAddress);

		tt.setToX(0.0);
		tt.play();

	}

	/**
	 * Fecha o painel de edição do endereço. Realiza uma transição para mover o
	 * anchorpane para a direita e depois o remove do conteúdo.
	 */
	public void closeEditAddress() {
		TranslateTransition tt = new TranslateTransition(Duration.millis(300), apEditAddress);

		tt.setToX(400.0);
		tt.setOnFinished(event -> {
			// Remover a tela de edição
			apContent.getChildren().remove(apEditAddress);
		});

		tt.play();
	}

	/**
	 * Abre o painel de edição do endereço. Cria um novo AnchorPane e carrega um
	 * arquivo FXML para exibir o painel de edição. Realiza uma transição de
	 * tradução para exibir o painel na tela.
	 */
	public void openAddAddress() {

		// Cria um novo AnchorPane
		apAddAddress = new AnchorPane();

		// Carrega o arquivo FXML para o painel de edição
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/AddAddress.fxml"));

		loader.setRoot(apAddAddress);
		loader.setController(new AddAddressController(this));

		try {
			loader.load();
		} catch (IOException e) {
			System.out.println("erro na abertura do pane atendimento");
			e.printStackTrace();
		}
		// Adiciona o painel de edição ao conteúdo atual
		apContent.getChildren().add(apAddAddress);
		// Define a translação inicial para exibir o painel na tela
		apAddAddress.setTranslateX(400.0);
		TranslateTransition tt = new TranslateTransition(new Duration(300), apAddAddress);

		tt.setToX(0.0);
		tt.play();

	}

	public void closeAddAddress() {
		TranslateTransition tt = new TranslateTransition(Duration.millis(300), apAddAddress);

		tt.setToX(400.0);
		tt.setOnFinished(event -> {
			// Remover a tela de edição
			apContent.getChildren().remove(apAddAddress);
		});

		tt.play();
	}

	/**
	 * Limpa todos os componentes da interface de edição. Limpa seleções e campos de
	 * texto, além de limpar listas e caixas de combinação.
	 */
	public void clearAllComponents() {
		cbDocType.getSelectionModel().clearSelection();
		tfNumber.clear();
		tfNumberSei.clear();
		obsProcess.clear();
		cbProcess.getSelectionModel().clearSelection();
		cbProcess.setValue(null);

		obsAddress.clear();
		cbAddress.getSelectionModel().clearSelection();
		cbAddress.setValue(null);
		// tfCity.clear();
		// tfCEP.clear();

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

			clearAllComponents();

			DocumentService documentService = new DocumentService(localUrl);

			String keyword = tfSearch.getText();

			List<Documento> documentos = documentService.fetchByParam(keyword);

			// Create a list of Document objects
			obsListDocs.clear();
			obsListDocs.addAll(documentos);
			cbDocType.setValue(obsDocumentTypes.get(0));

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

		String text = tfNumberSei.getText();

		// verjo que este m�todo � desnecess�rio pois o textfield j� est� aceitando
		// apenas n�meros
		Long numberSei = 0L;
		try {
			numberSei = Long.parseLong(text);
			// Use the 'numberSei' integer as needed
		} catch (NumberFormatException e) {
			// Handle the case where the input is not a valid integer
			System.out.println("Número SEI inválido.");
		}

		try {

			DocumentService documentService = new DocumentService(localUrl);

			Processo obsProcessList0 = processCbController.getSelectedObject();
			Anexo obsAttachList0 = attachmentCbController.getSelectedObject();
			
			System.out.println(obsAttachList0.getNumero());
				// Anexar o processo principal (anexo) ao processo
				obsProcessList0.setAnexo(obsAttachList0);
				
			Endereco obsAddressList0 = addressCbController.getSelectedObject();
			Usuario obsUserList0 = userCbController.getSelectedObject();
			Set<Usuario> usuarios = new HashSet<>();
			usuarios.add(obsUserList0);

			Documento requestDocument = new Documento(tfNumber.getText(), // Número
					obsProcessList0, // Processo
					numberSei, // numero sei
					cbDocType.getValue(), // doc Tipo
					obsAddressList0, // endereço
					usuarios // usuário
			);

			ServiceResponse<?> documentoServiceResponse = documentService.save(requestDocument);

			if (documentoServiceResponse.getResponseCode() == 201) {

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Documento salvo com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);
				// Adiciona resposta na tabela
				Documento responseDocumento = new Gson().fromJson((String) documentoServiceResponse.getResponseBody(),
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
			System.out.println("Nenhum documento selecionado para edição.");
			return;
		}

		// Retrieve values from text fields
		String updatedNumero = tfNumber.getText();
		String updatedNumeroSEI = tfNumberSei.getText();

		DocumentoTipo updateDocumentoTipo = cbDocType.getValue();

		// converter para integer
		Long updatedSei = 0L;
		try {
			updatedSei = Long.parseLong(updatedNumeroSEI);
		} catch (NumberFormatException e) {
			// Handle the case where the input is not a valid integer
			System.out.println("Número SEI Inválido.");
			// You may want to display a toast or alert here
			return;
		}

		// Edita objeto com novos valores
		selectedDoc.setDocTipo(updateDocumentoTipo);
		selectedDoc.setDocNumero(updatedNumero);
		selectedDoc.setDocSei(updatedSei);
		selectedDoc.setDocProcesso(obsProcess.get(0));
		/*
		 * selectedDoc.setDocEndereco(new Endereco(obsAddress.get(0).getEndId(),
		 * obsAddress.get(0).getEndLogradouro(), tfCity.getText(), tfCEP.getText()));
		 */

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
	public List<DocumentoTipo> fetchDocumentTypes() {

		try {

			DocumentoTipoService dtService = new DocumentoTipoService(localUrl);

			List<DocumentoTipo> docTypes = dtService.fetchDocumentTypes();

			return docTypes;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
