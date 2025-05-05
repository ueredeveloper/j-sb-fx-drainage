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
import models.Anexo;
import models.Endereco;
import models.Usuario;
import services.EnderecoService;

public class AddressComboBoxController {

	private String localUrl;
	private JFXComboBox<Endereco> comboBox;
	private ObservableList<Endereco> obsList = FXCollections.observableArrayList();
	// Objetos buscados no banco de dados
	private Set<Endereco> dbObjects = new HashSet<>();
	
	Endereco address = new Endereco();

	public AddressComboBoxController(String localUrl, JFXComboBox<Endereco> comboBox) {
		this.localUrl = localUrl;
		this.comboBox = comboBox;
		init();
	}

	public void init() {
		comboBox.setItems(obsList);
		comboBox.setEditable(true);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(comboBox, (typedText,
				itemToCompare) -> itemToCompare.getLogradouro().toLowerCase().contains(typedText.toLowerCase()));

		comboBox.valueProperty().addListener(new ChangeListener<Object>() {
			private String lastSearch = "";

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {

				// Remover os items repetidos na lista
				List<Endereco> filteredList = filterAndMaintainLastNullId(obsList);

				obsList.clear();
				obsList.addAll(filteredList);

				// Check if the newValue is a Processo or a String
				if (newValue instanceof Endereco) {
					Endereco object = (Endereco) newValue;
					
					address = object;
					

					if (object.getLogradouro() != null && !object.getLogradouro().isEmpty()) {
						// Check if the new search term is a continuation of the previous one
						if (lastSearch.contains(object.getLogradouro())
								|| object.getLogradouro().contains(lastSearch)) {
							obsList.clear();

							boolean containsSearchTerm = dbObjects.stream().anyMatch(item -> item.getLogradouro()
									.toLowerCase().contains(object.getLogradouro().toLowerCase()));

							if (containsSearchTerm) {
								obsList.clear();
								obsList.addAll(dbObjects);
							} else {
								obsList.clear();
								fetchAndUpdate(object.getLogradouro());
							}
						}

						lastSearch = object.getLogradouro();

						// Sort the list to put the selected value at the top
						obsList.sort((object1, object2) -> {
							if (object1.getLogradouro().equalsIgnoreCase(object.getLogradouro())) {
								return -1;
							} else if (object2.getLogradouro().equalsIgnoreCase(object.getLogradouro())) {
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
	public void addItemToDbObjects(Endereco object) {
		dbObjects.add(object);
	}

	private void fetchAndUpdate(String keyword) {

		try {
			EnderecoService service = new EnderecoService(localUrl);
			Set<Endereco> fetchedAddresses = new HashSet<>();

			// Buscar endereços apenas contento 2, 4 , 6 ou 8 caracteres. Assim o serviço
			// não fica superesplotado.
			if (keyword.length() == 2 || keyword.length() == 4 || keyword.length() == 6 || keyword.length() == 8) {
				
				fetchedAddresses.addAll(service.fetchAddressByKeyword(keyword));
			}

			if (!fetchedAddresses.isEmpty()) {
				
				dbObjects.addAll(fetchedAddresses);
				//Filtra por ids diferentes
				Set<Endereco> filteredUniqueIds = dbObjects.stream()
						.collect(Collectors.toMap(Endereco::getId, item -> item, (oldValue, newValue) -> newValue))
						.values().stream().collect(Collectors.toSet());
				
				obsList.clear();
				obsList.addAll(filteredUniqueIds);

			} else {
				// Se não houver resultados, adiciona o novo endereço diretamente
				Endereco newAddress = new Endereco(keyword);
				dbObjects.add(newAddress);
				obsList.add(newAddress);
			}
		} catch (Exception e) {
			// Trate exceções adequadamente
			e.printStackTrace();
		}
	}

	public Endereco getSelectedObject() {
		return address;
	}

	public ObservableList<Endereco> getObservableList() {
		return this.obsList;
	}

	public void fillAndSelectComboBox(Endereco object) {
		ObservableList<Endereco> newObsList = FXCollections.observableArrayList();
		newObsList.add(object);
		comboBox.setItems(newObsList);
		comboBox.getSelectionModel().select(object);
	}

	public void clearComponent() {
		comboBox.getSelectionModel().clearSelection();
	}

	public List<Endereco> filterAndMaintainLastNullId(ObservableList<Endereco> items) {

		// Convert ObservableList to Set to remove duplicates
		Set<Endereco> uniqueItems = new HashSet<>(items);

		// Use a map to retain only unique non-null IDs (each id maps to one Anexo
		// object)
		Map<Long, Endereco> nonNullIdMap = uniqueItems.stream().filter(item -> item.getId() != null)
				.collect(Collectors.toMap(Endereco::getId, // Key: id of the Anexo
						item -> item, // Value: Anexo itself
						(existing, replacement) -> existing // Keep the first occurrence if duplicates are found
		));

		// Get the last item with id == null (if it exists)
		Optional<Endereco> lastNullIdItem = uniqueItems.stream().filter(anexo -> anexo.getId() == null)
				.reduce((first, second) -> second); // Keep the last one

		// Convert the map values (unique non-null ids) to a list
		List<Endereco> filteredList = new ArrayList<>(nonNullIdMap.values());

		// Add the last item with id == null, if it exists
		lastNullIdItem.ifPresent(filteredList::add);

		return filteredList;
	}
}
