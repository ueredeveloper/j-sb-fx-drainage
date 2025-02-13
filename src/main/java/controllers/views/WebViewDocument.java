package controllers.views;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import enums.StaticData;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import models.Documento;
import models.HidrogeoFraturado;
import models.HidrogeoPoroso;
import models.Interferencia;
import models.Subterranea;
import models.TipoPoco;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import services.HidrogeoFraturadoService;
import services.HidrogeoPorosoService;
import utilities.JsonConverter;
import utilities.URLUtility;

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

		// Convert the document to JSON
		String docJson = JsonConverter.convertObjectToJson(selectedDocument);

		/*
		 * Separa a interferência do documento para não haver erro quando for converter
		 * a interferência para subterrânea e depois para json. Só assim não se perde
		 * atributos específicos da captação subterrânea.
		 */
		StringBuilder interJson = new StringBuilder();
		
		System.out.println(interJson);

		selectedDocument.getEndereco().getInterferencias().forEach(interferencia -> {
			System.out.println("instance of sub " );
			System.out.println(interferencia instanceof Subterranea);
			
			if (interferencia != null) {

				if (interferencia instanceof Subterranea) {

					Subterranea subterranea = (Subterranea) interferencia; // Cast it first

					Long typeOfWell = subterranea.getTipoPoco().getId();
					String urlService = URLUtility.getURLService();
					ObservableList<TipoPoco> obsTypeOfWell = StaticData.INSTANCE.getTypesOfWells();

					if (typeOfWell == 1L || typeOfWell == 2L) {
						
						System.out.println("tipo poço " + typeOfWell);

						HidrogeoPorosoService service = new HidrogeoPorosoService(urlService);

						Set<HidrogeoPoroso> set = service.findByPoint(subterranea.getLatitude().toString(),
								subterranea.getLongitude().toString());

						set.forEach(s -> {
							subterranea.setSistema(s.getSistema());

						});
						
						
						
						Optional<TipoPoco> foundTipoPoco = obsTypeOfWell.stream()
							    .filter(tp -> tp.getId() == typeOfWell) // Assuming getId() returns the ID
							    .findFirst();
						foundTipoPoco.ifPresent(subterranea::setTipoPoco);

					} else {
						
						System.out.println("tipo poço " + typeOfWell);
						
						HidrogeoFraturadoService service = new HidrogeoFraturadoService(urlService);

						Set<HidrogeoFraturado> set = service.findByPoint(subterranea.getLatitude().toString(),
								subterranea.getLongitude().toString());

						set.forEach(s -> {
							subterranea.setSistema(s.getSistema());
							subterranea.setSubsistema(s.getSubsistema());

						});
						Optional<TipoPoco> foundTipoPoco = obsTypeOfWell.stream()
							    .filter(tp -> tp.getId() == typeOfWell) // Assuming getId() returns the ID
							    .findFirst();
						foundTipoPoco.ifPresent(subterranea::setTipoPoco);
					}
					
					// Atualiza a interferência com estes novos dados
					interferencia = (Interferencia) subterranea;
				}
				
				

				interJson.append(JsonConverter.convertInterferenciaToJson(interferencia));
				return; // Exit the loop after processing the first interferencia
			}
		});

		System.out.println("webview selected interference");
		System.out.println(interJson);

		// Verifica se a página já renderizou e adicona o objeto json
		webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				try {
					// Call JavaScript to update the document with the JSON content
					invokeJS("utils.updateHtmlDocument(" + docJson + "," + interJson + ");");

					// Limpa o html de scripts
					String cleanHtml = clearHtmlContent();

					// Leitura do html limpol no editor html
					htmlEditor.setHtmlText(cleanHtml);

					System.out.println("HTML content updated and reloaded successfully.");
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
	 * script String with the JavaScript code to be executed.
	 */
	private void invokeJS(final String script) {

		// Check if `utils` exists in the JavaScript environment
		Boolean utilsExists = (Boolean) window.eval("typeof utils !== 'undefined';");
		if (utilsExists) {
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
		} else {
			System.err.println("JavaScript 'utils' is not defined in the current HTML context.");
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
