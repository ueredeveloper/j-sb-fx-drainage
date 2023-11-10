package enums;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

public enum ToastType {
    SUCCESS("success", FontAwesomeIcon.CHECK_CIRCLE_ALT),
    WARNING("warning", FontAwesomeIcon.EXCLAMATION_TRIANGLE),
    INFO("info", FontAwesomeIcon.INFO_CIRCLE),
    ERROR("error", FontAwesomeIcon.TIMES_CIRCLE);

    private final String styleClass;
    private final FontAwesomeIcon icon;

    ToastType(String styleClass, FontAwesomeIcon icon) {
        this.styleClass = styleClass;
        this.icon = icon;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public FontAwesomeIcon getIcon() {
        return icon;
    }
}
