package utilities;

import java.util.function.Consumer;

import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import models.Documento;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

public class WebViewContentLoader {
	
	

	// JavaScript object for communication with the WebView
	private JSObject jsObj;

	// WebView and WebEngine used to load and interact with web content
	private WebView webView = new WebView();
	private final WebEngine webEngine = webView.getEngine();

	// Flag to avoid adding multiple listeners unnecessarily
	private boolean contentLoaded = false;
	
	
	

	// Load initial HTML content
	public void loadWebViewContent(String htmlContent, Consumer<String> callback) {
		contentLoaded = false; // Reset flag to allow further content updates
		webEngine.loadContent(htmlContent);

		// Set up a one-time listener for when the content is fully loaded
		webEngine.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED && !contentLoaded) {
				contentLoaded = true; // Prevent listener duplication
				jsObj = (JSObject) webEngine.executeScript("window");

				// Allow JavaScript to access Java methods
				jsObj.setMember("app", WebViewContentLoader.this);

				// Execute JavaScript script to get the HTML content and pass it to the callback
				String finalHtml = (String) webEngine.executeScript("document.body.innerHTML;");
				callback.accept(finalHtml);
			}
		});
	}

	// Update the entire HTML content
	public void updateHtmlContent(String newHtmlContent, Consumer<String> callback) {
		loadWebViewContent(newHtmlContent, callback);
	}

	// Execute JavaScript to update part of the document
	public void executeJavaScriptUpdate(Documento documento) {

		webEngine.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {

				System.out.println("console suceeded and loaded false");
				String strJson = JsonConverter.convertObjectToJson(documento);
				contentLoaded = true;
				invokeJS("utils.updateHtmlDocument(" + strJson + ");");
			}
		});

	}

	private void invokeJS(final String script) {
		if (contentLoaded) {
			try {
				jsObj.eval(script);
			} catch (JSException e) {
				System.err.println("Erro ao executar script JavaScript: " + e.getMessage());
			}
		} else {
			// Aguarda até que o conteúdo esteja completamente carregado
			webEngine.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
				if (newState == Worker.State.SUCCEEDED) {
					jsObj.eval(script);
				}
			});
		}
	}

	public WebEngine getWebEngine() {
		return webEngine;
	}
	
	

	public WebView getWebView() {
		return webView;
	}

	public void getHtmlContent(Consumer<String> callback) {

		webEngine.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {

			if (newState == Worker.State.SUCCEEDED) {
				contentLoaded = true;
				String finalHtml = (String) webEngine.executeScript("document.body.innerHTML;");
				callback.accept(finalHtml);
			}
		});

	}
}
