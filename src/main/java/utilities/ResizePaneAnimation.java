/**
 * @file ResizePaneAnimation.java
 * @brief Classe para anima��o de redimensionamento de painel em JavaFX.
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
 * @brief Classe para anima��o de redimensionamento de painel.
 */
public class ResizePaneAnimation {
	
	// 031123 os valores target right e target bottom devem ser din�micos 705 para tela pequena �
	//muito mas n�o � para um tela grande
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
     * Incremento de redimensionamento durante a anima��o.
     */
    private final double step = 50.0;

    /**
     * @var duration
     * Dura��o da anima��o em milissegundos.
     */
    private final double duration = 10.0; // Dura��o em milissegundos

    /**
     * @var timeline
     * Linha do tempo para controlar a anima��o.
     */
    private Timeline timeline;

    /**
     * @fn animateResize
     * @brief Inicia a anima��o de redimensionamento do painel.
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
                    // Para a anima��o quando o tamanho alvo � atingido
                    timeline.stop();
                }
            }));

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    /**
     * @fn stopAnimation
     * @brief Para a anima��o atual, se houver.
     */
    public void stopAnimation() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}
