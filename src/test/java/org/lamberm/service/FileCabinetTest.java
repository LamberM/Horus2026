package org.lamberm.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.lamberm.samplecreator.FolderCreator;
import org.lamberm.samplecreator.MultiFolderCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.lamberm.service.Size.LARGE;


class FileCabinetTest {
    private static final Random RANDOM = new Random();
    FileCabinet fileCabinet;
    FolderCreator folderCreator = new FolderCreator();
    MultiFolderCreator multiFolderCreator = new MultiFolderCreator();

    @Nested
    class FindFolderBySize {
        @Test
        void givenListWithAllNullFolders_whenFindFoldersBySize_thenEmptyList() {
            List<Folder> folders = givenListWithAllNullFolders();
            fileCabinet = new FileCabinet(folders);
            //when
            var result = fileCabinet.findFoldersBySize(LARGE.name());
            //then
            Assertions.assertTrue(result.isEmpty());
        }

        @Test
        void givenListWithMultiFolderWhichHaveEmptyFolders_whenFindFoldersBySize_thenGetResult() {
            var size = generateSize();
            List<Folder> folders = givenListWithMultiFolderWhichHaveEmptyFolders(size);
            fileCabinet = new FileCabinet(folders);
            //when
            var result = fileCabinet.findFoldersBySize(size);
            //then
            Assertions.assertFalse(result.isEmpty());
            Assertions.assertEquals(3, result.size());
            Assertions.assertEquals(size, result.getFirst().getSize());
            Assertions.assertEquals(size, result.get(1).getSize());
            Assertions.assertEquals(size, result.get(2).getSize());
        }

        @Test
        void givenListWithAllNullMultiFolderWhichHaveCorrectFolders_whenFindFoldersBySize_thenEmptyList() {
            var size = generateSize();
            List<Folder> folders = givenListWithAllNullMultiFolderWhichHaveCorrectFolders(size);
            fileCabinet = new FileCabinet(folders);
            //when
            var result = fileCabinet.findFoldersBySize(size);
            //then
            Assertions.assertTrue(result.isEmpty());
        }

        @Test
        void givenListWithCorrectFoldersAndMultiFolder_whenFindFoldersBySize_thenCorrectFolders() {
            var size = generateSize();
            List<Folder> folders = givenListWithCorrectFoldersAndMultiFolder(size);
            fileCabinet = new FileCabinet(folders);
            //when
            var result = fileCabinet.findFoldersBySize(size);
            //then
            Assertions.assertFalse(result.isEmpty());
            Assertions.assertEquals(10, result.size());
        }

        @Test
        void givenListWithCorrectFoldersAndMultiFolderWhichHaveMultiFolder_whenFindFoldersBySize_thenCorrectFolders() {
            var size = generateSize();
            List<Folder> folders = givenListWithCorrectFoldersAndMultiFolderWhichHaveMultiFolder(size);
            fileCabinet = new FileCabinet(folders);
            //when
            var result = fileCabinet.findFoldersBySize(size);
            //then
            Assertions.assertFalse(result.isEmpty());
            Assertions.assertEquals(17, result.size());
        }

        @Test
        void givenFolders_whenFindFoldersBySize_thenThrowException() {
            //given

            List<Folder> folders = givenListWithCorrectFoldersAndMultiFolder(generateSize());
            fileCabinet = new FileCabinet(folders);
            //when
            //then
            var result = assertThrows(IllegalArgumentException.class,
                    () -> fileCabinet.findFoldersBySize("EXTRALARGE"));
            assertEquals("Size does not equal: SMALL,MEDIUM or LARGE", result.getMessage());
        }
    }

    @Nested
    class FindFolderByName {
        @Test
        void givenListWithAllNullFolders_whenFindFolderByName_thenEmptyFolder() {
            List<Folder> folders = givenListWithAllNullFolders();
            fileCabinet = new FileCabinet(folders);
            //when
            var result = fileCabinet.findFolderByName("");
            //then
            Assertions.assertTrue(result.isEmpty());
        }

        @Test
        void givenListWithMultiFolderWhichHaveEmptyFolders_whenFindFolderByName_thenGetMultiFolder() {
            List<Folder> folders = givenListWithMultiFolderWhichHaveEmptyFolders(generateSize());
            fileCabinet = new FileCabinet(folders);
            var expectedName = folders.getFirst().getName();
            //when
            var result = fileCabinet.findFolderByName(expectedName);
            //then
            Assertions.assertTrue(result.isPresent());
            Assertions.assertEquals(expectedName, result.get().getName());
        }

        @Test
        void givenListWithAllNullMultiFolderWhichHaveCorrectFolders_whenFindFolderByName_thenGetFolder() {
            List<Folder> folders = givenListWithAllNullMultiFolderWhichHaveCorrectFolders(generateSize());
            fileCabinet = new FileCabinet(folders);
            //when
            var result = fileCabinet.findFolderByName(null);
            //then
            Assertions.assertTrue(result.isEmpty());
        }

        @Test
        void givenListWithCorrectFoldersAndMultiFolder_whenFindFolderByName_thenGetFolder() {
            List<Folder> folders = givenListWithCorrectFoldersAndMultiFolder(generateSize());
            fileCabinet = new FileCabinet(folders);
            var expectedName = folders.getLast().getName();
            //when
            var result = fileCabinet.findFolderByName(expectedName);
            //then
            Assertions.assertTrue(result.isPresent());
            Assertions.assertEquals(expectedName, result.get().getName());
        }

        @Test
        void givenListWithCorrectFoldersAndMultiFolderWhichHaveMultiFolder_whenFindFolderByName_thenGetFolder() {
            List<Folder> folders = givenListWithCorrectFoldersAndMultiFolderWhichHaveMultiFolder(generateSize());
            fileCabinet = new FileCabinet(folders);
            var expectedName = folders.getLast().getName();
            //when
            var result = fileCabinet.findFolderByName(expectedName);
            //then
            Assertions.assertTrue(result.isPresent());
            Assertions.assertEquals(expectedName, result.get().getName());
        }
    }

    @Nested
    class Count {
        @Test
        void givenListWithAllNullFolders_whenCount_thenEqualsZero() {
            List<Folder> folders = givenListWithAllNullFolders();
            fileCabinet = new FileCabinet(folders);
            //when
            var result = fileCabinet.count();
            //then
            Assertions.assertEquals(0, result);
        }

        @Test
        void givenListWithMultiFolderWhichHaveEmptyFolders_whenCount_thenEqualsThree() {
            List<Folder> folders = givenListWithMultiFolderWhichHaveEmptyFolders(generateSize());
            fileCabinet = new FileCabinet(folders);
            //when
            var result = fileCabinet.count();
            //then
            Assertions.assertEquals(3, result);
        }

        @Test
        void givenListWithAllNullMultiFolderWhichHaveCorrectFolders_whenCount_thenEqualsZero() {
            List<Folder> folders = givenListWithAllNullMultiFolderWhichHaveCorrectFolders(generateSize());
            fileCabinet = new FileCabinet(folders);
            //when
            var result = fileCabinet.count();
            //then
            Assertions.assertEquals(0, result);
        }

        @Test
        void givenListWithCorrectFoldersAndMultiFolder_whenCount_thenEqualsTen() {
            List<Folder> folders = givenListWithCorrectFoldersAndMultiFolder(generateSize());
            fileCabinet = new FileCabinet(folders);
            //when
            var result = fileCabinet.count();
            //then
            Assertions.assertEquals(10, result);
        }

        @Test
        void givenListWithCorrectFoldersAndMultiFolderWhichHaveMultiFolder_whenCount_thenGetSeventeen() {
            List<Folder> folders = givenListWithCorrectFoldersAndMultiFolderWhichHaveMultiFolder(generateSize());
            fileCabinet = new FileCabinet(folders);
            //when
            var result = fileCabinet.count();
            //then
            Assertions.assertEquals(17, result);
        }
    }

    private List<Folder> givenListWithAllNullFolders() {
        List<Folder> folderList = new ArrayList<>();
        folderList.add(folderCreator.createSampleWithNullSize());
        folderList.add(folderCreator.createSampleWithNullName());
        folderList.add(folderCreator.createSampleWithIncorrectSize());
        return folderList;
    }

    private List<Folder> givenListWithMultiFolderWhichHaveEmptyFolders(String size) {
        List<Folder> folderList = new ArrayList<>();
        folderList.add(multiFolderCreator.createSampleWithNullFolders(size));
        folderList.add(multiFolderCreator.createSampleWithEmptyFolders(size));
        folderList.add(multiFolderCreator.createSampleWithIncorrectFolders(size));
        return folderList;
    }

    private List<Folder> givenListWithAllNullMultiFolderWhichHaveCorrectFolders(String size) {
        List<Folder> folderList = new ArrayList<>();
        folderList.add(multiFolderCreator.createSampleWithNullSizeWithCorrectFolders(size));
        folderList.add(multiFolderCreator.createSampleWithNullNameWithCorrectFolders(size));
        return folderList;
    }

    private List<Folder> givenListWithCorrectFoldersAndMultiFolder(String size) {
        List<Folder> folderList = new ArrayList<>();
        folderList.add(folderCreator.createSample(size));
        folderList.add(folderCreator.createSample(size));
        folderList.add(folderCreator.createSample(size));
        folderList.add(multiFolderCreator.createSampleWithCorrectFolders(size));
        return folderList;
    }

    private List<Folder> givenListWithCorrectFoldersAndMultiFolderWhichHaveMultiFolder(String size) {
        List<Folder> folderList = new ArrayList<>();
        folderList.add(folderCreator.createSample(size));
        folderList.add(folderCreator.createSample(size));
        folderList.add(folderCreator.createSample(size));
        folderList.add(multiFolderCreator.createSampleWithCorrectFoldersAndMultiFolder(size));
        return folderList;
    }

    private String generateSize() {
        var choice = RANDOM.nextInt(3);
        return switch (choice) {
            case 1 -> "MEDIUM";
            case 2 -> "LARGE";
            default -> "SMALL";
        };
    }
}