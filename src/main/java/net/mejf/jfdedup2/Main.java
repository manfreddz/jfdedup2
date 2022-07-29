package net.mejf.jfdedup2;

import org.apache.commons.cli.*;

public class Main {
    private static final Options options;
    private static boolean debug = false;

    static {
        options = new Options();

        options.addOption("h", "help", false, "Print help");
        options.addOption("d", "debug", false, "Print debug info");
    }

    public static void main(String[] args) {
        try {
            System.exit(Main.runMain(args));
        } catch (Throwable e) {
            err("Unexpected exception: " + e.getLocalizedMessage());
            printHelp();
            System.exit(Short.MAX_VALUE);
        }
    }

    static int runMain(String... args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            err("Failed to parse command line: %s", e.getLocalizedMessage());
            return 2;
        }

        if (cmd.hasOption('h')) {
            printHelp();
            return 0;
        }

        if (cmd.hasOption('d')) {
            Main.debug = true;
        }

        if (cmd.getArgList().isEmpty()) {
            err("No files or directories provided");
            return 1;
        }

        return 0;
    }

    public static void err(String format, Object... args) {
        System.err.printf(format + "\n", args);
    }

    public static void log(String format, Object... args) {
        System.out.printf(format + "\n", args);
    }

    public static void debug(String format, Object... args) {
        if (debug) {
            System.out.printf("DBG: " + format + "\n", args);
        }
    }

    private static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("jfdedup2 [OPTION]... [FILE/DIRECTORY]...", options);
    }
}
