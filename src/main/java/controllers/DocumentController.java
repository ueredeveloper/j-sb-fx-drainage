package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Documento;
import models.DocumentoTipo;
import services.DocumentService;

public class DocumentController implements Initializable {

	private String localUrl;
	private String remoteUrl;

	public DocumentController() {
		try {
			Properties prop = new Properties();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
			prop.load(inputStream);
			localUrl = prop.getProperty("local.url");
			remoteUrl = prop.getProperty("remote.url");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private AnchorPane apContent;

	@FXML
	private JFXComboBox<DocumentoTipo> cbDocType;

	@FXML
	private JFXTextField tfNumber;

	@FXML
	private JFXTextField tfNumberSEI;

	@FXML
	private JFXTextField tfProcess;

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

	@FXML
	private TableColumn<Documento, Integer> tcId;

	@FXML
	private TableColumn<Documento, String> tcNum;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcId.setCellValueFactory(new PropertyValueFactory<Documento, Integer>("doc_id"));
		tcNum.setCellValueFactory(new PropertyValueFactory<Documento, String>("doc_numero"));
		tcProc.setCellValueFactory(new PropertyValueFactory<Documento, String>("doc_processo"));
		tcNumSei.setCellValueFactory(new PropertyValueFactory<Documento, String>("doc_sei"));

		AnchorPane.setRightAnchor(apContent, 0.0);
		AnchorPane.setLeftAnchor(apContent, 0.0);

		cbDocType.getItems().addAll(new DocumentoTipo(1, "Requerimento"), new DocumentoTipo(2, "Ofício"));

		cbDocType.setValue(cbDocType.getItems().get(0));

		// Customize the cell factory to display the custom object's description
		cbDocType.setCellFactory(param -> new JFXListCell<DocumentoTipo>() {
			@Override
			public void updateItem(DocumentoTipo item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getDt_descricao());
				}
			}
		});
		// Customize the list cell for the dropdown (optional)
		cbDocType.setButtonCell(new JFXListCell<DocumentoTipo>() {
			@Override
			public void updateItem(DocumentoTipo item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getDt_descricao());
				}
			}
		});

		// Add a listener to the JFXComboBox's valueProperty
		cbDocType.valueProperty().addListener(new ChangeListener<DocumentoTipo>() {
			public void changed(ObservableValue<? extends DocumentoTipo> observable, DocumentoTipo oldValue,
					DocumentoTipo newValue) {
				/*
				 * if (newValue != null) { System.out.println("Selected Documento Tipo: " +
				 * newValue.getDt_id() + ": " + newValue.getDt_descricao() ); }
				 */
			}
		});

		// Add a listener to the TextField's textProperty
		tfNumber.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// System.out.println("TextField changed from: " + oldValue + " to: " +
				// newValue);
			}
		});
		tfNumberSEI.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// System.out.println("TextField changed from: " + oldValue + " to: " +
				// newValue);
			}
		});
		// Create a regular expression pattern to allow only numeric input
		String numericPattern = "^[0-9]*$";

		// Create a TextFormatter with the numeric pattern
		TextFormatter<String> textFormatter = new TextFormatter<String>(change -> {
			if (change.getControlNewText().matches(numericPattern)) {
				return change;
			} else {
				return null; // Reject the change (non-numeric input)
			}
		});
		tfNumberSEI.setTextFormatter(textFormatter);

		tfProcess.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// System.out.println("TextField changed from: " + oldValue + " to: " +
				// newValue);
			}
		});
		
		btnViews.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {

				try {
		            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/views/Document.fxml"));
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
		});
		

	}

	public void handleList() {

		try {

			DocumentService documentService = new DocumentService(localUrl);

			List<Documento> documentos = documentService.fetchDocuments();

			// Create a list of Document objects
			ObservableList<Documento> documentList = FXCollections.observableArrayList(documentos);

			// Set the items in the TableView
			tvDocs.setItems(documentList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleSave() {
		// System.out.println(cbDocType.getValue().getDt_descricao());
		System.out.println(tfNumber.getText());

		String text = tfNumberSEI.getText();
		int numeroSei = 0;
		try {
			numeroSei = Integer.parseInt(text);
			// Use the 'numeroSei' integer as needed
		} catch (NumberFormatException e) {
			// Handle the case where the input is not a valid integer
			System.out.println("Input is not a valid integer.");
		}

	//	Documento doc = new Documento(tfNumber.getText(), tfProcess.getText(), numeroSei, cbDocType.getValue());

	//	System.out.println(doc.toString());
	}

	public void handleEdit() {
		System.out.println(cbDocType.getValue());
	}

	public void handleDelete() {
		System.out.println(cbDocType.getValue());
	}
}
