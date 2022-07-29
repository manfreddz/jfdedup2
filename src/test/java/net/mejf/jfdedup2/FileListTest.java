package net.mejf.jfdedup2;

import net.mejf.jfdedup2.tools.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class FileListTest extends TestCase {
    @Test
    public void testFileList() throws IOException {
        createFile("a", "foobar");
        createFile("b", "foobar2");

        final List<String> actual = Main.getFileList(fileList("a", "b"));

        assertEquals(2, actual.size());
    }
}
