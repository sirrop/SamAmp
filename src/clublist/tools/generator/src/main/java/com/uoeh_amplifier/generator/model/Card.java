package com.uoeh_amplifier.generator.model;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private final String name;
    private final Twitter twitter;
    private final Instagram instagram;
    private final List<String> tags = new ArrayList<>();

    public Card(String name, Twitter twitter, Instagram instagram) {
        this.name = name;
        this.twitter = twitter;
        this.instagram = instagram;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getName() {
        return name;
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public Instagram getInstagram() {
        return instagram;
    }
}
