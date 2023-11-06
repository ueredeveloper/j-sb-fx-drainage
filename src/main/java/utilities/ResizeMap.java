package utilities;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * Classe para redimensionar painéis com animações suaves.
 */
public class ResizeMap {
	
	private AnchorPane apContent;
	private AnchorPane apMap;
	private AnchorPane apManager;

	
	public ResizeMap (AnchorPane apContent, AnchorPane apMap, AnchorPane apManager) {
		this.apContent = apContent;
		this.apMap = apMap;
		this.apManager = apManager;
	}

	/**
	 * Redimensiona o painel do mapa para a largura total.
	 */
	public void resizeMapToFullWidth () {
		double newWidth = this.apContent.getWidth();

		// Cria uma linha do tempo para a animação
		Timeline timeline = new Timeline();
		
		timeline.setOnFinished(event -> this.apManager.setVisible(false));

		// Define os keyframes para a animação
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.1), new KeyValue(this.apMap.prefWidthProperty(), newWidth));
		timeline.getKeyFrames().add(keyFrame);
	
		// Define a visibilidade do painel de gerenciador como falso no final da
		// animação
		

		// Inicia a animação
		timeline.play();
	}

	/**
	 * Redefine os painéis para seus tamanhos padrão.
	 */
	public void resetMapSize() {
        double newWidth = apContent.getWidth() / 2;

        // Create a timeline for the animation
        Timeline timeline = new Timeline();

        // Define the keyframes for the animation
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.1), new KeyValue(apMap.prefWidthProperty(), newWidth));
        timeline.getKeyFrames().add(keyFrame);

        // Show apManager and adjust the width of apMap at the end of the animation
        timeline.setOnFinished(event -> {
            apMap.setPrefWidth(newWidth);
            apManager.setVisible(true);
        });

        // Start the animation
        timeline.play();
    }
}
