package controllers.views;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jfoenix.controls.JFXComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Processo;
import services.ProcessoService;

public class ProcessComboBoxController {
	

	
	String localUrl;

	private JFXComboBox<Processo> cbProcess;
	ObservableList<Processo> obsList = FXCollections.observableArrayList();
	// Objetos buscados no banco de dados. Estes objectos não podem repetir.
	private Set<Processo> dbObjects = new HashSet<>();

	public ProcessComboBoxController(String localUrl, JFXComboBox<Processo> cbProcess) {
		this.localUrl = localUrl;
		this.cbProcess = cbProcess;
		
		init();
	}

	// Método para inicializar o ComboBox
	public void init () {

		cbProcess.setItems(obsList);
		cbProcess.setEditable(true);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(cbProcess, (typedText,
				itemToCompare) -> itemToCompare.getProcNumero().toLowerCase().contains(typedText.toLowerCase()));

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(cbProcess);

		// Preeche com valores do servido atualizando ao digitar
		cbProcess.getEditor().textProperty().addListener(new ChangeListener<String>() {
			
			private String lastSearch = "";
			
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				if (newValue != null && !newValue.isEmpty() && newValue != "") {
					
					obsList.clear();
					
					// Verifica se a nova busca é uma continuação da busca anterior, tanto
					// adicionando como removendo caracteres
					if (lastSearch.contains(newValue) || newValue.contains(lastSearch)) {
						
						boolean containsSearchTerm = dbObjects.stream().anyMatch(
								object -> object.getProcNumero().toLowerCase().contains(newValue.toLowerCase()));
						
						if (containsSearchTerm) {
							obsList.addAll(dbObjects);
						} else {
							fetchAndUpdate(newValue);
						}
					} /*else {
						System.out.println("cbProcess else | last " + lastSearch + " new " + newValue);
						// Nova busca completamente diferente, então limpamos o conjunto e fazemos uma
						// nova busca
						dbObjects.clear();
						fetchAndUpdate(newValue);
					}*/

					lastSearch = newValue;
				}
				
				
			}
		});
		
		
		//System.out.println(cbProcess.getItems().get(0).getProcNumero());
	}
	/* Ao selecionar algo na table view `DocumentController`, este ítem é adicionado aqui para que não seja preciso 
	buscá-lo no banco de dados e assim não ficando lento a seleção. 
	*/
	public void addItemToDbObjects (Processo object) {
		dbObjects.add(object);
	}
	

	private void fetchAndUpdate(String keyword) {
		try {
			ProcessoService service = new ProcessoService(localUrl);
			Set<Processo> fetchedObjects = new HashSet<>(service.fetchByKeyword(keyword));

			if (!fetchedObjects.isEmpty()) {
				dbObjects.addAll(fetchedObjects);
				obsList.addAll(dbObjects);
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
	public List<Processo> fetchProcesses(String keyword) {

		try {
			ProcessoService service = new ProcessoService(localUrl);

			List<Processo> list = service.fetchByKeyword(keyword);

			return list;

		} catch (Exception e) {

		}
		return null;
	}

	public Processo getSelectedObject() {
		// Verifica se nulo, se não nulo preenche objeto e retorna.
		Processo object = cbProcess.selectionModelProperty().get().isEmpty() ? null
				: new Processo(obsList.get(0).getProcId(), obsList.get(0).getProcNumero());
		return object;
	}
	
	public void fillAndSelectComboBoxProcess (Processo process) {
		ObservableList<Processo> newObs = FXCollections.observableArrayList();
		cbProcess.setItems(newObs);

		

		newObs.add(0, process);

		// Atualizando o ComboBox para refletir a mudança
		// cbProcess.setItems(null);
		cbProcess.setItems(newObs);

		// Selecionando o novo item no ComboBox
		cbProcess.getSelectionModel().select(0);
	}
}
