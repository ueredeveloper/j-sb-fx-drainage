package controllers.views;

import java.util.function.Consumer;

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
	                invokeJS("new Utils().updateHtmlDocument(" + strJson + ");");

	                // Fetch the updated HTML content after JS execution
	                fetchAndUpdateHtmlEditor();

	                System.out.println("HTML content updated successfully.");
	            } catch (JSException e) {
	                System.err.println("Error updating HTML content: " + e.getMessage());
	            }
	        } else if (newState == Worker.State.FAILED) {
	            System.err.println("Failed to load the web content.");
	        }
	    });
	}

	// Fetch and update the HTML editor with the latest HTML content
	private void fetchAndUpdateHtmlEditor() {
	    Platform.runLater(() -> {
	        try {
	            // Get the window object
	            window = (JSObject) webEngine.executeScript("window");

	            // Execute JS to fetch the outer HTML of the document body
	            Object htmlContent = window.eval("document.body");

	            // Update the HTML editor if the content is valid
	            if (htmlContent instanceof String) {
	                htmlEditor.setHtmlText((String) htmlContent);
	            } else {
	                System.err.println("HTML content is null or not a String.");
	            }
	        } catch (JSException e) {
	            System.err.println("Error while retrieving HTML content: " + e.getMessage());
	        }
	    });
	}

	public void getHtmlContent(Consumer<String> callback) {

		Platform.runLater(() -> {
			try {
				// Get the 'window' object
				window = (JSObject) webEngine.executeScript("window");

				// Execute JavaScript to get the outer HTML of the document
				Object htmlContent = window.eval("document.documentElement.outerHTML");

				// Attempt to cast the result to String
				if (htmlContent != null && htmlContent instanceof String) {
					callback.accept((String) htmlContent); // Pass the HTML content as a String to the callback
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

	public String getHtmlContentForHtmlEditor(Documento selectedDocument) {

		try {
			// Get the 'window' object
			window = (JSObject) webEngine.executeScript("window");

			// Execute JavaScript to get the outer HTML of the document
			Object htmlContent = window.eval("document.body.textContent");

			String strJson = JsonConverter.convertObjectToJson(selectedDocument);

			String runScript = "<script>documento = " + strJson + ";"
					+ "new Utils().createButtonForUpdate(documento);</script>";

			// Attempt to cast the result to String
			if (htmlContent != null && htmlContent instanceof String) {
				// Concatena a string que aciona os scripts
				return (String) htmlContent;
			} else {
				// If not a String, handle the null case or a fallback option
				System.err.println("HTML content is null or not a String.");
				return null;
			}
		} catch (JSException e) {
			System.err.println("Error while retrieving HTML content: " + e.getMessage());
			return null; // Pass null in case of error
		}

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
