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

	private JFXComboBox<Endereco> cbAddress;
	ObservableList<Endereco> obsAddress = FXCollections.observableArrayList();

	public AddressComboBoxController(String localUrl, JFXComboBox<Endereco> cbAddress) {
		super();
		this.localUrl = localUrl;
		this.cbAddress = cbAddress;
		
		init();
	}

	// Método para inicializar o ComboBox
	public void init () {
		cbAddress.setItems(obsAddress);
		cbAddress.setEditable(true);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(cbAddress, (typedText,
				itemToCompare) -> itemToCompare.getEndLogradouro().toLowerCase().contains(typedText.toLowerCase()));

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(cbAddress);

		// Preenche com valores do servidor atualizando ao digitar
		cbAddress.getEditor().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					obsAddress.clear();
					List<Endereco> list = fetchAddress(newValue);
					System.out.println("endereço size " + list.size());

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
			List<Endereco> list = service.fetchAddress(keyword);
			return list;
		} catch (Exception e) {
			// Trate exceções adequadamente
		}
		return null;
	}

	public Endereco getSelectedObject() {
		// Verifica se nulo e preenche objeto.
		Endereco endereco = cbAddress.selectionModelProperty().get().isEmpty() ? null
				: new Endereco(obsAddress.get(0).getEndId(), obsAddress.get(0).getEndLogradouro());

		return endereco;

	}
}
