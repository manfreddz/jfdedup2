package net.mejf.jfdedup;

import org.junit.Test;

public class NoInputTest extends TestCase {
    @Test
    public void testNoInput() {
        assertEquals(1, Main.runMain());

        assertNothingWritterntoStdout();
        assertSomethingWrittenToStderr();
    }

}
