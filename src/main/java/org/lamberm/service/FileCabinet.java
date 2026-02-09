package org.lamberm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.lamberm.service.Size.LARGE;
import static org.lamberm.service.Size.MEDIUM;
import static org.lamberm.service.Size.SMALL;

public class FileCabinet implements Cabinet {
    private List<Folder> processedFolders = new ArrayList<>();

    public FileCabinet(List<Folder> folders) {
        this.processedFolders = processAllFolders(folders);
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        if (!isValidSize(size)) {
            throw new IllegalArgumentException(
                    "Size does not equal: %s,%s or %s".formatted(SMALL.name(), MEDIUM.name(), LARGE.name()));
        }
        return processedFolders.stream()
                .filter(folder -> folder.getSize().equals(size))
                .toList();
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return processedFolders.stream().filter(folder -> folder.getName().equals(name)).findAny();
    }

    @Override
    public int count() {
        return processedFolders.size();
    }

    private List<Folder> processAllFolders(List<Folder> givenFolder) {
        var filteredFolders = filteredFolders(givenFolder);
        for (Folder folder : filteredFolders) {
            processedFolders.add(folder);
            if (folder instanceof MultiFolder multiFolder && multiFolder.getFolders() != null) {
                processAllFolders(multiFolder.getFolders());
            }
        }
        return processedFolders;
    }

    private List<Folder> filteredFolders(List<Folder> givenFolder) {
        return givenFolder.stream()
                .filter(Objects::nonNull)
                .filter(folder -> folder.getName() != null)
                .filter(folder -> folder.getSize() != null && isValidSize(folder.getSize()))
                .toList();
    }

    private boolean isValidSize(String size) {
        return size.equals(SMALL.name()) || size.equals(MEDIUM.name()) || size.equals(LARGE.name());
    }
}
