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
import models.Endereco;
import models.Interferencia;
import services.InterferenciaService;

public class InterferenceComboBoxController implements Initializable {

	String localUrl;

	private JFXComboBox<Interferencia> cbInterference;
	ObservableList<Interferencia> obsInterference = FXCollections.observableArrayList();

	public InterferenceComboBoxController(String localUrl, JFXComboBox<Interferencia> cbInterference) {
		this.localUrl = localUrl;
		this.cbInterference = cbInterference;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Deixe vazio, já que o ComboBox será inicializado em outro lugar.
	}

	// Método para inicializar o ComboBox
	public void initializeComboBox() {

		cbInterference.setItems(obsInterference);
		cbInterference.setEditable(true);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(cbInterference, (typedText, itemToCompare) -> {
			System.out.println("typedText: " + typedText);
			System.out.println("itemToCompare: " + itemToCompare);
			 if (itemToCompare == null) {
		            return false;
		        }

		        String interLogradouro = itemToCompare.getInterLogradouro();
		        if (interLogradouro == null) {
		            return false;
		        }

		        return interLogradouro.toLowerCase().contains(typedText.toLowerCase());
		});
		utilities.FxUtilComboBoxSearchable.getComboBoxValue(cbInterference);

		// Preeche com valores do servido atualizando ao digitar
		cbInterference.getEditor().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (newValue != null) {

					obsInterference.clear();
					List<Interferencia> list = fetch(newValue);
					boolean containsSearchTerm = list.stream()
							.anyMatch(interferencia -> interferencia.getInterLogradouro().contains(newValue));

					//
					// Se o que foi digitado está contido, não adicina novo processo, porém, se o
					// que foi digitado não está contido na lista, adiciona novo processo com id
					// nulo.
					//
					if (containsSearchTerm) {
						obsInterference.addAll(list);
					} else {
						obsInterference.add(new Interferencia(new Endereco(newValue)));
						obsInterference.addAll(list);
					}

				}

			}
		});
	}

	// Método para buscar processos e preencher o ComboBox
	public List<Interferencia> fetch(String keyword) {

		try {
			InterferenciaService service = new InterferenciaService(localUrl);

			List<Interferencia> list = service.fetch(keyword);

			return list;

		} catch (Exception e) {

		}

		return null;

	}
}
