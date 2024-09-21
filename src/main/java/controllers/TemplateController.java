package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import enums.ToastType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import models.Template;
import services.ServiceResponse;
import services.TemplateService;
import utilities.URLUtility;

public class TemplateController implements Initializable {
	
	
	
	

	private String localUrl;

	public TemplateController() {
		this.localUrl = URLUtility.getURLService();
	}

	@FXML
	private JFXTextField tfDescription;

	@FXML
	private JFXButton btnNew;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnUpdate;

	@FXML
	private JFXButton btnDelete;

	@FXML
	private JFXTextField tfSearch;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private TableView<Template> tableView;
	ObservableList<Template> obsList = FXCollections.observableArrayList();

	@FXML
	private TableColumn<Template, Long> tcId;

	@FXML
	private TableColumn<Template, String> tcDescription;

	@FXML
	private TabPane tabPane;

	@FXML
	private Tab tabView;

	@FXML
	private HTMLEditor htmlEditor;

	@FXML
	private Tab tabHtml;

	@FXML
	private TextArea taEditHTML;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tcId.setCellValueFactory(new PropertyValueFactory<Template, Long>("id"));
		tcDescription.setCellValueFactory(new PropertyValueFactory<Template, String>("descricao"));

		// Add listener to tableView selection
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Template>() {
			@Override
			public void changed(ObservableValue<? extends Template> observable, Template oldValue, Template newValue) {
				if (newValue != null) {

					tfDescription.setText(newValue.getDescricao());
					htmlEditor.setHtmlText(newValue.getHtml());

				}
			}
		});

		// Event handling for tab selection
		tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
			if (newTab == tabView) {
				// Update HTMLEditor content from TextArea
				htmlEditor.setHtmlText(taEditHTML.getText());
			} else if (newTab == tabHtml) {
				// Update TextArea content from HTMLEditor
				taEditHTML.setText(htmlEditor.getHtmlText());
			}
		});

		btnSearch.setOnAction(event -> searchByKeyword(event));
		btnSave.setOnAction(event -> save(event));

	}

	public void save(ActionEvent event) {

		String description = tfDescription.getText();
		String html = htmlEditor.getHtmlText();

		try {

			TemplateService service = new TemplateService(localUrl);
			Template newObject = new Template();

			newObject.setDescricao(description);
			newObject.setHtml(html);

			ServiceResponse<?> response = service.save(newObject);

			System.out.println(response.getResponseCode());

			if (response.getResponseCode() == 201) {

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Objeto salvo com sucesso!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

				Object responseBody = response.getResponseBody();

				if (responseBody instanceof Template) {
					Template responseObject = (Template) responseBody;

					// Adiciona com primeiro na lista
					tableView.getItems().add(0, responseObject);
					// Seleciona o objeto salvo na table view
					tableView.getSelectionModel().select(responseObject);

				} else {
					// Informa salvamento com sucesso
					Node _source = (Node) event.getSource();
					Stage _ownerStage = (Stage) _source.getScene().getWindow();
					String _toastMsg = "Objeto ao salvar não foi bem convertido!";
					utilities.Toast.makeText(_ownerStage, _toastMsg, ToastType.ERROR);
				}

			} else {
				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Erro ao salvar objeto!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			}

		} catch (Exception e) {
			// Informa salvamento com sucesso
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Erro ao salvar objeto! \n" + e.getMessage();
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			e.printStackTrace();
		}

	}

	public void searchByKeyword(ActionEvent event) {

		try {

			// clearAllComponents();

			TemplateService service = new TemplateService(localUrl);

			String keyword = tfSearch.getText();

			List<Template> objects = service.listByKeyword(keyword);

			// Create a list of Document objects
			obsList.clear();
			obsList.addAll(objects);
			tableView.setItems(obsList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
