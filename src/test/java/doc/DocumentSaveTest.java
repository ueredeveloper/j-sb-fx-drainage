package doc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.Main;
import models.DocumentoTipo;

public class DocumentSaveTest extends ApplicationTest {
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

		JFXTextField tfNumber = lookup("#tfNumber").query();
		tfNumber.setText("123/2024");
		sleep(500);
		JFXTextField tfNumberSei = lookup("#tfNumberSei").query();
		tfNumberSei.setText("456");
		sleep(500);

		ComboBox<?> cbAddress = lookup("#cbAddress").query();
		clickOn(cbAddress).write("Rua das Acássias, 19");

		sleep(500);
		selectFirstItemInComboBox(cbAddress);

		JFXTextField tfLatitude = lookup("#tfLatitude").query();
		sleep(500);
		tfLatitude.setText("-15.123456789");
		sleep(500);

		/*
		 * JFXJFXTextFieldtfLongitude = lookup("#tfLongitude").query(); sleep(1000);
		 * tfLongitude.setText("-47.123456789"); sleep(2000);
		 */

		ComboBox<?> cbUser = lookup("#cbUser").query();
		clickOn(cbUser).write("Carlos Drumond de Andrade");
		sleep(500);

		selectFirstItemInComboBox(cbUser);

		ComboBox<?> cbProcess = lookup("#cbProcess").query();
		clickOn(cbProcess).write("197.123.456/2023");

		sleep(500);

		selectFirstItemInComboBox(cbProcess);

		ComboBox<?> cbAttachment = lookup("#cbAttachment").query();
		clickOn(cbAttachment).write("195.265.555/2015");

		sleep(500);

		selectFirstItemInComboBox(cbAttachment);

		Button btnSave = lookup("#btnSave").query();
		clickOn(btnSave);

		sleep(3000);

	}

	private void selectFirstItemInComboBox(ComboBox<?> comboBox) {
		interact(() -> comboBox.show());
		type(KeyCode.DOWN); // Simulate pressing the DOWN arrow key
		type(KeyCode.TAB); // Simulate pressing the ENTER key
	}

}
