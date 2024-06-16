package utilities;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import utilities.FxUtilComboBoxSearchable;

import java.util.List;
import java.util.function.Function;

public class ComboBoxUtil {

    public static <T> void setupComboBox(ComboBox<T> comboBox, ObservableList<T> obsList, 
                                         Function<String, List<T>> fetchFunction, 
                                         Function<T, String> compareFunction) {
        comboBox.setItems(obsList);
        comboBox.setEditable(true);

        FxUtilComboBoxSearchable.autoCompleteComboBoxPlus(comboBox, (typedText, itemToCompare) -> 
            compareFunction.apply(itemToCompare).toLowerCase().contains(typedText.toLowerCase())
        );

        FxUtilComboBoxSearchable.getComboBoxValue(comboBox);

        comboBox.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    obsList.clear();
                    List<T> list = fetchFunction.apply(newValue);
                    boolean containsSearchTerm = list.stream()
                        .anyMatch(item -> compareFunction.apply(item).contains(newValue));

                    if (containsSearchTerm) {
                        obsList.addAll(list);
                    } else {
                        // Assuming T has a constructor that accepts a String. Adjust as necessary.
                        try {
                            T newItem = (T) item.getClass().getConstructor(String.class).newInstance(newValue);
                            obsList.add(newItem);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        obsList.addAll(list);
                    }
                }
            }
        });
    }
}
