package controllers.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import enums.StaticData;
import enums.ToastType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Demanda;
import models.Finalidade;
import models.Subterranea;
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
	private JFXComboBox<Integer> cbReqTimeFlow;

	@FXML
	private AnchorPane apContainer;

	@FXML
	private JFXComboBox<TipoPoco> cbWellType;

	@FXML
	private JFXComboBox<String> cbConcessionaire;

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
	private JFXTextField tfTotalRequestedConsumption, tfTotalAuthorizedConsumption;

	@FXML
	private JFXButton btnCalculateReqFin;

	@FXML
	private JFXButton btnFillReqFlow;

	@FXML
	private JFXButton btnFillReqTime;

	@FXML
	private JFXButton btnFillReqPeriod;

	@FXML
	private JFXButton btnCalculateAuthFin;

	@FXML
	private JFXButton btnFillAuthFlow;

	@FXML
	private JFXComboBox<?> cbAuthTimeFlow;

	@FXML
	private JFXButton btnFillAuthTime, btnCopyReqDemand;

	@FXML
	private JFXButton btnFillAuthPeriod;

	PurpousesWrapper purpousesWrapper = new PurpousesWrapper(new HashSet<>());

	DemandsWrapper demandsWrapper = new DemandsWrapper(new HashSet<>());

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Tipo de poço, se manual ou tubular
		obsTypesOfWells = StaticData.INSTANCE.getTypesOfWells();
		cbWellType.setItems(obsTypesOfWells);
		// Se há Caesb ou não
		cbConcessionaire.getItems().addAll("Sim", "Não");

		// Adiciona cadastro de finalidade
		addPurpouseRow(0, requestedPurposesGrid, tfTotalRequestedConsumption, btnCalculateReqFin,
				new TipoFinalidade(1L), new Finalidade());
		addPurpouseRow(0, authorizedPurposesGrid, tfTotalAuthorizedConsumption, btnCalculateAuthFin,
				new TipoFinalidade(2L), new Finalidade());

		// Cria lista de demandas requeridas e autorizadas vazias.
		for (int month = 1; month <= 12; month++) {
			Demanda demand = new Demanda(month, new TipoFinalidade(1L));
			demandsWrapper.getDemands().add(demand);
		}

		// Adiciona demandas vazias para finalidades autorizadas
		for (int month = 1; month <= 12; month++) {
			Demanda demand = new Demanda(month, new TipoFinalidade(2L));
			demandsWrapper.getDemands().add(demand);
		}

		// Lista para filtrar as demandas por tipo, demandas requeridas, e ordenar por
		// mês (jan,fev,...).
		Stream<Demanda> requestedDemandasStream = demandsWrapper.getDemands().stream();

		List<Demanda> requestedDemands = requestedDemandasStream.filter(d -> d.getTipoFinalidade().getId() == 1L)
				.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
		;

		// Preenchimento do GridPane com os textfields por linha, primeiro linha de
		// vazão.
		requestedDemands.forEach(demand -> {
			addDemandFlowLine(gpRequestedDemands, demand.getMes(), demand);
		});
		// Preenchimento da linha de tempo de captação
		requestedDemands.forEach(demand -> {
			addDemandTimeLine(gpRequestedDemands, demand.getMes(), demand);
		});

		// Preenchimento da linha de período de captação
		requestedDemands.forEach(demand -> {
			addDemandPeriodLine(gpRequestedDemands, demand.getMes(), demand);
		});

		// Lista para filtrar as demandas por tipo de finalidade (demanda da finalidade
		// autorizadas) e mês.
		Stream<Demanda> authorizedDemandsStream = demandsWrapper.getDemands().stream();

		List<Demanda> authorizedDemands = authorizedDemandsStream.filter(d -> d.getTipoFinalidade().getId() == 2L)
				.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());

		authorizedDemands.forEach(demand -> {
			addDemandFlowLine(gpAuthorizedDemands, demand.getMes(), demand);
		});

		authorizedDemands.forEach(demand -> {
			addDemandTimeLine(gpAuthorizedDemands, demand.getMes(), demand);
		});

		authorizedDemands.forEach(demand -> {
			addDemandPeriodLine(gpAuthorizedDemands, demand.getMes(), demand);
		});

		// Create and populate a list of integers from 1 to 20
		ObservableList<Integer> numbers = FXCollections
				.observableArrayList(IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList()));

		// Set the items in the ComboBox
		cbReqTimeFlow.setItems(numbers);

		cbReqTimeFlow.setOnAction(event -> {

		});

		/**
		 * Cria a opção false ou true encarregada por preencher as linhas de vazão
		 * automaticamente todo os meses ou apenas os meses de chuva.
		 */
		ButtonBooleanOption bboBtnFillRequestedFlow = new ButtonBooleanOption(true);
		/**
		 * Preenche a linha das vazões automaticamente de acordo com os cálculos de
		 * finalidade. Busca o valor para preenchimento no textfield
		 * `tfTotalRequestedConsumption` e preenche de duas formas, todas as linhas ou
		 * apenas as linhas de período sem chuva (abril a outubro).
		 */

		btnFillReqFlow.setOnMouseClicked(event -> {

			if (bboBtnFillRequestedFlow.getOption()) {

				if (tfTotalRequestedConsumption.getText() != null) {
					System.out.println(tfTotalRequestedConsumption.getText());

					// Lista para filtrar as demandas por tipo, demandas requeridas, e ordenar por
					// mês (jan,fev,...).
					Stream<Demanda> rdStream = demandsWrapper.getDemands().stream();

					List<Demanda> rdList = rdStream.filter(d -> d.getTipoFinalidade().getId() == 1L)
							.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
					;

					// Preenchimento do GridPane com os textfields por linha, primeiro linha de
					// vazão.

					rdList.forEach(demand -> {

						demand.setVazao(Double.parseDouble(tfTotalRequestedConsumption.getText()));

						System.out.println(demand.getVazao());

						addDemandFlowLine(gpRequestedDemands, demand.getMes(), demand);
					});

				}

			} else {

				System.out.println(bboBtnFillRequestedFlow.getOption());

				if (tfTotalRequestedConsumption.getText() != null) {

					// Lista para filtrar as demandas por tipo, demandas requeridas, e ordenar por
					// mês (jan,fev,...).
					Stream<Demanda> rdStream = demandsWrapper.getDemands().stream();

					List<Demanda> rdList = rdStream.filter(d -> d.getTipoFinalidade().getId() == 1L)
							.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
					;

					// Preenchimento do GridPane com os textfields por linha, primeiro linha de
					// vazão.
					int[] idx = { 0 };

					rdList.forEach(demand -> {

						// meses jan, fev, mar, nov, e dez vazios
						if (idx[0] == 0 || idx[0] == 1 || idx[0] == 2 || idx[0] == 10 || idx[0] == 11) {

							demand.setVazao(0.0);
							addDemandFlowLine(gpRequestedDemands, demand.getMes(), demand);

						} else {
							demand.setVazao(Double.parseDouble(tfTotalRequestedConsumption.getText()));
							addDemandFlowLine(gpRequestedDemands, demand.getMes(), demand);
						}
						idx[0]++;

					});

				}

			}

			bboBtnFillRequestedFlow.setOption(!bboBtnFillRequestedFlow.getOption());

		});

		ButtonBooleanOption bboBtnFillRequestedTime = new ButtonBooleanOption(true);

		btnFillReqTime.setOnMouseClicked(event -> {

			Integer selectedValue = null;

			try {
				// Get the current value, which could be a String (from user input)
				String rawValue = cbReqTimeFlow.getEditor().getText();

				// Parse the raw value into an Integer
				selectedValue = rawValue != null ? Integer.parseInt(rawValue) : null;

				if (selectedValue != null) {
					System.out.println("Selected Value: " + selectedValue);
				}
			} catch (NumberFormatException e) {
				System.err.println("Invalid input: Please enter a valid number.");
			}

			if (bboBtnFillRequestedTime.getOption()) {

				if (selectedValue != null) {

					// Lista para filtrar as demandas por tipo, demandas requeridas, e ordenar por
					// mês (jan,fev,...).
					Stream<Demanda> rdStream = demandsWrapper.getDemands().stream();

					List<Demanda> rdList = rdStream.filter(d -> d.getTipoFinalidade().getId() == 1L)
							.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
					;

					// Preenchimento do GridPane com os textfields por linha, primeiro linha de
					// vazão.

					rdList.forEach(demand -> {

						// Get the current value, which could be a String (from user input)
						String rawValue = cbReqTimeFlow.getEditor().getText();

						// Parse the raw value into an Integer
						Integer selectedTimeValue = rawValue != null ? Integer.parseInt(rawValue) : null;

						demand.setTempo(selectedTimeValue);

						addDemandTimeLine(gpRequestedDemands, demand.getMes(), demand);
					});

				}

			} else {

				if (selectedValue != null) {

					// Lista para filtrar as demandas por tipo, demandas requeridas, e ordenar por
					// mês (jan,fev,...).
					Stream<Demanda> rdStream = demandsWrapper.getDemands().stream();

					List<Demanda> rdList = rdStream.filter(d -> d.getTipoFinalidade().getId() == 1L)
							.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
					;

					// Preenchimento do GridPane com os textfields por linha, primeiro linha de
					// vazão.
					int[] idx = { 0 };

					rdList.forEach(demand -> {

						// meses jan, fev, mar, nov, e dez vazios
						if (idx[0] == 0 || idx[0] == 1 || idx[0] == 2 || idx[0] == 10 || idx[0] == 11) {

							demand.setTempo(0);
							addDemandTimeLine(gpRequestedDemands, demand.getMes(), demand);

						} else {

							// Get the current value, which could be a String (from user input)
							String rawValue = cbReqTimeFlow.getEditor().getText();

							// Parse the raw value into an Integer
							Integer selectedTimeValue = rawValue != null ? Integer.parseInt(rawValue) : null;

							demand.setTempo(selectedTimeValue);
							addDemandFlowLine(gpRequestedDemands, demand.getMes(), demand);
						}
						idx[0]++;

					});

				}

			}

			bboBtnFillRequestedTime.setOption(!bboBtnFillRequestedTime.getOption());

		});

		ButtonBooleanOption bboFillRequestedPeriod = new ButtonBooleanOption(true);

		btnFillReqPeriod.setOnMouseClicked(event -> {

			if (bboFillRequestedPeriod.getOption()) {

				// Preenchimento do GridPane com os textfields por linha, primeiro linha de
				// vazão.

				List<Integer> daysInMonths = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
				int[] idx = { 0 };

				// Lista para filtrar as demandas por tipo, demandas requeridas, e ordenar por
				// mês (jan,fev,...).
				Stream<Demanda> rdStream = demandsWrapper.getDemands().stream();

				List<Demanda> rdList = rdStream.filter(d -> d.getTipoFinalidade().getId() == 1L)
						.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
				;
				rdList.forEach(demand -> {

					demand.setPeriodo(daysInMonths.get(idx[0]));
					addDemandPeriodLine(gpRequestedDemands, demand.getMes(), demand);

					idx[0]++;
				});

			} else {

				List<Integer> daysInMonths = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);

				// Lista para filtrar as demandas por tipo, demandas requeridas, e ordenar por
				// mês (jan,fev,...).
				Stream<Demanda> rdStream = demandsWrapper.getDemands().stream();

				List<Demanda> rdList = rdStream.filter(d -> d.getTipoFinalidade().getId() == 1L)
						.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
				;

				// Preenchimento do GridPane com os textfields por linha, primeiro linha de
				// vazão.
				int[] idx = { 0 };

				rdList.forEach(demand -> {

					// meses jan, fev, mar, nov, e dez vazios
					if (idx[0] == 0 || idx[0] == 1 || idx[0] == 2 || idx[0] == 10 || idx[0] == 11) {

						demand.setPeriodo(0);
						addDemandPeriodLine(gpRequestedDemands, demand.getMes(), demand);

					} else {
						demand.setPeriodo(daysInMonths.get(idx[0]));
						addDemandPeriodLine(gpRequestedDemands, demand.getMes(), demand);
					}
					idx[0]++;

				});

			}

			bboFillRequestedPeriod.setOption(!bboFillRequestedPeriod.getOption());

		});

		btnCopyReqDemand.setOnMouseClicked(event -> {

			// demandsStream
			Stream<Demanda> ds1 = demandsWrapper.getDemands().stream();

			List<Demanda> rdList = ds1.filter(d -> d.getTipoFinalidade().getId() == 1L)
					.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
			;

			Stream<Demanda> ds2 = demandsWrapper.getDemands().stream();

			List<Demanda> adList = ds2.filter(d -> d.getTipoFinalidade().getId() == 2L)
					.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
			;

			int[] idx = { 0 };
			rdList.forEach(rdl -> {
				adList.get(idx[0]).setVazao(rdl.getVazao() + 30);
				adList.get(idx[0]).setTempo(rdl.getTempo() + 50);
				adList.get(idx[0]).setPeriodo(rdl.getPeriodo() + 60);

				addDemandFlowLine(gpAuthorizedDemands, rdl.getMes(), adList.get(idx[0]));
				addDemandTimeLine(gpAuthorizedDemands, rdl.getMes(), adList.get(idx[0]));
				addDemandPeriodLine(gpAuthorizedDemands, rdl.getMes(), adList.get(idx[0]));

				idx[0]++;

			});

		});

	}

	public void addDemandFlowLine(GridPane gridPane, int month, Demanda demand) {

		JFXTextField tfFlow = new JFXTextField();
		tfFlow.getStyleClass().addAll("tf-demands");
		// Limpa a célula para adicionar novo Textfield.
		clearGridCell(gridPane, month, 1);
		// Adiciona Textfields por célula
		gridPane.add(tfFlow, month, 1);

		// Se a demanda não está vazia, preencher com o valor
		if (demand.getVazao() != null)
			tfFlow.setText(demand.getVazao().toString());

		DemandWrapper demandWrapper = new DemandWrapper(demand);
		// Listeners
		tfFlow.textProperty().addListener((observable, oldValue, newValue) -> {
			demandWrapper.getDemand().setVazao(Double.parseDouble(newValue));

		});

	}

	public void addDemandTimeLine(GridPane gridPane, int month, Demanda demand) {
		JFXTextField tfTime = new JFXTextField();
		tfTime.getStyleClass().addAll("tf-demands");
		// Limpa a célula para adicionar novo Textfield.
		clearGridCell(gridPane, month, 2);
		gridPane.add(tfTime, month, 2);

		/*
		 * if (demand.getId() != null) { // Se não, é uma demanda buscada no banco e
		 * deve-se preencher os campos
		 * tfTime.setText(String.valueOf(demand.getTempo())); }
		 */
		if (demand.getTempo() != null) {
			// Se não, é uma demanda buscada no banco e deve-se preencher os campos
			tfTime.setText(String.valueOf(demand.getTempo()));
		}

		DemandWrapper demandWrapper = new DemandWrapper(demand);

		tfTime.textProperty().addListener((observable, oldValue, newValue) -> {
			demandWrapper.getDemand().setTempo(Integer.valueOf(newValue));

		});
	}

	public void addDemandPeriodLine(GridPane gridPane, int month, Demanda demand) {

		JFXTextField tfPeriod = new JFXTextField();

		tfPeriod.getStyleClass().addAll("tf-demands");

		// Limpa a célula para adicionar novo Textfield.

		clearGridCell(gridPane, month, 3);
		gridPane.add(tfPeriod, month, 3);

		if (demand.getPeriodo() != null) {
			// Se não, é uma demanda buscada no banco e deve-se preencher os campos

			tfPeriod.setText(String.valueOf(demand.getPeriodo()));
		}

		DemandWrapper demandWrapper = new DemandWrapper(demand);
		// Listeners

		tfPeriod.textProperty().addListener((observable, oldValue, newValue) -> {
			demandWrapper.getDemand().setPeriodo(Integer.valueOf(newValue));

		});
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

	// Verifica se há filhos na célula do GridPane, se houver limpa a célula onde
	// está um TextField.
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
			JFXButton btnTotalConsumption, TipoFinalidade typeOfPurpouse, Finalidade purpouse) {

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

			System.out.println("purpouses " + purpouseWrapper.getPurpouse());

			tfPurpouse.setText(purpouseWrapper.getPurpouse().getFinalidade());
			tfSubpurpose.setText(purpouseWrapper.getPurpouse().getSubfinalidade());
			if (purpouseWrapper.getPurpouse().getQuantidade() != null) {
				tfQuantity.setText(purpouseWrapper.getPurpouse().getQuantidade().toString());
			}

			tfConsumption.setText(purpouseWrapper.getPurpouse().getConsumo().toString());

			// Se o total não foi cadastrado não mostrar
			if (purpouseWrapper.getPurpouse().getTotal() != null) {
				tfTotal.setText(purpouseWrapper.getPurpouse().getTotal().toString());
			}

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
						btnCalculateReqFin, item.getTipoFinalidade(), item);
			} else {

				addPurpouseRow(autIndex.getAndIncrement(), authorizedPurposesGrid, tfTotalAuthorizedConsumption,
						btnCalculateAuthFin, item.getTipoFinalidade(), item);
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
				addDemandFlowLine(gpRequestedDemands, demand.getMes(), demand);
			});

			demandsByRequestedTypePurpouse.forEach(demand -> {
				addDemandTimeLine(gpRequestedDemands, demand.getMes(), demand);
			});

			demandsByRequestedTypePurpouse.forEach(demand -> {
				addDemandPeriodLine(gpRequestedDemands, demand.getMes(), demand);
			});

			// Demanda por tipo de finalidade autorizada
			List<Demanda> demandsByAuthorizedTypePurpouse = demandsWrapper.getDemands().stream()
					.filter(d -> d.getTipoFinalidade().getId() == 2L).sorted(Comparator.comparing(d -> d.getMes()))
					.collect(Collectors.toList());

			demandsByAuthorizedTypePurpouse.forEach(demand -> {
				addDemandFlowLine(gpAuthorizedDemands, demand.getMes(), demand);
			});

			demandsByAuthorizedTypePurpouse.forEach(demand -> {
				addDemandTimeLine(gpAuthorizedDemands, demand.getMes(), demand);
			});
			demandsByAuthorizedTypePurpouse.forEach(demand -> {
				addDemandPeriodLine(gpAuthorizedDemands, demand.getMes(), demand);
			});
		}
	}

	public Set<Demanda> getDemands() {
		return demandsWrapper.getDemands();
	}

	public Subterranea getSubterraneanAttributes() {

		Subterranea subterraneanAttributes = new Subterranea();

		// Se não selecionado, selecione falso
		subterraneanAttributes.setCaesb(cbConcessionaire.getSelectionModel().getSelectedItem() != null
				? "Sim".equals(cbConcessionaire.getSelectionModel().getSelectedItem()) ? true : false
				: false);

		subterraneanAttributes.setNivelEstatico(tfStaticLevel.getText());
		subterraneanAttributes.setNivelDinamico(tfDynamicLevel.getText());
		subterraneanAttributes.setProfundidade(tfWaterDepth.getText());
		subterraneanAttributes
				.setVazaoOutorgavel(tfSystemGrant.getText().isEmpty() ? 0 : Integer.parseInt(tfSystemGrant.getText()));
		subterraneanAttributes
				.setVazaoSistema(tfSystemFlow.getText().isEmpty() ? 0 : Integer.parseInt(tfSystemFlow.getText()));

		subterraneanAttributes
				.setVazaoTeste(tfSystemTest.getText().isEmpty() ? 0 : Integer.parseInt(tfSystemTest.getText()));

		// O tipo de poço é obrigatório, se não selecionar retorna vazio
		if (cbWellType.getSelectionModel().getSelectedItem() == null) {

			return null;
		} else {
			// Set the selected item if it exists
			subterraneanAttributes.setTipoPoco(cbWellType.getSelectionModel().getSelectedItem());
		}

		return subterraneanAttributes;

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

	/**
	 * Com esta opção, verdadeiro ou falso, posso fazer a mudança de preenchimento
	 * em que pode ser preenchido todos os meses com a vazão total, ou zerar os
	 * meses de janeiro, fevereiro, março, novembro e dezembro (período de chuvas e
	 * sem vazão outorgada) utilizado nas outorgas subterrâneas.
	 * 
	 * @author fabricio.barrozo
	 *
	 */
	public class ButtonBooleanOption {

		private Boolean option;

		public ButtonBooleanOption(Boolean option) {
			super();
			this.option = option;
		}

		public Boolean getOption() {
			return option;
		}

		public void setOption(Boolean option) {
			this.option = option;
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
