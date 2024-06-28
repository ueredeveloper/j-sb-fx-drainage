package controllers.views;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import controllers.MapController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import enums.StaticData;
import enums.ToastType;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Endereco;
import models.Interferencia;
import models.InterferenciaTipo;
import models.Subterranea;
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
	private JFXTextField tfLatitude;

	@FXML
	private JFXTextField tfLongitude;

	@FXML
	private FontAwesomeIconView iconMarker;

	@FXML
	private JFXComboBox<InterferenciaTipo> cbInterferenceType;

	@FXML
	private JFXComboBox<?> cbGrant;

	@FXML
	private JFXComboBox<?> cbGrantSubtype;

	@FXML
	private JFXComboBox<?> cbSituation;

	@FXML
	private JFXComboBox<?> cbActType;

	@FXML
	private JFXComboBox<?> cbWatershedBasin;

	@FXML
	private JFXComboBox<?> cbHydrographicUnit;

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

	String urlService;
	TranslateTransition ttClose;

	String latitude, longitude;
	AddressComboBoxController addressCbController;
	Endereco object;
	private MapController mapController;

	public AddInterferenceController(String urlService, TranslateTransition ttClose, Endereco object, String latitude,
			String longitude) {

		this.urlService = URLUtility.getURLService();
		this.ttClose = ttClose;
		this.latitude = latitude;
		this.longitude = longitude;
		this.object = object;
		this.mapController = MapController.getInstance();
	}

	ObservableList<InterferenciaTipo> obsListInterType;// = FXCollections.observableArrayList();
	ObservableList<Interferencia> obsListInterference = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		obsListInterType = StaticData.INSTANCE.getInterferenciaTipo();
		
		cbInterferenceType.setItems(obsListInterType);
	
		tfLatitude.setText(latitude);
		tfLongitude.setText(longitude);

		addressCbController = new AddressComboBoxController(urlService, cbAddress);

		tcInterferenceType.setCellValueFactory(
				cellData -> cellData.getValue().getProperty(Interferencia::getInterferenciaTipoDescricao));
		tcAddress
				.setCellValueFactory(cellData -> cellData.getValue().getProperty(Interferencia::getEnderecoLogradouro));
		tcLatitude.setCellValueFactory(new PropertyValueFactory<Interferencia, String>("interLatitude"));
		tcLongitude.setCellValueFactory(new PropertyValueFactory<Interferencia, String>("interLongitude"));

		tableView.setItems(obsListInterference);

		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newValue) -> {

			if (newValue != null) {

				tfLatitude.setText(newValue.getInterLatitude().toString());
				tfLongitude.setText(newValue.getInterLongitude().toString());
				
				cbAddress.getSelectionModel().select(newValue.getInterEndereco());
				
				 InterferenciaTipo selectedType = newValue.getInterferenciaTipo();
			        cbInterferenceType.getSelectionModel().select(selectedType);

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

			// updateComboBox(selectedEndereco);

		});

		// Ao abrir, verifica se object enviado é nulo, se não preenche combobox
		// cbAddress.
		if (object != null) {
			addressCbController.fillAndSelectComboBox(object);
		}

		btnSave.setOnAction(event -> save(event));
		btnUpdate.setOnAction(event -> update(event));
		btnSearch.setOnAction(event -> fetchByKeyword(event));

		// Adicionar try catch para ver se o usuário digitou algo, se há coordenada, se
		// é válida...

		// Mostra a coordenada no mapa.
		iconMarker.setOnMouseClicked(event -> {
			mapController.handleAddMarker(JsonConverter.convertObjectToJson(new Interferencia(
					Double.parseDouble(tfLatitude.getText()), Double.parseDouble(tfLongitude.getText()))));
		});

	}

	public void save(ActionEvent event) {

		Endereco endereco = cbAddress.selectionModelProperty().get().isEmpty() ? null
				: addressCbController.getSelectedObject();

		// Verifica se o endereço está vazio.
		if (endereco == null) {

			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Endereço vazio!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
		} else {

			String latitude = tfLatitude.getText();
			String longitude = tfLongitude.getText();
			InterferenciaTipo interferenciaTipo = cbInterferenceType.selectionModelProperty().get().isEmpty() ? null
					: cbInterferenceType.getValue();

			// Verifica se o tipo de interferência está vazio.
			if (interferenciaTipo == null) {

				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Tipo de Interferencia vazio!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			} else {

				try {

					// DocumentService documentService = new DocumentService(localUrl);
					InterferenciaService service = new InterferenciaService(urlService);

					Subterranea newInterference = new Subterranea(Double.parseDouble(latitude),
							Double.parseDouble(longitude), endereco, interferenciaTipo);

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

		// Retrieve values from text fields
		String latitude = tfLatitude.getText();
		String longitude = tfLongitude.getText();

	

		// Edita objeto com novos valores
		selectedObject.setInterLatitude(Double.parseDouble(latitude));
		selectedObject.setInterLongitude(Double.parseDouble(longitude));
		selectedObject.setInterEndereco(addressCbController.getSelectedObject());
		selectedObject.setInterferenciaTipo(cbInterferenceType.getValue());

		try {
			InterferenciaService service = new InterferenciaService(urlService);

			// Requisi��o de resposta de edi��o
			ServiceResponse<?> response = service.update(selectedObject);

			if (response.getResponseCode() == 200) {
				// Alerta (Toast) de sucesso na edi��o
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

				tableView.getItems().remove(selectedObject);
				// Converte objeto editado para Json
				Interferencia jsonObject = new Gson().fromJson((String) response.getResponseBody(), Interferencia.class);
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

			List<Interferencia> list = service.fetchByKeyword(keyword);

			// Create a Gson instance
			Gson gson = new Gson();

			// Define the Type for the list
			Type listType = new TypeToken<List<Interferencia>>() {
			}.getType();

			// Convert the list to JSON
			String json = gson.toJson(list, listType);

			// Print the JSON string
			System.out.println(json);

			// Create a list of Document objects
			obsListInterference.clear();
			obsListInterference.addAll(list);
			// cbDocType.setValue(obsDocumentTypes.get(0));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearAllComponents() {
		cbAddress.getSelectionModel().clearSelection();
		tfLatitude.clear();
		tfLongitude.clear();
		cbInterferenceType.getSelectionModel().clearSelection();

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
	 * @param interferencia
	 */
	public void updateCoordinates(Interferencia interferencia) {

		tfLatitude.setText(String.valueOf(interferencia.getInterLatitude()));
		tfLongitude.setText(String.valueOf(interferencia.getInterLongitude()));

	}
}
