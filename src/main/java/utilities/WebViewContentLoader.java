package utilities;

import java.util.function.Consumer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

/**
 * Lê um arquivo html e script para depois inserir no Html Editor do javafx que
 * não lê javascript.
 * 
 * @author fabricio.barrozo
 *
 */
public class WebViewContentLoader {

	// Para comunicação com o webview e webengine
	JSObject jsObj;
	public boolean ready;
	WebView webView = new WebView();
	WebEngine webEngine = webView.getEngine();

	// Method that accepts a callback (Consumer) to return the HTML content when
	// it's ready
	public void loadWebViewContent(Consumer<String> callback) {

		webEngine.load(getClass().getResource("/html/views/templates/1/index.html").toExternalForm());

		ready = false;

		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
			public void changed(final ObservableValue<? extends Worker.State> observableValue,
					final Worker.State oldState, final Worker.State newState) {

				if (newState == Worker.State.SUCCEEDED) {
					ready = true;
				}
			}
		});

		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
			public void changed(final ObservableValue<? extends Worker.State> observableValue,
					final Worker.State oldState, final Worker.State newState) {
				if (newState == Worker.State.SUCCEEDED) {
					jsObj = (JSObject) webEngine.executeScript("window");

					// Variável para voltar com dados do Webview para o Java. Por aqui tem-se acesso
					// às funções java dentro do javascript.
					jsObj.setMember("app", WebViewContentLoader.this);
				}
			}
		});

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

	private void invokeJS(final String str) {

		if (ready) {
			System.out.println(ready);
			try {
				jsObj.eval(str);
			} catch (JSException js) {
				System.out.println("erro na execução da leitura javascript: " + js);
			}
		} else {
			webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
				@Override
				public void changed(final ObservableValue<? extends Worker.State> observableValue,
						final Worker.State oldState, final Worker.State newState) {
					if (newState == Worker.State.SUCCEEDED) {
						jsObj.eval(str);

					}
				}
			});

		}
	}

	public void updateTableInfo() {
		invokeJS("let interferencia = {baciaHidrografica:{id: 1,descricao:'Bacia do Maranhão'},"
				+ "unidadeHidrografica: { id: 1, descricao: 'Unidade do Maranhão' },latitude: 1.123456789,"

				+ "longitude: 2.987654321};geoTable.updateTableInfo(interferencia);");

	}
	
	public String getHtml () {
		String script = "document.body.innerHTML;";
		String finalHtml = (String) webEngine.executeScript(script);
		
		return finalHtml;
	}
}
