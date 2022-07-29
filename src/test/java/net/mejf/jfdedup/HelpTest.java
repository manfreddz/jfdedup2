package net.mejf.jfdedup;

import org.junit.Test;

public class HelpTest extends TestCase {
    @Test
    public void testHelp() {
        assertEquals(0, Main.runMain("-h"));

        assertSomethingWrittenToStdout();
        assertNothingWritterntoStderr();
    }

}