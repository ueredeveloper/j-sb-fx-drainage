package doc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.Main;


public class OpenEditAddressTest extends ApplicationTest {
	
	public void start (Stage stage) throws Exception {
		new Main().start(stage);
	}
	
	@Test
	public void editDocument () {
	
		Button btnSignUp = lookup("#btnSignUp").query();
		assertNotNull(btnSignUp);
		clickOn(btnSignUp);
		sleep(500);
		
		Button btnSearch = lookup("#btnSearch").query();
		assertNotNull(btnSignUp);
		clickOn(btnSearch);
		sleep(500);
		
		/*
		TableView<Documento> tvDocs = lookup("#tvDocs").query();
		assertNotNull(tvDocs);
		// Clica no primeiro resultado da lista de  documentos
		clickOn(".table-row-cell").clickOn(".table-row-cell");*/
		
		// Começa a edição dos dados
		
		// Locate the ComboBox by its fx:id
        ComboBox<String> cbDocsAttributes = lookup("#cbDocsAttributes").query();

        // Ensure that the ComboBox has the expected items
       // verifyThat(comboBox, hasItems("Tipo", "Documento", "Processo", "Endereco"));

        // Simulate clicking on the ComboBox (selecting the first item)
        interact(() -> cbDocsAttributes.show());
        Node itemNode = from(cbDocsAttributes).lookup(".list-cell").nth(0).query();
        clickOn(itemNode);
        
		Platform.runLater(() -> {

			clickOn(cbDocsAttributes);
			cbDocsAttributes.getSelectionModel().select(1);
		});

		sleep(4000);
		
	}
	

}
