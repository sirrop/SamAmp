package com.uoeh_amplifier.generator.parser;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

public class Parsers {
    private Parsers() {
    }

    public static String parse(InputStream in) throws IOException, SAXException, ParserConfigurationException {
        var factory = SAXParserFactory.newDefaultInstance();
        var parser = factory.newSAXParser();
        var handler = new Handler();
        parser.parse(in, handler);
        return handler.getResult();
    }
}
