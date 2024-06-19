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

	private JFXComboBox<Usuario> cbUser;
	ObservableList<Usuario> obsUser = FXCollections.observableArrayList();

	public UserComboBoxController(String localUrl, JFXComboBox<Usuario> cbUser) {
		this.localUrl = localUrl;
		this.cbUser = cbUser;
		
		init();
	}

	// Método para inicializar o ComboBox
	public void init() {

		cbUser.setItems(obsUser);
		cbUser.setEditable(true);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(cbUser, (typedText,
				itemToCompare) -> itemToCompare.getUsNome().toLowerCase().contains(typedText.toLowerCase()));

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(cbUser);

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(cbUser);

		// Preeche com valores do servido atualizando ao digitar
		cbUser.getEditor().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					obsUser.clear();
					List<Usuario> list = fetchByKeyword(newValue);
					System.out.println("endereço size " + list.size());

					if (list != null) {
						boolean containsSearchTerm = list.stream()
								.anyMatch(endereco -> endereco.getUsNome().contains(newValue));
						if (containsSearchTerm) {
							obsUser.addAll(list);
						} else {
							obsUser.add(new Usuario(newValue));
							obsUser.addAll(list);
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
		Usuario object = cbUser.selectionModelProperty().get().isEmpty() ? null
				: new Usuario(obsUser.get(0).getUsId(), obsUser.get(0).getUsNome());

		return object;

	}
}
