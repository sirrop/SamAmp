package com.uoeh_amplifier.generator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Flags {
    private final Path src;
    private final Path dest;

    Flags(Path src, Path dest) {
        this.src = src;
        this.dest = dest;
    }

    public Path src() {
        return src;
    }

    public Path dest() {
        return dest;
    }

    public static Flags parse(String... args) {
        List<String> flags = Arrays.asList(args);
        Path src = null, dest;

        if (flags.contains("-s")) {
            src = Paths.get(flags.get(flags.indexOf("-s") + 1));
        } else {
            System.err.println("ソースが指定されていません");
            System.exit(1);
        }

        if (flags.contains("-d")) {
            dest = Paths.get(flags.get(flags.indexOf("-d") + 1));
        } else {
            long time = System.currentTimeMillis();
            dest = Paths.get(time + ".html");
        }

        return new Flags(src, dest);
    }
}
