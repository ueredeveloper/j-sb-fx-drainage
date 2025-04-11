package map;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Locale;

public class CoordenadaFormatTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        stage.setScene(new Scene(root, 200, 200));
        stage.show();
    }

    @Test
    public void testImpressaoCoordenadasComPontoDecimal() {
        Platform.runLater(() -> {
            double latitude = -15.7976181;
            double longitude = -47.8337325;

            // Forçar ponto decimal (não vírgula)
            String latFormatted = String.format(Locale.US, "%.6f", latitude);
            String lngFormatted = String.format(Locale.US, "%.6f", longitude);

            System.out.println("Latitude: " + latFormatted);
            System.out.println("Longitude: " + lngFormatted);

            // Asserts para confirmar
            assert latFormatted.equals("-15.797618");
            assert lngFormatted.equals("-47.833733");
        });

        sleep(1000); // Aguarda execução da thread JavaFX
    }
}
