package controllers.views;

import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import models.Documento;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import utilities.JsonConverter;

public class WebViewDocument {

    private WebView webView;
    private WebEngine webEngine;
    private JSObject window;  // Moved here

    // Constructor
    public WebViewDocument(WebView webView) {
        this.webView = webView;
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

    // Method to dynamically change the content of the element with id 'hello'
    public void changeContent(Documento selectedDocument) {
        webEngine.setOnError(event -> {
            System.out.println("Erro no JavaScript: " + event.getMessage());
        });

        String strJson = JsonConverter.convertObjectToJson(selectedDocument);
        invokeJS("utils.updateHtmlDocument(" + strJson + ");");
    }
    
    public String getHtmlContent () {
    	 // Get the HTML content from the WebView's WebEngine
        webEngine.executeScript("document.documentElement.outerHTML").toString();
        String htmlContent = (String) webEngine.executeScript("document.documentElement.outerHTML");
        
        return htmlContent;
    }

    /**
     * Invokes JavaScript code in the WebView, waiting for the content to be ready.
     * 
     * @param script String with the JavaScript code to be executed.
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
}
