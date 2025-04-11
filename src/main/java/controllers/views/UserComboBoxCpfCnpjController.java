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

public class UserComboBoxCpfCnpjController {

	
	String localUrl;

	private JFXComboBox<String> comboBox;
	ObservableList<String> obsList = FXCollections.observableArrayList();
	// Objetos buscados no banco de dados. Estes objectos não podem repetir.
	private Set<String> dbObjects = new HashSet<>();

	public UserComboBoxCpfCnpjController(String localUrl, JFXComboBox<String> comboBox) {
		this.localUrl = localUrl;
		this.comboBox = comboBox;

		init();
	}

	// Método para inicializar o ComboBox
	public void init() {

		comboBox.setItems(obsList);
		comboBox.setEditable(true);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(comboBox,
				(typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(comboBox);

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(comboBox);

		comboBox.valueProperty().addListener(new ChangeListener<Object>() {
			private String lastSearch = "";

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {

				// Remover os items repetidos na lista
				List<String> filteredList = filterAndMaintainLastNullId(obsList);

				obsList.clear();
				obsList.addAll(filteredList);

				// Check if the newValue is a Processo or a String
				if (newValue instanceof Usuario) {
					Usuario object = (Usuario) newValue;

					if (object.getNome() != null && !object.getNome().isEmpty()) {
						// Check if the new search term is a continuation of the previous one
						if (lastSearch.contains(object.getNome()) || object.getNome().contains(lastSearch)) {
							obsList.clear();

							boolean containsSearchTerm = dbObjects.stream()
									.anyMatch(item -> item.toLowerCase().contains(object.getNome().toLowerCase()));

							if (containsSearchTerm) {
								obsList.clear();
								obsList.addAll(dbObjects);
							} else {
								obsList.clear();
								fetchAndUpdate(object.getNome());
							}
						}

						lastSearch = object.getNome();

						// Sort the list to put the selected value at the top
						obsList.sort((object1, object2) -> {
							if (object1.equalsIgnoreCase(object.getNome())) {
								return -1;
							} else if (object2.equalsIgnoreCase(object.getNome())) {
								return 1;
							}
							return 0;
						});

					}
				} else if (newValue instanceof String) {
					// Handle the case where newValue is a String (when the user is typing)
					String searchText = (String) newValue;
					fetchAndUpdate(searchText);
				}
			}
		});
	}

	/*
	 * Ao selecionar algo na table view `DocumentController`, este ítem é adicionado
	 * aqui para que não seja preciso buscá-lo no banco de dados e assim não ficando
	 * lento a seleção.
	 */
	public void addItemToDbObjects(String s) {
		dbObjects.add(s);
	}

	private void fetchAndUpdate(String keyword) {
		try {
			UsuarioService service = new UsuarioService(localUrl);
			Set<String> fetchedObjects = new HashSet<>();

			if (keyword.length() == 2 || keyword.length() == 4 || keyword.length() == 6 || keyword.length() == 8) {
				fetchedObjects.addAll(service.listByCpfCnpj(keyword));
			}

			if (!fetchedObjects.isEmpty()) {
				dbObjects.addAll(fetchedObjects);
				obsList.addAll(dbObjects);
			} else {
				// Se não houver resultados, adiciona o novo endereço diretamente
				// Usuario newObject = new Usuario(keyword);
				dbObjects.add(keyword);
				obsList.add(keyword);
			}
		} catch (Exception e) {
			// Trate exceções adequadamente
			e.printStackTrace();
		}
	}

	// Método para buscar processos e preencher o ComboBox
	public Set<String> listByUserName(String keyword) {

		try {
			UsuarioService service = new UsuarioService(localUrl);

			Set<String> list = service.listByCpfCnpj(keyword);

			return list;

		} catch (Exception e) {

		}

		return null;

	}

	public Usuario getSelectedObject() {

		/*
		 * Usuario object = comboBox.selectionModelProperty().get().isEmpty() ? null :
		 * comboBox.getItems().get(0);
		 * 
		 * /System.out.println(object.getNome()); return object;
		 */
		return null;
	}

	public void fillAndSelectComboBox(String str) {

		ObservableList<String> newObsList = FXCollections.observableArrayList();
		comboBox.setItems(newObsList);

		newObsList.add(0, str);

		// Atualizando o ComboBox para refletir a mudança //
		comboBox.setItems(null);
		comboBox.setItems(newObsList);

		// Selecionando o novo item no ComboBox
		comboBox.getSelectionModel().select(0);

	}

	public List<String> filterAndMaintainLastNullId(ObservableList<String> items) {
		return items;

		// Convert ObservableList to Set to remove duplicates
		/*
		 * Set<String> uniqueItems = new HashSet<>(items);
		 * 
		 * // Use a map to retain only unique non-null IDs (each id maps to one Anexo //
		 * object) Map<Long, String> nonNullIdMap = uniqueItems.stream().filter(item ->
		 * item != null) .collect(Collectors.toMap(Usuario::getId, // Key: id of the
		 * Anexo item -> item, // Value: Anexo itself (existing, replacement) ->
		 * existing // Keep the first occurrence if duplicates are found ));
		 * 
		 * // Get the last item with id == null (if it exists) Optional<String>
		 * lastNullIdItem = uniqueItems.stream().filter(anexo -> anexo.getId() == null)
		 * .reduce((first, second) -> second); // Keep the last one
		 * 
		 * // Convert the map values (unique non-null ids) to a list List<String>
		 * filteredList = new ArrayList<>(nonNullIdMap.values());
		 * 
		 * // Add the last item with id == null, if it exists
		 * lastNullIdItem.ifPresent(filteredList::add);
		 * 
		 * return filteredList;
		 */
	}
}
