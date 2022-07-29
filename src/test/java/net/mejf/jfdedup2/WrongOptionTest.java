package net.mejf.jfdedup2;

import net.mejf.jfdedup2.tools.TestCase;
import org.junit.Test;

public class WrongOptionTest extends TestCase {
    @Test
    public void testWrongOption() {
        assertNotEquals(0, Main.runMain("--apa"));
        assertSomethingWrittenToStderr();
        assertSomethingWrittenToStdout();
    }

    @Test
    public void testWrongShortOption() {
        assertNotEquals(0, Main.runMain("-x"));
        assertSomethingWrittenToStderr();
        assertSomethingWrittenToStdout();
    }

}
