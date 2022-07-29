package net.mejf.jfdedup;

import org.apache.maven.surefire.util.TeeStream;
import org.junit.Assert;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TestCase extends Assert {

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private ByteArrayOutputStream outBuffer;
    private ByteArrayOutputStream errBuffer;

    @Before
    public void setupOutErr() {
        outBuffer = new ByteArrayOutputStream();
        System.setOut(new TeeStream(new PrintStream(outBuffer, false, CHARSET), System.out));
        errBuffer = new ByteArrayOutputStream();
        System.setErr(new TeeStream(new PrintStream(errBuffer, false, CHARSET), System.err));
    }

    protected String getOut() {
        System.out.flush();
        return outBuffer.toString(CHARSET);
    }

    protected String getErr() {
        System.out.flush();
        return errBuffer.toString(CHARSET);
    }

    protected void assertSomethingWrittenToStdout() {
        assertNotEquals("Nothing written to stdout", 0, getOut().length());
    }

    protected void assertNothingWritterntoStdout() {
        assertEquals("Something written to stdout", "", getOut());
    }

    protected void assertSomethingWrittenToStderr() {
        assertNotEquals("Nothing written to stderr", 0, getErr().length());
    }

    protected void assertNothingWritterntoStderr() {
        assertEquals("Something written to stderr", "", getErr());
    }
}
