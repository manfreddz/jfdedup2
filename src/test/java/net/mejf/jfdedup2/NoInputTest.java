package net.mejf.jfdedup2;

import net.mejf.jfdedup2.tools.TestCase;
import org.junit.Test;

public class NoInputTest extends TestCase {
    @Test
    public void testNoInput() {
        assertEquals(2, Main.runMain());

        assertNothingWritterntoStdout();
        assertSomethingWrittenToStderr();
    }

}
