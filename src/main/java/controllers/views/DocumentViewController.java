package controllers.views;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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
import netscape.javascript.JSObject;
import services.InterferenciaService;
import services.TemplateService;
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
	private JFXComboBox<Interferencia> cbInterference;

	@FXML
	private HTMLEditor htmlEditor;

	@FXML
	private FontAwesomeIconView iconCopyDocument;

	ObservableList<Interferencia> obsList = FXCollections.observableArrayList();

	WebViewContentLoader webViewContentLoader;

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

		fetchInterferenciesByLogradouro(logradouro);

		cbInterference.setOnAction(e -> {

			Interferencia object = cbInterference.getSelectionModel().getSelectedItem();
			// webViewContentLoader.updateTableInfo(object);

			// htmlEditor.setHtmlText(webViewContentLoader.getHtml());

			if (object != null) {

				Set<Template> templates = searchByKeyword("");
				WebContent webContent = new WebContent();

				
				// Adiciona primeiro o index.html
				templates.forEach(template -> {
					System.out.println(template.getNome() + template.getNome().equals("index.html"));
					if (template.getNome().equals("index.html")) {
						webContent.setWebContent(template.getConteudo());
					}
					
				});
				
				
				// Adiciona depois os outros arquivos
				templates.forEach(template -> {
					
					String str = webContent.getWebContent();

					if (!template.getNome().equals("index.html")) {
						str += "<script>" + template.getConteudo() + "</script>";
					} 
					webContent.setWebContent(str);

				});
				
				System.out.println(webContent.getWebContent());

				// Atualiza o WebView com o conteúdo atualizado
				webViewContentLoader.getWebEngine().loadContent(webContent.getWebContent());

				// Opcional: atualiza o HTMLEditor também
				htmlEditor.setHtmlText(webViewContentLoader.getHtml());

			}

		});

	}

	public Set<Template> searchByKeyword(String keyword) {

		Set<Template> objects = null;

		try {

			String urlService = URLUtility.getURLService();

			TemplateService service = new TemplateService(urlService);

			objects = service.listByKeyword(keyword);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}

	public void fetchInterferenciesByLogradouro(String logradouro) {

		try {

			String urlService = URLUtility.getURLService();

			InterferenciaService service = new InterferenciaService(urlService);

			List<Interferencia> list = service.fetchByKeyword(logradouro);

			obsList.addAll(list);
			cbInterference.setItems(obsList);

		} catch (Exception e) {
			e.printStackTrace();
		}
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