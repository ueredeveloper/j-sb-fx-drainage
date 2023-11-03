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

    /**
     * @var targetRight
     * Largura alvo do painel.
     */
    private final double targetRight = 750.0;

    /**
     * @var targetBottom
     * Altura alvo do painel.
     */
    private final double targetBottom = 350.0;

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
    public void animateResize(Pane pane) {
        if (pane.getParent() instanceof AnchorPane) {
            AnchorPane anchorPane = (AnchorPane) pane.getParent();

            DoubleProperty rightAnchorProperty = new SimpleDoubleProperty();
            DoubleProperty bottomAnchorProperty = new SimpleDoubleProperty();

            rightAnchorProperty.bind(anchorPane.widthProperty().subtract(pane.widthProperty()));
            bottomAnchorProperty.bind(anchorPane.heightProperty().subtract(pane.heightProperty()));

            timeline = new Timeline(new KeyFrame(Duration.millis(duration), event -> {
                double currentWidth = rightAnchorProperty.get();
                double currentHeight = bottomAnchorProperty.get();

                if (currentWidth < targetRight) {
                    currentWidth += step;
                    AnchorPane.setRightAnchor(pane, currentWidth);
                }

                if (currentHeight < targetBottom) {
                    currentHeight += step;
                    AnchorPane.setBottomAnchor(pane, currentHeight);
                }

                if (currentWidth >= targetRight && currentHeight >= targetBottom) {
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
