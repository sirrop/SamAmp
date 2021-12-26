package com.uoeh_amplifier.generator.parser;

import com.uoeh_amplifier.generator.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.ext.Attributes2;
import org.xml.sax.ext.DefaultHandler2;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Handler extends DefaultHandler2 {
    public String getResult() {
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);

        out.println("<!DOCTYPE html>");
        out.println("<link rel=\"stylesheet\" href=\"style.css\">");
        out.println("<link rel =\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css\">");
        out.println();

        out.println("<div class=\"club-list-header\">");
        out.println(indent(1, "<form class=\"search-container\">"));
        out.println(indent(2, "<input type=\"text\" class=\"search-area\" contenteditable=\"true\"></input>"));
        out.println(indent(2, "<input type=\"submit\" class=\"fas search-button\" value=\"&#xf002;\"></input>\n"));
        out.println(indent(1, "</form>"));
        out.println("</div>");

        out.println();

        out.println("<div id=\"club-list-wrapper\">");

        for (Card card: cards) {
            out.println(indent(1, "<div class=\"club-card-container\">"));
            out.println(indent(2, "<div class=\"club-card\">"));
            out.println(indent(3, "<div class=\"club-header\">"));
            out.println(indent(4, "<img class=\"icon\" alt=\"icon\" src=\"background.svg\" width=\"16px\" height=\"16px\" />"));
            out.println(indent(4, "<span class=\"club-name\"" + Styles.getStyle(card.getName(), 1) + ">" + card.getName() + "</span>"));
            out.println(indent(3, "</div>"));
            out.println(indent(3, "<div class=\"club-card-footer\">"));
            out.println(indent(4, "<div class=\"club-tag-list\">"));
            for (String tag: card.getTags()) {
                out.println(indent(5, "<div class=\"club-tag\"><i class=\"fas fa-tags\"></i>" + tag + "</div>"));
            }
            out.println(indent(4, "</div>"));
            out.println(indent(4, "<div class=\"sns-list\">"));
            Optional.ofNullable(card.getTwitter().getPath())
                    .ifPresentOrElse(
                            it -> out.println(indent(5, "<a class=\"sns-icon fab fa-twitter twitter\" href=\"" + it + "\"></a>")),
                            () -> out.println(indent(5, "<a class=\"sns-icon fab fa-twitter twitter\"></a>"))
                    );
            Optional.ofNullable(card.getInstagram().getPath())
                    .ifPresentOrElse(
                            it -> out.println(indent(5, "<a class=\"sns-icon fab fa-instagram instagram\" href=\"" + it + "\"></a>")),
                            () -> out.println(indent(5, "<a class=\"sns-icon fab fa-instagram instagram\"></a>"))
                    );
            out.println(indent(4, "</div>"));
            out.println(indent(3, "</div>"));
            out.println(indent(2, "</div>"));
            out.println(indent(1, "</div>"));
        }

        out.println("</div>");

        return writer.toString();
    }

    private static String indent(int indent, String text) {
        return "    ".repeat(indent) + text;
    }

    private final List<Card> cards = new ArrayList<>();
    private Card currentCard;
    private boolean readName = false;
    private String nameStyle;
    private boolean readTags = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        switch (qName) {
            case "club":
                startClub((Attributes2) attrs);
                break;
            case "tag":
                startTag();
                break;
            case "name":
                startName((Attributes2) attrs);
                break;
        }
    }

    private void startClub(Attributes2 attrs) {
        String name = attrs.getValue("name");
        String twitterPath = attrs.getValue("twitter");
        String instagramPath = attrs.getValue("instagram");
        Twitter twitter = Twitter.getInstance(twitterPath);
        Instagram instagram = Instagram.getInstance(instagramPath);
        currentCard = new Card(Name.getInstance(name), twitter, instagram);
    }

    private void startTag() {
        readTags = true;
    }

    private void startName(Attributes2 attrs) {
         nameStyle = attrs.getValue("style");
         readName = true;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (readTags) {
            currentCard.getTags().add(String.valueOf(ch, start, length));
        }
        if (readName) {
            currentCard = currentCard.withName(Name.getInstance(String.valueOf(ch, start, length), nameStyle));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "club":
                if (currentCard.getName() == null) {
                    System.err.println("名前が指定されていません");
                    System.exit(1);
                }
                cards.add(currentCard);
                break;
            case "tag":
                readTags = false;
                break;
            case "name":
                nameStyle = null;
                readName = false;
                break;
        }
    }
}
