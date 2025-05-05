package doc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.Main;
import models.DocumentoTipo;
import models.Usuario;

public class DocumentEditTest extends ApplicationTest {

	public void start(Stage stage) throws Exception {
		new Main().start(stage);
	}

	@Test
	public void editDocument() {

		Button btnSignUp = lookup("#btnSignUp").query();
		assertNotNull(btnSignUp);
		clickOn(btnSignUp);
		
		JFXTextField tfSearch = lookup("#tfSearch").query();
		tfSearch.setText("teste");
		sleep(1000);

		Button btnSearch = lookup("#btnSearch").query();
		assertNotNull(btnSignUp);
		clickOn(btnSearch);

		TableView<?> tvDocs = lookup("#tvDocs").query();
		// Verifica se existe
		assertNotNull(tvDocs);
		// Clica no primeiro resultado da lista de documentos
		Platform.runLater(() -> tvDocs.getSelectionModel().select(0)); // Select the first row

		sleep(500);
		// Começa a edição dos dados

		ComboBox<DocumentoTipo> cbDocType = lookup("#cbDocType").query();
		Node item = lookup(".list-cell").nth(0).query();
		assertNotNull(item);
		clickOn(item);
		sleep(1500);

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

		JFXTextField tfNumber = lookup("#tfNumber").query();
		tfNumber.setText("123456789");
		sleep(1000);

		JFXTextField tfNumberSei = lookup("#tfNumberSei").query();
		tfNumberSei.setText("123/456789");
		sleep(1000);

		ComboBox<?> cbAddress = lookup("#cbAddress").query();
		assertNotNull(cbAddress); // Ensure the ComboBox exists
		// Limpa o combobox
		Platform.runLater(() -> {
			cbAddress.setValue(null); // Limpa o valor selecionado
			if (cbAddress.isEditable()) {
				cbAddress.getEditor().clear(); // Limpa o editor
			}
		});

		sleep(500);
		clickOn(cbAddress).write("Casa 30, Lago Norte");

		sleep(500);
		selectFirstItemInComboBox(cbAddress);

		/*
		 * JFXTextField tfLat itude = lookup("#tfLatitude").query();
		 * tfLatitude.setText("-15.123"); sleep(1000);
		 */

		JFXTextField tfLongitude = lookup("#tfLongitude").query();
		tfLongitude.setText("-47.123");
		sleep(1000);

		ComboBox<Usuario> cbUser = lookup("#cbUser").query();
		assertNotNull(cbUser); // Ensure the ComboBox exists
		// Limpa o combobox
		Platform.runLater(() -> {
			cbUser.setValue(null); // Limpa o valor selecionado
			if (cbUser.isEditable()) {
				cbUser.getEditor().clear(); // Limpa o editor
			}
		});
		clickOn(cbUser).write("Wagner de Campos Rosário");

		sleep(500);
		selectFirstItemInComboBox(cbUser);

		ComboBox<?> cbProcess = lookup("#cbProcess").query();
		assertNotNull(cbProcess); // Ensure the ComboBox exists
		// Limpa o combobox
		Platform.runLater(() -> {
			cbProcess.setValue(null); // Limpa o valor selecionado
			if (cbProcess.isEditable()) {
				cbProcess.getEditor().clear(); // Limpa o editor
			}
		});
		clickOn(cbProcess).write("663/");

		sleep(500);

		Platform.runLater(() -> {

			clickOn(cbProcess);
			cbProcess.getSelectionModel().select(0);
		});

		ComboBox<?> cbAttachment = lookup("#cbAttachment").query();
		assertNotNull(cbAttachment); // Ensure the ComboBox exists
		// Limpa o combobox
		Platform.runLater(() -> {
			cbAttachment.setValue(null); // Limpa o valor selecionado
			if (cbAttachment.isEditable()) {
				cbAttachment.getEditor().clear(); // Limpa o editor
			}
		});
		clickOn(cbAttachment).write("663/");

		sleep(500);
		selectFirstItemInComboBox(cbAttachment);
		sleep(500);

		Button btnEdit = lookup("#btnEdit").query();
		assertNotNull(btnEdit);
		clickOn(btnEdit);

		sleep(3000);

	}

	private void selectFirstItemInComboBox(ComboBox<?> comboBox) {
		interact(() -> comboBox.show());
		type(KeyCode.DOWN); // Simulate pressing the DOWN arrow key
		type(KeyCode.TAB); // Simulate pressing the ENTER key
	}

}
