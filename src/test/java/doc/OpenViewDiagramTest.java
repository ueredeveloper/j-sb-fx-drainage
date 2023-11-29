package doc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.Main;
import models.Documento;

public class OpenViewDiagramTest extends ApplicationTest {
	
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
		
		
		TableView<Documento> tvDocs = lookup("#tvDocs").query();
		assertNotNull(tvDocs);
		// Clica no primeiro resultado da lista de  documentos
		clickOn(".table-row-cell").clickOn(".table-row-cell");
		
		Button btnViews = lookup("#btnViews").query();
		assertNotNull(btnViews);
		clickOn(btnViews);
		
		sleep(5000);
		
		
	}
	

}
