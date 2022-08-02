package net.mejf.jfdedup2;

import net.mejf.jfdedup2.tools.TestCase;
import org.junit.Test;

import java.io.IOException;

public class HardlinkTest extends TestCase {
    @Test
    public void testDifferentFileContent() throws IOException {
        createFile("a", "foobar");
        createFile("b", "foobar2");

        assertEquals(0, runMain("-H", fileArray("a", "b")));

        assertFilesAreDistinctInodes("a", "b");
    }

    @Test
    public void testSameFileContent() throws IOException {
        createFile("a", "foobar");
        createFile("b", "foobar");

        assertEquals(0, runMain("-H", fileArray("a", "b")));

        assertFilesAreSameInodes("a", "b");
    }

    @Test
    public void testSameFileContentInDir() throws IOException {
        createFile("a", "foobar");
        createDir("c");
        createFile("c/b", "foobar");

        assertEquals(0, runMain("-H", fileArray("a", "c/b")));

        assertFilesAreSameInodes("a", "c/b");
    }

    @Test
    public void testSameFileContentInDirNotRecursive() throws IOException {
        createFile("a", "foobar");
        createDir("c");
        createFile("c/b", "foobar");

        assertEquals(0, runMain("-H", fileArray("a", "c")));

        assertFilesAreDistinctInodes("a", "c/b");
    }

    @Test
    public void testSameFileContentInDirAllRecursive() throws IOException {
        createFile("a", "foobar");
        createDir("c");
        createFile("c/b", "foobar");

        assertEquals(0, runMain("-H", "-R", fileArray("a", "c")));

        assertFilesAreSameInodes("a", "c/b");
    }

    @Test
    public void testSameFileContentInDirEntryRecursive() throws IOException {
        createFile("a", "foobar");
        createDir("c");
        createFile("c/b", "foobar");

        assertEquals(0, runMain("-H", fileArray("a", "r:c")));

        assertFilesAreSameInodes("a", "c/b");
    }

    @Test
    public void testFilesPointingToTheSame() throws IOException {
        createFile("a", "foobar");
        createHardlink("b", "a");

        assertEquals(0, runMain("-H", fileArray("a", "b")));

        assertFilesAreSameInodes("a", "b");
    }

    @Test
    public void testTwoFilesInDirectory() throws IOException {
        createFile("a", "foobar");
        createDir("c");
        createFile("c/b", "foobar");
        createFile("c/d", "foobar2");

        assertEquals(0, runMain("-H", fileArray("a", "r:c")));

        assertFilesAreSameInodes("a", "c/b");
        assertFilesAreDistinctInodes("a", "c/d");
    }

    @Test
    public void testPrio() throws IOException {
        createFile("a", "foobar");
        createDir("c");
        createFile("c/b", "foobar");
        createFile("c/d", "foobar2");

        String bFingerprint = getPrependedFingerprint("c/b");
        assertEquals(0, runMain("-H", fileArray("c/b", "a", "r:c")));

        assertEquals(bFingerprint, getPrependedFingerprint("a"));
    }

    @Test
    public void testPrioReverse() throws IOException {
        createFile("x", "foobar");
        createDir("c");
        createFile("c/b", "foobar");
        createFile("c/d", "foobar2");

        String bFingerprint = getPrependedFingerprint("c/b");
        assertEquals(0, runMain("-H", fileArray("c/b", "x", "r:c")));

        assertEquals(bFingerprint, getPrependedFingerprint("x"));
    }

    @Test
    public void testSameFileMultipleTimes() throws IOException {
        createFile("x", "foobar");
        createDir("c");
        createFile("c/b", "foobar");
        createFile("c/d", "foobar2");

        assertEquals(0, runMain("-H", fileArray("c/b", "x", "r:c")));

        assertEquals(3, Main.getLastFilelist().size());
    }

}
