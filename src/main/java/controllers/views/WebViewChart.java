package controllers.views;

import java.io.IOException;

import com.google.gson.Gson;

import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import utilities.HTMLFileLoader;

public class WebViewChart {

	private WebView webView;
	private Object selectedDocument;

	public WebViewChart(WebView webView, Object selectedDocument) {
		this.webView = webView;
		this.selectedDocument = selectedDocument;
		initializeWebView();
	}

	private void initializeWebView() {
		// Convert selected document to JSON
		String json = new Gson().toJson(selectedDocument);

		// Path to your HTML content
		String gojsDiagramPath = "/html/views/e-chart-tree/index.html";
		String htmlDiagramContent = null;

		try {
			htmlDiagramContent = HTMLFileLoader.loadHTMLResourceToString(gojsDiagramPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (htmlDiagramContent != null) {
			// Inject JSON into HTML template
			htmlDiagramContent = htmlDiagramContent.replace("${json}", json);
		}

		// Get the WebEngine of the WebView
		WebEngine webEngine = webView.getEngine();

		// Load the HTML file into the WebView
		webEngine.load(getClass().getResource(gojsDiagramPath).toExternalForm());

		// Listen for when the page has finished loading
		webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				// Once the HTML is loaded, inject the JSON data and call JavaScript function
				JSObject jsObject = (JSObject) webEngine.executeScript("window");

				// Format JSON string for JavaScript
				String sJson = json.replace("\"", "'");

				// Update chart data using JavaScript function in the HTML
				JSObject updatedData = (JSObject) webEngine.executeScript("(" + sJson + ")");
				jsObject.call("updateSeriesData", updatedData);
			}
		});
	}
}
