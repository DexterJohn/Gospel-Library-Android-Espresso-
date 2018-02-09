package org.lds.ldssa.model.prefs.model;

import org.lds.ldssa.model.database.types.HighlightAnnotationStyle;
import org.lds.ldssa.util.HighlightColor;

public class HighlightHistoryItem {
    private HighlightAnnotationStyle style;
    private HighlightColor color; //e.g. blue, red, green, etc.

    public HighlightHistoryItem(HighlightColor color, HighlightAnnotationStyle style) {
        this.color = color;
        this.style = style;
    }

    public HighlightAnnotationStyle getStyle() {
        return style;
    }

    public HighlightColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof HighlightHistoryItem)) {
            return false;
        }

        HighlightHistoryItem other = (HighlightHistoryItem)o;
        return other.getStyle() == style && other.getColor() == color;
    }
}
