package com.uoeh_amplifier.generator.model;

public class Instagram extends SNSIcon {
    public static final Instagram NONE = new Instagram(null);

    private final String path;

    private Instagram(String path) {
        this.path = path;
    }

    public static Instagram getInstance(String path) {
        if (path == null) {
            return NONE;
        } else {
            return new Instagram(path);
        }
    }

    public String getPath() {
        return path;
    }
}
