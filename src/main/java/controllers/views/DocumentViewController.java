package controllers.views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import models.Documento;
import models.Interferencia;
import models.Template;
import models.Usuario;
import netscape.javascript.JSObject;
import services.InterferenciaService;
import services.TemplateService;
import services.UsuarioService;
import utilities.HTMLFileLoader;
import utilities.URLUtility;
import utilities.WebViewContentLoader;

public class DocumentViewController implements Initializable {

	private static DocumentViewController instance;

	public static DocumentViewController getInstance() {
		return instance;
	}

	private Documento selectedDocument;

	// Constructor that takes the selected document as a parameter
	public DocumentViewController(Documento selectedDocument) {
		this.selectedDocument = selectedDocument;
	}

	@FXML
	private Button btnSelectAndCopy;

	@FXML
	private WebView webView;

	@FXML
	private AnchorPane apContainer;

	@FXML
	private JFXTextField tfDocument;

	@FXML
	private JFXTextField tfAddress;

	@FXML
	private JFXComboBox<Interferencia> cbInterferencies;

	@FXML
	private JFXComboBox<String> cbTemplates;

	@FXML
	private JFXComboBox<Usuario> cbUsers;

	@FXML
	private HTMLEditor htmlEditor;

	@FXML
	private FontAwesomeIconView iconCopyDocument;

	ObservableList<Interferencia> obsListInterferencies = FXCollections.observableArrayList();
	ObservableList<Usuario> obsListUsers = FXCollections.observableArrayList();
	ObservableList<String> obsListTemplates = FXCollections.observableArrayList();

	WebViewContentLoader webViewContentLoader;

	Set<Template> templates = new HashSet<>();
	List<String> descricaoList = new ArrayList<String>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Html Editor
		webViewContentLoader = new WebViewContentLoader();
		webViewContentLoader.loadWebViewContent(finalHtml -> {
			// Set the retrieved HTML content in the HTMLEditor
			htmlEditor.setHtmlText(finalHtml);
		});

		// Copiar o modelo de ato para colar no SEI.
		iconCopyDocument.setOnMouseClicked(event -> {
			Clipboard clipboard = Clipboard.getSystemClipboard();
			ClipboardContent content = new ClipboardContent();
			content.putHtml(htmlEditor.getHtmlText());
			clipboard.setContent(content);
		});

		// Retira o link com a stilização light ou dark, assim fica a estilização do
		// componente pai (MainController)
		apContainer.getStylesheets().clear();

		// Webview Chart - Diagrama
		String json = new Gson().toJson(selectedDocument);

		// Load HTML content from a resource file.
		String gojsDiagramPath = "/html/views/e-chart-tree/index.html";
		String htmlDiagramContent = null;

		try {
			htmlDiagramContent = HTMLFileLoader.loadHTMLResourceToString(gojsDiagramPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		htmlDiagramContent = htmlDiagramContent.replace("${json}", json);

		WebEngine webEngine;

		webEngine = webView.getEngine();

		webEngine.load(getClass().getResource("/html/views/e-chart-tree/index.html").toExternalForm());

		webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {

			if (newState == Worker.State.SUCCEEDED) {

				JSObject jsObject = (JSObject) webEngine.executeScript("window");

				String sJson = json.replace("\"", "'");

				JSObject updatedData = (JSObject) webEngine.executeScript("(" + sJson + ")");

				jsObject.call("updateSeriesData", updatedData);
			}
		});

		// Componentes

		tfDocument.setText(
				"Número: " + this.selectedDocument.getNumero() + " | Sei: " + this.selectedDocument.getNumeroSei());
		tfAddress.setText(this.selectedDocument.getEnderecoLogradouro());

		String logradouro = this.selectedDocument.getEnderecoLogradouro();

		Set<Interferencia> interferencies = listInterferenciesByLogradouro(logradouro);
		obsListInterferencies.addAll(interferencies);
		cbInterferencies.setItems(obsListInterferencies);

		Set<Usuario> users = listUsersByDocumentId(this.selectedDocument.getId());
		obsListUsers.addAll(users);
		cbUsers.setItems(obsListUsers);

		String typeOfDocument = this.selectedDocument.getTipoDocumento().getDescricao();

		cbInterferencies.setOnAction(e -> {

			Interferencia selectedInterference = cbInterferencies.getSelectionModel().getSelectedItem();

			if (selectedInterference != null) {
				this.selectedDocument.getEndereco().getInterferencias().clear();
				this.selectedDocument.getEndereco().getInterferencias().add(selectedInterference);
			}

		});

		cbUsers.setOnAction(e -> {
			Usuario user = cbUsers.getSelectionModel().getSelectedItem();

			if (user != null) {
				this.selectedDocument.getUsuarios().clear();
				this.selectedDocument.getUsuarios().add(user);
			}

			Interferencia selectedInterference = cbInterferencies.getSelectionModel().getSelectedItem();

			String typeOfGrant = "";
			String subtypeOfGrant = "";
			if (selectedInterference != null) {
				typeOfGrant = selectedInterference.getTipoOutorga().getDescricao();
				subtypeOfGrant = selectedInterference.getSubtipoOutorga().getDescricao();
			}

			templates = listTemplatesByParams(typeOfDocument, typeOfGrant, subtypeOfGrant);

			if (!templates.isEmpty() && templates != null) {
				descricaoList = templates.stream()
						// Seleciona por descrição, mas remove os arquivos das pastas compartilhadas (models, utils, actions)
						 .filter(template -> !"utils".equals(template.getPasta()))
						 .filter(template -> !"models".equals(template.getPasta()))
						 .filter(template -> !"actions".equals(template.getPasta()))
						.map(Template::getDescricao ) // Extract the 'descricao' attribute
						.distinct() // Ensure only unique descriptions
						.collect(Collectors.toList()); // Collect to a List

				if (descricaoList != null) {
					obsListTemplates.clear();
					obsListTemplates.addAll(descricaoList);
					cbTemplates.setItems(obsListTemplates);
				}
			}

		});

		cbTemplates.setOnAction(e -> {
			Interferencia selectedInterference = cbInterferencies.getSelectionModel().getSelectedItem();
			// webViewContentLoader.updateTableInfo(object);

			// htmlEditor.setHtmlText(webViewContentLoader.getHtml());

			if (selectedInterference != null) {

				this.selectedDocument.getEndereco().getInterferencias().clear();
				this.selectedDocument.getEndereco().getInterferencias().add(selectedInterference);

				WebContent webContent = new WebContent();

				// Get the selected description from the ComboBox
				String description = cbTemplates.getSelectionModel().getSelectedItem();
				
				System.out.println(description);

				if (!descricaoList.isEmpty()) {

					List<Template> filteredTemplates = templates.stream()
						    .filter(t -> 
						        t.getDescricao().equals(description)  // Filter by the selected description
						        || "models".equals(t.getPasta())      // Include templates where 'pasta' is 'models'
						        || "utils".equals(t.getPasta())       // Include templates where 'pasta' is 'utils'
						        || "actions".equals(t.getPasta())     // Include templates where 'pasta' is 'actions'
						    )
						    .distinct() // Ensure unique templates
						    .collect(Collectors.toList());


					// Adiciona primeiro o index.html
					filteredTemplates.forEach(t -> {
						System.out.println(t.getNome());
						if (t.getNome().equals("index.html")) {
							
							webContent.setWebContent(t.getConteudo());
						}
					});

					// Adiciona depois os outros arquivos
					filteredTemplates.forEach(t -> {

						String str = webContent.getWebContent();

						if (!t.getNome().equals("index.html")) {
							str += "<script>" + t.getConteudo() + "</script>";
						}
						webContent.setWebContent(str);

					});
					
					System.out.println(webContent.getWebContent());

					// Atualiza o WebView com o conteúdo atualizado
					webViewContentLoader.getWebEngine().loadContent(webContent.getWebContent());

					// Opcional: atualiza o HTMLEditor também
					htmlEditor.setHtmlText(webViewContentLoader.getHtml());

				}

			}

		});

	}

	public Set<Template> listTemplatesByParams(String typeOfDocument, String typeOfGrand, String subtypeOfGrant) {

		Set<Template> objects = null;

		try {

			String urlService = URLUtility.getURLService();

			TemplateService service = new TemplateService(urlService);

			objects = service.listTemplatesByParams(typeOfDocument, typeOfGrand, subtypeOfGrant);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}

	public Set<Usuario> listUsersByDocumentId(Long id) {

		Set<Usuario> objects = null;

		try {

			String urlService = URLUtility.getURLService();

			UsuarioService service = new UsuarioService(urlService);

			objects = service.listUsersByDocumentId(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}

	public Set<Interferencia> listInterferenciesByLogradouro(String logradouro) {

		Set<Interferencia> objects = null;

		try {

			String urlService = URLUtility.getURLService();

			InterferenciaService service = new InterferenciaService(urlService);

			objects = service.fetchByKeyword(logradouro);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}

	class WebContent {
		String webContent = "";

		public String getWebContent() {
			return webContent;
		}

		public void setWebContent(String webContent) {
			this.webContent = webContent;
		}

	}

}