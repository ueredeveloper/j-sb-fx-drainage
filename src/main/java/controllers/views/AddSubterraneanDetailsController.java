package controllers.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
	private JFXTextField tfTotalConsumption;

	@FXML
	private GridPane gpPurpouses;

	ObservableList<TipoPoco> obsTypesOfWells;

	// Use set para não repetir finalidade
	Set<Finalidade> purpouses = new HashSet<>();
	
	@FXML
	FontAwesomeIconView btnTotalCalculate;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		obsTypesOfWells = StaticData.INSTANCE.getTypesOfWells();
		cbWellType.setItems(obsTypesOfWells);

		// Adiciona cadastro de finalidade
		addRow(0);

	}

	public void addRow(int index) {

		JFXTextField tfPurpouse = new JFXTextField();
		JFXTextField tfSubpurpose = new JFXTextField();
		JFXTextField tfQuantity = new JFXTextField();
		JFXTextField tfConsumption = new JFXTextField();
		JFXTextField tfTotal = new JFXTextField();
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

		tfPurpouse.setPrefHeight(40.0);
		tfPurpouse.setMinHeight(40.00);
		tfPurpouse.setPrefWidth(200.0);
		tfPurpouse.getStyleClass().add("text-field-subpurpose");
		tfPurpouse.setPromptText("Finalidade");

		tfSubpurpose.setPrefHeight(40.0);
		tfSubpurpose.setMinHeight(40.0);
		tfSubpurpose.setPrefWidth(200.0);
		tfSubpurpose.getStyleClass().add("text-field-subpurpose");
		tfSubpurpose.setPromptText("Subfinalidade");

		tfQuantity.setPrefHeight(40.0);
		tfQuantity.setMinHeight(40.0);
		tfQuantity.setPrefWidth(200.0);
		tfQuantity.getStyleClass().add("text-field-subpurpose");
		tfQuantity.setPromptText("Quantidade");

		tfConsumption.setPrefHeight(40.0);
		tfConsumption.setMinHeight(40.0);
		tfConsumption.setPrefWidth(200.0);
		tfConsumption.getStyleClass().add("text-field-subpurpose");
		tfConsumption.setPromptText("Consumo");

		tfTotal.setPrefHeight(40.0);
		tfTotal.setMinHeight(40.0);
		tfTotal.setPrefWidth(200.0);
		tfTotal.getStyleClass().add("text-field-subpurpose");
		tfTotal.setPromptText("Total");

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

		});

		btnCalculate.setOnMouseClicked(event -> {
			Double x = Double.parseDouble(tfConsumption.getText());
			Double y = Double.parseDouble(tfQuantity.getText());

			Double result = x * y;
			obj.setTotal(result);

			tfTotal.setText(result.toString());

		});

		btnPlus.setOnMouseClicked(event -> {

			// Busca a última linha com componente para adicionar mais uma linha com
			// componentes na sequêcia.
			Integer rowIndex = null;
			int lastRow = -1;

			for (javafx.scene.Node child : gpPurpouses.getChildren()) {
				// Get the row index of the current child
				rowIndex = GridPane.getRowIndex(child);

				// If rowIndex is null, it defaults to 0
				if (rowIndex == null) {
					rowIndex = 0;
				}

				// Update the lastRow if the current row is greater
				if (rowIndex > lastRow) {
					lastRow = rowIndex;
				}
			}

			addRow(rowIndex + 1);

		});

		btnMinus.setOnMouseClicked(event -> {

			Integer rowIndex = GridPane.getRowIndex(btnMinus);

			if (rowIndex == null) {
				rowIndex = 0; // If the row index is not set, it defaults to 0
			}

			// Remove a linha de finalidade
			removeRowAndShift(gpPurpouses, rowIndex);
			// Remove do Set a finalidade removida
			purpouses.remove(obj);

		});
		
		btnTotalCalculate.setOnMouseClicked(event -> {
			final Double[] total = {0.0};  // Usando um array para contornar a limitação de variáveis finais
		    purpouses.forEach(p -> {
		        Double subtotal = p.getTotal();
		        if (subtotal != null) {
		            total[0] += subtotal;
		        }
		    });
		    
		    tfTotalConsumption.setText(total[0].toString());
		    
		    
		});
		
		
			btnCalculate.setOnMouseClicked(event -> {
			Double x = Double.parseDouble(tfConsumption.getText());
			Double y = Double.parseDouble(tfQuantity.getText());

			Double result = x * y;
			obj.setTotal(result);

			tfTotal.setText(result.toString());

		});
		

		gpPurpouses.add(tfPurpouse, 0, index);
		gpPurpouses.add(tfSubpurpose, 1, index);
		gpPurpouses.add(tfQuantity, 2, index);
		gpPurpouses.add(tfConsumption, 3, index);
		gpPurpouses.add(tfTotal, 4, index);
		gpPurpouses.add(btnCalculate, 5, index);
		gpPurpouses.add(btnPlus, 6, index);
		// Na primeira linha não coloca sinal de menos. O mínimo é uma linha, não
		// podendo retirar a primeira.
		if (index != 0) {
			gpPurpouses.add(btnMinus, 7, index);
		}

	}

	// Method to remove a row and shift other rows up
	public static void removeRowAndShift(GridPane gridPane, int rowIndex) {
		// Create a list to store nodes that should be shifted
		ArrayList<javafx.scene.Node> nodesToShift = new ArrayList<>();

		// Use an iterator to remove nodes in the specified row
		Iterator<javafx.scene.Node> iterator = gridPane.getChildren().iterator();

		while (iterator.hasNext()) {
			javafx.scene.Node node = iterator.next();

			// Get the row index of the node
			Integer row = GridPane.getRowIndex(node);
			if (row == null) {
				row = 0; // Default to row 0 if no row index is set
			}

			// If the node is in the row to be removed, remove it
			if (row == rowIndex) {
				iterator.remove();
			} else if (row > rowIndex) {
				// If the node is in a row below the one to be removed, it needs to be shifted
				// up
				nodesToShift.add(node);
			}
		}

		// Shift the remaining nodes up by 1 row
		for (javafx.scene.Node node : nodesToShift) {
			int currentRow = GridPane.getRowIndex(node);
			GridPane.setRowIndex(node, currentRow - 1);
		}
	}

	public Set<Finalidade> getPurpouses () {

		return purpouses;
	}

}
