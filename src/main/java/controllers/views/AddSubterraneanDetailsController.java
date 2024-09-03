package controllers.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import enums.StaticData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
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

	ObservableList<TipoPoco> obsTypesOfWells;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		obsTypesOfWells = StaticData.INSTANCE.getTypesOfWells();
		cbWellType.setItems(obsTypesOfWells);

	}

}
