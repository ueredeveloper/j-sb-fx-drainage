package controllers.views;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import services.InterferenciaService;
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

	WebViewContentLoader documentLoader;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Html Editor
		documentLoader = new WebViewContentLoader();
		documentLoader.loadWebViewContent(finalHtml -> {
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
			documentLoader.updateTableInfo();
			
			htmlEditor.setHtmlText(documentLoader.getHtml());
		});

	}

	// Method that accepts a callback (Consumer) to return the HTML content when
	// it's ready
	public void loadWebViewContent(Consumer<String> callback) {
		WebView webView = new WebView();
		WebEngine webEngine = webView.getEngine();
		webEngine.load(getClass().getResource("/html/views/templates/1/index.html").toExternalForm());

		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
			@Override
			public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldState,
					Worker.State newState) {
				if (newState == Worker.State.SUCCEEDED) {
					// Once the content is fully loaded, retrieve the body content
					String script = "document.body.innerHTML;";
					String finalHtml = (String) webEngine.executeScript(script);

					// Use the callback to return the final HTML content
					callback.accept(finalHtml);
				}
			}
		});
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

}