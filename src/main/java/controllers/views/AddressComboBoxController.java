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
import models.Endereco;
import services.EnderecoService;

public class AddressComboBoxController implements Initializable {

    String localUrl;

    private JFXComboBox<Endereco> cbAddress;
    ObservableList<Endereco> obsAddress = FXCollections.observableArrayList();

    public AddressComboBoxController(String localUrl, JFXComboBox<Endereco> cbAddress) {
        super();
        this.localUrl = localUrl;
        this.cbAddress = cbAddress;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Aqui você pode deixar vazio, já que você inicializa o ComboBox em outro lugar.
    }

    // Método para inicializar o ComboBox
    public void initializeComboBox() {
        cbAddress.setItems(obsAddress);
        cbAddress.setEditable(true);

        utilities.FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(cbAddress, (typedText,
                itemToCompare) -> itemToCompare.getEndLogradouro().toLowerCase().contains(typedText.toLowerCase()));

        utilities.FxUtilComboBoxSearchable.getComboBoxValue(cbAddress);

        // Preenche com valores do servidor atualizando ao digitar
        cbAddress.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    obsAddress.clear();
                    List<Endereco> list = fetchAddress(newValue);
                    if (list != null) {
                        boolean containsSearchTerm = list.stream()
                                .anyMatch(endereco -> endereco.getEndLogradouro().contains(newValue));
                        if (containsSearchTerm) {
                            obsAddress.addAll(list);
                        } else {
                            obsAddress.add(new Endereco(newValue));
                            obsAddress.addAll(list);
                        }
                    }
                }
            }
        });
    }

    public List<Endereco> fetchAddress(String keyword) {
        try {
            EnderecoService service = new EnderecoService(localUrl);
            List<Endereco> list = service.fetchAddress(keyword);
            return list;
        } catch (Exception e) {
            // Trate exceções adequadamente
        }
        return null;
    }
}
