package org.lua.minescript.managers.watcher;

import org.bukkit.ChatColor;
import org.lua.minescript.Main;
import org.lua.minescript.managers.directory.DirectoryManager;
import org.lua.minescript.managers.lua.ScriptManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class WatcherManager {
    public static WatchService WatcherService;
    private static Boolean WatcherIsActive = false;
    private static WatcherThread MainWatcherThread;
    private static ArrayList<String> IsListenerDirs = new ArrayList<String>();

    public static void EnableWatcher() {
        try {
            WatcherService = FileSystems.getDefault().newWatchService();
            WatcherIsActive = true;
            MainWatcherThread = new WatcherThread();
            MainWatcherThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void DisableWatcher() {
        if (WatcherIsActive) {
            WatcherIsActive = false;
            if (MainWatcherThread.isAlive())
                MainWatcherThread.interrupt();
        }
    }

    public static void AddPathToWatcher(String DirectoryPath) {
        try {
            if (!IsListenerDirs.stream().anyMatch(x -> x.equals(DirectoryPath))) {
                Path n_DirectoryPath = Paths.get(DirectoryPath);
                n_DirectoryPath.register(WatcherService,
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY);

                IsListenerDirs.add(DirectoryPath);
            }

            Main.console.sendMessage(ChatColor.DARK_GRAY +
                    "Add directory from watcher - " + DirectoryPath);

            for (String SubDir : DirectoryManager.GetAllDirsFromDirectory(DirectoryPath)) {
                if (IsListenerDirs.stream().noneMatch(x -> x.equals(SubDir)))
                    AddPathToWatcher(SubDir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class WatcherThread extends Thread {
        public void run() {
            WatchKey key;
            try {
                while (!Thread.interrupted() && (key = WatcherService.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path DirectoryPath = (Path)key.watchable();
                        Path ContextPath = (Path)event.context();
                        Path FullContextPath = DirectoryPath.resolve(ContextPath);

                        File f = new File(FullContextPath.toUri());

                        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                            if (f.isDirectory()) {
                                Main.console.sendMessage(ChatColor.DARK_GRAY +
                                        "The directory \"" + FullContextPath.toString() + "\" was created");
                                WatcherManager.AddPathToWatcher(FullContextPath.toString());
                            } else {
                                Main.console.sendMessage(ChatColor.DARK_GRAY +
                                        "The file \"" + FullContextPath.toString() + "\" was created");
                                ScriptManager.ScriptLoad(FullContextPath.toString());
                            }
                        }

                        if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                            if (f.isFile()) {
                                Main.console.sendMessage(ChatColor.DARK_GRAY +
                                        "File \"" + FullContextPath + "\" has been deleted");

                                ScriptManager.Scripts.removeIf(x -> x.Path.equals(FullContextPath.toString()));
                            }
                        }

                        if (f.length() > 0) {
                            Main.console.sendMessage(ChatColor.DARK_GRAY +
                                    "File \"" + FullContextPath + "\" has been edited");

                            if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                                if (f.isFile() && ScriptManager.Scripts.stream().anyMatch(
                                        x -> x.Path.equals(FullContextPath.toString()) && x.IsIncluded)) {
                                    ScriptManager.ScriptExecute(FullContextPath.toString());
                                }
                            }
                        }
                    }
                    key.reset();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
