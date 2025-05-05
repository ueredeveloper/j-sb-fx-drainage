package controllers.views;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import services.UsuarioService;
import utilities.CpfCnpjFormatter;
import utilities.CpfCnpjSearchFormatter;

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

				(typedText, itemToCompare) -> {

					// Gera todas as variações possíveis do texto digitado, adicionando caracteres especiais (., - ou /).
					List<String> formattedStrings = CpfCnpjSearchFormatter.formatSearch(typedText.toLowerCase());

					// Verifica se o item digitado no ComboBox contém alguma das variações geradas
					return formattedStrings.stream().anyMatch(str -> itemToCompare.toLowerCase().contains(str));
				});

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(comboBox);

		comboBox.valueProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {

				// Checa se o valor é um objeto usuário
				if (newValue instanceof String) {

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

		String formattedKeyword = "";
		try {
			formattedKeyword = CpfCnpjFormatter.format(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbObjects.add(formattedKeyword);
	}

	private void fetchAndUpdate(String keyword) {

		try {
			UsuarioService service = new UsuarioService(localUrl);
			Set<String> fetchedObjects = new HashSet<>();

			// Buscar se houver mais de um caractere
			if (keyword.length() != 0) {
				// Busca o último caractere se especial (., - ou /)
				String lastChar = keyword.substring(keyword.length() - 1);
				// Seleciona lugares da string de busca que será acessado o serviço do banco de
				// dados
				//
				if (lastChar.equals(".") || lastChar.equals("/") || lastChar.equals("-") 
						|| keyword.length() == 3|| keyword.length() == 5 
						|| keyword.length() == 6 || keyword.length() == 9
						|| keyword.length() == 11) {
					// Retira caracteres especiais para busca no banco de dados (apenas números)
					String cleanNewValue = ((String) keyword).replaceAll("\\D", "");

					Set<String> fetchByCpfCnpj = service.listByCpfCnpj(cleanNewValue);
					if (fetchByCpfCnpj != null && !fetchByCpfCnpj.isEmpty()) {
						fetchedObjects.addAll(fetchByCpfCnpj);
					}

				}

				if (!fetchedObjects.isEmpty()) {
					
					//String formattedKeyword = CpfCnpjFormatter.format(keyword);
					
					// Gera todas as variações possíveis do texto digitado, adicionando caracteres especiais (., - ou /).
					//List<String> formattedStrings = CpfCnpjSearchFormatter.formatSearch(keyword.toLowerCase());


					Set<String> dbObjectsFormatted = fetchedObjects.stream()
						    .map(dbObject -> {
						        try {
						            return CpfCnpjFormatter.format(dbObject);
						        } catch (ParseException e) {
						            e.printStackTrace();
						            return dbObject;
						        }
						    })
						    /*.sorted((o1, o2) -> {
						        // Bring exact match with keyword to the top
						        if (formattedStrings.stream().anyMatch(str -> o1.toLowerCase().contains(str))) return -1;
						        if (formattedStrings.stream().anyMatch(str -> o2.toLowerCase().contains(str))) return 1;
						        return o1.compareTo(o2); // fallback to alphabetical
						    })*/
						    .collect(Collectors.toSet());
					
					dbObjects.addAll(dbObjectsFormatted);
					
					obsList.clear();
					obsList.addAll(dbObjects);
				} else {
					// Se não houver resultados, adiciona o novo endereço diretamente
					// Formata CPF ou CNPJ
					String formattedKeyword = CpfCnpjFormatter.format(keyword);

					dbObjects.add(formattedKeyword);
					// Evita abrir muitos valores neste caso
					obsList.clear();
					obsList.add(formattedKeyword);
				}

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

	public void fillAndSelectComboBox(String str) {

		ObservableList<String> newObsList = FXCollections.observableArrayList();
		comboBox.setItems(newObsList);

		String formattedKeyword = "";
		try {
			formattedKeyword = CpfCnpjFormatter.format(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		newObsList.add(0, formattedKeyword);

		// Atualizando o ComboBox para refletir a mudança //
		comboBox.setItems(null);
		comboBox.setItems(newObsList);

		// Selecionando o novo item no ComboBox
		comboBox.getSelectionModel().select(0);

	}

}
