package controllers.views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import models.Processo;
import services.ProcessoService;

public class ProcessComboBoxController implements Initializable {

    String localUrl;

    private JFXComboBox<Processo> cbProcess;
    ObservableList<Processo> obsProcess = FXCollections.observableArrayList();

    public ProcessComboBoxController(String localUrl, JFXComboBox<Processo> cbProcess) {
        this.localUrl = localUrl;
        this.cbProcess = cbProcess;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Deixe vazio, já que o ComboBox será inicializado em outro lugar.
    }

    // Método para inicializar o ComboBox
    public void initializeComboBox() {
    	
    	cbProcess.setItems(obsProcess);
		cbProcess.setEditable(true);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(cbProcess, (typedText,
				itemToCompare) -> itemToCompare.getProcNumero().toLowerCase().contains(typedText.toLowerCase()));

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(cbProcess);

		// Preeche com valores do servido atualizando ao digitar
		cbProcess.getEditor().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (newValue != null) {

					obsProcess.clear();
					List<Processo> list = fetchProcesses(newValue);
					boolean containsSearchTerm = list.stream()
							.anyMatch(processo -> processo.getProcNumero().contains(newValue));
					//
					// Se o que foi digitado está contido, não adicina novo processo, porém, se o
					// que foi digitado não está contido na lista, adiciona novo processo com id
					// nulo.
					//
					if (containsSearchTerm) {
						obsProcess.addAll(list);
					} else {
						obsProcess.add(new Processo(newValue));
						obsProcess.addAll(list);
					}

				}

			}
		});
    }

    // Método para buscar processos e preencher o ComboBox
	public List<Processo> fetchProcesses(String keyword) {

		try {
			ProcessoService service = new ProcessoService(localUrl);

			List<Processo> list = service.fetchProcesses(keyword);

			return list;

		} catch (Exception e) {

		}

		return null;

	}
}
