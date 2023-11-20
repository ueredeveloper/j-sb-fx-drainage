package doc;

import static org.junit.Assert.assertNotNull;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;

import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.Main;
import models.DocumentoTipo;

public class SelectDocumentTypeTest extends ApplicationTest{
	
	public void start (Stage stage) throws Exception {
		new Main().start(stage);
	}
	
	/*@Test
	public void test () {
		javafx.scene.control.Button btnSignUp = lookup("#btnSignUp").query();
		assertNotNull(btnSignUp);
		clickOn(btnSignUp);
		
		ComboBox<DocumentoTipo> cbDocType = lookup("#cbDocType").query();
		assertNotNull(cbDocType);
		
		Node item = lookup(".list-cell").nth(0).query();
		assertNotNull(item);
		clickOn(item);
		assert
		//assertEquals("Ofício", cbDocType.getEditor().getText());
		
		System.out.println(cbDocType.getSelectionModel().getSelectedItem());
		
	}*/
	
	/*
	@Test
    public void testSelectComboBoxItem() {
		javafx.scene.control.Button btnSignUp = lookup("#btnSignUp").query();
		assertNotNull(btnSignUp);
		clickOn(btnSignUp);
		
		ComboBox<DocumentoTipo> cbDocType = lookup("#cbDocType").query();
		assertNotNull(cbDocType);
		
		DocumentoTipo dt = new DocumentoTipo(2, "Ofício");
        Platform.runLater(() -> cbDocType.getSelectionModel().select(dt));
        sleep(500); // Wait for the selection to take effect

        verifyThat(cbDocType, hasSelectedItem(dt));
        System.out.println("Selected item: " + cbDocType.getSelectionModel().getSelectedItem());
    }*/
	
	@Test
    public void testSelectOficioInComboBox() {
		
		javafx.scene.control.Button btnSignUp = lookup("#btnSignUp").query();
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
        sleep(500); // Wait for the selection to take effect

        verifyThat(cbDocType, hasSelectedItem(new DocumentoTipo(2, "Ofício")));
        sleep(1000);
        System.out.println("Selected item: " + cbDocType.getSelectionModel().getSelectedItem());
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }
}
