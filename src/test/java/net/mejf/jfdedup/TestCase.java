package net.mejf.jfdedup;

import java.nio.charset.Charset;
import org.apache.maven.surefire.util.TeeStream;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class TestCase {

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
}
