package net.mejf.jfdedup2;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class Main {
    private static final Options options;
    private static boolean debug = false;
    private static boolean allRecursive = false;
    private static FileList lastFileList;

    static {
        options = new Options();

        options.addOption("h", "help", false, "Print help");
        options.addOption("d", "debug", false, "Print debug info");
        options.addOption("H", "hardlinks", false, "Replace dups with hardlinks");
        options.addOption("R", "all-recursive", false, "Search all directories recursively");
//        options.addOption("s", "follow-symlinks", false, "Follow symlinks (not implemented yet)");
//        options.addOption("D", "delete-dups", true, "Whether to delete dups");
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

    public static int runMain(String... args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            err("Failed to parse command line: %s", e.getLocalizedMessage());
            printHelp();
            return 1;
        }

        if (cmd.hasOption('h')) {
            printHelp();
            return 0;
        }

        if (cmd.hasOption('d')) {
            Main.debug = true;
        }

        if (cmd.hasOption('R')) {
            Main.allRecursive = true;
        }

        if (cmd.getArgList().isEmpty()) {
            err("No files or directories provided");
            return 2;
        }

        final FileList fileList = getFileList(cmd.getArgList());
        lastFileList = fileList;

        if (fileList.isEmpty()) {
            err("No files or directories found");
            return 3;
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

    static FileList getFileList(List<String> fileList) {
        FileList ret = new FileList();
        int prio = 1;

        for (String path : fileList) {
            boolean recursive = allRecursive;
            if (path.startsWith("r:")) {
                path = path.substring(2);
                recursive = true;
            }

            getFileListWorker(path, ret, prio++, recursive);
        }

        if (debug) {
            debug("Got file list:");
            for (JFile jFile : ret) {
                debug(jFile.getName());
            }
        }

        return ret;
    }

    private static void getFileListWorker(String path, FileList ret, int prio, boolean recursive) {
        File file = new File(path);

        try {
            if (file.isFile()) {
                debug("File: %s", file.getAbsolutePath());
                ret.add(new JFile(file.getAbsolutePath(), prio));

            } else if (file.isDirectory()) {
                debug("Dir: %s", file.getAbsolutePath());
                for (File fileInDirectory : Files.list(file.toPath())
                        .sorted()
                        .map(Path::toFile)
                        .collect(Collectors.toList())) {
                    debug("Content: %s", fileInDirectory.getAbsolutePath());
                    if ((fileInDirectory.isDirectory() && recursive) || fileInDirectory.isFile()) {
                        getFileListWorker(fileInDirectory.getAbsolutePath(), ret, prio, recursive);
                    }
                }

            } else {
                throw new RuntimeException("Unknown file type: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static FileList getLastFilelist() {
        return lastFileList;
    }
}
