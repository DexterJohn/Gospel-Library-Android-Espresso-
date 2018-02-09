package org.lds.ldssa.model.prefs.type;

public enum DisplayThemeType {
    LIGHT("default", 0),
    DARK("night", 2),
    SEPIA("sepia", 1),
    DARK_BLUE("darkBlue", 3),//TODO: determine the htmlScheme name for the dark blue theme (we made up "darkBlue" so that our css could use it)
    MAGENTA("magenta", 4);

    private final String htmlScheme;
    private final int displayOrder;

    DisplayThemeType(String htmlScheme, int displayOrder) {
        this.htmlScheme = htmlScheme;
        this.displayOrder = displayOrder;
    }

    public String getHtmlScheme() {
        return htmlScheme;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public static DisplayThemeType getByDisplayOrder(int displayOrder) {
        for (DisplayThemeType type : values()) {
            if (type.getDisplayOrder() == displayOrder) {
                return type;
            }
        }

        return LIGHT;
    }
}
