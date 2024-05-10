package controllers;

public enum NotificationType {

    INFO("Info", "info"),
    ERROR("Erreur", "error"),
    WARNING("Warning", "warning");

    private String text;
    private String style;

    NotificationType(String text, String style) {
        this.text = text;
        this.style = style;
    }

    public String text() {
        return text;
    }
    public String style() {
        return style;
    }

}
