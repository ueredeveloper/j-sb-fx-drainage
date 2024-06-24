package controllers.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;

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
import netscape.javascript.JSObject;
import utilities.HTMLFileLoader;

public class DocumentViewController implements Initializable {
	
	
	
	

	private Documento selectedDocument;

	// Constructor that takes the selected document as a parameter
	public DocumentViewController(Documento selectedDocument) {
		this.selectedDocument = selectedDocument;
	}

	@FXML
	private AnchorPane apContainer;

	@FXML
	private HTMLEditor htmlEditor;

	@FXML
	private Button btnSelectAndCopy;

	@FXML
	private WebView webView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Retira o link com a stilização light ou dark, assim fica a estilização do
		// componente pai (MainController)
		apContainer.getStylesheets().clear();

		// Load HTML content from a resource file.
		String resourcePath = "/html/views/index.html";
		String htmlContent = null;

		try {
			htmlContent = HTMLFileLoader.loadHTMLResourceToString(resourcePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Replace a placeholder in the HTML content with JSON data.
		String json = new Gson().toJson(selectedDocument);

	
		htmlContent = htmlContent.replace("${json}", json);
		htmlEditor.setHtmlText(htmlContent);

		btnSelectAndCopy.setOnAction(e -> {
			Clipboard clipboard = Clipboard.getSystemClipboard();
			ClipboardContent content = new ClipboardContent();
			content.putHtml(htmlEditor.getHtmlText());
			clipboard.setContent(content);
		});

		// Load HTML content from a resource file.
		String gojsDiagramPath = "/html/views/e-chart-tree/index.html";
		String htmlDiagramContent = null;

		try {
			htmlDiagramContent = HTMLFileLoader.loadHTMLResourceToString(gojsDiagramPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		htmlDiagramContent = htmlDiagramContent.replace("${json}", json);

		WebEngine webEngine = webView.getEngine();

		webEngine.load(getClass().getResource("/html/views/e-chart-tree/index.html").toExternalForm());

		webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {

			if (newState == Worker.State.SUCCEEDED) {

				JSObject jsObject = (JSObject) webEngine.executeScript("window");

				String sJson = json.replace("\"", "'");

				JSObject updatedData = (JSObject) webEngine.executeScript("(" + sJson + ")");

				jsObject.call("updateSeriesData", updatedData);
			}
		});

	}

}
