package controllers.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Processo;
import models.Usuario;
import services.ProcessoService;

public class ProcessComboBoxController {

	
	String localUrl;

	private JFXComboBox<Processo> comboBox;
	ObservableList<Processo> obsList = FXCollections.observableArrayList();

	// Objetos buscados no banco de dados. Estes objectos não podem repetir.
	private Set<Processo> dbObjects = new HashSet<>();

	public ProcessComboBoxController(String localUrl, JFXComboBox<Processo> comboBox) {
		this.localUrl = localUrl;
		this.comboBox = comboBox;

		init();
	}

	// Método para inicializar o ComboBox
	public void init() {

		comboBox.setItems(obsList);
		comboBox.setEditable(true);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(comboBox, (typedText,
				itemToCompare) -> itemToCompare.getNumero().toLowerCase().contains(typedText.toLowerCase()));

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(comboBox);

		comboBox.valueProperty().addListener(new ChangeListener<Object>() {
			private String lastSearch = "";

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				
				// Remover os items repetidos na lista
				List<Processo> filteredList = filterAndMaintainLastNullId(obsList);

				obsList.clear();
				obsList.addAll(filteredList);

				// Check if the newValue is a Processo or a String
				if (newValue instanceof Processo) {
					Processo processo = (Processo) newValue;

					if (processo.getNumero() != null && !processo.getNumero().isEmpty()) {
						// Check if the new search term is a continuation of the previous one
						if (lastSearch.contains(processo.getNumero()) || processo.getNumero().contains(lastSearch)) {
							obsList.clear();

							boolean containsSearchTerm = dbObjects.stream().anyMatch(object -> object.getNumero()
									.toLowerCase().contains(processo.getNumero().toLowerCase()));

							if (containsSearchTerm) {
								obsList.clear();
								obsList.addAll(dbObjects);
							} else {
								obsList.clear();
								fetchAndUpdate(processo.getNumero());
							}
						}

						lastSearch = processo.getNumero();

						// Sort the list to put the selected value at the top
						obsList.sort((object1, object2) -> {
							if (object1.getNumero().equalsIgnoreCase(processo.getNumero())) {
								return -1;
							} else if (object2.getNumero().equalsIgnoreCase(processo.getNumero())) {
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

		// System.out.println(comboBox.getItems().get(0).getNumero());
	}

	/*
	 * Ao selecionar algo na table view `DocumentController`, este ítem é adicionado
	 * aqui para que não seja preciso buscá-lo no banco de dados e assim não ficando
	 * lento a seleção.
	 */
	public void addItemToDbObjects(Processo object) {
		dbObjects.add(object);
	}

	private void fetchAndUpdate(String keyword) {
		try {
			ProcessoService service = new ProcessoService(localUrl);
			Set<Processo> fetchedObjects = new HashSet<>();

			if (keyword.length() == 2 || keyword.length() == 4 || keyword.length() == 6 || keyword.length() == 8) {
				fetchedObjects.addAll(service.fetchByKeyword(keyword));
			}

			if (!fetchedObjects.isEmpty()) {
				dbObjects.addAll(fetchedObjects);
				
				//Filtra por ids diferentes
				Set<Processo> filteredUniqueIds = dbObjects.stream()
						.collect(Collectors.toMap(Processo::getId, item -> item, (oldValue, newValue) -> newValue))
						.values().stream().collect(Collectors.toSet());
				
				obsList.clear();
				obsList.addAll(filteredUniqueIds);
			} else {
				// Se não houver resultados, adiciona o novo endereço diretamente
				Processo newObject = new Processo(keyword);
				dbObjects.add(newObject);
				obsList.add(newObject);
			}
		} catch (Exception e) {
			// Trate exceções adequadamente
			e.printStackTrace();
		}
	}

	// Método para buscar processos e preencher o ComboBox
	public Set<Processo> fetchProcesses(String keyword) {

		try {
			ProcessoService service = new ProcessoService(localUrl);

			Set<Processo> objects = service.fetchByKeyword(keyword);

			return objects;

		} catch (Exception e) {

		}
		return null;
	}

	public Processo getSelectedObject() {

		Processo object = comboBox.selectionModelProperty().get().isEmpty() ? null : comboBox.getItems().get(0);
		return object;
	}

	public void fillAndSelectComboBoxProcess(Processo process) {
		// Create a new ObservableList and add the process
		ObservableList<Processo> newObs = FXCollections.observableArrayList(process);

		// Set the new list as the ComboBox items
		comboBox.setItems(newObs);

		// Select the first item (index 0)
		comboBox.getSelectionModel().select(0);
	}
	
	


	public List<Processo> filterAndMaintainLastNullId(ObservableList<Processo> items) {

		// Convert ObservableList to Set to remove duplicates
		Set<Processo> uniqueItems = new HashSet<>(items);

		// Use a map to retain only unique non-null IDs (each id maps to one Anexo
		// object)
		Map<Long, Processo> nonNullIdMap = uniqueItems.stream().filter(processo -> processo.getId() != null)
				.collect(Collectors.toMap(Processo::getId, // Key: id of the Anexo
						anexo -> anexo, // Value: Anexo itself
						(existing, replacement) -> existing // Keep the first occurrence if duplicates are found
		));

		// Get the last item with id == null (if it exists)
		Optional<Processo> lastNullIdItem = uniqueItems.stream().filter(processo -> processo.getId() == null)
				.reduce((first, second) -> second); // Keep the last one

		// Convert the map values (unique non-null ids) to a list
		List<Processo> filteredList = new ArrayList<>(nonNullIdMap.values());

		// Add the last item with id == null, if it exists
		lastNullIdItem.ifPresent(filteredList::add);

		return filteredList;
	}
}
