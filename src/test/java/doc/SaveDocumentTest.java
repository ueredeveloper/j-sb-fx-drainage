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
				if (tipo.getDescricao().equals("Ofício")) {
					cbDocType.getSelectionModel().select(tipo);
					break;
				}
			}
		});
		// fechar a seleção
		clickOn(item);
		sleep(500);

		javafx.scene.control.TextField tfNumber = lookup("#tfNumber").query();
		tfNumber.setText("123/2024");
		sleep(500);
		javafx.scene.control.TextField tfNumberSei = lookup("#tfNumberSei").query();
		tfNumberSei.setText("456");
		sleep(500);

		ComboBox<?> cbAddress = lookup("#cbAddress").query();
		clickOn(cbAddress).write("Rua dos Bosques, 1");

		sleep(500);

		Platform.runLater(() -> {

			clickOn(cbAddress);
			cbAddress.getSelectionModel().select(1);
		});

		ComboBox<?> cbUser = lookup("#cbUser").query();
		clickOn(cbUser).write("Carlos Drumond de Andrade");

		sleep(500);

		Platform.runLater(() -> {

			clickOn(cbUser);
			cbUser.getSelectionModel().select(1);
		});

		ComboBox<?> cbProcess = lookup("#cbProcess").query();
		clickOn(cbProcess).write("197");

		sleep(500);

		Platform.runLater(() -> {

			clickOn(cbProcess);
			cbProcess.getSelectionModel().select(1);
		});

		ComboBox<?> cbAttachment = lookup("#cbAttachment").query();
		clickOn(cbAttachment).write("195.265.555/2015");

		sleep(500);

		Platform.runLater(() -> {

			clickOn(cbAttachment);
			cbAttachment.getSelectionModel().select(1);
		});

		Button btnSave = lookup("#btnSave").query();
		clickOn(btnSave);

		sleep(3000);

	}
}
