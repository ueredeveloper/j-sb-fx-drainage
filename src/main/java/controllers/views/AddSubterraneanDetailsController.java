package controllers.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import enums.StaticData;
import enums.ToastType;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Documento;
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
	private GridPane requestedPurposesGrid, authorizedPurposesGrid;

	ObservableList<TipoPoco> obsTypesOfWells;

	
	@FXML
	FontAwesomeIconView btnTotalCalculate;

	@FXML
	FontAwesomeIconView btnRequestedTotalCalculate, btnAuthorizedTotalCalculate;
	@FXML
	private JFXTextField tfTotalRequestedConsumption, tfTotalAuthorizedConsumption;
	
	PurpousesWrapper purpouses = new PurpousesWrapper(new HashSet<>());

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		obsTypesOfWells = StaticData.INSTANCE.getTypesOfWells();
		cbWellType.setItems(obsTypesOfWells);

		// Adiciona cadastro de finalidade
		addRow(0, requestedPurposesGrid, tfTotalRequestedConsumption, btnRequestedTotalCalculate,
				new TipoFinalidade(1L), new Finalidade());
		addRow(0, authorizedPurposesGrid, tfTotalAuthorizedConsumption, btnAuthorizedTotalCalculate,
				new TipoFinalidade(2L), new Finalidade());

	}

	public void addRow(int index, GridPane gridPane, JFXTextField tfTotalConsumption,
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
		if (purpouse == null || purpouse.getTipoFinalidade() == null ) {
			
			purpouse = new Finalidade(typeOfPurpouse);
			
			purpouseWrapper.setPurpouse(purpouse);
			// Adiciona finalidade com única na lista (Set)

			purpouses.getPurpouses().add(purpouseWrapper.getPurpouse());

		} else {
			
			purpouseWrapper.setPurpouse(purpouse);
			
			purpouses.getPurpouses().add(purpouseWrapper.getPurpouse());
			
			System.out.println("else: fill textfields ");
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

			addRow(rowIndex + 1, gridPane, tfTotalConsumption, btnTotalConsumption, typeOfPurpouse, null);

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

		    // Check if the purpose object and its ID are not null before attempting to delete
		    if (purpouseToDelete != null && purpouseToDelete.getId() != null) {
		    	delete(btnMinus, purpouseToDelete);
		    }

		    // Remove the purpose from the Set
		    purpouses.getPurpouses().remove(purpouseToDelete);
		});


		btnTotalConsumption.setOnMouseClicked(event -> {
			final Double[] total = { 0.0 }; // Usando um array para contornar a limitação de variáveis finais
			purpouses.getPurpouses().forEach(p -> {
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
		
		return purpouses.getPurpouses();
	}

	public void setPurpouses(Set<Finalidade> newPurpouses) {
	    purpouses.setPurpouses(newPurpouses);

	    requestedPurposesGrid.getChildren().clear();
	    authorizedPurposesGrid.getChildren().clear();

	    AtomicInteger reqIndex = new AtomicInteger(0);
	    AtomicInteger autIndex = new AtomicInteger(0);

	    purpouses.getPurpouses().forEach(item -> {
	        if (item.getTipoFinalidade().getId() == 1) {
	            System.out.println("req " + reqIndex.get());
	            addRow(reqIndex.getAndIncrement(), requestedPurposesGrid, tfTotalRequestedConsumption, btnRequestedTotalCalculate,
	                    item.getTipoFinalidade(), item);
	        } else {
	            System.out.println("aut " + autIndex.get());
	            addRow(autIndex.getAndIncrement(), authorizedPurposesGrid, tfTotalAuthorizedConsumption, btnAuthorizedTotalCalculate,
	                    item.getTipoFinalidade(), item);
	        }
	    });
	}

	// Modifica as finalidade dentro de uma  expressão lambda.
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
	
	public void delete (Node source, Finalidade purpouse) {

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
				//tvDocs.getItems().remove(selectedDocument);

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
