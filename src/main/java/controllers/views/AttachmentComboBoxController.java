package controllers.views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import models.Anexo;
import services.AnexoService;

public class AttachmentComboBoxController implements Initializable {
	

	String localUrl;

	private JFXComboBox<Anexo> comboBox;
	ObservableList<Anexo> obsList = FXCollections.observableArrayList();

	public AttachmentComboBoxController(String localUrl, JFXComboBox<Anexo> comboBox) {
		this.localUrl = localUrl;
		this.comboBox = comboBox;

		init();
	}

	// Método para inicializar o ComboBox
	public void init() {

		comboBox.setItems(obsList);
		comboBox.setEditable(true);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(comboBox, (typedText,
				itemToCompare) -> itemToCompare.getAnNumero().toLowerCase().contains(typedText.toLowerCase()));

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(comboBox);

		// Preeche com valores do servido atualizando ao digitar
		comboBox.getEditor().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (newValue != null) {

					obsList.clear();
					List<Anexo> list = fetchByKeyword(newValue);
					boolean containsSearchTerm = list.stream()
							.anyMatch(Anexo -> Anexo.getAnNumero().contains(newValue));
					//
					// Se o que foi digitado está contido, não adicina novo Anexo, porém, se o
					// que foi digitado não está contido na lista, adiciona novo Anexo com id
					// nulo.
					//
					if (containsSearchTerm) {
						obsList.addAll(list);
					} else {
						obsList.add(new Anexo(newValue));
						obsList.addAll(list);
					}

				}

			}
		});
	}

	// Método para buscar Anexos e preencher o ComboBox
	public List<Anexo> fetchByKeyword(String keyword) {

		try {
			AnexoService service = new AnexoService(localUrl);

			List<Anexo> list = service.fecthByKeyword(keyword);

			return list;

		} catch (Exception e) {

		}

		return null;

	}

	public Anexo getSelectedObject() {
		// Verifica se nulo, se não nulo preenche objeto e retorna.
		Anexo object = comboBox.selectionModelProperty().get().isEmpty() ? null
				: new Anexo(obsList.get(0).getAnId(), obsList.get(0).getAnNumero());
		return object;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void fillAndSelectComboBox(Anexo object) {
		ObservableList<Anexo> newObsList = FXCollections.observableArrayList();
		comboBox.setItems(newObsList);

		newObsList.add(0, object);

		// Atualizando o ComboBox para refletir a mudança
		// cbProcess.setItems(null);
		comboBox.setItems(newObsList);

		// Selecionando o novo item no ComboBox
		comboBox.getSelectionModel().select(0);
	}

}
