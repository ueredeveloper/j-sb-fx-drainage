package doc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.Main;
import models.Documento;
import models.DocumentoTipo;
import models.Endereco;
import models.Processo;

public class EditDocumentTest extends ApplicationTest {
	
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
		
		// Começa a edição dos dados
		
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
		tfNumber.setText("11111/2024");
		sleep(500);
		javafx.scene.control.TextField tfNumberSEI = lookup("#tfNumberSEI").query();
		tfNumberSEI.setText("111112024");
		sleep(500);
		
		ComboBox<Processo> cbProcess = lookup("#cbProcess").query();
		clickOn(cbProcess).write("1");
	
		sleep(500);

		Platform.runLater(() -> {

			clickOn(cbProcess);
			cbProcess.getSelectionModel().select(1);
		});
		
		ComboBox<Processo> cbAddress = lookup("#cbAddress").query();
		clickOn(cbAddress).write("Sorriso");
	
		sleep(1500);
		selectFirstItemInComboBox(cbAddress);

		/*
		Platform.runLater(() -> {

			clickOn(cbAddress);
			cbAddress.getSelectionModel().select(0);
		});*/

		sleep(500);
		javafx.scene.control.TextField tfCity = lookup("#tfCity").query();
		tfCity.setText("Goiânia Junit ");
		sleep(500);
		
		javafx.scene.control.TextField tfCEP = lookup("#tfCEP").query();
		tfCEP.setText("72130- Junit");
		sleep(500);
		
		Button btnEdit = lookup("#btnEdit").query();
		assertNotNull(btnEdit);
		clickOn(btnEdit);
		
		sleep(3000);
		
		
	}
	
	private void selectFirstItemInComboBox(ComboBox<Processo> comboBox) {
		  interact(() -> comboBox.show());
	        type(KeyCode.DOWN); // Simulate pressing the DOWN arrow key
	        type(KeyCode.TAB); // Simulate pressing the ENTER key 
    }

}
