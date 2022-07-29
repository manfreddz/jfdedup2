package net.mejf.jfdedup2.tools;

import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FileHandlingTools extends OutErrTools {
    private String tmpdir;
    private Set<String> createdFiles = new HashSet<>();

    @Before
    public void setupDir() throws IOException {
        tmpdir = createTmpDir();
        System.setProperty("user.dir", tmpdir);
    }

    @After
    public void deleteDir() throws IOException {
        for (String file: createdFiles) {
            Files.delete(new File(file).toPath());
        }
        createdFiles.clear();
        Files.delete(new File(tmpdir).toPath());
    }

    private String createTmpDir() throws IOException {
        Path path = Paths.get(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
        return Files.createDirectories(path).toFile().getAbsolutePath();
    }

    protected void createFile(String filename, String content) throws IOException {
        final String fullFilename = tmpdir + "/" + filename;
        try (FileOutputStream fos = new FileOutputStream(fullFilename, false)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
            fos.flush();
            createdFiles.add(fullFilename);
        }
    }

    public String getTmpdir() {
        return tmpdir;
    }

    protected List<String> fileList(String... files) {
        return Arrays.stream(files).map(file -> getTmpdir() + "/" + file).collect(Collectors.toList());
    }
}
