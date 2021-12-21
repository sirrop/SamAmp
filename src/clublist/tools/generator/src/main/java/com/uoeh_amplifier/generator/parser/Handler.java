package com.uoeh_amplifier.generator.parser;

import com.uoeh_amplifier.generator.model.Card;
import com.uoeh_amplifier.generator.model.Instagram;
import com.uoeh_amplifier.generator.model.Twitter;
import org.xml.sax.Attributes;
import org.xml.sax.ext.Attributes2;
import org.xml.sax.ext.DefaultHandler2;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Handler extends DefaultHandler2 {
    public String getResult() {
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);

        out.println("<!DOCTYPE html>");
        out.println("<link rel=\"stylesheet\" href=\"style.css\">");
        out.println("<link rel =\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css\">");
        out.println();
        out.println("<div id=\"club-list-wrapper\">");

        for (Card card: cards) {
            out.println(indent(1, "<div class=\"club-card-container\">"));
            out.println(indent(2, "<div class=\"club-card\">"));
            out.println(indent(3, "<div class=\"club-header\">"));
            out.println(indent(4, "<span class=\"club-name\">" + card.getName() + "</span>"));
            out.println(indent(4, "<img src=\"background.svg\" width=\"50px\" height=\"50px\" />"));
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
    private boolean readTags = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if (qName.equals("club")) {
            startClub((Attributes2) attrs);
        } else if (qName.equals("tag")) {
            startTag();
        }
    }

    private void startClub(Attributes2 attrs) {
        String name = attrs.getValue("name");
        String twitterPath = attrs.getValue("twitter");
        String instagramPath = attrs.getValue("instagram");
        if (name == null) {
            System.err.println("name is absent.");
            System.exit(2);
        }
        Twitter twitter = Twitter.getInstance(twitterPath);
        Instagram instagram = Instagram.getInstance(instagramPath);
        currentCard = new Card(name, twitter, instagram);
    }

    private void startTag() {
        readTags = true;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (readTags) {
            currentCard.getTags().add(String.valueOf(ch, start, length));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("club")) {
            cards.add(currentCard);
        } else if (qName.equals("tag")) {
            readTags = false;
        }
    }
}
