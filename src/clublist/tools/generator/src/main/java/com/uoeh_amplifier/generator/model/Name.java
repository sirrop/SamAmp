package com.uoeh_amplifier.generator.model;

public class Name implements Styleable {
    private static final Name NULL = new Name(null, null);

    private final String name;
    private final String style;

    public static Name getInstance(String string) {
        return getInstance(string, null);
    }

    public static Name getInstance(String string, String style) {
        if (string == null) {
            return NULL;
        } else {
            return new Name(string, style);
        }
    }

    public Name(String name, String style) {
        this.name = name;
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public String toString() {
        return name;
    }
}
