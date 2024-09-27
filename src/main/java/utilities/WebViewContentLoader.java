package utilities;

import java.util.function.Consumer;

import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import models.Interferencia;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

/**
 * Classe responsável por carregar conteúdo HTML em um WebView, permitindo
 * comunicação bidirecional entre JavaScript e Java.
 * 
 * Carrega um arquivo HTML e permite inserir informações dinâmicas usando
 * JavaScript.
 * 
 * @author fabricio.barrozo
 */
public class WebViewContentLoader {

	// Objeto JavaScript para comunicação com o WebView
	private JSObject jsObj;
	private boolean ready = false;

	// WebView e WebEngine usados para carregar e interagir com o conteúdo web
	private WebView webView = new WebView();
	private WebEngine webEngine = webView.getEngine();

	/**
	 * Carrega o conteúdo HTML no WebView e utiliza um callback para retornar o
	 * conteúdo HTML.
	 * 
	 * @param callback
	 *            Consumer para processar o conteúdo HTML quando carregado.
	 */
	public void loadWebViewContent(Consumer<String> callback) {
		// Carrega o arquivo HTML especificado
		webEngine.load(getClass().getResource("/html/views/templates/1/index.html").toExternalForm());

		// Listener para verificar quando o conteúdo HTML foi carregado
		webEngine.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				ready = true;
				jsObj = (JSObject) webEngine.executeScript("window");

				// Permite que o JavaScript tenha acesso a métodos Java
				jsObj.setMember("app", WebViewContentLoader.this);

				// Executa script JavaScript para obter o conteúdo HTML e passa para o callback
				String finalHtml = (String) webEngine.executeScript("document.body.innerHTML;");
				callback.accept(finalHtml);
			}
		});
	}

	/**
	 * Invoca código JavaScript no WebView, esperando que o conteúdo esteja pronto.
	 * 
	 * @param script
	 *            String com o código JavaScript a ser executado.
	 */
	private void invokeJS(final String script) {
		if (ready) {
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

	/**
	 * Atualiza informações na tabela dentro do HTML, passando dados de um objeto
	 * Interferencia.
	 * 
	 * @param interferencia
	 *            Objeto com as informações a serem inseridas no HTML.
	 */
	public void updateTableInfo(Interferencia interferencia) {
		// Converte o objeto Interferencia para JSON e o envia para o JavaScript
		String strJson = JsonConverter.convertObjectToJson(interferencia);
		invokeJS("geographicTable.updateTableInfo(" + strJson + ");");
	}

	/**
	 * Retorna o conteúdo HTML atual do WebView.
	 * 
	 * @return String com o conteúdo HTML.
	 */
	public String getHtml() {
		return (String) webEngine.executeScript("document.body.innerHTML;");
	}
}
