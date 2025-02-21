package utilities;

import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class SVGIconLoader {

	private static SVGPath createSVG(String content, double size, Color color) {
		SVGPath svg = new SVGPath();
		svg.setContent(content);
		svg.setFill(color);
		svg.setScaleX(size / 24);
		svg.setScaleY(size / 24);
		return svg;
	}

	public static SVGPath getLeafletIcon(double size) {
		String path = "M12 2L2 22h20L12 2zm0 3.84L18.78 20H5.22L12 5.84z";
		return createSVG(path, size, Color.BLACK);
	}

	public static SVGPath getMapBoxIcon(double size) {
		String path = "M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 1.5c4.7 0 8.5 3.8 8.5 8.5s-3.8 8.5-8.5 8.5-8.5-3.8-8.5-8.5S7.3 3.5 12 3.5zm2.25 5.75L12 13.5 9.75 9.25h4.5z";
		return createSVG(path, size, Color.WHITE);
	}

	public static SVGPath getOpenLayersIcon(double size) {
		String path = "M12 2L4 7v10l8 5 8-5V7l-8-5zm6 15-6 3.5L6 17V9l6-3.5L18 9v8z";
		return createSVG(path, size, Color.web("#35BBCE"));
	}
}
