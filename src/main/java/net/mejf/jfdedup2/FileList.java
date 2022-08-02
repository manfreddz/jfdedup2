package net.mejf.jfdedup2;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class FileList implements Iterable<JFile> {
    LinkedList<JFile> fileList = new LinkedList<>();
    Set<String> addedFiles = new HashSet<>();

    public void add(JFile jFile) {
        if (!addedFiles.contains(jFile.getName())) {
            addedFiles.add(jFile.getName());
            fileList.add(jFile);
        }
    }

    public int size() {
        return fileList.size();
    }

    @Override
    public Iterator<JFile> iterator() {
        return fileList.iterator();
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
