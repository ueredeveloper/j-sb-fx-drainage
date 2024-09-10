package controllers.views;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import enums.StaticData;
import enums.ToastType;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Finalidade;
import models.TipoPoco;

public class AddSubterraneanDetailsController implements Initializable {

	private static AddInterferenceController instance;

	public static AddInterferenceController getInstance() {
		return instance;
	}

	@FXML
	private AnchorPane apContainer;

	@FXML
	private JFXComboBox<TipoPoco> cbWellType;

	@FXML
	private JFXComboBox<?> cbSubsystem;

	@FXML
	private JFXTextField tfCodeSystem;

	@FXML
	private JFXTextField tfSystemFlow;

	@FXML
	private JFXTextField tfSystemTest;

	@FXML
	private JFXTextField tfSystemGrant;

	@FXML
	private JFXTextField tfStaticLevel;

	@FXML
	private JFXTextField tfDynamicLevel;

	@FXML
	private JFXTextField tfWaterDepth;

	@FXML
	private GridPane gpPurpouses;

	ObservableList<TipoPoco> obsTypesOfWells;

	// Use set para não repetir finalidade
	Set<Finalidade> purpouses = new HashSet<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		obsTypesOfWells = StaticData.INSTANCE.getTypesOfWells();
		cbWellType.setItems(obsTypesOfWells);

		String[] strPurpouses = { "Finalidade", "Subfinalidade" };

		JFXTextField tfPurpouse = new JFXTextField();
		JFXTextField tfSubpurpose = new JFXTextField();
		// Cria finalidade
		Finalidade obj = new Finalidade();

		for (String item : strPurpouses) {

			tfPurpouse.setPrefHeight(30.0);
			tfPurpouse.setPrefWidth(200.0);
			tfPurpouse.getStyleClass().add("text-field-subpurpose");
			tfPurpouse.setPromptText(item);

			tfSubpurpose.setPrefHeight(30.0);
			tfSubpurpose.setPrefWidth(200.0);
			tfSubpurpose.getStyleClass().add("text-field-subpurpose");
			tfSubpurpose.setPromptText(item);

			// Adiciona finalidade com única na lista (Set)
			purpouses.add(obj);

			// Não pemite criar dois listeners
			if (item == "Finalidade") {

				tfPurpouse.textProperty().addListener((observable, oldValue, newValue) -> {

					obj.setFinalidade(newValue);

					// You can add custom logic here, for example:
					if (newValue.length() > 20) {
						// Since you don't have access to an Event, just use the tfPurpouse as the
						// source
						Node source = tfPurpouse; // The source is tfPurpouse (JFXTextField)
						Stage ownerStage = (Stage) source.getScene().getWindow();

						// Display toast message
						String toastMsg = "O texto de finalidade está muito longo!!!";
						utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
					}

					purpouses.forEach(p -> System.out.println(
							"fin " + p.getFinalidade() + "sub " + p.getSubfinalidade() + " size: " + purpouses.size()));
				});

			}

			if (item == "Subfinalidade") {
				tfSubpurpose.textProperty().addListener((observable, oldValue, newValue) -> {

					obj.setSubfinalidade(newValue);

					// You can add custom logic here, for example:
					if (newValue.length() > 20) {
						// Since you don't have access to an Event, just use the tfPurpouse as the
						// source
						Node source = tfSubpurpose; // The source is tfPurpouse (JFXTextField)
						Stage ownerStage = (Stage) source.getScene().getWindow();

						// Display toast message
						String toastMsg = "O texto de subfinalidade está muito longo!!!";
						utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
					}

					purpouses.forEach(p -> System.out.println(
							"fin --> " + p.getFinalidade() + " sub ---> " + p.getSubfinalidade() + " size: " + purpouses.size()));
				});
			}

		} // fim for

		gpPurpouses.add(tfPurpouse, 0, 0);
		gpPurpouses.add(tfSubpurpose, 1, 0);

	}

	public Finalidade getFinalidade() {

		Finalidade fin = new Finalidade();

		return null;
	}

}
