package org.lamberm.samplecreator;

import org.lamberm.service.Folder;

import java.util.Random;

import static org.lamberm.service.Size.SMALL;

public class FolderCreator {
    private static final Random RANDOM = new Random();
    private static final String FOLDER_SAMPLE_PATTERN = "test-%d";

    public Folder createSample(String size) {
        return new TestFolder(generateName(), size);
    }

    public Folder createSampleWithNullSize() {
        return new TestFolder(generateName(), null);
    }

    public Folder createSampleWithIncorrectSize() {
        return new TestFolder(generateName(), "test");
    }

    public Folder createSampleWithNullName() {
        return new TestFolder(null, SMALL.name());
    }

    private String generateName() {
        return String.format(FOLDER_SAMPLE_PATTERN, RANDOM.nextInt());
    }

}
