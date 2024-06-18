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
import models.Anexo;
import services.AnexoService;

public class AttachmentComboBoxController implements Initializable {

	String localUrl;

	private JFXComboBox<Anexo> cbAttachment;
	ObservableList<Anexo> obsAttachment = FXCollections.observableArrayList();

	public AttachmentComboBoxController(String localUrl, JFXComboBox<Anexo> cbAttachment) {
		this.localUrl = localUrl;
		this.cbAttachment = cbAttachment;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Deixe vazio, já que o ComboBox será inicializado em outro lugar.
	}

	// Método para inicializar o ComboBox
	public void init() {

		cbAttachment.setItems(obsAttachment);
		cbAttachment.setEditable(true);

		utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(cbAttachment, (typedText,
				itemToCompare) -> itemToCompare.getAnNumero().toLowerCase().contains(typedText.toLowerCase()));

		utilities.FxUtilComboBoxSearchable.getComboBoxValue(cbAttachment);

		// Preeche com valores do servido atualizando ao digitar
		cbAttachment.getEditor().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (newValue != null) {

					obsAttachment.clear();
					List<Anexo> list = fetch(newValue);
					boolean containsSearchTerm = list.stream()
							.anyMatch(processo -> processo.getAnNumero().contains(newValue));
					//
					// Se o que foi digitado está contido, não adicina novo processo, porém, se o
					// que foi digitado não está contido na lista, adiciona novo processo com id
					// nulo.
					//
					if (containsSearchTerm) {
						obsAttachment.addAll(list);
					} else {
						obsAttachment.add(new Anexo(newValue));
						obsAttachment.addAll(list);
					}

				}

			}
		});
	}

	// Método para buscar processos e preencher o ComboBox
	public List<Anexo> fetch(String keyword) {

		try {
			AnexoService service = new AnexoService(localUrl);

			List<Anexo> list = service.fetchAttachments(keyword);

			return list;

		} catch (Exception e) {

		}

		return null;

	}
}
