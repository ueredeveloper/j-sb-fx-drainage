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
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Demanda;
import models.Finalidade;
import models.HidrogeoFraturado;
import models.HidrogeoPoroso;
import models.Interferencia;
import models.SubsystemCodeAttributes;
import models.Subterranea;
import models.TipoFinalidade;
import models.TipoInterferencia;
import models.TipoPoco;
import services.FinalidadeService;
import services.HidrogeoFraturadoService;
import services.HidrogeoPorosoService;
import services.ServiceResponse;
import utilities.URLUtility;

public class AddSubterraneanDetailsController implements Initializable {

	private String urlService;

	AddInterferenceController addInterferenceControler;

	public AddSubterraneanDetailsController() {
		this.urlService = URLUtility.getURLService();
	}

	public void setAddInterferenceController(AddInterferenceController addInterferenceControler) {
		this.addInterferenceControler = addInterferenceControler;
	}

	@FXML
	private JFXComboBox<Integer> cbReqTimeFlow, cbAuthTimeFlow;

	@FXML
	private AnchorPane apContainer;

	@FXML
	private JFXComboBox<TipoPoco> cbWellType;

	@FXML
	private JFXComboBox<String> cbConcessionaire;

	@FXML
	private JFXComboBox<String> cbSystem, cbSubsystem;

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
    private Button btnRefereshSubsystem;

    @FXML
    private Button btnFillReqFlow;

    @FXML
    private Button btnFillReqTime;

    @FXML
    private Button btnFillReqPeriod;


    @FXML
    private Button btnCalculateAuthTotalConsumption, btnCalculateReqTotalConsumption;	

    @FXML
    private Button btnCopyReqDemand;

    @FXML
    private Button btnFillAuthFlow;

    @FXML
    private Button btnFillAuthTime;

    @FXML
    private Button btnFillAuthPeriod;

	PurpousesWrapper purpousesWrapper = new PurpousesWrapper(new HashSet<>());

	DemandsWrapper demandsWrapper = new DemandsWrapper(new HashSet<>());

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Tipo de poço, se manual ou tubular
		obsTypesOfWells = StaticData.INSTANCE.getTypesOfWells();
		cbWellType.setItems(obsTypesOfWells);

		/*
		 * Através do tipo de poço captura o subsistema e vazão do subsistema
		 */
		cbWellType.setOnAction((event) -> {

			SubsystemCodeAttributes sca = addInterferenceControler.getSubsystemCodeAttributes();
			TipoPoco tp = cbWellType.getSelectionModel().getSelectedItem();

			fillSystemAndSubsystem(sca.getTypeOfInterference(), tp, sca.getLatitude().toString(),
					sca.getLongitude().toString());
		});

		// Se há Caesb ou não
		cbConcessionaire.getItems().addAll("Sim", "Não");

		// Adiciona cadastro de finalidade
		addPurpouseRow(0, requestedPurposesGrid, tfTotalRequestedConsumption, btnCalculateReqTotalConsumption,
				new TipoFinalidade(1L), new Finalidade(new TipoFinalidade(1L)));
		addPurpouseRow(0, authorizedPurposesGrid, tfTotalAuthorizedConsumption, btnCalculateAuthTotalConsumption,
				new TipoFinalidade(2L), new Finalidade(new TipoFinalidade(2L)));

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

		// Cria as horas de vazão para o combobox cbReqTimeFlow (1 - 20 horas)
		ObservableList<Integer> numbers = FXCollections
				.observableArrayList(IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList()));

		// Set the items in the ComboBox
		cbReqTimeFlow.setItems(numbers);
		cbAuthTimeFlow.setItems(numbers);

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

						addDemandFlowLine(gpRequestedDemands, demand.getMes(), demand);
					});

				}

			} else {

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

		/**
		 * Cria a opção false ou true encarregada por preencher as linhas de vazão
		 * automaticamente todo os meses ou apenas os meses de chuva.
		 */
		ButtonBooleanOption bboBtnFillAuthFlow = new ButtonBooleanOption(true);
		/**
		 * Preenche a linha das vazões automaticamente de acordo com os cálculos de
		 * finalidade. Busca o valor para preenchimento no textfield
		 * `tfTotalRequestedConsumption` e preenche de duas formas, todas as linhas ou
		 * apenas as linhas de período sem chuva (abril a outubro).
		 */

		btnFillAuthFlow.setOnMouseClicked(event -> {

			if (bboBtnFillAuthFlow.getOption()) {

				if (tfTotalAuthorizedConsumption.getText() != null) {

					// Lista para filtrar as demandas por tipo, demandas requeridas, e ordenar por
					// mês (jan,fev,...).
					Stream<Demanda> rdStream = demandsWrapper.getDemands().stream();

					List<Demanda> rdList = rdStream.filter(d -> d.getTipoFinalidade().getId() == 2L)
							.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
					;

					// Preenchimento do GridPane com os textfields por linha, primeiro linha de
					// vazão.

					rdList.forEach(demand -> {

						demand.setVazao(Double.parseDouble(tfTotalAuthorizedConsumption.getText()));

						addDemandFlowLine(gpAuthorizedDemands, demand.getMes(), demand);
					});

				}

			} else {

				if (tfTotalAuthorizedConsumption.getText() != null) {

					// Lista para filtrar as demandas por tipo, demandas requeridas, e ordenar por
					// mês (jan,fev,...).
					Stream<Demanda> rdStream = demandsWrapper.getDemands().stream();

					List<Demanda> rdList = rdStream.filter(d -> d.getTipoFinalidade().getId() == 2L)
							.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
					;

					// Preenchimento do GridPane com os textfields por linha, primeiro linha de
					// vazão.
					int[] idx = { 0 };

					rdList.forEach(demand -> {

						// meses jan, fev, mar, nov, e dez vazios
						if (idx[0] == 0 || idx[0] == 1 || idx[0] == 2 || idx[0] == 10 || idx[0] == 11) {

							demand.setVazao(0.0);
							addDemandFlowLine(gpAuthorizedDemands, demand.getMes(), demand);

						} else {
							//System.out.println(tfTotalRequestedConsumption.getText());
							demand.setVazao(Double.parseDouble(tfTotalAuthorizedConsumption.getText()));
							addDemandFlowLine(gpAuthorizedDemands, demand.getMes(), demand);
						}
						idx[0]++;

					});

				}

			}

			bboBtnFillAuthFlow.setOption(!bboBtnFillAuthFlow.getOption());

		});

		/**
		 * Cria as opções false ou true. Assim preenche de duas formas o tempo, todos os
		 * campos, ou apenas de abril a outubro.
		 */
		ButtonBooleanOption bboBtnFillRequestedTime = new ButtonBooleanOption(true);

		/**
		 * Ação de preenchimento da linha tempo de captação
		 */
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

		/**
		 * Cria as opções false ou true. Assim preenche de duas formas o tempo, todos os
		 * campos, ou apenas de abril a outubro.
		 */
		ButtonBooleanOption bboBtnFillAuthTime = new ButtonBooleanOption(true);

		/**
		 * Ação de preenchimento da linha tempo de captação
		 */
		btnFillAuthTime.setOnMouseClicked(event -> {

			Integer selectedValue = null;

			try {
				// Get the current value, which could be a String (from user input)
				String rawValue = cbAuthTimeFlow.getEditor().getText();

				// Parse the raw value into an Integer
				selectedValue = rawValue != null ? Integer.parseInt(rawValue) : null;

				if (selectedValue != null) {
					System.out.println("Selected Value: " + selectedValue);
				}
			} catch (NumberFormatException e) {
				System.err.println("Invalid input: Please enter a valid number.");
			}

			if (bboBtnFillAuthTime.getOption()) {

				if (selectedValue != null) {

					// Lista para filtrar as demandas por tipo, demandas requeridas, e ordenar por
					// mês (jan,fev,...).
					Stream<Demanda> rdStream = demandsWrapper.getDemands().stream();

					List<Demanda> rdList = rdStream.filter(d -> d.getTipoFinalidade().getId() == 2L)
							.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
					;

					// Preenchimento do GridPane com os textfields por linha, primeiro linha de
					// vazão.

					rdList.forEach(demand -> {

						// Get the current value, which could be a String (from user input)
						String rawValue = cbAuthTimeFlow.getEditor().getText();

						// Parse the raw value into an Integer
						Integer selectedTimeValue = rawValue != null ? Integer.parseInt(rawValue) : null;

						demand.setTempo(selectedTimeValue);

						addDemandTimeLine(gpAuthorizedDemands, demand.getMes(), demand);
					});

				}

			} else {

				if (selectedValue != null) {

					// Lista para filtrar as demandas por tipo, demandas requeridas, e ordenar por
					// mês (jan,fev,...).
					Stream<Demanda> rdStream = demandsWrapper.getDemands().stream();

					List<Demanda> rdList = rdStream.filter(d -> d.getTipoFinalidade().getId() == 2L)
							.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
					;

					// Preenchimento do GridPane com os textfields por linha, primeiro linha de
					// vazão.
					int[] idx = { 0 };

					rdList.forEach(demand -> {

						// meses jan, fev, mar, nov, e dez vazios
						if (idx[0] == 0 || idx[0] == 1 || idx[0] == 2 || idx[0] == 10 || idx[0] == 11) {

							demand.setTempo(0);
							addDemandTimeLine(gpAuthorizedDemands, demand.getMes(), demand);

						} else {

							// Get the current value, which could be a String (from user input)
							String rawValue = cbAuthTimeFlow.getEditor().getText();

							// Parse the raw value into an Integer
							Integer selectedTimeValue = rawValue != null ? Integer.parseInt(rawValue) : null;

							demand.setTempo(selectedTimeValue);
							addDemandFlowLine(gpAuthorizedDemands, demand.getMes(), demand);
						}
						idx[0]++;

					});

				}

			}

			bboBtnFillAuthTime.setOption(!bboBtnFillAuthTime.getOption());

		});

		/**
		 * Cria a opção falso ou true de preenchimento completo da linha ou somente de
		 * abril a outubro, período de seca.
		 */
		ButtonBooleanOption bboFillRequestedPeriod = new ButtonBooleanOption(true);

		/**
		 * Ação de preenchimento automático dos dias de captação mês a mês.
		 */
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

		/**
		 * Cria a opção falso ou true de preenchimento completo da linha ou somente de
		 * abril a outubro, período de seca.
		 */
		ButtonBooleanOption bboFillAuthPeriod = new ButtonBooleanOption(true);

		/**
		 * Ação de preenchimento automático dos dias de captação mês a mês.
		 */
		btnFillAuthPeriod.setOnMouseClicked(event -> {

			if (bboFillAuthPeriod.getOption()) {

				// Preenchimento do GridPane com os textfields por linha, primeiro linha de
				// vazão.

				List<Integer> daysInMonths = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
				int[] idx = { 0 };

				// Lista para filtrar as demandas por tipo, demandas requeridas, e ordenar por
				// mês (jan,fev,...).
				Stream<Demanda> rdStream = demandsWrapper.getDemands().stream();

				List<Demanda> rdList = rdStream.filter(d -> d.getTipoFinalidade().getId() == 2L)
						.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
				;
				rdList.forEach(demand -> {

					demand.setPeriodo(daysInMonths.get(idx[0]));
					addDemandPeriodLine(gpAuthorizedDemands, demand.getMes(), demand);

					idx[0]++;
				});

			} else {

				List<Integer> daysInMonths = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);

				// Lista para filtrar as demandas por tipo, demandas requeridas, e ordenar por
				// mês (jan,fev,...).
				Stream<Demanda> rdStream = demandsWrapper.getDemands().stream();

				List<Demanda> rdList = rdStream.filter(d -> d.getTipoFinalidade().getId() == 2L)
						.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
				;

				// Preenchimento do GridPane com os textfields por linha, primeiro linha de
				// vazão.
				int[] idx = { 0 };

				rdList.forEach(demand -> {

					// meses jan, fev, mar, nov, e dez vazios
					if (idx[0] == 0 || idx[0] == 1 || idx[0] == 2 || idx[0] == 10 || idx[0] == 11) {

						demand.setPeriodo(0);
						addDemandPeriodLine(gpAuthorizedDemands, demand.getMes(), demand);

					} else {
						demand.setPeriodo(daysInMonths.get(idx[0]));
						addDemandPeriodLine(gpAuthorizedDemands, demand.getMes(), demand);
					}
					idx[0]++;

				});

			}

			bboFillAuthPeriod.setOption(!bboFillAuthPeriod.getOption());

		});

		/**
		 * Atualiza dados do subsistema, com sistema, subsistema, código e vazão.
		 */
		btnRefereshSubsystem.setOnAction(event -> {

			System.out.println("clicked");

			SubsystemCodeAttributes sca = addInterferenceControler.getSubsystemCodeAttributes();

			if (sca.getLatitude() != null && sca.getLongitude() != null) {

				fillSystemAndSubsystem(sca.getTypeOfInterference(), cbWellType.getSelectionModel().getSelectedItem(),
						sca.getLatitude().toString(), sca.getLongitude().toString());

			}

		});

		/**
		 * Copia a finalidades e demandas requeridas para as finalidades e demandas
		 * autorizadas, igualando as duas.
		 */
		btnCopyReqDemand.setOnMouseClicked(event -> {

			Stream<Finalidade> purpouses = purpousesWrapper.getPurpouses().stream();

			List<Finalidade> purListType1 = purpouses.filter(d -> d.getTipoFinalidade().getId() == 1L)
					// .sorted(Comparator.comparing(d -> d.getMes()))
					.collect(Collectors.toList());
			;

			purpouses = purpousesWrapper.getPurpouses().stream();

			List<Finalidade> purListType2 = new ArrayList<Finalidade>();
			// 07/02/2025 -> removi pois neste caso não me parece necessário captura o que
			// já existe nas finalidades autorizadas

			// purpouses.filter(d -> d.getTipoFinalidade().getId() == 2L)
			// .sorted(Comparator.comparing(d -> d.getMes()))
			// .collect(Collectors.toList());

			int[] idxPur = { 0 };
			// Limpa todas as finalidades
			purpousesWrapper.getPurpouses().clear();
			// Limpa o GridPane de finalidades autorizadas
			authorizedPurposesGrid.getChildren().clear();

			// Ajusta o tamanho da array de finalidade autorizada para o mesmo tamanho das
			// finalidades requeridas.
			// Assim se houver mais finalidades autorizadas que requeridas, será removida
			// estas finalidades a mais
			// if (purListType2.size() > purListType1.size()) {
			// while (purListType2.size() > purListType1.size()) {
			// purListType2.remove(purListType2.size() - 1); // Remove elements from the end
			// }
			// }

			purListType1.forEach(pur -> {

				if (idxPur[0] < purListType2.size()) {

					if (purListType2.get(idxPur[0]).getId() != null) {

						System.out.println("!null id " + purListType2.get(idxPur[0]).getId());
						// Edita a finalidade autorizada com os valores da finalidade requerida
						purListType2.get(idxPur[0]).setFinalidade(pur.getFinalidade());
						purListType2.get(idxPur[0]).setSubfinalidade(pur.getSubfinalidade());
						purListType2.get(idxPur[0]).setQuantidade(pur.getQuantidade());
						purListType2.get(idxPur[0]).setConsumo(pur.getConsumo());
						purListType2.get(idxPur[0]).setTotal(pur.getTotal());

					} else {

						purListType2.add(new Finalidade(pur.getFinalidade(), pur.getSubfinalidade(),
								pur.getQuantidade(), pur.getConsumo(), pur.getTotal(), pur.getInterferencia(),
								new TipoFinalidade(2L)));
					}

				} else {

					purListType2.add(new Finalidade(pur.getFinalidade(), pur.getSubfinalidade(), pur.getQuantidade(),
							pur.getConsumo(), pur.getTotal(), pur.getInterferencia(), new TipoFinalidade(2L)));
				}

				// Atualiza o GridPane
				addPurpouseRow(idxPur[0], authorizedPurposesGrid, tfTotalConsumption, btnCalculateAuthTotalConsumption,
						new TipoFinalidade(2L), pur);

				idxPur[0]++;

			});

			// Adiciona as finalidades
			purpousesWrapper.getPurpouses().addAll(purListType1);
			purpousesWrapper.getPurpouses().addAll(purListType2);

			// demandsStream
			Stream<Demanda> demands = demandsWrapper.getDemands().stream();

			List<Demanda> demType1List = demands.filter(d -> d.getTipoFinalidade().getId() == 1L)
					.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
			;

			demands = demandsWrapper.getDemands().stream();

			List<Demanda> demType2List = demands.filter(d -> d.getTipoFinalidade().getId() == 2L)
					.sorted(Comparator.comparing(d -> d.getMes())).collect(Collectors.toList());
			;

			int[] idxDem = { 0 };

			/*
			 * Copia a demanda requerida para a demanda autorizada
			 */
			demType1List.forEach(dem -> {

				demType2List.get(idxDem[0]).setVazao(dem.getVazao());
				demType2List.get(idxDem[0]).setTempo(dem.getTempo());
				demType2List.get(idxDem[0]).setPeriodo(dem.getPeriodo());
				demType2List.get(idxDem[0]).setMes(dem.getMes());
				demType2List.get(idxDem[0]).setTipoFinalidade(new TipoFinalidade(2L));

				idxDem[0]++;

			});

			// Atualiza a visualização (GridPane)
			// addDemandFlowLine(gpAuthorizedDemands, dem.getMes(), dem);
			// addDemandTimeLine(gpAuthorizedDemands, dem.getMes(), dem);
			// addDemandPeriodLine(gpAuthorizedDemands, dem.getMes(), dem);

			demType2List.forEach(demand -> {
				addDemandFlowLine(gpAuthorizedDemands, demand.getMes(), demand);
			});

			demType2List.forEach(demand -> {
				addDemandTimeLine(gpAuthorizedDemands, demand.getMes(), demand);
			});

			demType2List.forEach(demand -> {
				addDemandPeriodLine(gpAuthorizedDemands, demand.getMes(), demand);
			});

			demandsWrapper.getDemands().addAll(demType1List);
			demandsWrapper.getDemands().addAll(demType2List);

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
	 * index gridPane tfTotalConsumption btnTotalConsumption typeOfPurpouse purpouse
	 */
	public void addPurpouseRow(int index, GridPane gridPane, JFXTextField tfTotalConsumption,
			Button btnTotalConsumption, TipoFinalidade typeOfPurpouse, Finalidade purpouse) {

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
			if (purpouseWrapper.getPurpouse().getQuantidade() != null) {
				tfQuantity.setText(purpouseWrapper.getPurpouse().getQuantidade().toString());
			}
			if (purpouseWrapper.getPurpouse().getConsumo() != null) {
				tfConsumption.setText(purpouseWrapper.getPurpouse().getConsumo().toString());
			}

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
			if (newValue.length() > 70) {
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
			if (newValue.length() > 70) {
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
			if (newValue.length() > 30) {
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
			if (newValue.length() > 30) {
				// Since you don't have access to an Event, just use the tfPurpouse as the
				// source
				Node source = tfSubpurpose; // The source is tfPurpouse (JFXTextField)
				Stage ownerStage = (Stage) source.getScene().getWindow();

				// Display toast message
				String toastMsg = "O texto de consumo está muito longo!!!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			}

		});

		tfTotal.textProperty().addListener((observable, oldValue, newValue) -> {

			purpouseWrapper.getPurpouse().setTotal(Double.parseDouble((newValue)));

			// You can add custom logic here, for example:
			if (newValue.length() > 30) {
				// Since you don't have access to an Event, just use the tfPurpouse as the
				// source
				Node source = tfTotal; // The source is tfPurpouse (JFXTextField)
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
			// Usando um array para contornar a limitação de variáveis finais
			final Double[] total = { 0.0 };

			purpousesWrapper.getPurpouses().forEach(p -> {

				if (p.getTipoFinalidade().getId() == typeOfPurpouse.getId()) {
					Double subtotal = p.getTotal();
					if (subtotal != null) {
						total[0] += subtotal;
					}
				}

			});

			// Quando copia as finalidades requeridas para as finalidades autozizadas este
			// textfild fica nulo e não atualiza o total.
			if (tfTotalConsumption != null) {
				tfTotalConsumption.setText(total[0].toString());

			}

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

		/**
		 * Se não houver finalidades cadastradas, adicione uma requerida e uma
		 * autorizada com valores vazios
		 */
		if (newPurpouses.size() == 0) {
			newPurpouses.add(new Finalidade("", "", 0.0, 0.0, 0.0, null, new TipoFinalidade(1L)));
			newPurpouses.add(new Finalidade("", "", 0.0, 0.0, 0.0, null, new TipoFinalidade(2L)));
		}

		/*
		 * Se não tiver sido salvo o tipo de finalidade, adicinar como finalidade
		 * requerida. Assim o usuário pode ver, editar ou apagar se for o caso
		 */
		newPurpouses.forEach(item -> {
			if (item.getTipoFinalidade() == null) {
				item.setTipoFinalidade(new TipoFinalidade(1L));
			}
		});

		// Busca finalidade requerida, se não houver, adiciona. É preciso have ao menos
		// uma para que o usuário possa manipular.
		Finalidade finReq = newPurpouses.stream().filter(obj -> obj.getTipoFinalidade().getId() == 1L).findAny()
				.orElse(null);

		if (finReq == null) {
			newPurpouses.add(new Finalidade("", "", 0.0, 0.0, 0.0, null, new TipoFinalidade(1L)));
		}

		// Busca finalidade autorizada, se não houver, adiciona. É preciso have ao menos
		// uma para que o usuário possa manipular.
		Finalidade finAuth = newPurpouses.stream().filter(obj -> obj.getTipoFinalidade().getId() == 2L).findAny()
				.orElse(null);

		if (finAuth == null) {
			newPurpouses.add(new Finalidade("", "", 0.0, 0.0, 0.0, null, new TipoFinalidade(2L)));
		}

		purpousesWrapper.setPurpouses(newPurpouses);

		requestedPurposesGrid.getChildren().clear();
		authorizedPurposesGrid.getChildren().clear();

		AtomicInteger reqIndex = new AtomicInteger(0);
		AtomicInteger autIndex = new AtomicInteger(0);

		purpousesWrapper.getPurpouses().forEach(item -> {
			if (item.getTipoFinalidade().getId() == 1L) {
				addPurpouseRow(reqIndex.getAndIncrement(), requestedPurposesGrid, tfTotalRequestedConsumption,
						btnCalculateReqTotalConsumption, item.getTipoFinalidade(), item);
			} else {
				addPurpouseRow(autIndex.getAndIncrement(), authorizedPurposesGrid, tfTotalAuthorizedConsumption,
						btnCalculateAuthTotalConsumption, item.getTipoFinalidade(), item);
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

	public void fillAttributes(Interferencia selectedObject) {

		// Verifica se a interferência é do tipo Subterrânea apenas para teste, sem
		// necessidade a mais
		if (selectedObject instanceof Subterranea) {
			Subterranea subterranea = (Subterranea) selectedObject;

			// Como os valores no banco é true ou false (boolean), verifica se true = "Sim",
			// false = "não".
			cbConcessionaire.getSelectionModel().select(subterranea.getCaesb() ? "Sim" : "Não");
			// Como só recebo o id do tipo do poço, sem a descrição, preciso buscar o tipo
			// de poço pelo id.
			Long tipoPocoId = subterranea.getTipoPoco() != null ? subterranea.getTipoPoco().getId() : null;

			if (tipoPocoId != null) {
				// Busca o item correspondente na ObservableList
				TipoPoco matchingTipoPoco = obsTypesOfWells.stream()
						.filter(tipoPoco -> tipoPoco.getId().equals(tipoPocoId)).findFirst().orElse(null);

				if (matchingTipoPoco != null) {
					// Seleciona o item no ComboBox
					cbWellType.getSelectionModel().select(matchingTipoPoco);

					fillSystemAndSubsystem(subterranea.getTipoInterferencia(), subterranea.getTipoPoco(),
							subterranea.getLatitude().toString(), subterranea.getLongitude().toString());

					if (subterranea.getCodPlan() != null && !subterranea.getCodPlan().isEmpty()) {
						if (matchingTipoPoco.getId() == 1L || matchingTipoPoco.getId() == 2L) {
							Set<HidrogeoPoroso> set = findPorosoByCodPlan(subterranea.getCodPlan());

							set.forEach(s -> System.out.println("poroso " + s.getCodPlan()));

						} else {
							Set<HidrogeoFraturado> set = findSubsystemFraturadoByCodPlan(subterranea.getCodPlan());

							set.forEach(s -> System.out.println("fraturado " + s.getCodPlan()));
						}
					}

				} else {
					System.out.println("Nenhum TipoPoco encontrado com o ID: " + tipoPocoId);
				}
			} else {
				System.out.println("TipoPoco ID é nulo.");
			}

			// Continua os outros preenchimentos
			tfSystemFlow.setText(subterranea.getVazaoSistema().toString());
			tfSystemGrant.setText(subterranea.getVazaoOutorgavel().toString());
			tfSystemTest.setText(subterranea.getVazaoTeste().toString());
			tfStaticLevel.setText(subterranea.getNivelDinamico());
			tfDynamicLevel.setText(subterranea.getNivelDinamico().toString());
			tfWaterDepth.setText(subterranea.getProfundidade());

		} else {
			System.out.println("A interferência selecionada não é do tipo Subterrânea.");
		}

	}

	public Set<Demanda> getDemands() {
		return demandsWrapper.getDemands();
	}

	public Subterranea getSubterraneanAttributes() {

		Subterranea subterraneanAttributes = new Subterranea();

		// O tipo de poço é obrigatório, se não selecionar retorna vazio
		if (cbWellType.getSelectionModel().getSelectedItem() == null) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) cbConcessionaire;
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione o Tipo de Poço, se manual ou tubular!!!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			return null;
			
		} else if (cbConcessionaire.getSelectionModel().getSelectedItem() == null) {
			// Alerta (Toast) de sucesso na edi��o
			Node source = (Node) cbConcessionaire;
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione se há uso da Caesb no empreendimento!!!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			return null;
			
		} else {

			// Set the selected item if it exists
			subterraneanAttributes.setTipoPoco(cbWellType.getSelectionModel().getSelectedItem());

			// Se não selecionado, selecione falso
			subterraneanAttributes.setCaesb(cbConcessionaire.getSelectionModel().getSelectedItem() != null
					? "Sim".equals(cbConcessionaire.getSelectionModel().getSelectedItem()) ? true : false
					: false);

			subterraneanAttributes.setNivelEstatico(tfStaticLevel.getText());
			subterraneanAttributes.setNivelDinamico(tfDynamicLevel.getText());
			subterraneanAttributes.setProfundidade(tfWaterDepth.getText());
			subterraneanAttributes.setVazaoOutorgavel(
					tfSystemGrant.getText().isEmpty() ? 0 : Integer.parseInt(tfSystemGrant.getText()));
			subterraneanAttributes
					.setVazaoSistema(tfSystemFlow.getText().isEmpty() ? 0 : Integer.parseInt(tfSystemFlow.getText()));

			subterraneanAttributes
					.setVazaoTeste(tfSystemTest.getText().isEmpty() ? 0 : Integer.parseInt(tfSystemTest.getText()));

			// Sistema e Subsistema (Hidrogeo Poroso ou Fraturado)
			subterraneanAttributes.setSistema(cbSystem.getSelectionModel().getSelectedItem());
			subterraneanAttributes.setSubsistema(cbSubsystem.getSelectionModel().getSelectedItem());
			subterraneanAttributes.setCodPlan(tfCodeSystem.getText());

			return subterraneanAttributes;

		}

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
			FinalidadeService documentService = new FinalidadeService(urlService);

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

	public Set<HidrogeoFraturado> findSubsystemFraturadoByPoint(String lat, String lng) {

		try {

			HidrogeoFraturadoService service = new HidrogeoFraturadoService(urlService);

			Set<HidrogeoFraturado> set = service.findByPoint(lat, lng);

			return set;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public Set<HidrogeoPoroso> findSubsystemPorosoByPoint(String lat, String lng) {

		try {

			HidrogeoPorosoService service = new HidrogeoPorosoService(urlService);

			Set<HidrogeoPoroso> set = service.findByPoint(lat, lng);

			return set;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public Set<HidrogeoPoroso> findPorosoByCodPlan(String codPlan) {

		try {

			HidrogeoPorosoService service = new HidrogeoPorosoService(urlService);

			Set<HidrogeoPoroso> set = service.findByCodPlan(codPlan);

			return set;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public Set<HidrogeoFraturado> findSubsystemFraturadoByCodPlan(String codPlan) {

		try {

			HidrogeoFraturadoService service = new HidrogeoFraturadoService(urlService);

			Set<HidrogeoFraturado> set = service.findByCodPlan(codPlan);

			return set;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public void fillSystemAndSubsystem(TipoInterferencia typeOfInterference, TipoPoco typeOfWell, String latitude,
			String longitude) {
		// Se tipo de interferência subterrânea
		if (typeOfInterference.getId() == 2L) {

			if (typeOfWell != null) {

				if (typeOfWell.getId() == 1L || typeOfWell.getId() == 2L) {

					// Buscar o sistema poroso e remover apenas os sistema (P1, P2) em formato de
					// string.
					ObservableList<HidrogeoPoroso> obsList = StaticData.INSTANCE.fetchAllPoroso();
					// Cria nova ObservableList<String> para os valores do "sistema", cbSystem.

					ObservableList<String> obsSystem = FXCollections.observableArrayList();

					// Cria uma array em formato set para não repetir os valores
					Set<String> setSystem = new HashSet<>();

					// Extrai o sistema em formato string e preenche a lista para o comobobx
					// cbSystem.
					for (HidrogeoPoroso item : obsList) {
						setSystem.add(item.getSistema()); // Assuming `getSistema()` is the getter for the `sistema`
															// attribute

					}

					// Preenche observable list e combobox
					obsSystem.addAll(setSystem);
					cbSystem.setItems(obsSystem);

					// Preenchimento do código do subsistema
					Set<HidrogeoPoroso> set = findSubsystemPorosoByPoint(latitude, longitude);
					// Como foi solicitado um select no banco, virá uma array com um valor, sendo
					// necessário
					// capturar o primeiro valor desta array
					if (set != null) {

						set.forEach(s -> {
							cbSystem.getSelectionModel().select(s.getSistema());
							// Como não há subsistema no poroso, limpe a seleção
							cbSubsystem.getSelectionModel().clearSelection();
							tfCodeSystem.setText(s.getCodPlan());
							tfSystemFlow.setText(String.valueOf((int) (s.getqMedia() * 1000)));

						});

					}

				} else {

					// Buscar o sistema poroso e remover apenas os sistema (P1, P2) em formato de
					// string.
					ObservableList<HidrogeoFraturado> obsList = StaticData.INSTANCE.fetchAllFraturado();
					// Cria nova ObservableList<String> para os valores do "sistema", cbSystem.

					ObservableList<String> obsSystem = FXCollections.observableArrayList();
					// No caso do subsistema fraturado há o subsistema.
					ObservableList<String> obsSubsystem = FXCollections.observableArrayList();

					// Cria uma array em formato set para não repetir os valores
					Set<String> setSystem = new HashSet<>();
					Set<String> setSubsystem = new HashSet<>();

					// Extrai o sistema em formato string e preenche a lista para o comobobx
					// cbSystem.
					for (HidrogeoFraturado item : obsList) {
						setSystem.add(item.getSistema()); // Assuming `getSistema()` is the getter for the `sistema`
															// attribute
						setSubsystem.add(item.getSubsistema());
					}

					// Preenche observable list e combobox (Systema)
					obsSystem.addAll(setSystem);
					cbSystem.setItems(obsSystem);
					// Preenche o subsistema (cbSubsystem)
					obsSubsystem.addAll(setSubsystem);
					cbSubsystem.setItems(obsSubsystem);

					Set<HidrogeoFraturado> set = findSubsystemFraturadoByPoint(latitude, longitude);
					if (set != null) {

						set.forEach(s -> {
							// Preenchimento de dados do subsistema
							cbSystem.getSelectionModel().select(s.getSistema());
							cbSubsystem.getSelectionModel().select(s.getSubsistema());
							tfCodeSystem.setText(s.getCodPlan());
							tfSystemFlow.setText(String.valueOf((int) (s.getVazao() * 1000)));
						});

					}

				}

			} else {

			}

		}
	}

}
