package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principal que inicia a aplica��o JavaFX.
 */
public class Main extends Application {

	
	/**
	 * Método principal que inicia a aplicação
	 *
	 * @param args.
	 */
	public static void main(String[] args) {

		launch(args);
	} 

	/**
	 * Método de inicialização da aplicação JavaFX.
	 *
	 * @param stage
	 *            O palco principal da aplicação.
	 */
	@Override
	public void start(Stage stage) {
		
		Parent root = null;
		try {
			// Carrega o arquivo FXML que define a interface de login.
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
			root = fxmlLoader.load();
		} catch (IOException e) {
			// Se houver um erro ao carregar o arquivo FXML, imprime o erro.
			e.printStackTrace();
		}

		// Cria uma cena com a raiz carregada a partir do arquivo FXML.
		Scene scene = new Scene(root, 900, 450);
		// Define o t�tulo da janela de aplica��o.
		stage.setTitle("Login");
		// Define a cena no palco e exibe a janela.
		stage.setScene(scene);
		stage.show();
	}
}
