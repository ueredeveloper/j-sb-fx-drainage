package controllers.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
import models.Demanda;
import models.Finalidade;
import models.TipoFinalidade;
import models.TipoPoco;
import services.FinalidadeService;
import services.ServiceResponse;
import utilities.URLUtility;

public class AddSubterraneanDetailsController implements Initializable {
	

	private String localUrl;

	public AddSubterraneanDetailsController() {
		this.localUrl = URLUtility.getURLService();
	}

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
	private GridPane requestedPurposesGrid, authorizedPurposesGrid, gpRequestedDemands, gpAuthorizedDemands;

	ObservableList<TipoPoco> obsTypesOfWells;

	@FXML
	FontAwesomeIconView btnTotalCalculate;

	@FXML
	FontAwesomeIconView btnRequestedTotalCalculate, btnAuthorizedTotalCalculate;
	@FXML
	private JFXTextField tfTotalRequestedConsumption, tfTotalAuthorizedConsumption;

	PurpousesWrapper purpousesWrapper = new PurpousesWrapper(new HashSet<>());

	DemandsWrapper demandsWrapper = new DemandsWrapper(new HashSet<>());

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		obsTypesOfWells = StaticData.INSTANCE.getTypesOfWells();
		cbWellType.setItems(obsTypesOfWells);

		// Adiciona cadastro de finalidade
		addPurpouseRow(0, requestedPurposesGrid, tfTotalRequestedConsumption, btnRequestedTotalCalculate,
				new TipoFinalidade(1L), new Finalidade());
		addPurpouseRow(0, authorizedPurposesGrid, tfTotalAuthorizedConsumption, btnAuthorizedTotalCalculate,
				new TipoFinalidade(2L), new Finalidade());

		// Adiciona demandas vazias para finalidades requeridas
		for (int month = 1; month <= 12; month++) {

			Demanda demand = new Demanda(month, new TipoFinalidade(1L));
			addDemandColumn(gpRequestedDemands, month, demand);

			demandsWrapper.getDemands().add(demand);
		}

		// Adiciona demandas vazias para finalidades autorizadas
		for (int month = 1; month <= 12; month++) {
			Demanda demand = new Demanda(month, new TipoFinalidade(2L));
			addDemandColumn(gpAuthorizedDemands, month, demand);

			demandsWrapper.getDemands().add(demand);
		}


	}

	public void addDemandColumn(GridPane gridPane, int month, Demanda demand) {

		JFXTextField tfFlow = new JFXTextField();
		JFXTextField tfTime = new JFXTextField();
		JFXTextField tfPeriod = new JFXTextField();

		tfFlow.getStyleClass().addAll("tf-demands");
		tfTime.getStyleClass().addAll("tf-demands");
		tfPeriod.getStyleClass().addAll("tf-demands");

		// Limpa a célula para adicionar novo Textfield.
		clearGridCell(gridPane, month, 1);
		// Adiciona Textfields por célula
		gridPane.add(tfFlow, month, 1);
		clearGridCell(gridPane, month, 2);
		gridPane.add(tfTime, month, 2);
		clearGridCell(gridPane, month, 3);
		gridPane.add(tfPeriod, month, 3);

		if (demand.getId() != null) {
			// Se não, é uma demanda buscada no banco e deve-se preencher os campos
			tfFlow.setText(demand.getVazao().toString());
			tfTime.setText(String.valueOf(demand.getTempo()));
			tfPeriod.setText(String.valueOf(demand.getPeriodo()));
		}

		DemandWrapper demandWrapper = new DemandWrapper(demand);
		// Listeners
		tfFlow.textProperty().addListener((observable, oldValue, newValue) -> {
			demandWrapper.getDemand().setVazao(Double.parseDouble(newValue));
		
		});
		tfTime.textProperty().addListener((observable, oldValue, newValue) -> {
			demandWrapper.getDemand().setTempo(Integer.valueOf(newValue));
		
		});
		tfPeriod.textProperty().addListener((observable, oldValue, newValue) -> {
			demandWrapper.getDemand().setPeriodo(Integer.valueOf(newValue));
		
		});

	}

	// Verifica se há filhos na célula do GridPane, se houver limpa a célula onde está um TextField.
	private void clearGridCell(GridPane gridPane, int column, int row) {
	    // Check if the cell has any children (avoid null pointer exception)
	    boolean hasChildren = gridPane.getChildren().stream().anyMatch(node -> {
	        Integer nodeColumn = GridPane.getColumnIndex(node);
	        Integer nodeRow = GridPane.getRowIndex(node);
	        return nodeColumn != null && nodeRow != null && nodeColumn == column && nodeRow == row;
	    });

	    // Only attempt to remove nodes if children are present in the specified cell
	    if (hasChildren) {
	        gridPane.getChildren().removeIf(node -> {
	            Integer nodeColumn = GridPane.getColumnIndex(node);
	            Integer nodeRow = GridPane.getRowIndex(node);
	            return nodeColumn != null && nodeRow != null && nodeColumn == column && nodeRow == row;
	        });
	    }
	}


	/**
	 * Adiciona linhas de finalidades, cada linha tem uma finalidade, subfinalidade,
	 * quantidade, consumo e total por finalidade.
	 * 
	 * @param index
	 * @param gridPane
	 * @param tfTotalConsumption
	 * @param btnTotalConsumption
	 * @param typeOfPurpouse
	 * @param purpouse
	 */
	public void addPurpouseRow(int index, GridPane gridPane, JFXTextField tfTotalConsumption,
			FontAwesomeIconView btnTotalConsumption, TipoFinalidade typeOfPurpouse, Finalidade purpouse) {

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

		PurpouseWrapper purpouseWrapper = new PurpouseWrapper();

		// Cria finalidade
		if (purpouse == null || purpouse.getTipoFinalidade() == null) {

			purpouse = new Finalidade(typeOfPurpouse);

			purpouseWrapper.setPurpouse(purpouse);
			// Adiciona finalidade com única na lista (Set)

			purpousesWrapper.getPurpouses().add(purpouseWrapper.getPurpouse());

		} else {

			purpouseWrapper.setPurpouse(purpouse);

			purpousesWrapper.getPurpouses().add(purpouseWrapper.getPurpouse());

			tfPurpouse.setText(purpouseWrapper.getPurpouse().getFinalidade());
			tfSubpurpose.setText(purpouseWrapper.getPurpouse().getSubfinalidade());
			tfQuantity.setText(purpouseWrapper.getPurpouse().getQuantidade().toString());
			tfConsumption.setText(purpouseWrapper.getPurpouse().getConsumo().toString());
			tfTotal.setText(purpouseWrapper.getPurpouse().getTotal().toString());
		}

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

			purpouseWrapper.getPurpouse().setFinalidade(newValue);

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

			purpouseWrapper.getPurpouse().setSubfinalidade(newValue);

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

			purpouseWrapper.getPurpouse().setQuantidade(Double.parseDouble(newValue));

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

			purpouseWrapper.getPurpouse().setConsumo(Double.parseDouble((newValue)));

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
			purpouseWrapper.getPurpouse().setTotal(result);

			tfTotal.setText(result.toString());

		});

		btnPlus.setOnMouseClicked(event -> {

			// Busca a última linha com componente para adicionar mais uma linha com
			// componentes na sequêcia.
			Integer rowIndex = null;
			int lastRow = -1;

			for (javafx.scene.Node child : gridPane.getChildren()) {
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

			addPurpouseRow(rowIndex + 1, gridPane, tfTotalConsumption, btnTotalConsumption, typeOfPurpouse, null);

		});

		btnMinus.setOnMouseClicked(event -> {

			Integer rowIndex = GridPane.getRowIndex(btnMinus);

			if (rowIndex == null) {
				rowIndex = 0; // If the row index is not set, it defaults to 0
			}

			// Remove the row from the gridPane
			removeRowAndShift(gridPane, rowIndex);

			// Retrieve the purpose object to be deleted
			Finalidade purpouseToDelete = purpouseWrapper.getPurpouse();

			// Check if the purpose object and its ID are not null before attempting to
			// delete
			if (purpouseToDelete != null && purpouseToDelete.getId() != null) {
				deletePurpouse(btnMinus, purpouseToDelete);
			}

			// Remove the purpose from the Set
			purpousesWrapper.getPurpouses().remove(purpouseToDelete);
		});

		btnTotalConsumption.setOnMouseClicked(event -> {
			final Double[] total = { 0.0 }; // Usando um array para contornar a limitação de variáveis finais
			purpousesWrapper.getPurpouses().forEach(p -> {
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
			purpouseWrapper.getPurpouse().setTotal(result);

			tfTotal.setText(result.toString());

		});

		gridPane.add(tfPurpouse, 0, index);
		gridPane.add(tfSubpurpose, 1, index);
		gridPane.add(tfQuantity, 2, index);
		gridPane.add(tfConsumption, 3, index);
		gridPane.add(tfTotal, 4, index);
		gridPane.add(btnCalculate, 5, index);
		gridPane.add(btnPlus, 6, index);
		// Na primeira linha não coloca sinal de menos. O mínimo é uma linha, não
		// podendo retirar a primeira.
		if (index != 0) {
			gridPane.add(btnMinus, 7, index);
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

	public Set<Finalidade> getPurpouses() {

		return purpousesWrapper.getPurpouses();
	}

	public void setPurpouses(Set<Finalidade> newPurpouses) {
		purpousesWrapper.setPurpouses(newPurpouses);

		requestedPurposesGrid.getChildren().clear();
		authorizedPurposesGrid.getChildren().clear();

		AtomicInteger reqIndex = new AtomicInteger(0);
		AtomicInteger autIndex = new AtomicInteger(0);

		purpousesWrapper.getPurpouses().forEach(item -> {
			if (item.getTipoFinalidade().getId() == 1) {

				addPurpouseRow(reqIndex.getAndIncrement(), requestedPurposesGrid, tfTotalRequestedConsumption,
						btnRequestedTotalCalculate, item.getTipoFinalidade(), item);
			} else {
		
				addPurpouseRow(autIndex.getAndIncrement(), authorizedPurposesGrid, tfTotalAuthorizedConsumption,
						btnAuthorizedTotalCalculate, item.getTipoFinalidade(), item);
			}
		});
	}

	public void fillDemandsDetails(Set<Demanda> newSet) {

		demandsWrapper.setDemands(newSet);

		if (demandsWrapper.getDemands() != null) {

			// Demanda por tipo de finalidade requerida
			List<Demanda> demandsByRequestedTypePurpouse = demandsWrapper.getDemands().stream()
					.filter(d -> d.getTipoFinalidade().getId() == 1L).sorted(Comparator.comparing(d -> d.getMes()))
					.collect(Collectors.toList());

			demandsByRequestedTypePurpouse.forEach(demand -> {
				//addDemandsCollumns(gpRequestedDemands, demand, demand.getTipoFinalidade());
				addDemandColumn(gpRequestedDemands, demand.getMes(), demand);
			});

			// Demanda por tipo de finalidade autorizada
			List<Demanda> demandsByAuthorizedTypePurpouse = demandsWrapper.getDemands().stream()
					.filter(d -> d.getTipoFinalidade().getId() == 2L).sorted(Comparator.comparing(d -> d.getMes()))
					.collect(Collectors.toList());

			demandsByAuthorizedTypePurpouse.forEach(demand -> {
				//addDemandsCollumns(gpAuthorizedDemands, demand, demand.getTipoFinalidade());
				addDemandColumn(gpAuthorizedDemands, demand.getMes(), demand);
			});

		}

	}

	public Set<Demanda> getDemands() {
		return demandsWrapper.getDemands();
	}

	// Modifica as finalidade dentro de uma expressão lambda.
	public class PurpouseWrapper {

		private Finalidade purpouse;

		public PurpouseWrapper() {
		}

		public Finalidade getPurpouse() {
			return purpouse;
		}

		public void setPurpouse(Finalidade purpouse) {
			this.purpouse = purpouse;
		}
	}

	public class PurpousesWrapper {
		// Use set para não repetir finalidade
		Set<Finalidade> purpouses;

		public PurpousesWrapper(Set<Finalidade> purpouses) {
			super();
			this.purpouses = purpouses;
		}

		public Set<Finalidade> getPurpouses() {
			return purpouses;
		}

		public void setPurpouses(Set<Finalidade> purpouses) {
			this.purpouses = purpouses;
		}

	}

	public class DemandWrapper {
		private Demanda demand;

		public DemandWrapper() {
			super();
		}

		public DemandWrapper(Demanda demand) {
			super();
			this.demand = demand;
		}

		public Demanda getDemand() {
			return demand;
		}

		public void setDemand(Demanda demand) {
			this.demand = demand;
		}

		@Override
		public String toString() {
			return "Demanda: Vazão = " + demand.getVazao() + " Tempo: " + demand.getTempo() + " Perído: "
					+ demand.getPeriodo() + "\n";
		}

	}

	public class DemandsWrapper {
		// Use set para não repetir finalidade
		Set<Demanda> demands;

		public DemandsWrapper(Set<Demanda> demands) {
			super();
			this.demands = demands;
		}

		public Set<Demanda> getDemands() {
			return demands;
		}

		public void setDemands(Set<Demanda> demands) {
			this.demands = demands;
		}

		// Listar por mês, assim preencher o grid de demanda mês a mês, do mes 1 até o
		// mes 12.
		public List<Demanda> sortByMes() {
			// Convert Set to List
			List<Demanda> sortedList = new ArrayList<>(demands);

			// Sort by 'mes' field
			sortedList.sort(Comparator.comparingInt(Demanda::getMes));

			return sortedList;
		}

		@Override
		public String toString() {
			// Use stream to concatenate all demands into a single string
			return demands.stream().map(Demanda::toString) // Call Demanda's toString method for each demand
					.collect(Collectors.joining(", ", "Demands: [", "]")); // Join all results with commas
		}

	}

	public void deletePurpouse(Node source, Finalidade purpouse) {

		try {
			FinalidadeService documentService = new FinalidadeService(localUrl);

			ServiceResponse<?> serviceResponse = documentService.deleteById(purpouse.getId());

			if (serviceResponse.getResponseCode() == 200) {

				if (source != null && source.getScene() != null && source.getScene().getWindow() != null) {
					Stage ownerStage = (Stage) source.getScene().getWindow();
					String toastMsg = "Finalidade deletada com sucesso!";
					utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);
				}

				// retira objecto da tabela de documentos tvDocs
				// tvDocs.getItems().remove(selectedDocument);

			} else {
				// Informa erro em deletar

				if (source != null && source.getScene() != null && source.getScene().getWindow() != null) {
					Stage ownerStage = (Stage) source.getScene().getWindow();
					String toastMsg = "Erro ao deletar!";
					utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
