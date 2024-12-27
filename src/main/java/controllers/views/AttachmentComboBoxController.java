package controllers.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import models.Anexo;
import services.AnexoService;

public class AttachmentComboBoxController implements Initializable {

	String urlService;

	private JFXComboBox<Anexo> comboBox;
	ObservableList<Anexo> obsList = FXCollections.observableArrayList();
	// Objetos buscados no banco de dados. Estes objectos não podem repetir.
	private Set<Anexo> dbObjects = new HashSet<>();

	public AttachmentComboBoxController(String urlService, JFXComboBox<Anexo> comboBox) {
		this.urlService = urlService;
		this.comboBox = comboBox;

		init();
	}

	// Método para inicializar o ComboBox
	public void init() {

		comboBox.setItems(obsList);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(comboBox, (typedText,
				itemToCompare) -> itemToCompare.getNumero().toLowerCase().contains(typedText.toLowerCase()));

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(comboBox);

		// Preeche com valores do servido atualizando ao digitar

		comboBox.valueProperty().addListener(new ChangeListener<Object>() {
			private String lastSearch = "";

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {

				// Remover os items repetidos na lista
				List<Anexo> filteredList = filterAndMaintainLastNullId(obsList);

				obsList.clear();
				obsList.addAll(filteredList);

				// Check if the newValue is a Processo or a String
				if (newValue instanceof Anexo) {
					Anexo anexo = (Anexo) newValue;

					if (anexo.getNumero() != null && !anexo.getNumero().isEmpty()) {
						// Check if the new search term is a continuation of the previous one
						if (lastSearch.contains(anexo.getNumero()) || anexo.getNumero().contains(lastSearch)) {
							obsList.clear();

							boolean containsSearchTerm = dbObjects.stream().anyMatch(object -> object.getNumero()
									.toLowerCase().contains(anexo.getNumero().toLowerCase()));

							if (containsSearchTerm) {
								obsList.clear();
								obsList.addAll(dbObjects);
							} else {
								obsList.clear();
								fetchAndUpdate(anexo.getNumero());
							}
						}

						lastSearch = anexo.getNumero();

						// Sort the list to put the selected value at the top
						obsList.sort((object1, object2) -> {
							if (object1.getNumero().equalsIgnoreCase(anexo.getNumero())) {
								return -1;
							} else if (object2.getNumero().equalsIgnoreCase(anexo.getNumero())) {
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
	public void addItemToDbObjects(Anexo object) {
		dbObjects.add(object);
	}

	private void fetchAndUpdate(String keyword) {
		try {
			AnexoService service = new AnexoService(urlService);
			Set<Anexo> fetchedObjects = new HashSet<>();

			if (keyword.length() == 2 || keyword.length() == 4 || keyword.length() == 6 || keyword.length() == 8) {
				fetchedObjects.addAll(service.fecthByKeyword(keyword));
			}

			if (!fetchedObjects.isEmpty()) {
				dbObjects.addAll(fetchedObjects);
				obsList.addAll(dbObjects);
			} else {
				// Se não houver resultados, adiciona o novo endereço diretamente
				Anexo newObject = new Anexo(keyword);
				dbObjects.add(newObject);
				obsList.add(newObject);
			}
		} catch (Exception e) {
			// Trate exceções adequadamente
			e.printStackTrace();
		}
	}

	// Método para buscar Anexos e preencher o ComboBox
	public Set<Anexo> fetchByKeyword(String keyword) {

		try {
			AnexoService service = new AnexoService(urlService);

			Set<Anexo> objects = service.fecthByKeyword(keyword);

			return objects;

		} catch (Exception e) {

		}

		return null;

	}

	public Anexo getSelectedObject() {

		Anexo object = comboBox.selectionModelProperty().get().isEmpty() ? null : comboBox.getItems().get(0);
		return object;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void fillAndSelectComboBox(Anexo object) {
		ObservableList<Anexo> newObsList = FXCollections.observableArrayList();
		comboBox.setItems(newObsList);

		newObsList.add(0, object);

		// Atualizando o ComboBox para refletir a mudança
		// cbProcess.setItems(null);
		comboBox.setItems(newObsList);

		// Selecionando o novo item no ComboBox
		comboBox.getSelectionModel().select(0);
	}

	/**
	 * Filtra a lista fornecida de objetos Processo, removendo duplicatas e
	 * garantindo que apenas uma instância com id nulo (a última) seja mantida. Este
	 * método também garante que cada id não nulo único apareça apenas uma vez.
	 * 
	 *  items
	 *            ObservableList<Anexo> items a ser filtrada
	 *  List<Anexo> uma lista filtrada com ids não nulos únicos e, se
	 *         aplicável, o último item com id nulo.
	 */
	public List<Anexo> filterAndMaintainLastNullId(ObservableList<Anexo> items) {

		// Convert ObservableList to Set to remove duplicates
		Set<Anexo> uniqueItems = new HashSet<>(items);

		// Use a map to retain only unique non-null IDs (each id maps to one Anexo
		// object)
		Map<Long, Anexo> nonNullIdMap = uniqueItems.stream().filter(anexo -> anexo.getId() != null)
				.collect(Collectors.toMap(Anexo::getId, // Key: id of the Anexo
						anexo -> anexo, // Value: Anexo itself
						(existing, replacement) -> existing // Keep the first occurrence if duplicates are found
		));

		// Get the last item with id == null (if it exists)
		Optional<Anexo> lastNullIdItem = uniqueItems.stream().filter(anexo -> anexo.getId() == null)
				.reduce((first, second) -> second); // Keep the last one

		// Convert the map values (unique non-null ids) to a list
		List<Anexo> filteredList = new ArrayList<>(nonNullIdMap.values());

		// Add the last item with id == null, if it exists
		lastNullIdItem.ifPresent(filteredList::add);

		return filteredList;
	}
}
