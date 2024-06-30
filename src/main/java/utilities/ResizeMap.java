package utilities;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * Classe para redimensionar pain�is com anima��es suaves.
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

		// Cria uma linha do tempo para a anima��o
		Timeline timeline = new Timeline();
		
		timeline.setOnFinished(event -> this.apManager.setVisible(false));

		// Define os keyframes para a anima��o
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.1), new KeyValue(this.apMap.prefWidthProperty(), newWidth));
		timeline.getKeyFrames().add(keyFrame);
	
		// Define a visibilidade do painel de gerenciador como falso no final da
		// anima��o
		

		// Inicia a anima��o
		timeline.play();
	}

	/**
	 * Redefine os pain�is para seus tamanhos padr�o.
	 */
	public void resetMapSize() {
		 // A tela é divida em três partes, duas para o cadastro e uma para o mapa. Esta parte é a do mapa.
        double newWidth = apContent.getWidth() / 2.99;

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
