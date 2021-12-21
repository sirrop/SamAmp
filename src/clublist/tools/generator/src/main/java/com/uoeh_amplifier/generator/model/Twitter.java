package com.uoeh_amplifier.generator.model;

public class Twitter extends SNSIcon {

    public static final Twitter NONE = new Twitter(null);

    private final String path;

    private Twitter(String path) {
        this.path = path;
    }

    public static Twitter getInstance(String path) {
        if (path == null) {
            return NONE;
        } else {
            return new Twitter(path);
        }
    }

    public String getPath() {
        return path;
    }
}
