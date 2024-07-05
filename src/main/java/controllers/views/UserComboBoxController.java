package controllers.views;

import java.util.List;

import com.jfoenix.controls.JFXComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Usuario;
import services.UsuarioService;

public class UserComboBoxController {
	
	

	
	String localUrl;

	private JFXComboBox<Usuario> comboBox;
	ObservableList<Usuario> obsList = FXCollections.observableArrayList();

	public UserComboBoxController(String localUrl, JFXComboBox<Usuario> comboBox) {
		this.localUrl = localUrl;
		this.comboBox = comboBox;

		init();
	}

	// Método para inicializar o ComboBox
	public void init() {

		comboBox.setItems(obsList);
		comboBox.setEditable(true);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(comboBox, (typedText,
				itemToCompare) -> itemToCompare.getUsNome().toLowerCase().contains(typedText.toLowerCase()));

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(comboBox);

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(comboBox);

		// Preeche com valores do servido atualizando ao digitar
		comboBox.getEditor().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					obsList.clear();
					List<Usuario> list = fetchByKeyword(newValue);

					if (list != null) {
						boolean containsSearchTerm = list.stream()
								.anyMatch(endereco -> endereco.getUsNome().contains(newValue));
						if (containsSearchTerm) {
							obsList.addAll(list);
						} else {
							obsList.add(new Usuario(newValue));
							obsList.addAll(list);
						}
					}
				}
			}
		});
	}

	// Método para buscar processos e preencher o ComboBox
	public List<Usuario> fetchByKeyword(String keyword) {

		try {
			UsuarioService service = new UsuarioService(localUrl);

			List<Usuario> list = service.fetchByKeyword(keyword);

			return list;

		} catch (Exception e) {

		}

		return null;

	}

	public Usuario getSelectedObject() {
		// Verifica se nulo, se não preenche objeto e retorna.
		Usuario object = comboBox.selectionModelProperty().get().isEmpty() ? null
				: new Usuario(obsList.get(0).getUsId(), obsList.get(0).getUsNome());

		return object;

	}

	public void fillAndSelectComboBox(Usuario object) {
		ObservableList<Usuario> newObsList = FXCollections.observableArrayList();
		comboBox.setItems(newObsList);

		newObsList.add(0, object);

		// Atualizando o ComboBox para refletir a mudança
		// cbProcess.setItems(null);
		comboBox.setItems(newObsList);

		// Selecionando o novo item no ComboBox
		comboBox.getSelectionModel().select(0);
	}
}
