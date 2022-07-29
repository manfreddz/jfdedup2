package net.mejf.jfdedup;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HelpTest extends TestCase {
    @Test
    public void testHelp() throws ParseException {
        assertEquals(0, Main.runMain("-h"));

        assertNotEquals("No help written to stdout", 0, getOut().length());
        assertEquals("Something written to stderr", 0, getErr().length());
    }
}