package controllers.views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.Documento;
import models.Usuario;
import services.DocumentService;
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
							Documento currentDocumento = getTableView().getItems().get(getIndex());
							handleAction(currentDocumento);
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

	private void handleAction(Documento documento) {
		// Define what happens when the button is clicked
		System.out.println("Button clicked for: " + documento);
	}

	Usuario user;

	public void updateUser(Usuario user) {
		this.user = user;

		listDocumentByUserId(user.getId());
	}

	public void listDocumentByUserId(Long userId) {

		try {

			DocumentService documentService = new DocumentService(urlService);
			List<Documento> documents = documentService.fetchDocumentByUserId(userId);

			// Create a list of Document objects
			obsList.clear();
			obsList.addAll(documents);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
