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
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXTextField;

import enums.ToastType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import services.DocumentService;
import services.ServiceResponse;
import utilities.ConvertToUTF8;

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

		cbDocType.getItems().addAll(
				new DocumentoTipo(1, "Requerimento"), 
				new DocumentoTipo(2, "OfÃ­cio")
				);

		//cbDocType.setValue(cbDocType.getItems().get(0));

		// Customize the cell factory to display the custom object's description
		/*cbDocType.setCellFactory(param -> new JFXListCell<DocumentoTipo>() {
			@Override
			public void updateItem(DocumentoTipo item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getDt_descricao());
				}
			}
		});*/
		cbDocType.setConverter(new StringConverter<DocumentoTipo>() {
	        @Override
	        public String toString(DocumentoTipo documentoTipo) {
	            return documentoTipo == null ? null : new ConvertToUTF8(documentoTipo.getDt_descricao()).convertToUTF8();
	        }

	        @Override
	        public DocumentoTipo fromString(String string) {
	            // You can implement this method if needed
	            // It converts a string back to a DocumentoTipo object
	            return null;
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
					setText(new ConvertToUTF8(item.getDt_descricao()).convertToUTF8());
				}
			}
		});

		// Add a listener to the JFXComboBox's valueProperty
		cbDocType.valueProperty().addListener(new ChangeListener<DocumentoTipo>() {
			public void changed(ObservableValue<? extends DocumentoTipo> observable, DocumentoTipo oldValue,
					DocumentoTipo newValue) {
				 // if (newValue != null) { System.out.println("Selected Documento Tipo: " +
				//newValue.getDt_id() + ": " + newValue.getDt_descricao() ); }
				 
			}
		});

		tvDocs.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            // Update your text fields with the selected document
	        	
	            tfNumber.setText(newSelection.getDoc_numero());
	            tfNumberSEI.setText(String.valueOf(newSelection.getDoc_sei()));
	            tfProcess.setText(newSelection.getDoc_processo());
	            
	            // Update the ComboBox with the selected document's doc_tipo
	            cbDocType.getSelectionModel().select(newSelection.getDoc_tipo());
	        } else {
	            // Clear text fields if no selection
	            tfNumber.clear();
	            tfNumberSEI.clear();
	            tfProcess.clear();
	            cbDocType.getSelectionModel().clearSelection();
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

		btnSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleSave(event);

			}
		});
		
		btnSearch.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleListByParam(event);
				
			}
		});
		btnDelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleDeleteById(event);
				
			}
		});
		btnEdit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleEdit(event);
				
			}
		});
	}

	public void handleListByParam(ActionEvent event) {

		try {

			DocumentService documentService = new DocumentService(localUrl);

			String keyword = tfSearch.getText();

			List<Documento> documentos = documentService.fetchByParam(keyword);

			// Create a list of Document objects
			ObservableList<Documento> documentList = FXCollections.observableArrayList(documentos);

			// Set the items in the TableView
			tvDocs.setItems(documentList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleSave(ActionEvent event) {

		String text = tfNumberSEI.getText();

		// verjo que este método é desnecessário pois o textfield já está aceitando
		// apenas números
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

			Documento reqDoc = new Documento(tfNumber.getText(), tfProcess.getText(), numeroSei, cbDocType.getValue());

			ServiceResponse<?> serviceResponse = documentService.save(reqDoc);

			if (serviceResponse.getResponseCode() == 201) {
				
				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Documento salvo com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);
				// Adiciona resposta na tabela
				Documento responseDoc = new Gson().fromJson((String) serviceResponse.getResponseBody(), Documento.class);
				tvDocs.getItems().add(responseDoc);

			} else {
				// adiconar alerta (Toast) de erro
				//System.out.println(serviceResponse.getResponseCode());
			}

		} catch (Exception e) {
			// adicionar Toast de erro
			e.printStackTrace();
		}

	}

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
	    String updatedProcess = tfProcess.getText();
	    String updatedNumeroSEI = tfNumberSEI.getText();
	    DocumentoTipo updateDocumentoTipo = cbDocType.getValue();

	    int updatedSei = 0;
	    try {
	        updatedSei = Integer.parseInt(updatedNumeroSEI);
	    } catch (NumberFormatException e) {
	        // Handle the case where the input is not a valid integer
	        System.out.println("Input is not a valid integer for SEI.");
	        // You may want to display a toast or alert here
	        return;
	    }

	    // Update the selected document with new values
	    selectedDoc.setDoc_numero(updatedNumero);
	    selectedDoc.setDoc_processo(updatedProcess);
	    selectedDoc.setDoc_sei(updatedSei);
	    selectedDoc.setDoc_tipo(updateDocumentoTipo);

	    try {
	        DocumentService documentService = new DocumentService(localUrl);

	        // Send a PUT request to update the document
	        ServiceResponse<?> serviceResponse = documentService.update(selectedDoc);

	        if (serviceResponse.getResponseCode() == 200) {
	            // Display a success toast or alert
	            Node source = (Node) event.getSource();
	            Stage ownerStage = (Stage) source.getScene().getWindow();
	            String toastMsg = "Documento editado com sucesso!";
	            utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);
	            
	            tvDocs.getItems().remove(selectedDoc);
	         // Adiciona resposta na tabela
				Documento responseDocumento = new Gson().fromJson((String) serviceResponse.getResponseBody(), Documento.class);
				tvDocs.getItems().add(0, responseDocumento);
				
	        } else {
	            // Display an error toast or alert
	            // System.out.println(serviceResponse.getResponseCode());
	        }

	    } catch (Exception e) {
	        // Display an error toast or alert
	        e.printStackTrace();
	    }
	}


	public void handleDeleteById(ActionEvent event) {

		Documento selectedDocumento = tvDocs.getSelectionModel().getSelectedItem();

		try {
			DocumentService documentService = new DocumentService(localUrl);
			
			ServiceResponse<?> serviceResponse = documentService.deleteById(selectedDocumento.getDoc_id());
			
			if (serviceResponse.getResponseCode() == 200) {
				
				// Informa sucesso em deletar
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Documento deletado com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);
				// Objecto removido
				//Documento responseDoc = new Gson().fromJson((String) serviceResponse.getResponseBody(), Documento.class);
				
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

}
