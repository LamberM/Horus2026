package org.lamberm.samplecreator;

import org.lamberm.service.Folder;
import org.lamberm.service.MultiFolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lamberm.service.Size.MEDIUM;

public class MultiFolderCreator {
    private static final Random RANDOM = new Random();
    private static final String FOLDER_SAMPLE_PATTERN = "test-%d";
    FolderCreator folderCreator = new FolderCreator();

    public MultiFolder createSampleWithCorrectFolders(String size) {
        return new TestMultiFolder(generateName(), size,
                createFolderListWithCorrectFoldersWithoutMultiFolders(size));
    }

    public MultiFolder createSampleWithCorrectFoldersAndMultiFolder(String size) {
        return new TestMultiFolder(generateName(), size, createFolderListWithCorrectFoldersWithMultiFolder(size));
    }

    public MultiFolder createSampleWithIncorrectFolders(String size) {
        return new TestMultiFolder(generateName(), size,
                createFolderListWithIncorrectFoldersWithoutMultiFolders());
    }

    public MultiFolder createSampleWithNullNameWithCorrectFolders(String size) {
        return new TestMultiFolder(null, MEDIUM.name(), createFolderListWithCorrectFoldersWithoutMultiFolders(size));
    }

    public MultiFolder createSampleWithNullSizeWithCorrectFolders(String size) {
        return new TestMultiFolder(generateName(), null, createFolderListWithCorrectFoldersWithoutMultiFolders(size));
    }

    public MultiFolder createSampleWithNullFolders(String size) {
        return new TestMultiFolder(generateName(), size, null);
    }

    public MultiFolder createSampleWithEmptyFolders(String size) {
        return new TestMultiFolder(generateName(), size, List.of());
    }

    private List<Folder> createFolderListWithIncorrectFoldersWithoutMultiFolders() {
        List<Folder> folderList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            folderList.add(folderCreator.createSampleWithNullName());
            folderList.add(folderCreator.createSampleWithNullSize());
            folderList.add(folderCreator.createSampleWithIncorrectSize());
        }
        return folderList;
    }

    private List<Folder> createFolderListWithCorrectFoldersWithoutMultiFolders(String size) {
        List<Folder> folderList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            folderList.add(folderCreator.createSample(size));
        }
        return folderList;
    }

    private List<Folder> createFolderListWithCorrectFoldersWithMultiFolder(String size) {
        List<Folder> folderList = createFolderListWithCorrectFoldersWithoutMultiFolders(size);
        folderList.add(createSampleWithCorrectFolders(size));
        return folderList;
    }

    private String generateName() {
        return String.format(FOLDER_SAMPLE_PATTERN, RANDOM.nextInt());
    }
}
