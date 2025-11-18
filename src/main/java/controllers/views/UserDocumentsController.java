package controllers.views;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import enums.ToastType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.ApiResponse;
import models.Documento;
import models.Usuario;
import services.DocumentoService;
import services.ServiceResponse;
import utilities.URLUtility;

public class UserDocumentsController implements Initializable {

	@FXML
	private TableView<Documento> tableView;

	@FXML
	private TableColumn<Documento, String> tcNumber;

	@FXML
	private TableColumn<Documento, String> tcNumberSei;

	@FXML
	private TableColumn<Documento, String> tcActions;

	ObservableList<Documento> obsList = FXCollections.observableArrayList();

	AddUserController addUserController;

	String urlService;

	public UserDocumentsController(AddUserController addUserController) {
		this.addUserController = addUserController;
		this.urlService = URLUtility.getURLService();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		System.out.println("clear table view document relation with user");
		tableView.setItems(obsList);
		tcNumber.setCellValueFactory(new PropertyValueFactory<Documento, String>("numero"));
		tcNumberSei.setCellValueFactory(new PropertyValueFactory<Documento, String>("numeroSei"));

		// Define the cell factory for the tcActions column
		tcActions.setCellFactory(new Callback<TableColumn<Documento, String>, TableCell<Documento, String>>() {
			@Override
			public TableCell<Documento, String> call(TableColumn<Documento, String> param) {
				return new TableCell<Documento, String>() {
					private final Button actionButton = new Button("Deletar Relacionamento");

					{
						// Add event handler for the button
						actionButton.setOnAction(event -> {
							Documento document = getTableView().getItems().get(getIndex());
							handleAction(document, user);
						});
					}

					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);

						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(actionButton);
						}
					}
				};
			}
		});
	}

	private void handleAction(Documento document, Usuario user) {

		/// System.out.println(document.getId() + " " + user.getId());
		deleteDocUserRelation(document.getId(), user.getId());
	}

	Usuario user;

	public void updateUser(Usuario user) {
		this.user = user;

		if (this.user == null) {
			// Limpa os componentes
			obsList.clear();
			tableView.getItems().clear();
		} else {
			listDocumentByUserId(user.getId());
		}

	}

	public void listDocumentByUserId(Long userId) {

		try {

			DocumentoService documentService = new DocumentoService(urlService);
			Set<Documento> documents = documentService.fetchDocumentByUserId(userId);

			// Limpa os componentes
			obsList.clear();
			obsList.addAll(documents);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public void deleteDocUserRelation(Long docId, Long userId) {

		DocumentoService documentService = new DocumentoService(urlService);
		ServiceResponse<?> serviceResponse = documentService.deleteDocUserRelation(docId, userId);

		// Caputura a mensagem do banco de dados e converte para o objeto solicitado, no
		// caso Usuario, além de status e mensagem.
		ApiResponse<Documento> serviceResponseFromJava = ApiResponse
				.fromJson(serviceResponse.getResponseBody().toString(), Documento.class);

		if (!serviceResponseFromJava.getStatus().equals("erro")) {

			Documento serviceResponseDoc = serviceResponseFromJava.getObject();

			System.out.println("remove doc id " + serviceResponseDoc.getId());

			// Iterate over the observable list to find and remove the object
			obsList.removeIf(document -> document.getId().equals(serviceResponseDoc.getId()));

			// Success toast message
			Node source = tableView; // The source is tfPurpouse (JFXTextField)
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = serviceResponseFromJava.getMensagem();
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);
		} else {
			// Error toast message if the document was not found
			Node source = tableView; // The source is tfPurpouse (JFXTextField)
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = serviceResponseFromJava.getMensagem();
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
		}
	}

}
