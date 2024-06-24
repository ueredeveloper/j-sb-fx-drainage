package doc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import models.DocumentoTipo;

public class SaveUsingAddAddressController extends ApplicationTest {

	public void start(Stage stage) throws Exception {
		new Main().start(stage);
	}

	@Test
	public void editDocument() {

		Button btnSignUp = lookup("#btnSignUp").query();
		assertNotNull(btnSignUp);
		clickOn(btnSignUp);
		
		ComboBox<DocumentoTipo> cbDocType = lookup("#cbDocType").query();
		Node item = lookup(".list-cell").nth(0).query();
		assertNotNull(item);
		
		// Abre o ComboBox
		clickOn(item);

		// Seleciona algo dentro do ComboBox
		Platform.runLater(() -> {
			for (DocumentoTipo tipo : cbDocType.getItems()) {
				if (tipo.getDtDescricao().equals("Ofício")) {
					cbDocType.getSelectionModel().select(tipo);
					break;
				}
			}
		});
		// fechar o ComboBox
		clickOn(item);
		sleep(500);
		
		TextField tfNumber = lookup("#tfNumber").query();
		tfNumber.setText("11111/2024");
		sleep(1000);
		TextField tfNumberSei = lookup("#tfNumberSei").query();
		tfNumberSei.setText("111111");
		sleep(1000);
		
		FontAwesomeIconView btnAddress = lookup("#btnAddress").query();
		assertNotNull(btnAddress);
		clickOn(btnAddress);
		
		
		
		TextField tfStreet = lookup("#tfStreet").query();
		tfStreet.setText("Quadra 333, Lote 000");
		
		TextField tfNeighborhood = lookup("#tfNeighborhood").query();
		tfNeighborhood.setText("Taguatinga Norte");
		
		TextField tfCity = lookup("#tfCity").query();
		tfCity.setText("São Paulo");
		
		
		TextField tfPostalCode = lookup("#tfPostalCode").query();
		tfPostalCode.setText("72.456-56");
		
		sleep(500);
		
		Button btnAdd = lookup("#btnAdd").query();
		assertNotNull(btnAdd);
		clickOn(btnAdd);
		
		sleep(500);
		
		
		Button btnClose = lookup("#btnClose").query();
		assertNotNull(btnClose);
		clickOn(btnClose);
		
		// Verificar se há algum listener do mapa para as coordenadas, não está funcionando
		/*TextField tfLatitude = lookup("#tfLatitude").query();
		tfLatitude.setText("-15.123");
		sleep(1000);

		TextField tfLongitude = lookup("#tfLongitude").query();
		tfLongitude.setText("-47.123");
		sleep(1000);*/
		
		ComboBox<?> cbUser = lookup("#cbUser").query();
		clickOn(cbUser).write("Carlos Gardel");
		
		Platform.runLater(() -> {

			clickOn(cbUser);
			cbUser.getSelectionModel().select(0);
		});
		sleep(500);
		
		ComboBox<?> cbProcess = lookup("#cbProcess").query();
		clickOn(cbProcess).write("195.456.789/2016");
		
		Platform.runLater(() -> {

			clickOn(cbProcess);
			cbProcess.getSelectionModel().select(0);
		});
		sleep(500);
		
		
		ComboBox<?> cbAttachment = lookup("#cbAttachment").query();
		clickOn(cbAttachment).write("165.456.855/2014");
		
		Platform.runLater(() -> {

			clickOn(cbAttachment);
			cbAttachment.getSelectionModel().select(0);
		});
		sleep(500);
		
		
		Button btnSave = lookup("#btnSave").query();
		assertNotNull(btnSave);
		clickOn(btnSave);
		
		
		
		sleep(2000);
	}
		
}
