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
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import services.InterferenciaService;
import services.TemplateService;
import services.UsuarioService;
import utilities.HTMLFileLoader;
import utilities.JsonConverter;
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
	private WebView webViewChart, webViewContent;

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
	
	WebEngine webEngineContent;// = webViewContent.getEngine();
	JSObject jsObj = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Html Editor
		webViewContentLoader = new WebViewContentLoader();
	

		String initialHtml5Content = "<h2>Hello World</h2>";
		webViewContentLoader.loadWebViewContent(initialHtml5Content, finalHtml -> {
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

		WebEngine webEngineChart;

		webEngineChart = webViewChart.getEngine();

		webEngineChart.load(getClass().getResource("/html/views/e-chart-tree/index.html").toExternalForm());

		webEngineChart.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {

			if (newState == Worker.State.SUCCEEDED) {

				JSObject jsObject = (JSObject) webEngineChart.executeScript("window");

				String sJson = json.replace("\"", "'");

				JSObject updatedData = (JSObject) webEngineChart.executeScript("(" + sJson + ")");

				jsObject.call("updateSeriesData", updatedData);
			}
		});

		// Componentes

		tfDocument.setText(
				"Número: " + this.selectedDocument.getNumero() + " | Sei: " + this.selectedDocument.getNumeroSei());
		tfAddress.setText(this.selectedDocument.getEnderecoLogradouro());

		// Captura logradouro da tabela endereço para buscar interferência por
		// logradouro
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

			// Atualiza documento seleciona com a interferência que será utilizada na
			// criação do ato (Parecer, Despacho etc)
			if (selectedInterference != null) {
				this.selectedDocument.getEndereco().getInterferencias().clear();
				this.selectedDocument.getEndereco().getInterferencias().add(selectedInterference);
			}

		});

		cbUsers.setOnAction(e -> {
			Usuario user = cbUsers.getSelectionModel().getSelectedItem();

			// Atualiza documento com o usuário que será utilizado na criação do ato
			// (Parecer, Despacho etc)
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

			// Busca os templates que atendem aos requisitos tipo de documento e tipo e
			// subtipo de outorga.
			templates = listTemplatesByParams(typeOfDocument, typeOfGrant, subtypeOfGrant);

			if (!templates.isEmpty() && templates != null) {
				descricaoList = templates.stream()
						// Seleciona por descrição, mas remove os arquivos das pastas compartilhadas
						// (models, utils, actions)
						.filter(template -> !"utils".equals(template.getPasta()))
						.filter(template -> !"models".equals(template.getPasta()))
						.filter(template -> !"actions".equals(template.getPasta())).map(Template::getDescricao) // Extract
																												// the
																												// 'descricao'
																												// attribute
						.distinct() // Ensure only unique descriptions
						.collect(Collectors.toList()); // Collect to a List

				if (descricaoList != null) {
					obsListTemplates.clear();
					obsListTemplates.addAll(descricaoList);
					cbTemplates.setItems(obsListTemplates);
				}
				// Verifica documento com interferencia e usuário selecionado
				// System.out.println(JsonConverter.convertObjectToJson(this.selectedDocument));
			}

		});
		

		webEngineContent = webViewContent.getEngine();

		cbTemplates.setOnAction(e -> {
			Interferencia selectedInterference = cbInterferencies.getSelectionModel().getSelectedItem();

			if (selectedInterference != null) {

				this.selectedDocument.getEndereco().getInterferencias().clear();
				this.selectedDocument.getEndereco().getInterferencias().add(selectedInterference);

				WebContent webContent = new WebContent();

				// Get the selected description from the ComboBox
				String description = cbTemplates.getSelectionModel().getSelectedItem();

				if (!descricaoList.isEmpty()) {

					List<Template> filteredTemplates = templates.stream()
							.filter(t -> t.getDescricao().equals(description) // Filter by the selected description
									|| "models".equals(t.getPasta()) // Include templates where 'pasta' is 'models'
									|| "utils".equals(t.getPasta()) // Include templates where 'pasta' is 'utils'
									|| "actions".equals(t.getPasta()) // Include templates where 'pasta' is 'actions'
					).distinct() // Ensure unique templates
							.collect(Collectors.toList());

					// Adiciona primeiro o index.html
					filteredTemplates.forEach(t -> {
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

					String newHtml5Content = webContent.getWebContent();

					webViewContentLoader.updateHtmlContent(newHtml5Content, finalHtml -> {
						// Set the retrieved HTML content in the HTMLEditor
						htmlEditor.setHtmlText(finalHtml);
					});

					webViewContentLoader.executeJavaScriptUpdate(this.selectedDocument);
					
					webViewContentLoader.loadWebViewContent(initialHtml5Content, finalHtml -> {
						// Set the retrieved HTML content in the HTMLEditor
						webContent.setWebContent(finalHtml);
					});
					
					webViewContentLoader.updateHtmlContent(webContent.getWebContent(), finalHtml -> {
						// Set the retrieved HTML content in the HTMLEditor
						htmlEditor.setHtmlText(finalHtml);
					});
					
					String strJson = JsonConverter.convertObjectToJson(this.selectedDocument);
					contentLoaded = true;
					invokeJS("utils.updateHtmlDocument(" + strJson + ");");
					
					

				}
			}

		});
		
		
		
		

	}
	private boolean contentLoaded = false;
	private void invokeJS(final String script) {
		
		jsObj = (JSObject) webEngineContent.executeScript("window");
		
		if (contentLoaded) {
			try {
				jsObj.eval(script);
			} catch (JSException e) {
				System.err.println("Erro ao executar script JavaScript: " + e.getMessage());
			}
		} else {
			// Aguarda até que o conteúdo esteja completamente carregado
			webEngineContent.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
				if (newState == Worker.State.SUCCEEDED) {
					jsObj.eval(script);
				}
			});
		}
	}
	
	// Execute JavaScript to update part of the document
		public void executeJavaScriptUpdate(Documento documento) {

			webEngineContent.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
				if (newState == Worker.State.SUCCEEDED) {

					System.out.println("console suceeded and loaded false");
					String strJson = JsonConverter.convertObjectToJson(documento);
					contentLoaded = true;
					invokeJS("utils.updateHtmlDocument(" + strJson + ");");
				}
			});

		}


	public Set<Template> listTemplatesByParams(String typeOfDocument, String typeOfGrant, String subtypeOfGrant) {

		Set<Template> objects = null;

		try {

			String urlService = URLUtility.getURLService();

			TemplateService service = new TemplateService(urlService);

			objects = service.listTemplatesByParams(typeOfDocument, typeOfGrant, subtypeOfGrant);

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