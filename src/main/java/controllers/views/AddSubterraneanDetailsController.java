package controllers.views;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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

		// Adiciona cadastro de finalidade
		addPurpouseComponents(0);

	}

	public void addPurpouseComponents(int index) {
		String[] strPurpouses = { "Finalidade", "Subfinalidade", "Quantidade", "Consumo", "Subtotal", "B1", "B2" };

		JFXTextField tfPurpouse = new JFXTextField();
		JFXTextField tfSubpurpose = new JFXTextField();
		JFXTextField tfQuantity = new JFXTextField();
		JFXTextField tfConsumption = new JFXTextField();
		JFXTextField tfSubtotal = new JFXTextField();
		// Criando o ícone FontAwesome
		FontAwesomeIconView btnCalculate = new FontAwesomeIconView();
		FontAwesomeIconView btnPlus = new FontAwesomeIconView();
		FontAwesomeIconView btnMinus = new FontAwesomeIconView();

		btnCalculate.setGlyphName("EDIT");
		btnCalculate.getStyleClass().addAll("icon-light-dark", "icons");

		btnPlus.setGlyphName("PLUS");
		btnPlus.getStyleClass().addAll("icon-light-dark", "icons");

		btnMinus.setGlyphName("MINUS");
		btnMinus.getStyleClass().addAll("icon-light-dark", "icons");

		// Cria finalidade
		Finalidade obj = new Finalidade();
		// Adiciona finalidade com única na lista (Set)
		purpouses.add(obj);

		tfPurpouse.setPrefHeight(30.0);
		tfPurpouse.setPrefWidth(200.0);
		tfPurpouse.getStyleClass().add("text-field-subpurpose");
		tfPurpouse.setPromptText("Finalidade");

		tfSubpurpose.setPrefHeight(30.0);
		tfSubpurpose.setPrefWidth(200.0);
		tfSubpurpose.getStyleClass().add("text-field-subpurpose");
		tfSubpurpose.setPromptText("Subfinalidade");

		tfQuantity.setPrefHeight(30.0);
		tfQuantity.setPrefWidth(200.0);
		tfQuantity.getStyleClass().add("text-field-subpurpose");
		tfQuantity.setPromptText("Quantidade");

		tfConsumption.setPrefHeight(30.0);
		tfConsumption.setPrefWidth(200.0);
		tfConsumption.getStyleClass().add("text-field-subpurpose");
		tfConsumption.setPromptText("Consumo");

		tfSubtotal.setPrefHeight(30.0);
		tfSubtotal.setPrefWidth(200.0);
		tfSubtotal.getStyleClass().add("text-field-subpurpose");
		tfSubtotal.setPromptText("Subtotal");

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

		});

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

		});

		tfQuantity.textProperty().addListener((observable, oldValue, newValue) -> {

			obj.setQuantidade(Double.parseDouble(newValue));

			// You can add custom logic here, for example:
			if (newValue.length() > 20) {
				// Since you don't have access to an Event, just use the tfPurpouse as the
				// source
				Node source = tfSubpurpose; // The source is tfPurpouse (JFXTextField)
				Stage ownerStage = (Stage) source.getScene().getWindow();

				// Display toast message
				String toastMsg = "O texto de quantidade está muito longo!!!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			}

		});

		tfConsumption.textProperty().addListener((observable, oldValue, newValue) -> {

			obj.setConsumo(Double.parseDouble((newValue)));

			// You can add custom logic here, for example:
			if (newValue.length() > 20) {
				// Since you don't have access to an Event, just use the tfPurpouse as the
				// source
				Node source = tfSubpurpose; // The source is tfPurpouse (JFXTextField)
				Stage ownerStage = (Stage) source.getScene().getWindow();

				// Display toast message
				String toastMsg = "O texto de consumo está muito longo!!!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			}

			System.out.println(obj.toString() + " size: " + purpouses.size());

		});

		btnCalculate.setOnMouseClicked(event -> {
			Double x = Double.parseDouble(tfConsumption.getText());
			Double y = Double.parseDouble(tfQuantity.getText());

			Double result = x * y;
			obj.setSubtotal(result);

			tfSubtotal.setText(result.toString());

		});

		btnPlus.setOnMouseClicked(event -> {
			addPurpouseComponents(index + 1);

		});

		gpPurpouses.add(tfPurpouse, 0, index);
		gpPurpouses.add(tfSubpurpose, 1, index);
		gpPurpouses.add(tfQuantity, 2, index);
		gpPurpouses.add(tfConsumption, 3, index);
		gpPurpouses.add(tfSubtotal, 4, index);
		gpPurpouses.add(btnCalculate, 5, index);
		gpPurpouses.add(btnPlus, 6, index);
		// Na primeira linha não coloca sinal de menos. O mínimo é uma linha, não
		// podendo retirar a primeira.
		if (index != 0) {
			gpPurpouses.add(btnMinus, 7, index);
		}

	}

	public Finalidade getFinalidade() {

		Finalidade fin = new Finalidade();

		return null;
	}

}
