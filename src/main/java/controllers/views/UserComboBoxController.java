package controllers.views;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	// Objetos buscados no banco de dados. Estes objectos não podem repetir.
	private Set<Usuario> dbObjects = new HashSet<>();

	public UserComboBoxController(String localUrl, JFXComboBox<Usuario> comboBox) {
		this.localUrl = localUrl;
		this.comboBox = comboBox;

		init();
	}

	// Método para inicializar o ComboBox
	public void init() {

		comboBox.setItems(obsList);
		comboBox.setEditable(true);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(comboBox,
				(typedText, itemToCompare) -> itemToCompare.getNome().toLowerCase().contains(typedText.toLowerCase()));

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(comboBox);

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(comboBox);

		// Preeche com valores do servido atualizando ao digitar
		comboBox.getEditor().textProperty().addListener(new ChangeListener<String>() {
			private String lastSearch = "";

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.isEmpty() && newValue != "") {
					

					// Verifica se a nova busca é uma continuação da busca anterior, tanto
					// adicionando como removendo caracteres
					if (lastSearch.contains(newValue) || newValue.contains(lastSearch)) {
						
						obsList.clear();
						
						boolean containsSearchTerm = dbObjects.stream()
								.anyMatch(object -> object.getNome().toLowerCase().contains(newValue.toLowerCase()));

						if (containsSearchTerm) {
							
							obsList.clear();
							
							obsList.addAll(dbObjects);
						} else {
							
							obsList.clear();
							fetchAndUpdate(newValue);
						}
					} /*
						 * else { // Nova busca completamente diferente, então limpamos o conjunto e
						 * fazemos uma // nova busca dbObjects.clear(); fetchAndUpdate(newValue); }
						 */

					lastSearch = newValue;
					
					// Ordena a lista colocando o newValue no início e assim podendo buscar (obsList.get(0) no método getSelectedObject.
		            obsList.sort((object1, object2) -> {
		                if (object1.getNome().equalsIgnoreCase(newValue)) {
		                    return -1; // Coloca endereco1 (com logradouro igual ao newValue) no início
		                } else if (object2.getNome().equalsIgnoreCase(newValue)) {
		                    return 1;  // Coloca endereco2 no início, se for o newValue
		                }
		                return 0;
		            });
		            
				}

			}
		});
	}

	/*
	 * Ao selecionar algo na table view `DocumentController`, este ítem é adicionado
	 * aqui para que não seja preciso buscá-lo no banco de dados e assim não ficando
	 * lento a seleção.
	 */
	public void addItemToDbObjects(Usuario object) {
		dbObjects.add(object);
	}

	private void fetchAndUpdate(String keyword) {
		try {
			UsuarioService service = new UsuarioService(localUrl);
			Set<Usuario> fetchedObjects = new HashSet<>(service.fetchByKeyword(keyword));

			if (!fetchedObjects.isEmpty()) {
				dbObjects.addAll(fetchedObjects);
				obsList.addAll(dbObjects);
			} else {
				// Se não houver resultados, adiciona o novo endereço diretamente
				Usuario newObject = new Usuario(keyword);
				dbObjects.add(newObject);
				obsList.add(newObject);
			}
		} catch (Exception e) {
			// Trate exceções adequadamente
			e.printStackTrace();
		}
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
				: new Usuario(obsList.get(0).getId(), obsList.get(0).getNome());

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
