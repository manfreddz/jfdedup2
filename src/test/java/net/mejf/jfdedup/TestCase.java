package net.mejf.jfdedup;

import org.apache.maven.surefire.util.TeeStream;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestCase {

    private ByteArrayOutputStream outBuffer;
    private ByteArrayOutputStream errBuffer;

    @Before
    public void setupOutErr() {
        outBuffer = new ByteArrayOutputStream();
        System.setOut(new TeeStream(new PrintStream(outBuffer), System.out));
        errBuffer = new ByteArrayOutputStream();
        System.setErr(new TeeStream(new PrintStream(errBuffer), System.err));
    }
}
