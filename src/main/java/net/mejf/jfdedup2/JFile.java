package net.mejf.jfdedup2;

public class JFile {
    private final String absolutePath;
    private final int prio;

    public JFile(String absolutePath, int prio) {
        this.absolutePath = absolutePath;
        this.prio = prio;
    }

    public String getName() {
        return absolutePath;
    }
}
