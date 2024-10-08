package controllers.views;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import models.Documento;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import utilities.JsonConverter;

public class WebViewDocument {

	private WebView webView;
	private WebEngine webEngine;
	private JSObject window; // Moved here
	private HTMLEditor htmlEditor;

	HtmlString htmlString = new HtmlString();

	// Constructor
	public WebViewDocument(WebView webView, HTMLEditor htmlEditor) {
		this.webView = webView;
		this.htmlEditor = htmlEditor;
		initializeWebView();
	}

	private void initializeWebView() {
		// Get the WebEngine of the WebView
		webEngine = webView.getEngine();

		// Load initial HTML content
		webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				System.out.println("Web content loaded successfully.");
				// Initialize the window reference when the content is fully loaded
				window = (JSObject) webEngine.executeScript("window");
			}
		});
	}

	// Load HTML content into the WebView
	public void loadContent(String content) {
		webEngine.loadContent(content);
	}

	public void changeContent(Documento selectedDocument) {
		// Register error handler for JavaScript errors
		webEngine.setOnError(event -> {
			System.err.println("JavaScript error: " + event.getMessage());
		});

		// Convert the Documento object to JSON
		String strJson = JsonConverter.convertObjectToJson(selectedDocument);

		// Ensure the page is loaded before executing JS and updating HTML
		webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				try {
					// Call JavaScript to update the document with the JSON content
					invokeJS("utils.updateHtmlDocument(" + strJson + ");");

					htmlEditor.setHtmlText(clearHtmlContent());

					System.out.println("HTML content updated successfully.");
				} catch (JSException e) {
					System.err.println("Error updating HTML content: " + e.getMessage());
				}
			} else if (newState == Worker.State.FAILED) {
				System.err.println("Failed to load the web content.");
			}
		});
	}

	public void getHtmlContent(Consumer<String> callback) {

		Platform.runLater(() -> {
			try {
				String cleanHtml = clearHtmlContent();

				// Attempt to cast the result to String
				if (cleanHtml != null && cleanHtml instanceof String) {
					callback.accept((String) cleanHtml); // Pass the HTML content as a String to the callback
				} else {
					// If not a String, handle the null case or a fallback option
					System.err.println("HTML content is null or not a String.");
					callback.accept(null);
				}
			} catch (JSException e) {
				System.err.println("Error while retrieving HTML content: " + e.getMessage());
				callback.accept(null); // Pass null in case of error
			}
		});
	}

	/**
	 * É preciso remover os scripts do arquivo html para enviar para o html editor.
	 */
	public String clearHtmlContent() {
		String regex = "<script[\\s\\S]*?</script>";

		window = (JSObject) webEngine.executeScript("window");

		String htmlContent = window.eval("document.documentElement.outerHTML").toString();

		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(htmlContent);

		String cleanHtml = matcher.replaceAll("");

		return cleanHtml;
	}

	/**
	 * Invokes JavaScript code in the WebView, waiting for the content to be ready.
	 * 
	 * @param script
	 *            String with the JavaScript code to be executed.
	 */
	private void invokeJS(final String script) {
		if (window != null) {
			try {
				window.eval(script);
			} catch (JSException e) {
				System.err.println("Erro ao executar script JavaScript: " + e.getMessage());
			}
		} else {
			// Wait until the content is completely loaded
			webEngine.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
				if (newState == Worker.State.SUCCEEDED) {
					window = (JSObject) webEngine.executeScript("window");
					window.eval(script);
				}
			});
		}
	}

	class HtmlString {
		String htmlString;

		public HtmlString() {
			super();

		}

		public String getHtmlString() {
			return htmlString;
		}

		public void setHtmlString(String htmlString) {
			this.htmlString = htmlString;
		}

	}
}