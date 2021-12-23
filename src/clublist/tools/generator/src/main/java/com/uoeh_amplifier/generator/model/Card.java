package com.uoeh_amplifier.generator.model;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private final Name name;
    private final Twitter twitter;
    private final Instagram instagram;
    private final List<String> tags = new ArrayList<>();

    public Card(Name name, Twitter twitter, Instagram instagram) {
        this.name = name;
        this.twitter = twitter;
        this.instagram = instagram;
    }

    public List<String> getTags() {
        return tags;
    }

    public Card withName(Name name) {
        var clone = new Card(name, twitter, instagram);
        clone.tags.addAll(this.tags);
        return clone;
    }

    public Name getName() {
        return name;
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public Instagram getInstagram() {
        return instagram;
    }
}
