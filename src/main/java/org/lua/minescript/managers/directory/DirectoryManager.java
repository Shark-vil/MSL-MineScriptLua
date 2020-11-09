package org.lua.minescript.managers.directory;

import org.lua.minescript.Main;

import java.io.File;
import java.io.FilenameFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class DirectoryManager {
    private static String PluginDirectoryPath;
    private static String AddonsDirectoryPath;
    private static String[] AddonsPathArray = new String[0];

    public static void SetPluginPath(String Path)
    {
        PluginDirectoryPath = Path;
        Main.console.sendMessage("PluginDirectoryPath - " + PluginDirectoryPath);
        AddonsDirectoryPath = PluginDirectoryPath + "\\addons";
        Main.console.sendMessage("AddonsDirectoryPath - " + AddonsDirectoryPath);
    }

    public static String GetPluginPath()
    {
        return PluginDirectoryPath;
    }

    public static String GetAddonsPath()
    {
        return AddonsDirectoryPath;
    }

    public static String[] GetAddonsPathArray()
    {
        return AddonsPathArray;
    }

    public static void CreatePluginDirectory()
    {
        File f;

        f = new File(PluginDirectoryPath);
        if(!f.exists())
            f.mkdir();

        f = new File(AddonsDirectoryPath);
        if(!f.exists())
            f.mkdir();

        AddonsPathArray = f.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
    }

    public static ArrayList<String> GetAllFilesFromDirectory(String FolderPath) {
        ArrayList<String> FilesList = new ArrayList<String>();

        for (File fileEntry : new File(FolderPath).listFiles()) {
            if (fileEntry.isDirectory()) {
                ArrayList<String> SubFilesList = GetAllFilesFromDirectory(fileEntry.getPath());
                FilesList.addAll(SubFilesList);
            } else {
                FilesList.add(fileEntry.getPath());
            }
        }

        return FilesList;
    }

    public static ArrayList<String> GetAllDirsFromDirectory(String FolderPath) {
        ArrayList<String> DirsList = new ArrayList<String>();

        for (File DirEntry : new File(FolderPath).listFiles()) {
            if (DirEntry.isDirectory()) {
                DirsList.add(DirEntry.getPath());
                ArrayList<String> SubFilesList = GetAllDirsFromDirectory(DirEntry.getPath());

                for (String SubDirPath : SubFilesList) {
                    if (!DirsList.stream().anyMatch(x -> x.equals(SubDirPath)))
                        DirsList.add(SubDirPath);
                }
            }
        }

        return DirsList;
    }
}
