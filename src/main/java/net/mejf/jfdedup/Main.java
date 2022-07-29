package net.mejf.jfdedup;

import org.apache.commons.cli.Options;

public class Main {
    private static Options options;

    static {
        options = new Options();
    }

    public static void main(String[] args) {
        System.exit(Main.runMain(args));
    }

    static int runMain(String[] args) {
        return 0;
    }
}
