package controllers.views;

import java.util.List;

import com.jfoenix.controls.JFXComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Endereco;
import services.EnderecoService;

public class AddressComboBoxController {

	String localUrl;

	private JFXComboBox<Endereco> comboBox;
	ObservableList<Endereco> obsAddress = FXCollections.observableArrayList();

	public AddressComboBoxController(String localUrl, JFXComboBox<Endereco> comboBox) {
		super();
		this.localUrl = localUrl;
		this.comboBox = comboBox;

		init();
	}

	// Método para inicializar o ComboBox
	public void init() {
		comboBox.setItems(obsAddress);
		comboBox.setEditable(true);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(comboBox, (typedText,
				itemToCompare) -> itemToCompare.getEndLogradouro().toLowerCase().contains(typedText.toLowerCase()));

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(comboBox);

		// Preenche com valores do servidor atualizando ao digitar
		comboBox.getEditor().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					obsAddress.clear();
					List<Endereco> list = fetchAddress(newValue);

					if (list != null) {
						boolean containsSearchTerm = list.stream()
								.anyMatch(endereco -> endereco.getEndLogradouro().contains(newValue));
						if (containsSearchTerm) {
							obsAddress.addAll(list);
						} else {
							obsAddress.add(new Endereco(newValue));
							obsAddress.addAll(list);
						}
					}
				}
			}
		});
	}

	public List<Endereco> fetchAddress(String keyword) {
		try {
			EnderecoService service = new EnderecoService(localUrl);
			List<Endereco> list = service.fetchAddressByKeyword(keyword);
			return list;
		} catch (Exception e) {
			// Trate exceções adequadamente
		}
		return null;
	}

	public Endereco getSelectedObject() {
		// Verifica se nulo e preenche objeto.
		Endereco endereco = comboBox.selectionModelProperty().get().isEmpty() ? null
				: new Endereco(obsAddress.get(0).getEndId(), obsAddress.get(0).getEndLogradouro());

		return endereco;

	}

	public ObservableList<Endereco> getObservableList() {
		return this.obsAddress;
	}

	public void fillAndSelectComboBox(Endereco object) {
		ObservableList<Endereco> newObsList = FXCollections.observableArrayList();
		comboBox.setItems(newObsList);

		newObsList.add(0, object);

		// Atualizando o ComboBox para refletir a mudança
		comboBox.setItems(newObsList);

		// Selecionando o novo item no ComboBox
		comboBox.getSelectionModel().select(0);
	}

	public void clearComponent() {
		comboBox.getSelectionModel().clearSelection();
	}
}
