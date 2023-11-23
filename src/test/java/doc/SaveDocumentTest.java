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
import models.DocumentoTipo;
import models.Processo;

public class SaveDocumentTest extends ApplicationTest {
	public void start(Stage stage) throws Exception {
		new Main().start(stage);
	}

	@Test
	public void saveDocument() {
		Button btnSignUp = lookup("#btnSignUp").query();
		assertNotNull(btnSignUp);
		clickOn(btnSignUp);

		ComboBox<DocumentoTipo> cbDocType = lookup("#cbDocType").query();
		Node item = lookup(".list-cell").nth(0).query();
		assertNotNull(item);
		clickOn(item);
		sleep(500);

		Platform.runLater(() -> {
			for (DocumentoTipo tipo : cbDocType.getItems()) {
				if (tipo.getDtDescricao().equals("Ofício")) {
					cbDocType.getSelectionModel().select(tipo);
					break;
				}
			}
		});
		// fechar a seleção
		clickOn(item);

		javafx.scene.control.TextField tfNumber = lookup("#tfNumber").query();
		tfNumber.setText("123");
		sleep(500);
		javafx.scene.control.TextField tfNumberSEI = lookup("#tfNumberSEI").query();
		tfNumberSEI.setText("456");
		sleep(500);
		
		ComboBox<Processo> cbProcess = lookup("#cbProcess").query();
		clickOn(cbProcess).write("1");
	
		sleep(500);

		Platform.runLater(() -> {

			clickOn(cbProcess);
			cbProcess.getSelectionModel().select(1);
		});
		
		ComboBox<Processo> cbAddress = lookup("#cbAddress").query();
		clickOn(cbAddress).write("Rua");
	
		sleep(500);

		Platform.runLater(() -> {

			clickOn(cbAddress);
			cbAddress.getSelectionModel().select(1);
		});

		sleep(500);
		javafx.scene.control.TextField tfCity = lookup("#tfCity").query();
		tfCity.setText("Brasília");
		sleep(500);
		
		javafx.scene.control.TextField tfCEP = lookup("#tfCEP").query();
		tfCEP.setText("72130-040");
		sleep(500);
		
		Button btnSave = lookup("#btnSave").query();
		clickOn(btnSave);

		sleep(3000);

	}
}
