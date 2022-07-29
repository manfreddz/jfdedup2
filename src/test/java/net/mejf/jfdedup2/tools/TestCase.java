package net.mejf.jfdedup2.tools;

import net.mejf.jfdedup2.Main;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TestCase extends FileHandlingTools {

    protected String[] mixed(Object... args) {
        List<String> ret = new LinkedList<>();
        for (Object arg: args) {
            if (arg instanceof String) {
                ret.add((String) arg);
            } else if (arg instanceof String[]) {
                Collections.addAll(ret, (String[]) arg);
            } else {
                throw new RuntimeException("Only String and String[] supported!");
            }
        }
        return ret.toArray(new String[0]);
    }

    protected int runMain(Object... args) {
        return Main.runMain(mixed(args));
    }
}
