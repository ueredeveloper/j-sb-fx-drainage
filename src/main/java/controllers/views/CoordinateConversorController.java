package controllers.views;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import org.locationtech.proj4j.ProjCoordinate;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import controllers.MapController;
import enums.ToastType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import utilities.CoordinateConverter;

public class CoordinateConversorController implements Initializable {

	@FXML
	private JFXTextField tfUtmX;

	@FXML
	private JFXTextField tfUtmY;

	@FXML
	private JFXComboBox<String> cbUtmZone, cbUtmHemisphere;

	@FXML
	private JFXTextField tfLatUtmToDd;

	@FXML
	private JFXTextField tfLngUtmToDd;

	@FXML
	private Button btnUtmToDd;

	@FXML
	private Button btnUtmAddMarker, btnDmsAddMarker;

	@FXML
	private Tooltip btnDmsToDdInMap;

	@FXML
	private Button btnDmsToDd;

	@FXML
	private JFXTextField tfLatDegree;

	@FXML
	private JFXTextField tfLatMinute;

	@FXML
	private JFXTextField tfLatSecond;

	@FXML
	private JFXComboBox<String> cbDmsNS, cbDmsWE;

	@FXML
	private JFXTextField tfLngDegree;

	@FXML
	private JFXTextField tfLngMinute;

	@FXML
	private JFXTextField tfLngSecond;

	@FXML
	private JFXTextField tfLatDmsToDd;

	@FXML
	private JFXTextField tfLngDmsToDd;

	MapController mapController;

	public void setMapController(MapController mapController) {
		this.mapController = mapController;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		cbUtmZone.getItems().addAll("22", "23");
		cbUtmZone.getSelectionModel().select(1);
		cbUtmHemisphere.getItems().addAll("N", "S");
		cbUtmHemisphere.getSelectionModel().select(1);

		tfUtmX.setText("191141.09");
		tfUtmY.setText("8251747.16");

		btnUtmToDd.setOnMouseClicked(event -> {
			Double x = Double.parseDouble(tfUtmX.getText());
			Double y = Double.parseDouble(tfUtmY.getText());
			String zone = cbUtmZone.getSelectionModel().getSelectedItem();
			String hemisphere = cbUtmHemisphere.getSelectionModel().getSelectedItem();

			CoordinateConverter cc = new CoordinateConverter();

			ProjCoordinate latLng = cc.convertUtmlToDecimal(x, y, zone, hemisphere);

			// Padroniza seis números depois da vírgula nas coordenadas
			BigDecimal bd;

			bd = new BigDecimal(latLng.y).setScale(7, RoundingMode.FLOOR);

			String lat = String.valueOf(bd);

			// Padroniza seis números depois da vírgula nas coordenadas
			bd = new BigDecimal(latLng.x).setScale(7, RoundingMode.FLOOR);

			String lng = String.valueOf(bd);

			tfLatUtmToDd.setText(lat);
			tfLngUtmToDd.setText(lng);

			mapController.setCoordinates(lat, lng);

		});

		cbDmsNS.getItems().addAll("N", "S");
		cbDmsNS.getSelectionModel().select(1);

		cbDmsWE.getItems().addAll("E", "W");
		cbDmsWE.getSelectionModel().select(1);

		// 15° 47' 38.0004'' S 47° 52' 58.0008'' W
		tfLatDegree.setText("15");
		tfLatMinute.setText("47");
		tfLatSecond.setText("38.0004");

		tfLngDegree.setText("47");
		tfLngMinute.setText("52");
		tfLngSecond.setText("58.0008");

		btnDmsToDd.setOnMouseClicked(event -> {

			int latDegrees = Integer.parseInt(tfLatDegree.getText());
			int latMinutes = Integer.parseInt(tfLatMinute.getText());
			double latSeconds = Double.parseDouble(tfLatSecond.getText());

			boolean latIsNegative = cbDmsNS.getValue().equals("S") ? true : false;

			CoordinateConverter cc;

			// Converte a latitude
			cc = new CoordinateConverter();
			// (int degrees, int minutes, double seconds, boolean isNegative)
			double lat = cc.convertGMSToDecimal(latDegrees, latMinutes, latSeconds, latIsNegative);

			// Padroniza seis números depois da vírgula nas coordenadas
			BigDecimal bd;

			bd = new BigDecimal(lat).setScale(7, RoundingMode.FLOOR);
			tfLatDmsToDd.setText(String.valueOf(bd));

			int lngDegrees = Integer.parseInt(tfLngDegree.getText());
			int lngMinutes = Integer.parseInt(tfLngMinute.getText());
			double lngSeconds = Double.parseDouble(tfLngSecond.getText());

			boolean lngIsNegative = cbDmsWE.getValue().equals("W") ? true : false;

			// Converte a longitude
			cc = new CoordinateConverter();
			// (int degrees, int minutes, double seconds, boolean isNegative)
			double lng = cc.convertGMSToDecimal(lngDegrees, lngMinutes, lngSeconds, lngIsNegative);

			bd = new BigDecimal(lng).setScale(7, RoundingMode.FLOOR);
			tfLngDmsToDd.setText(String.valueOf(bd));

			mapController.setCoordinates(String.valueOf(lat), String.valueOf(lng));

		});

		btnUtmAddMarker.setOnAction(evet -> {

			if (tfLatUtmToDd.getText().isEmpty() || tfLngUtmToDd.getText().isEmpty()) {
				// Informa salvamento com sucesso
				Node source = (Node) tfLatUtmToDd;
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Pesquise uma coordenada primeiro, valores de coordenadas vazios !!!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
				return;
			}

			String lat = tfLatUtmToDd.getText();
			String lng = tfLngUtmToDd.getText();

			mapController.handleAddMarker("{lat:" + lat + "," + "lng:" + lng + "}");
		});

		btnDmsAddMarker.setOnAction(evet -> {

			if (tfLatDmsToDd.getText().isEmpty() || tfLngDmsToDd.getText().isEmpty()) {
				// Informa salvamento com sucesso
				Node source = (Node) tfLatDmsToDd;
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Pesquise uma coordenada primeiro, valores de coordenadas vazios !!!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
				return;
			}

			String lat = tfLatDmsToDd.getText();
			String lng = tfLngDmsToDd.getText();

			mapController.handleAddMarker("{lat:" + lat + "," + "lng:" + lng + "}");
		});

	}

}
