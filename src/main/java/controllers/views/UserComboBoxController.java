package controllers.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Anexo;
import models.Usuario;
import services.UsuarioService;

public class UserComboBoxController {

	String localUrl;

	private JFXComboBox<Usuario> comboBox;
	ObservableList<Usuario> obsList = FXCollections.observableArrayList();
	// Objetos buscados no banco de dados. Estes objectos não podem repetir.
	private Set<Usuario> dbObjects = new HashSet<>();
	
	Usuario user = new Usuario ();

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

		comboBox.valueProperty().addListener(new ChangeListener<Object>() {
			private String lastSearch = "";

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {

				// Remover os items repetidos na lista
				List<Usuario> filteredList = filterAndMaintainLastNullId(obsList);

				obsList.clear();
				obsList.addAll(filteredList);

				// Check if the newValue is a Processo or a String
				if (newValue instanceof Usuario) {
					Usuario object = (Usuario) newValue;
					
					user = object;

					if (object.getNome() != null && !object.getNome().isEmpty()) {
						// Check if the new search term is a continuation of the previous one
						if (lastSearch.contains(object.getNome()) || object.getNome().contains(lastSearch)) {
							obsList.clear();

							boolean containsSearchTerm = dbObjects.stream().anyMatch(
									item -> item.getNome().toLowerCase().contains(object.getNome().toLowerCase()));

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
							if (object1.getNome().equalsIgnoreCase(object.getNome())) {
								return -1;
							} else if (object2.getNome().equalsIgnoreCase(object.getNome())) {
								return 1;
							}
							return 0;
						});

					}
				} else if (newValue instanceof String) {
					// Handle the case where newValue is a String (when the  is typing)
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
	public void addItemToDbObjects(Usuario object) {
		dbObjects.add(object);
	}

	private void fetchAndUpdate(String keyword) {
		try {
			UsuarioService service = new UsuarioService(localUrl);
			Set<Usuario> fetchedObjects = new HashSet<>();

			if (keyword.length() == 2 || keyword.length() == 4 || keyword.length() == 6 || keyword.length() == 8) {
				fetchedObjects.addAll(service.listByName(keyword));
			}

			if (!fetchedObjects.isEmpty()) {
				dbObjects.addAll(fetchedObjects);
				//Filtra por ids diferentes
				Set<Usuario> filteredUniqueIds = dbObjects.stream()
						.collect(Collectors.toMap(Usuario::getId, item -> item, (oldValue, newValue) -> newValue))
						.values().stream().collect(Collectors.toSet());
				obsList.clear();
				obsList.addAll(filteredUniqueIds);
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
	public Set<Usuario> listByUserName(String keyword) {

		try {
			UsuarioService service = new UsuarioService(localUrl);

			Set<Usuario> list = service.listByName(keyword);

			return list;

		} catch (Exception e) {

		}

		return null;

	}

	public Usuario getSelectedObject() {
		
		System.out.println(user.getCpfCnpj());
		return user;
	}

	public void fillAndSelectComboBox(Usuario object) {
	
		user = object;
		
		ObservableList<Usuario> newObsList = FXCollections.observableArrayList();
		comboBox.setItems(newObsList);

		newObsList.add(0, object);

		// Atualizando o ComboBox para refletir a mudança
		// cbProcess.setItems(null);
		comboBox.setItems(newObsList);

		// Selecionando o novo item no ComboBox
		comboBox.getSelectionModel().select(0);
	}

	public List<Usuario> filterAndMaintainLastNullId(ObservableList<Usuario> items) {

		// Convert ObservableList to Set to remove duplicates
		Set<Usuario> uniqueItems = new HashSet<>(items);

		// Use a map to retain only unique non-null IDs (each id maps to one Anexo
		// object)
		Map<Long, Usuario> nonNullIdMap = uniqueItems.stream().filter(item -> item.getId() != null)
				.collect(Collectors.toMap(Usuario::getId, // Key: id of the Anexo
						item -> item, // Value: Anexo itself
						(existing, replacement) -> existing // Keep the first occurrence if duplicates are found
				));

		// Get the last item with id == null (if it exists)
		Optional<Usuario> lastNullIdItem = uniqueItems.stream().filter(anexo -> anexo.getId() == null)
				.reduce((first, second) -> second); // Keep the last one

		// Convert the map values (unique non-null ids) to a list
		List<Usuario> filteredList = new ArrayList<>(nonNullIdMap.values());

		// Add the last item with id == null, if it exists
		lastNullIdItem.ifPresent(filteredList::add);

		return filteredList;
	}
}
