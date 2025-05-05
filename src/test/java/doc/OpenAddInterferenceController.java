package doc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.Main;


public class OpenAddInterferenceController extends ApplicationTest {
	
	public void start (Stage stage) throws Exception {
		new Main().start(stage);
	}
	
	@Test
	public void openAddInterferenceController () {
	
		Button btnSignUp = lookup("#btnSignUp").query();
		assertNotNull(btnSignUp);
		clickOn(btnSignUp);
		sleep(500);
	
		// Combobox de busca de endereço
		ComboBox<?> cbAddress = lookup("#cbAddress").query();
		clickOn(cbAddress).write("Chácara 21");

		sleep(500);
		// Seleciona o primeiro ítem do combobox depois da busca
		selectFirstItemInComboBox(cbAddress);
		
		// Endereço: Núcleo Rural Lago Oeste, Rua 10, Chácara 21, Sobradinho
		// Endereço: Conjunto Habitacional Vicente Pires, Chácara 172, Rua 10, Lote 27
		
		// Abre tela de interferência
		Button btnInterference = lookup("#btnInterference").query();
		assertNotNull(btnInterference);
		clickOn(btnInterference);
		sleep(500);
		
		// Fecha tela de interferência
		Button btnClose = lookup("#btnClose").query();
		assertNotNull(btnClose);
		clickOn(btnClose);
		sleep(500);

		// Limpa o combobox para a busca do segundo endereço
		clearComboBox(cbAddress);
		sleep(500);
		
		// Busca o segundo endereço
		clickOn(cbAddress).write("Chácara 172");
		sleep(500);
		
		// Seleciona endereço que atende à busca
		selectFirstItemInComboBox(cbAddress);
		
		// Abre novamente a tela de interferência
		assertNotNull(btnInterference);
		clickOn(btnInterference);
		sleep(1000);
		
		// Fecha a tela de interferência
		assertNotNull(btnClose);
		clickOn(btnClose);


		sleep(1000);
		
	}
	
	private void selectFirstItemInComboBox(ComboBox<?> comboBox) {
		interact(() -> comboBox.show());
		type(KeyCode.DOWN); // Simulate pressing the DOWN arrow key
		type(KeyCode.TAB); // Simulate pressing the ENTER key
	}
	
	private void clearComboBox(ComboBox<?> comboBox) {
		interact(() -> {
			comboBox.getSelectionModel().clearSelection();
			comboBox.setValue(null);
			if (comboBox.isEditable()) {
				comboBox.getEditor().clear();
			}
		});
	}

	

}
