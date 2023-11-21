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

public class FillProcessComboBoxTest extends ApplicationTest {
	
	public void start(Stage stage) throws Exception {
		new Main().start(stage);
	}

	@Test
	public void fillProcessComboBox () {
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
		ComboBox<Processo> cbMainProcess = lookup("#cbMainProcess").query();
		clickOn(cbMainProcess).write("4");
		sleep(500);

		Platform.runLater(() -> {

			clickOn(cbMainProcess);
			cbMainProcess.getSelectionModel().select(1);
		});

		sleep(500);

		ComboBox<Processo> cbProcess = lookup("#cbProcess").query();
		clickOn(cbProcess).write("1");
		sleep(500);
		Platform.runLater(() -> {

			clickOn(cbProcess);
			cbProcess.getSelectionModel().select(1);
		});

		Button btnSave = lookup("#btnSave").query();
		clickOn(btnSave);

		sleep(2000);

	}

}
