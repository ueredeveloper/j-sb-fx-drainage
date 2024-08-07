package doc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.Main;
import models.DocumentoTipo;
import models.Usuario;

public class EditDocumentTest extends ApplicationTest {

	public void start(Stage stage) throws Exception {
		new Main().start(stage);
	}

	@Test
	public void editDocument() {

		Button btnSignUp = lookup("#btnSignUp").query();
		assertNotNull(btnSignUp);
		clickOn(btnSignUp);

		Button btnSearch = lookup("#btnSearch").query();
		assertNotNull(btnSignUp);
		clickOn(btnSearch);

		TableView<?> tvDocs = lookup("#tvDocs").query();
		assertNotNull(tvDocs);
		// Clica no primeiro resultado da lista de documentos
		clickOn(".table-row-cell").clickOn(".table-row-cell");

		// Começa a edição dos dados

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

		javafx.scene.control.TextField tfNumber = lookup("#tfNumber").query();
		tfNumber.setText("11111/2024");
		sleep(1000);
		javafx.scene.control.TextField tfNumberSei = lookup("#tfNumberSei").query();
		tfNumberSei.setText("111111");
		sleep(1000);

		ComboBox<?> cbAddress = lookup("#cbAddress").query();
		clickOn(cbAddress).write("Rua das Acássias, 19");

		sleep(500);
		selectFirstItemInComboBox(cbAddress);

		Platform.runLater(() -> {

			clickOn(cbAddress);
			cbAddress.getSelectionModel().select(0);
		});

		TextField tfLatitude = lookup("#tfLatitude").query();
		tfLatitude.setText("-15.123");
		sleep(1000);

		TextField tfLongitude = lookup("#tfLongitude").query();
		tfLongitude.setText("-47.123");
		sleep(1000);

		ComboBox<Usuario> cbUser = lookup("#cbUser").query();
		clickOn(cbUser).write("Carlos José");

		sleep(500);
		selectFirstItemInComboBox(cbUser);

		Platform.runLater(() -> {

			clickOn(cbUser);
			cbUser.getSelectionModel().select(0);
		});

		ComboBox<?> cbProcess = lookup("#cbProcess").query();
		clickOn(cbProcess).write("195.123.564/2012");

		sleep(500);

		Platform.runLater(() -> {

			clickOn(cbProcess);
			cbProcess.getSelectionModel().select(0);
		});

		ComboBox<?> cbAttachment = lookup("#cbAttachment").query();
		clickOn(cbAttachment).write("195.123.555/2015");

		sleep(500);

		Platform.runLater(() -> {

			clickOn(cbAttachment);
			cbAttachment.getSelectionModel().select(0);
		});

		sleep(500);

		/*
		 * 
		 * Button btnEdit = lookup("#btnEdit").query(); assertNotNull(btnEdit);
		 * clickOn(btnEdit);
		 */

		sleep(3000);

	}

	private void selectFirstItemInComboBox(ComboBox<?> comboBox) {
		interact(() -> comboBox.show());
		type(KeyCode.DOWN); // Simulate pressing the DOWN arrow key
		type(KeyCode.TAB); // Simulate pressing the ENTER key
	}

}
