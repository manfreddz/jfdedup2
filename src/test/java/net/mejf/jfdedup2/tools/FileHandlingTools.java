package net.mejf.jfdedup2.tools;

import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandlingTools extends OutErrTools {
    private String tmpdir;
    private Set<String> createdFiles = new HashSet<>();
    private Set<String> createdDirs = new HashSet<>();

    @Before
    public void setupDir() throws IOException {
        tmpdir = createTmpDir();
        System.setProperty("user.dir", tmpdir);
    }

    @After
    public void deleteDir() throws IOException {
        for (String file : createdFiles) {
            Files.delete(new File(file).toPath());
        }
        for (String file : createdDirs.stream().sorted((o1, o2) -> -o1.compareTo(o2)).collect(Collectors.toList())) {
            Files.delete(new File(file).toPath());
        }
        createdFiles.clear();
        createdDirs.clear();
        Files.delete(new File(tmpdir).toPath());
    }

    private String createTmpDir() throws IOException {
        Path path = Paths.get(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
        return Files.createDirectories(path).toFile().getAbsolutePath();
    }

    protected void createDir(String dirname) {
        final String fullDirname = prependTmpdir(dirname);
        if (new File(fullDirname).mkdir()) {
            createdDirs.add(fullDirname);
        }
    }

    protected void createFile(String filename, String content) throws IOException {
        final String fullFilename = prependTmpdir(filename);
        try (FileOutputStream fos = new FileOutputStream(fullFilename, false)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
            fos.flush();
            createdFiles.add(fullFilename);
        }
    }

    /**
     * Makes a hardlink called filename pointing at pointAt
     *
     * @param filename
     * @param pointAt
     */
    protected void createHardlink(String filename, String pointAt) throws IOException {
        final String fullDest = prependTmpdir(filename);
        Files.createLink(Paths.get(fullDest), Paths.get(prependTmpdir(pointAt)));
        createdFiles.add(fullDest);
    }

    public String getTmpdir() {
        return tmpdir;
    }

    protected List<String> fileList(String... files) {
        return getStringStream(files).collect(Collectors.toList());
    }

    private Stream<String> getStringStream(String[] files) {
        return Arrays.stream(files).map(file ->
        {
            String prefix;
            if (file.startsWith("r:")) {
                file = file.substring(2);
                prefix = "r:";
            } else {
                prefix = "";
            }
            return prefix + getTmpdir() + File.separator + file;
        });
    }

    protected String[] fileArray(String... files) {
        return getStringStream(files).toArray(String[]::new);
    }

    protected void assertFilesAreDistinctInodes(String file1, String file2) {
        assertNotEquals(getFingerprint(prependTmpdir(file1)), getFingerprint(prependTmpdir(file2)));
    }

    protected void assertFilesAreSameInodes(String file1, String file2) {
        assertEquals(getFingerprint(prependTmpdir(file1)), getFingerprint(prependTmpdir(file2)));
    }

    private String prependTmpdir(String file1) {
        return tmpdir + File.separator + file1;
    }

    private String getFingerprint(String file1) {
        final Map<String, Object> attributes;
        File f1 = new File(file1);
        try {
            attributes = Files.readAttributes(f1.toPath(), "unix:ino,dev", LinkOption.NOFOLLOW_LINKS);
        } catch (IOException e) {
            throw new RuntimeException("Exception while getting attributes for " + f1.getPath(), e);
        }
        return attributes.get("dev") + ":" + attributes.get("ino");
    }
}
