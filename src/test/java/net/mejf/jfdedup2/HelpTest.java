package net.mejf.jfdedup2;

import net.mejf.jfdedup2.tools.TestCase;
import org.junit.Test;

public class HelpTest extends TestCase {
    @Test
    public void testHelp() {
        assertEquals(0, Main.runMain("-h"));

        assertSomethingWrittenToStdout();
        assertNothingWritterntoStderr();
    }

}