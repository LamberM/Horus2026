package org.lamberm.samplecreator;

import org.lamberm.service.Folder;
import org.lamberm.service.MultiFolder;

import java.util.List;

public class TestMultiFolder implements MultiFolder {
    private final String name;
    private final String size;
    private final List<Folder> testFolders;

    public TestMultiFolder(String name, String size, List<Folder> testFolders) {
        this.name = name;
        this.size = size;
        this.testFolders = testFolders;
    }

    @Override
    public List<Folder> getFolders() {
        return testFolders;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSize() {
        return size;
    }
}
