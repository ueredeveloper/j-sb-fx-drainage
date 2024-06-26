// Toast.java
package utilities;

import java.io.IOException;

import controllers.ToastController;
import enums.ToastType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public final class Toast {
	
	public static void makeText(Stage ownerStage, String toastMsg, ToastType toastType) {
		
		
		
		
		
		int toastDelay = 1000; // 0.5 seconds
	    int fadeInDelay = 500; // 0.5 seconds
	    int fadeOutDelay = 500; // 0.5 seconds
	    
		try {
			FXMLLoader loader = new FXMLLoader(Toast.class.getResource("/fxml/Toast.fxml"));
			Parent root = loader.load();
		
			ToastController controller = loader.getController();
			controller.showToast(toastMsg, toastType);
		
			Stage toastStage = new Stage();
			
			double centerX = ownerStage.getX();
			double topY = ownerStage.getY();
			double width = ownerStage.getWidth();

			// centralizar o Toast
			toastStage.setX(centerX);
			toastStage.setY(topY);
			toastStage.setWidth(width);
			toastStage.setHeight(160.0);
			
			toastStage.initOwner(ownerStage);
			toastStage.setResizable(false);
			toastStage.initStyle(StageStyle.TRANSPARENT);
			Scene scene = new Scene(root);
			scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
			toastStage.setScene(scene);
			toastStage.show();

			Timeline fadeInTimeline = new Timeline();
			KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDelay),
					new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
			fadeInTimeline.getKeyFrames().add(fadeInKey1);
			fadeInTimeline.setOnFinished((ae) -> {
				new Thread(() -> {
					try {
						Thread.sleep(toastDelay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Timeline fadeOutTimeline = new Timeline();
					KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay),
							new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
					fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
					fadeOutTimeline.setOnFinished((aeb) -> toastStage.close());
					fadeOutTimeline.play();
				}).start();
			});
			fadeInTimeline.play();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
