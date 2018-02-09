package org.lds.ldssa.model.prefs.type;

/**
 * The size that the text can be in the content
 */
public enum ContentTextSizeType {
    XS("xs", 12),
    S("s", 15),
    M("m", 18),
    L("l", 21),
    XL("xl", 26),
    XXL("xxl", 40),
    XXXL("xxxl", 60);

    private String sizeCode;
    private int pixelSize;

    ContentTextSizeType(String sizeCode, int pixelSize) {
        this.sizeCode = sizeCode;
        this.pixelSize = pixelSize;
    }

    public static ContentTextSizeType get(int ordinal) {
        return values()[ordinal];
    }

    public String getSizeCode() {
        return sizeCode;
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public ContentTextSizeType reduceBy(int reduceBy) {
        int originalOrdinalValue = ordinal();
        int newOrdinalValue;
        if (originalOrdinalValue > 0) {
            newOrdinalValue = originalOrdinalValue - reduceBy;
        } else {
            newOrdinalValue = originalOrdinalValue;
        }

        return values()[newOrdinalValue];
    }
}
