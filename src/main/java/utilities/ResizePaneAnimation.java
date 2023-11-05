/**
 * @file ResizePaneAnimation.java
 * @brief Classe para animação de redimensionamento de painel em JavaFX.
 */

package utilities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * @class ResizePaneAnimation
 * @brief Classe para animação de redimensionamento de painel.
 */
public class ResizePaneAnimation {
	
	// 031123 os valores target right e target bottom devem ser dinâmicos 705 para tela pequena é
	//muito mas não é para um tela grande
    /**
     * @var targetRight
     * Largura alvo do painel.
     */
    private double targetRight;

	public ResizePaneAnimation (double width) {
		super();
        this.targetRight = width/2;
		
	}

	

    /**
     * @var step
     * Incremento de redimensionamento durante a animação.
     */
    private final double step = 50.0;

    /**
     * @var duration
     * Duração da animação em milissegundos.
     */
    private final double duration = 10.0; // Duração em milissegundos

    /**
     * @var timeline
     * Linha do tempo para controlar a animação.
     */
    private Timeline timeline;

    /**
     * @fn animateResize
     * @brief Inicia a animação de redimensionamento do painel.
     * @param pane O painel a ser redimensionado.
     */
    public void animateResize(Pane paneToResize) {
        if (paneToResize.getParent() instanceof AnchorPane) {
            AnchorPane anchorPane = (AnchorPane) paneToResize.getParent();

            DoubleProperty rightAnchorProperty = new SimpleDoubleProperty();

            rightAnchorProperty.bind(anchorPane.widthProperty().subtract(paneToResize.widthProperty()));

            timeline = new Timeline(new KeyFrame(Duration.millis(duration), event -> {
                double currentWidth = rightAnchorProperty.get();
                
                if (currentWidth < targetRight) {
                    currentWidth += step;
                    AnchorPane.setRightAnchor(paneToResize, currentWidth);
                }


                if (currentWidth >= targetRight) {
                    // Para a animação quando o tamanho alvo é atingido
                    timeline.stop();
                }
            }));

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    /**
     * @fn stopAnimation
     * @brief Para a animação atual, se houver.
     */
    public void stopAnimation() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}
