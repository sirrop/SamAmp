package com.uoeh_amplifier.generator.model;

public final class Styles {
    private Styles() {
    }

    public static String getStyle(Styleable styleable) {
        return getStyle(styleable, 0);
    }

    public static String getStyle(Styleable styleable, int numSpaces) {
        var style = styleable.getStyle();
        if (style == null) {
            return "";
        } else {
            return " ".repeat(numSpaces) + String.format("style=\"%s\"", style);
        }
    }
}
