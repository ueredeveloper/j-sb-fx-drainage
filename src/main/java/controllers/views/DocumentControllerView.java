package controllers.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import models.Documento;
import models.DocumentoTipo;
import models.Endereco;
import utilities.HTMLFileLoader;

public class DocumentControllerView implements Initializable {
	
	@FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Button btnSelectAndCopy;

    @FXML
    private WebView webView;
    
    private Documento documento;
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		/*// Create a Documento object and set its properties.
		Documento doc = new Documento();
		doc.setDoc_id(1);
		doc.setDoc_numero("12/2015");
		doc.setDoc_processo("197.456789/2013");
		doc.setDoc_sei(123456789);
		doc.setDoc_tipo(new DocumentoTipo(1, "Requerimento"));
		doc.setDoc_endereco(new Endereco (1, "Rua dos Novaes Peres, 1"));

		// Convert the Documento object to JSON and print it.
		System.out.println(new Gson().toJson(doc));*/
		
		System.out.println(this.documento);

		// Load HTML content from a resource file.
		String resourcePath = "/html/views/index.html";
		String htmlContent = null;

		try {
			htmlContent = HTMLFileLoader.loadHTMLResourceToString(resourcePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Replace a placeholder in the HTML content with JSON data.
		String json = new Gson().toJson(this.documento);
		htmlContent = htmlContent.replace("${json}", json);
		htmlEditor.setHtmlText(htmlContent);

		btnSelectAndCopy.setOnAction(e -> {
			Clipboard clipboard = Clipboard.getSystemClipboard();
			ClipboardContent content = new ClipboardContent();
			content.putHtml(htmlEditor.getHtmlText());
			clipboard.setContent(content);
		});
		
		// Load HTML content from a resource file.
		String gojsDiagramPath = "/html/views/gojs-real-time-diagram.html";
		String htmlDiagramContent = null;

				try {
					htmlDiagramContent = HTMLFileLoader.loadHTMLResourceToString(gojsDiagramPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
		
				htmlDiagramContent = htmlDiagramContent.replace("${json}", json);
				
		
				WebEngine we = webView.getEngine();
				we.loadContent(htmlDiagramContent);
		
	}

    public void setDocument (Documento documento) {
    	this.documento = documento;
    }

}
