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
import controllers.views.AddAttachmentController;
import controllers.views.AddInterferenceController;
import controllers.views.AddProcessController;
import controllers.views.AddUserController;
import controllers.views.AddressComboBoxController;
import controllers.views.AttachmentComboBoxController;
import controllers.views.DocumentViewController;
import controllers.views.EditAddressController;
import controllers.views.InterferenceTextFieldsController;
import controllers.views.ProcessComboBoxController;
import controllers.views.UserComboBoxController;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Anexo;
import models.Documento;
import models.DocumentoTipo;
import models.Endereco;
import models.Interferencia;
import models.Processo;
import models.Template;
import models.Usuario;
import services.DocumentService;
import services.DocumentoTipoService;
import services.ServiceResponse;
import utilities.MapListener;
import utilities.URLUtility;


/**
 * Controlador para lidar com operações relacionadas aos documentos.
 */
public class DocumentController implements Initializable {

	@FXML
	private ComboBox<Documento> cbDocument;

	private String urlService;

	public DocumentController() {
		this.urlService = URLUtility.getURLService();
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
	ObservableList<Anexo> obsAttachment = FXCollections.observableArrayList();

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
	private JFXButton btnViews;

	@FXML
	private Button btnInterference, btnAddress, btnProcess, btnAttachment, btnUser;

	@FXML
	private MainController mainController;

	AnchorPane apEditAddress, apAddAddress, apAddInterference;

	public Endereco getDocAddress() {
		// Get the selected document from the TableView
		Endereco address = tvDocs.getSelectionModel().getSelectedItem().getEndereco();

		return address;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	private AddressComboBoxController addressCbController;
	private ProcessComboBoxController processCbController;
	private AttachmentComboBoxController attachmentCbController;
	private UserComboBoxController userCbController;
	private InterferenceTextFieldsController interferenceTFController;

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

		tcTipo.setCellValueFactory(cellData -> cellData.getValue().getProperty(Documento::getTipoDescricao));
		tcNum.setCellValueFactory(new PropertyValueFactory<Documento, String>("numero"));
		tcNumSei.setCellValueFactory(new PropertyValueFactory<Documento, String>("numeroSei"));
		tcProc.setCellValueFactory(cellData -> cellData.getValue().getProperty(Documento::getProcessoNumero));
		tcAddress.setCellValueFactory(cellData -> cellData.getValue().getProperty(Documento::getEnderecoLogradouro));

		
		AnchorPane.setRightAnchor(apContent, 0.0);
		AnchorPane.setLeftAnchor(apContent, 50.0);

		tvDocs.setItems(obsListDocs);

		obsDocumentTypes = FXCollections.observableArrayList(fetchDocumentTypes());

		cbDocType.setItems(obsDocumentTypes);
		cbDocType.setValue(null);

		tvDocs.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			if (newSelection != null) {
				// Atualiza ComboBox (Tipo de Documento) a partir do documento selecionado
				cbDocType.getSelectionModel().select(newSelection.getTipoDocumento());
				// Atualizar componentes de acordo com o documento selecionado
				tfNumber.setText(newSelection.getNumero());
				tfNumberSei.setText(String.valueOf(newSelection.getNumeroSei()));

				Processo processo = newSelection.getProcesso();
				// Adiciona o objeto à lista para não precisar buscar no banco de dados.
				if (processo != null) {
					processCbController.addItemToDbObjects(processo);

				}
				cbProcess.getSelectionModel().select(processo);

				if (processo.getAnexo() != null) {
					Anexo anexo = processo.getAnexo();

					cbAttachment.getSelectionModel().select(anexo);
				}

				Endereco endereco = newSelection.getEndereco();
				// Adiciona o objeto à lista para não precisar buscar no banco de dados.
				if (endereco != null) {
					addressCbController.addItemToDbObjects(endereco);

				}
				//cbAddress.getSelectionModel().select(endereco);
				addressCbController.fillAndSelectComboBox(endereco);

				// Limpar componentes que não são preenchidos.

				cbAttachment.getSelectionModel().clearSelection();
				cbUser.getSelectionModel().clearSelection();
				tfLatitude.clear();
				tfLongitude.clear();

			} else {
				clearAllComponents();
			}
		});

		// Listener do TextField
		tfNumber.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// /System.out.println("TextField changed from: " + oldValue + " to: " +
				// newValue);
			}
		});
		// Listener do TextField
		tfNumberSei.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// /System.out.println("TextField changed from: " + oldValue + " to: " +
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

		addressCbController = new AddressComboBoxController(urlService, cbAddress);
		processCbController = new ProcessComboBoxController(urlService, cbProcess);
		attachmentCbController = new AttachmentComboBoxController(urlService, cbAttachment);
		interferenceTFController = new InterferenceTextFieldsController(urlService, tfLatitude, tfLongitude);
		userCbController = new UserComboBoxController(urlService, cbUser);

		btnNew.setOnAction(e -> clearAllComponents());
		btnViews.setOnAction(event -> showDocumentView());
		btnSave.setOnAction(event -> saveDocument(event));
		btnSearch.setOnAction(event -> searchDocument(event));
		btnDelete.setOnAction(event -> deleteDocument(event));
		btnEdit.setOnAction(event -> editDocument(event));

		btnAddress.setOnMouseClicked(event -> openAnchorPaneAddress());
		btnInterference.setOnMouseClicked(event -> openAddInterference());
		btnProcess.setOnMouseClicked(event -> openAddProcess());
		btnAttachment.setOnMouseClicked(event -> openAnchorPaneAttrachment());
		btnUser.setOnMouseClicked(event -> openAnchorPaneUser());

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

	public void openAddInterference() {

		// Cria um novo AnchorPane
		apAddInterference = new AnchorPane();

		// Carrega o arquivo FXML para o painel de edição
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/AddInterference.fxml"));
		
		TranslateTransition ttClose = new TranslateTransition(Duration.millis(300), apAddInterference);

		ttClose.setToX(400.0);
		ttClose.setOnFinished(event -> {
			// Remover a tela de edição
			apContent.getChildren().remove(apAddInterference);
		});

		loader.setRoot(apAddInterference);

		// Captura o endereço selecionado
		Endereco address = cbAddress.selectionModelProperty().get().isEmpty() ? null : addressCbController.getSelectedObject();
	
		if (address == null) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = cbAddress; // The source is tfPurpouse (JFXTextField)
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um endereço !!!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
		} else {
			
			MapController mapController = this.mainController.getMapController();
			
			AddInterferenceController addInterferenceController = new AddInterferenceController(this, address, this.urlService, ttClose);
			
			mapController.addMapClickListener(addInterferenceController);
			addInterferenceController.setTextFieldsListener(mapController);

			loader.setController(addInterferenceController);

			try {
				loader.load();
			} catch (IOException e) {
				System.out.println("erro na abertura do pane atendimento");
				e.printStackTrace();
			}
			// Adiciona o painel de edição ao conteúdo atual
			apContent.getChildren().add(apAddInterference);
			// Define a translação inicial para exibir o painel na tela
			apAddInterference.setTranslateX(400.0);
			TranslateTransition tt = new TranslateTransition(new Duration(300), apAddInterference);

			tt.setToX(0.0);
			tt.play();

		}

	}
	/**
	 * Captura a latitude para envio para a tela AddInterferenceControler
	 * @return String
	 */
	public String getLatitude() {
	    Double latitude = interferenceTFController.getLatLng().getLatitude();
	    return latitude != null ? String.valueOf(latitude) : "";
	}
	/**
	 * Captura a longitude para envio para a tela AddInterferenceControler
	 * @return String
	 */
	public String getLongitude() {
	    Double longitude = interferenceTFController.getLatLng().getLongitude();
	    return longitude != null ? String.valueOf(longitude) : "";
	}

	public void openAddProcess() {
		// Cria um novo AnchorPane
		AnchorPane apAddProcess = new AnchorPane();

		// Carrega o arquivo FXML para o painel de adição de processo
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/AddProcess.fxml"));

		// Configuração da transição de animação
		TranslateTransition ttClose = new TranslateTransition(Duration.millis(300), apAddProcess);
		ttClose.setToX(400.0);
		ttClose.setOnFinished(event -> {
			// Remove o painel de adição de processo após a animação
			apContent.getChildren().remove(apAddProcess);
		});

		// Configura o loader FXML
		loader.setRoot(apAddProcess);

		Processo object = cbProcess.selectionModelProperty().get().isEmpty() ? null : cbProcess.getItems().get(0);

		loader.setController(new AddProcessController(this, object, urlService, ttClose));

		try {
			// Carrega o FXML e configura o controle
			loader.load();
		} catch (IOException e) {
			System.out.println("Erro na abertura do painel de adição de processo");
			e.printStackTrace();
		}

		// Adiciona o painel de adição de processo ao conteúdo atual
		apContent.getChildren().add(apAddProcess);

		AnchorPane.setTopAnchor(apAddProcess, 5.0);
		AnchorPane.setBottomAnchor(apAddProcess, 5.0);
		AnchorPane.setLeftAnchor(apAddProcess, 5.0);
		AnchorPane.setRightAnchor(apAddProcess, 5.0);

		// Define a translação inicial para exibir o painel na tela
		apAddProcess.setTranslateX(400.0);
		TranslateTransition tt = new TranslateTransition(new Duration(300), apAddProcess);
		tt.setToX(0.0);
		tt.play();
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

	public void fillAndSelectComboBoxAttachment(Anexo object) {
		ObservableList<Anexo> newObs = FXCollections.observableArrayList();
		cbAttachment.setItems(newObs);

		newObs.add(0, object);

		// Atualizando o ComboBox para refletir a mudança
		// cbProcess.setItems(null);
		cbAttachment.setItems(newObs);

		// Selecionando o novo item no ComboBox
		cbAttachment.getSelectionModel().select(0);
	}

	public void openAnchorPaneUser() {
		// Cria um novo AnchorPane
		AnchorPane anchorPaneUser = new AnchorPane();

		// Carrega o arquivo FXML para o painel de adição de processo
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/AddUser.fxml"));

		// Configuração da transição de animação
		TranslateTransition ttClose = new TranslateTransition(Duration.millis(300), anchorPaneUser);
		ttClose.setToX(400.0);
		ttClose.setOnFinished(event -> {
			// Remove o painel de adição de processo após a animação
			apContent.getChildren().remove(anchorPaneUser);
		});

		// Configura o loader FXML
		loader.setRoot(anchorPaneUser);

		Usuario object = cbUser.selectionModelProperty().get().isEmpty() ? null : userCbController.getSelectedObject();

		loader.setController(new AddUserController(this, object, urlService, ttClose));

		try {
			// Carrega o FXML e configura o controle
			loader.load();
		} catch (IOException e) {
			System.out.println("Erro na abertura do painel ");
			e.printStackTrace();
		}

		// Adiciona o painel de adição de processo ao conteúdo atual
		apContent.getChildren().add(anchorPaneUser);

		AnchorPane.setTopAnchor(anchorPaneUser, 5.0);
		AnchorPane.setBottomAnchor(anchorPaneUser, 5.0);
		AnchorPane.setLeftAnchor(anchorPaneUser, 5.0);
		AnchorPane.setRightAnchor(anchorPaneUser, 5.0);

		// Define a translação inicial para exibir o painel na tela
		anchorPaneUser.setTranslateX(400.0);
		TranslateTransition tt = new TranslateTransition(new Duration(300), anchorPaneUser);
		tt.setToX(0.0);
		tt.play();
	}

	public void openAnchorPaneAddress() {
		// Cria um novo AnchorPane
		AnchorPane anchorPaneAddress = new AnchorPane();

		// Carrega o arquivo FXML para o painel de adição de processo
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/AddAddress.fxml"));

		// Configuração da transição de animação
		TranslateTransition ttClose = new TranslateTransition(Duration.millis(300), anchorPaneAddress);
		ttClose.setToX(400.0);
		ttClose.setOnFinished(event -> {
			// Remove o painel de adição de processo após a animação
			apContent.getChildren().remove(anchorPaneAddress);
		});

		// Configura o loader FXML
		loader.setRoot(anchorPaneAddress);

		Endereco object = cbAddress.selectionModelProperty().get().isEmpty() ? null : cbAddress.getItems().get(0);

		loader.setController(new AddAddressController(this, object, urlService, ttClose));

		try {
			// Carrega o FXML e configura o controle
			loader.load();
		} catch (IOException e) {
			System.out.println("Erro na abertura do painel ");
			e.printStackTrace();
		}

		// Adiciona o painel de adição de processo ao conteúdo atual
		apContent.getChildren().add(anchorPaneAddress);

		AnchorPane.setTopAnchor(anchorPaneAddress, 5.0);
		AnchorPane.setBottomAnchor(anchorPaneAddress, 5.0);
		AnchorPane.setLeftAnchor(anchorPaneAddress, 5.0);
		AnchorPane.setRightAnchor(anchorPaneAddress, 5.0);

		// Define a translação inicial para exibir o painel na tela
		anchorPaneAddress.setTranslateX(400.0);
		TranslateTransition tt = new TranslateTransition(new Duration(300), anchorPaneAddress);
		tt.setToX(0.0);
		tt.play();
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

	public void receiveAddressAndInterference(Endereco address, Interferencia interference) {

		ObservableList<Endereco> newObsList = FXCollections.observableArrayList();
		cbAddress.setItems(newObsList);

		// Adiciona a interferência no endereço
		Set<Interferencia> interferencies = new HashSet<>();
		interferencies.add(interference);
		address.setInterferencias(interferencies);

		newObsList.add(0, address);

		// Atualizando o ComboBox para refletir a mudança
		// cbProcess.setItems(null);
		cbAddress.setItems(newObsList);

		// Seleciona o novo item no ComboBox
		cbAddress.getSelectionModel().select(0);

		// Preenche os textfields com a interferência enviada
		if (interference != null) {
			tfLatitude.setText(interference.getLatitude().toString());
			tfLongitude.setText(interference.getLongitude().toString());
		}

	}

	public void fillAndSelectComboBoxUser(Usuario object) {
		ObservableList<Usuario> newObs = FXCollections.observableArrayList();
		cbUser.setItems(newObs);

		newObs.add(0, object);

		// Atualizando o ComboBox para refletir a mudança
		// cbProcess.setItems(null);
		cbUser.setItems(newObs);

		// Selecionando o novo item no ComboBox
		cbUser.getSelectionModel().select(0);
	}

	public void openAnchorPaneAttrachment() {
		// Cria um novo AnchorPane
		AnchorPane anchorPaneAttachment = new AnchorPane();

		// Carrega o arquivo FXML para o painel de adição de processo
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/AddAttachment.fxml"));

		// Configuração da transição de animação
		TranslateTransition ttClose = new TranslateTransition(Duration.millis(300), anchorPaneAttachment);
		ttClose.setToX(400.0);
		ttClose.setOnFinished(event -> {
			// Remove o painel de adição de processo após a animação
			apContent.getChildren().remove(anchorPaneAttachment);
		});

		// Configura o loader FXML
		loader.setRoot(anchorPaneAttachment);

		Anexo object = cbAttachment.selectionModelProperty().get().isEmpty() ? null : cbAttachment.getItems().get(0);

		loader.setController(new AddAttachmentController(this, object, urlService, ttClose));

		try {
			// Carrega o FXML e configura o controle
			loader.load();
		} catch (IOException e) {
			System.out.println("Erro na abertura do painel de adição de processo");
			e.printStackTrace();
		}

		// Adiciona o painel de adição de processo ao conteúdo atual
		apContent.getChildren().add(anchorPaneAttachment);

		AnchorPane.setTopAnchor(anchorPaneAttachment, 5.0);
		AnchorPane.setBottomAnchor(anchorPaneAttachment, 5.0);
		AnchorPane.setLeftAnchor(anchorPaneAttachment, 5.0);
		AnchorPane.setRightAnchor(anchorPaneAttachment, 5.0);

		// Define a translação inicial para exibir o painel na tela
		anchorPaneAttachment.setTranslateX(400.0);
		TranslateTransition tt = new TranslateTransition(new Duration(300), anchorPaneAttachment);
		tt.setToX(0.0);
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

		cbUser.getSelectionModel().clearSelection();
		cbUser.setValue(null);

		obsAddress.clear();
		cbAddress.getSelectionModel().clearSelection();
		cbAddress.setValue(null);

		obsAttachment.clear();
		cbAttachment.getSelectionModel().clearSelection();
		cbAttachment.setValue(null);

		tfLatitude.clear();
		tfLongitude.clear();

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

	Set<Template> templates = new HashSet<>();

	public void showDocumentView() {
		try {
			Documento selectedDocument = tvDocs.getSelectionModel().getSelectedItem();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DocumentView.fxml"));
			DocumentViewController docViewController = new DocumentViewController(selectedDocument, templates);
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
	public void searchDocument(ActionEvent event) {

		try {

			clearAllComponents();

			DocumentService documentService = new DocumentService(urlService);

			String keyword = tfSearch.getText();

			Set<Documento> documentos = documentService.fetchByParam(keyword);

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
	public void saveDocument(ActionEvent event) {

		DocumentoTipo updateDocumentoTipo = cbDocType.getValue();

		if (updateDocumentoTipo == null) {

			// Alerta (Toast) de sucesso na edição
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um tipo de documento!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			// Para terminar o processo
			return;
		}

		// Retrieve values from text fields
		String toUpdateNumber = tfNumber.getText();
		String toUpdateNumberSei = tfNumberSei.getText();

		// converter para integer
		Long toUpdateNumberSeiLong = 0L;

		if (toUpdateNumber == null || toUpdateNumber.isEmpty()) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Insira o número do documento!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			return;

		}

		if (toUpdateNumberSei == null || toUpdateNumberSei.isEmpty()) {

			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Insira o número do documento Sei!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			return;
		}

		try {
			toUpdateNumberSeiLong = Long.parseLong(toUpdateNumberSei);
		} catch (NumberFormatException e) {

			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Número do Sei Inválido!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			// Para terminar o processo
			return;
		}

		Endereco selectedAddress = addressCbController.getSelectedObject();

		if (selectedAddress == null) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um endereço!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			// Para terminar o processo
			return;
		}

		Processo selectedProcess = processCbController.getSelectedObject();

		if (selectedProcess == null) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um processo!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			// Para terminar o processo
			return;
		}

		Anexo selectedAttachment = attachmentCbController.getSelectedObject();

		if (selectedAttachment == null) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um processo anexo!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			// Para terminar o processo
			return;
		}

		selectedProcess.setAnexo(selectedAttachment);

		Usuario selectedUser = userCbController.getSelectedObject();

		if (selectedUser == null) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um usuário!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			// Para terminar o processo
			return;
		}

		Set<Usuario> usuarios = new HashSet<>();
		usuarios.add(selectedUser);

		try {

			DocumentService documentService = new DocumentService(urlService);

			DocumentoTipo docType = cbDocType.getValue();

			Documento newDocument = new Documento();
			newDocument.setTipoDocumento(docType);
			newDocument.setNumero(tfNumber.getText());
			newDocument.setNumeroSei(toUpdateNumberSeiLong);
			newDocument.setEndereco(selectedAddress);
			newDocument.setProcesso(selectedProcess);

			newDocument.setUsuarios(usuarios);

			ServiceResponse<?> documentoServiceResponse = documentService.save(newDocument);

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
				// /System.out.println(serviceResponse.getResponseCode());
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
	public void editDocument(ActionEvent event) {
		// Get the selected document from the TableView
		Documento selectedDocument = tvDocs.getSelectionModel().getSelectedItem();

		if (selectedDocument == null) {

			// Alerta (Toast) de sucesso na edição
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um documento!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			// Para terminar o processo
			return;

		}

		DocumentoTipo updateDocumentoTipo = cbDocType.getValue();

		if (updateDocumentoTipo == null) {

			// Alerta (Toast) de sucesso na edição
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um tipo de documento!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			// Para terminar o processo
			return;
		}

		// Retrieve values from text fields
		String toUpdateNumber = tfNumber.getText();
		String toUpdateNumberSei = tfNumberSei.getText();

		// converter para integer
		Long toUpdateNumberSeiLong = 0L;

		if (toUpdateNumber == null || toUpdateNumber.isEmpty()) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Insira o número do documento!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			return;

		}

		if (toUpdateNumberSei == null || toUpdateNumberSei.isEmpty()) {

			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Insira o número do documento Sei!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			return;
		}

		try {
			toUpdateNumberSeiLong = Long.parseLong(toUpdateNumberSei);
		} catch (NumberFormatException e) {

			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Número do Sei Inválido!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			// Para terminar o processo
			return;
		}

		Endereco selectedAddress = addressCbController.getSelectedObject();

		if (selectedAddress == null) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um endereço!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			// Para terminar o processo
			return;
		}

		selectedDocument.setEndereco(selectedAddress);

		// Edita objeto com novos valores
		selectedDocument.setTipoDocumento(updateDocumentoTipo);
		selectedDocument.setNumero(toUpdateNumber);
		selectedDocument.setNumeroSei(toUpdateNumberSeiLong);

		Processo selectedProcess = processCbController.getSelectedObject();

		if (selectedProcess == null) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um processo!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			// Para terminar o processo
			return;
		}

		selectedDocument.setProcesso(selectedProcess);

		Anexo selectedAttachment = attachmentCbController.getSelectedObject();

		if (selectedAttachment == null) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um processo anexo!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			// Para terminar o processo
			return;
		}

		selectedProcess.setAnexo(selectedAttachment);

		Usuario selectedUser = userCbController.getSelectedObject();

		if (selectedUser == null) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um usuário!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			// Para terminar o processo
			return;
		}

		Set<Usuario> usuarios = new HashSet<>();
		usuarios.add(selectedUser);

		selectedDocument.setUsuarios(usuarios);

		try {
			DocumentService service = new DocumentService(urlService);

			// Requisi��o de resposta de edi��o
			ServiceResponse<?> serviceResponse = service.update(selectedDocument);

			if (serviceResponse.getResponseCode() == 200) {
				// Alerta (Toast) de sucesso na edi��o
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Documento editado com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

				tvDocs.getItems().remove(selectedDocument);
				// Converte objeto editado para Json
				Documento responseDocumento = new Gson().fromJson((String) serviceResponse.getResponseBody(),
						Documento.class);
				// Adiciona objeto editado como primeiro �tem ma fila na table view
				tvDocs.getItems().add(0, responseDocumento);
				// Seleciona o objeto editado na table view
				tvDocs.getSelectionModel().select(responseDocumento);

			} else {
				// Alerta (Toast) de sucesso na edi��o
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Erro ao editar objeto!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

				// Para terminar o processo
				return;
			}

		} catch (Exception e) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Erro! \n" + e.getMessage();
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Manipula a ação de deletar um documento existente.
	 *
	 * @param event
	 *            O evento de ação associado à exclusão do documento.
	 */
	public void deleteDocument(ActionEvent event) {

		Documento selectedDocument = tvDocs.getSelectionModel().getSelectedItem();

		try {
			DocumentService documentService = new DocumentService(urlService);

			ServiceResponse<?> serviceResponse = documentService.deleteById(selectedDocument.getId());

			if (serviceResponse.getResponseCode() == 200) {

				// Informa sucesso em deletar
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Documento deletado com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

				// retira objecto da tabela de documentos tvDocs
				tvDocs.getItems().remove(selectedDocument);

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

			DocumentoTipoService dtService = new DocumentoTipoService(urlService);

			List<DocumentoTipo> docTypes = dtService.fetchDocumentTypes();

			return docTypes;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public MapListener getLatLngController() {
		return this.interferenceTFController;
	}

}