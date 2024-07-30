package controllers.views;

import java.util.HashSet;
import java.util.Set;

import com.jfoenix.controls.JFXComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Endereco;
import services.EnderecoService;

public class AddressComboBoxController {

	private String localUrl;
	private JFXComboBox<Endereco> comboBox;
	private ObservableList<Endereco> obsList = FXCollections.observableArrayList();
	// Objetos buscados no banco de dados
	private Set<Endereco> dbObjects = new HashSet<>();

	public AddressComboBoxController(String localUrl, JFXComboBox<Endereco> comboBox) {
		this.localUrl = localUrl;
		this.comboBox = comboBox;
		init();
	}

	public void init() {
		comboBox.setItems(obsList);
		comboBox.setEditable(true);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(comboBox, (typedText,
				itemToCompare) -> itemToCompare.getEndLogradouro().toLowerCase().contains(typedText.toLowerCase()));

		comboBox.getEditor().textProperty().addListener(new ChangeListener<String>() {
			private String lastSearch = "";

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.isEmpty() && newValue != "") {
					obsList.clear();

					// Verifica se a nova busca é uma continuação da busca anterior, tanto
					// adicionando como removendo caracteres
					if (lastSearch.contains(newValue) || newValue.contains(lastSearch)) {
						boolean containsSearchTerm = dbObjects.stream().anyMatch(
								endereco -> endereco.getEndLogradouro().toLowerCase().contains(newValue.toLowerCase()));

						if (containsSearchTerm) {
							obsList.addAll(dbObjects);
						} else {
							
							
							fetchAndUpdate(newValue);
						}
					} 
					/*else {
						// Nova busca completamente diferente, então limpamos o conjunto e fazemos uma
						// nova busca
						
						System.out.println("else fetch" + newValue + "last " + lastSearch);
						dbObjects.clear();
						fetchAndUpdate(newValue);
					}*/

					lastSearch = newValue;
				}
			}
		});
	}
	
	/* Ao selecionar algo na table view `DocumentController`, este ítem é adicionado aqui para que não seja preciso 
	buscá-lo no banco de dados e assim não ficando lento a seleção. 
	*/
	public void addItemToDbObjects (Endereco object) {
		dbObjects.add(object);
	}

	private void fetchAndUpdate(String keyword) {
		
		System.out.println("Adddres  cb fetch and up");
		try {
			EnderecoService service = new EnderecoService(localUrl);
			Set<Endereco> fetchedAddresses = new HashSet<>(service.fetchAddressByKeyword(keyword));

			if (!fetchedAddresses.isEmpty()) {
				dbObjects.addAll(fetchedAddresses);
				obsList.addAll(dbObjects);
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
		// Verifica se nulo, se não nulo preenche objeto e retorna.
		Endereco object = comboBox.selectionModelProperty().get().isEmpty() ? null
				: new Endereco(obsList.get(0).getEndId(), obsList.get(0).getEndLogradouro());
		return object;
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
}
