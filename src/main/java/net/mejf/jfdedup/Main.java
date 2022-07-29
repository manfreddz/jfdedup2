package net.mejf.jfdedup;

import org.apache.commons.cli.*;

public class Main {
    private static Options options;

    static {
        options = new Options();

        options.addOption("h", "help", false, "Print help");
    }

    public static void main(String[] args) {
        try {
            System.exit(Main.runMain(args));
        } catch (ParseException e) {
            err("Unexpected exception: " + e.getLocalizedMessage());
            printHelp();
            System.exit(2);
        }
    }

    static int runMain(String... args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption('h')) {
            printHelp();
            return 0;
        }

        return 0;
    }

    public static void err(String format, Object... args) {
        System.err.printf(format + "\n", args);
    }

    public static void log(String format, Object... args) {
        System.out.printf(format + "\n", args);
    }

    private static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("jfdedup2 [OPTION]... [FILE/DIRECTORY]...", options);
    }
}
