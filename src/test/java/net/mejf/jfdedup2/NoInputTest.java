package net.mejf.jfdedup2;

import org.junit.Test;

public class NoInputTest extends TestCase {
    @Test
    public void testNoInput() {
        assertEquals(1, Main.runMain());

        assertNothingWritterntoStdout();
        assertSomethingWrittenToStderr();
    }

}
