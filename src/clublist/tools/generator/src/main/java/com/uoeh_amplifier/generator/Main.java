package com.uoeh_amplifier.generator;

import com.uoeh_amplifier.generator.parser.Parsers;

import java.io.PrintWriter;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws Exception {
        Flags flags = Flags.parse(args);

        String result = Parsers.parse(Files.newInputStream(flags.src()));

        var out = new PrintWriter(Files.newBufferedWriter(flags.dest()));

        out.print(result);
        out.close();
    }
}