package com.example.folderpractice.Model;// FolderModel.java
import java.io.File;
import java.io.FileFilter;

public class FolderModel {
    private String folderName;
    private String folderPath;
    private int numberOfFiles;

    public FolderModel(String folderPath) {
        this.folderPath = folderPath;
        File folder = new File(folderPath);
        this.folderName = folder.getName();
        this.numberOfFiles = getFilesCount(folder);
    }

    public String getFolderName() {
        return folderName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    private int getFilesCount(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isFile();
                }
            });
            if (files != null) {
                return files.length;
            }
        }
        return 0;
    }
}
