package controllers.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import controllers.DocumentController;
import controllers.MapController;
import controllers.NavigationController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import enums.StaticData;
import enums.ToastType;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Demanda;
import models.Endereco;
import models.Finalidade;
import models.Interferencia;
import models.SituacaoProcesso;
import models.Subterranea;
import models.SubtipoOutorga;
import models.TipoAto;
import models.TipoInterferencia;
import models.TipoOutorga;
import services.InterferenciaService;
import services.ServiceResponse;
import utilities.JsonConverter;
import utilities.URLUtility;

public class AddInterferenceController implements Initializable {

	private static AddInterferenceController instance;

	public static AddInterferenceController getInstance() {
		return instance;
	}

	@FXML
	private AnchorPane anchorPaneContainer, anchorPaneResizable;

	@FXML
	JFXComboBox<Endereco> cbAddress;

	@FXML
	private JFXComboBox<TipoInterferencia> cbTypeOfInterference;

	@FXML
	private JFXComboBox<TipoOutorga> cbTypeOfGrant;

	@FXML
	private JFXComboBox<SubtipoOutorga> cbSubtypeOfGrant;

	@FXML
	private JFXComboBox<SituacaoProcesso> cbProcessSituation;

	@FXML
	private JFXComboBox<TipoAto> cbTypeOfAct;

	@FXML
	private JFXComboBox<?> cbWatershedBasin;

	@FXML
	private JFXComboBox<?> cbHydrographicUnit;

	@FXML
	private JFXTextField tfLatitude;

	@FXML
	private JFXTextField tfLongitude;

	@FXML
	private FontAwesomeIconView iconMarker;

	@FXML
	private JFXTextField tfSearch;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private JFXButton btnNew;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnUpdate;

	@FXML
	private JFXButton btnDelete;

	@FXML
	private TableView<Interferencia> tableView;
	@FXML
	private TableColumn<Interferencia, String> tcInterferenceType;

	@FXML
	private TableColumn<Interferencia, String> tcAddress;

	@FXML
	private TableColumn<Interferencia, String> tcLatitude;

	@FXML
	private TableColumn<Interferencia, String> tcLongitude;

	@FXML
	private JFXButton btnClose;

	@FXML
	AnchorPane apTypeOfInterference;

	String urlService;
	TranslateTransition ttClose;

	String latitude, longitude;
	AddressComboBoxController addressCbController;
	Endereco address;
	private MapController mapController;

	private DocumentController documentController;

	public AddInterferenceController(DocumentController documentController, Endereco address, String urlService,
			TranslateTransition ttClose) {

		this.documentController = documentController;
		this.address = address;
		this.urlService = URLUtility.getURLService();
		this.ttClose = ttClose;
	}

	ObservableList<TipoInterferencia> obsTypesOfInterferences;// = FXCollections.observableArrayList();
	ObservableList<TipoOutorga> obsTypesOfGrants;
	ObservableList<SubtipoOutorga> obsSubtypesOfGrants;
	ObservableList<SituacaoProcesso> obsProcessSituations;
	ObservableList<TipoAto> obsTypesOfActs;

	ObservableList<Interferencia> obsInterferences = FXCollections.observableArrayList();

	AddSubterraneanDetailsController addSubterraneanDetailsController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		obsTypesOfInterferences = StaticData.INSTANCE.getTypesOfInterferences();
		cbTypeOfInterference.setItems(obsTypesOfInterferences);

		obsTypesOfGrants = StaticData.INSTANCE.getTypesOfGrants();
		cbTypeOfGrant.setItems(obsTypesOfGrants);

		obsSubtypesOfGrants = StaticData.INSTANCE.getSubtypesOfGrants();
		cbSubtypeOfGrant.setItems(obsSubtypesOfGrants);

		obsProcessSituations = StaticData.INSTANCE.getProcessesSituations();
		cbProcessSituation.setItems(obsProcessSituations);

		obsTypesOfActs = StaticData.INSTANCE.getTypesOfActs();
		cbTypeOfAct.setItems(obsTypesOfActs);

		tfLatitude.setText(latitude);
		tfLongitude.setText(longitude);

		addressCbController = new AddressComboBoxController(urlService, cbAddress);

		tcInterferenceType.setCellValueFactory(
				cellData -> cellData.getValue().getProperty(Interferencia::getTipoInterferenciaDescricao));
		tcAddress
				.setCellValueFactory(cellData -> cellData.getValue().getProperty(Interferencia::getEnderecoLogradouro));
		tcLatitude.setCellValueFactory(new PropertyValueFactory<Interferencia, String>("latitude"));
		tcLongitude.setCellValueFactory(new PropertyValueFactory<Interferencia, String>("longitude"));

		tableView.setItems(obsInterferences);

		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newValue) -> {

			if (newValue != null) {

				tfLatitude.setText(newValue.getLatitude().toString());
				tfLongitude.setText(newValue.getLongitude().toString());

				Endereco selecteInterferenceAddres = newValue.getEndereco();
				// Se houver endereço relacionado com a interferência, preencher o combobox do
				// endereço
				if (selecteInterferenceAddres != null) {
					addressCbController.fillAndSelectComboBox(newValue.getEndereco());
				}

				TipoInterferencia ti = newValue.getTipoInterferencia();
				cbTypeOfInterference.getSelectionModel().select(ti);

				TipoOutorga to = newValue.getTipoOutorga();
				cbTypeOfGrant.getSelectionModel().select(to);

				SubtipoOutorga so = newValue.getSubtipoOutorga();
				cbSubtypeOfGrant.getSelectionModel().select(so);

				SituacaoProcesso sp = newValue.getSituacaoProcesso();
				cbProcessSituation.getSelectionModel().select(sp);

				TipoAto ta = newValue.getTipoAto();
				cbTypeOfAct.getSelectionModel().select(ta);

				// Envia as finalidades para o controlador AddSubterraneanDetailsController
				Set<Finalidade> purpouses = newValue.getFinalidades();
				addSubterraneanDetailsController.setPurpouses(purpouses);

				// Preenchimento das demandas buscadas (AddSubterraneanController)
				Set<Demanda> demands = newValue.getDemandas();

				addSubterraneanDetailsController.fillDemandsDetails(demands);

			} else {

				clearAllComponents();

			}
		});

		// Redimensionar o anchor pane interno.
		anchorPaneContainer.widthProperty().addListener((observable, oldValue, newValue) -> {
			anchorPaneResizable.setPrefWidth(newValue.doubleValue() - 20.0);
		});

		// Fechar tela
		btnClose.setOnAction(e -> {

			ttClose.play();

			Interferencia selectedObject = tableView.getSelectionModel().getSelectedItem();
		
			if (this.address == null) {
				// Alerta (Toast) de sucesso na edi��o
				Node source = (Node) e.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Selecione um endereço !!!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			} else {
				this.documentController.receiveAddressAndInterference(this.address, selectedObject);
			}

		});

		// Ao abrir, verifica se object enviado é nulo, se não preenche combobox
		// cbAddress.
		if (address != null) {
			addressCbController.fillAndSelectComboBox(address);
		}

		btnSave.setOnAction(event -> save(event));
		btnUpdate.setOnAction(event -> update(event));
		btnSearch.setOnAction(event -> fetchByKeyword(event));
		btnDelete.setOnAction(event -> delete(event));
		btnNew.setOnAction(event -> clearAllComponents());

		// Adicionar try catch para ver se o usuário digitou algo, se há coordenada, se
		// é válida...

		// Mostra a coordenada no mapa.
		iconMarker.setOnMouseClicked(event -> {

			// Verifica se o valor está vazio.
			if (!tfLatitude.getText().isEmpty() && !tfLongitude.getText().isEmpty()) {

				// ALERTA!! Adicionar verificação se o valor é mesmo double ou float,
				// representando uma coordenada.

				mapController.handleAddMarker(JsonConverter.convertObjectToJson(new Interferencia(
						Double.parseDouble(tfLatitude.getText()), Double.parseDouble(tfLongitude.getText()))));
			} else {
				// Alerta (Toast) de sucesso na edi��o
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Adicione as Coordenadas!!!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			}

		});

		cbTypeOfInterference.setOnAction((event) -> {
			TipoInterferencia item = cbTypeOfInterference.getSelectionModel().getSelectedItem();

			// Limpa antes de adicionar os detalhes de cada tipo de interferência.
			// No momento só há detalhes para a interferência subterrânea.
			apTypeOfInterference.getChildren().clear();

			if (item != null && item.getDescricao() != null && !item.getDescricao().isEmpty()) {
				if (item.getDescricao().equals("Subterrânea")) {
					openTypeOfInterferenceDetails();
				}
			}

			// No action needed for other cases, so the else block is removed
		});
	}

	public void save(ActionEvent event) {

		Endereco address = cbAddress.selectionModelProperty().get().isEmpty() ? null
				: addressCbController.getSelectedObject();

		TipoInterferencia typeOfInterference = cbTypeOfInterference.selectionModelProperty().get().isEmpty() ? null
				: cbTypeOfInterference.getValue();
		TipoOutorga typeOfGrant = cbTypeOfGrant.selectionModelProperty().get().isEmpty() ? null
				: cbTypeOfGrant.getValue();

		SubtipoOutorga subtypeOfGrant = cbSubtypeOfGrant.selectionModelProperty().get().isEmpty() ? null
				: cbSubtypeOfGrant.getValue();

		SituacaoProcesso processSituation = cbProcessSituation.selectionModelProperty().get().isEmpty() ? null
				: cbProcessSituation.getValue();

		TipoAto typeOfAct = cbTypeOfAct.selectionModelProperty().get().isEmpty() ? null : cbTypeOfAct.getValue();

		String latitude = tfLatitude.getText();
		String longitude = tfLongitude.getText();

		Set<Finalidade> purpouses = null;
		Set<Demanda> demands = null;

		Subterranea subterraneanAttributes = null;

		if (addSubterraneanDetailsController != null) {
			purpouses = addSubterraneanDetailsController.getPurpouses();
			demands = addSubterraneanDetailsController.getDemands();

			subterraneanAttributes = addSubterraneanDetailsController.getSubterraneanAttributes();

		}

		// Verifica se o endereço está vazio.
		if (address == null) {

			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Endereço vazio!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
		} else if (latitude.isEmpty() || longitude.isEmpty()) {

			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Preencha a Latitude e Longitude!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

		} // Verifica se o tipo de interferência está vazio.
		else if (typeOfInterference == null) {

			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Tipo de Interferencia vazio!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			// Verifica se o tipo de outorga não foi selecionado
		} else if (typeOfGrant == null) {
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione o Tipo de Outorga!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

		} else if (subtypeOfGrant == null) {
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione o Subtipo de Outorga!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

		} else if (processSituation == null) {
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione a Situação do Processo!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
		} else if (typeOfAct == null) {
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione o Tipo de Ato!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			// É obrigatório o tipo de poço, então o objeto retornará nulo se não escolher
		} else if (subterraneanAttributes == null) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione o Tipo de Poço!!!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
		}

		else {

			try {

				InterferenciaService service = new InterferenciaService(urlService);

				// ADICIOINAR GEOMETRY, BACIA E UNIDADE HIDROGRÁFICA

				Subterranea newInterference = new Subterranea(Double.parseDouble(latitude),
						Double.parseDouble(longitude), address, typeOfInterference, typeOfGrant, subtypeOfGrant,
						processSituation, typeOfAct, purpouses, demands);

				// Atributos da interferência subterrânea
				newInterference.setCaesb(subterraneanAttributes.getCaesb());
				newInterference.setNivelEstatico(subterraneanAttributes.getNivelEstatico());
				newInterference.setNivelDinamico(subterraneanAttributes.getNivelDinamico());
				newInterference.setProfundidade(subterraneanAttributes.getProfundidade());
				newInterference.setVazaoOutorgavel(subterraneanAttributes.getVazaoOutorgavel());
				newInterference.setVazaoSistema(subterraneanAttributes.getVazaoSistema());
				newInterference.setVazaoTeste(subterraneanAttributes.getVazaoTeste());
				newInterference.setTipoPoco(subterraneanAttributes.getTipoPoco());

				ServiceResponse<?> response = service.save(newInterference);

				if (response.getResponseCode() == 201) {

					// Mensagem
					Node source = (Node) event.getSource();
					Stage ownerStage = (Stage) source.getScene().getWindow();
					String toastMsg = "Sucesso!";
					utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

					// Adiciona resposta na tabela
					Subterranea newInterferencia = new Gson().fromJson((String) response.getResponseBody(),
							Subterranea.class);
					// Adiciona com primeiro na lista
					tableView.getItems().add(0, newInterferencia);
					// Seleciona o objeto salvo na table view
					tableView.getSelectionModel().select(newInterferencia);

				} else {
					Node source = (Node) event.getSource();
					Stage ownerStage = (Stage) source.getScene().getWindow();
					String toastMsg = "Erro!";
					utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

				}

			} catch (Exception e) {
				// adicionar Toast de erro
				e.printStackTrace();
			}

		}

	}

	public void update(ActionEvent event) {
		// Get the selected document from the TableView
		Interferencia selectedObject = tableView.getSelectionModel().getSelectedItem();

		if (selectedObject == null) {
			// Display an alert or toast message indicating that no document is selected
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Nenhum objeto selecionado!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			return;
		}

		TipoOutorga typeOfGrant = cbTypeOfGrant.selectionModelProperty().get().isEmpty() ? null
				: cbTypeOfGrant.getValue();

		SubtipoOutorga subtypeOfGrant = cbSubtypeOfGrant.selectionModelProperty().get().isEmpty() ? null
				: cbSubtypeOfGrant.getValue();

		SituacaoProcesso processSituation = cbProcessSituation.selectionModelProperty().get().isEmpty() ? null
				: cbProcessSituation.getValue();

		TipoAto typeOfAct = cbTypeOfAct.selectionModelProperty().get().isEmpty() ? null : cbTypeOfAct.getValue();

		String latitude = tfLatitude.getText();
		String longitude = tfLongitude.getText();

		// Edita objeto com novos valores
		selectedObject.setLatitude(Double.parseDouble(latitude));
		selectedObject.setLongitude(Double.parseDouble(longitude));
		selectedObject.setEndereco(addressCbController.getSelectedObject());
		// Não é para permitir a edição do tipo de interferência
		// selectedObject.setTipoInterferencia(cbTypeOfInterference.getValue());
		selectedObject.setTipoOutorga(typeOfGrant);
		selectedObject.setSubtipoOutorga(subtypeOfGrant);
		selectedObject.setSituacaoProcesso(processSituation);
		selectedObject.setTipoAto(typeOfAct);

		try {
			InterferenciaService service = new InterferenciaService(urlService);

			// Requisicao de edicao e reposta
			ServiceResponse<?> response = service.update(selectedObject);

			if (response.getResponseCode() == 200) {
				// Alerta (Toast) de sucesso na edi��o
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

				tableView.getItems().remove(selectedObject);
				// Converte objeto editado para Json
				Interferencia jsonObject = new Gson().fromJson((String) response.getResponseBody(),
						Interferencia.class);
				// Adiciona objeto editado como primeiro �tem ma fila na table view
				tableView.getItems().add(0, jsonObject);
				// Seleciona o objeto editado na table view
				tableView.getSelectionModel().select(jsonObject);

			} else {
				// Display an error toast or alert
				// System.out.println(serviceResponse.getResponseCode());
			}

		} catch (Exception e) {
			// Display an error toast or alert
			e.printStackTrace();
		}
	}

	public void fetchByKeyword(ActionEvent event) {

		try {

			clearAllComponents();

			InterferenciaService service = new InterferenciaService(urlService);

			String keyword = tfSearch.getText();

			Set<Interferencia> list = service.fetchByKeyword(keyword);

			// Create a list of Document objects
			obsInterferences.clear();

			obsInterferences.addAll(list);
			// cbDocType.setValue(obsDocumentTypes.get(0));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(ActionEvent event) {

		Interferencia selected = tableView.getSelectionModel().getSelectedItem();

		try {
			InterferenciaService service = new InterferenciaService(urlService);

			// É preciso informar o tipo de
			ServiceResponse<?> response = service.deleteById(selected.getId(), selected.getTipoInterferencia().getId());

			if (response.getResponseCode() == 200) {

				// Informa sucesso em deletar
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Ojeto deletado com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

				// retira objecto da tabela de documentos tvDocs
				tableView.getItems().remove(selected);

			} else {
				// Informa erro em deletar
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Erro ao deletar!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void clearAllComponents() {
		// Limpa o combobox no controlador específico.
		addressCbController.clearComponent();
		// Limpa os componentes deste controlador.
		tfLatitude.clear();
		tfLongitude.clear();
		cbTypeOfInterference.getSelectionModel().clearSelection();

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

	/**
	 * Atualiza as coordenadas dos TextFields de acordo com o clique no mapa.
	 * 
	 *  interferencia
	 */
	public void updateCoordinates(Interferencia interferencia) {

		tfLatitude.setText(String.valueOf(interferencia.getLatitude()));
		tfLongitude.setText(String.valueOf(interferencia.getLongitude()));

	}

	private AnchorPane loadTypeOfInterferenceDetails() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/AddSubterraneanDetails.fxml"));
			// Setting the custom controller factory for NavigationController
			loader.setControllerFactory(controllerClass -> {
				if (controllerClass == NavigationController.class) {
					addSubterraneanDetailsController = new AddSubterraneanDetailsController();
					return addSubterraneanDetailsController;
				} else {
					try {
						addSubterraneanDetailsController = (AddSubterraneanDetailsController) controllerClass
								.getDeclaredConstructor().newInstance();
						return addSubterraneanDetailsController;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});
			return loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void openTypeOfInterferenceDetails() {
		AnchorPane anchorPane = loadTypeOfInterferenceDetails();
		if (anchorPane != null) {
			apTypeOfInterference.getChildren().add(anchorPane);
			AnchorPane.setLeftAnchor(anchorPane, 10.0);
			AnchorPane.setRightAnchor(anchorPane, 10.0);
		}
	}

}