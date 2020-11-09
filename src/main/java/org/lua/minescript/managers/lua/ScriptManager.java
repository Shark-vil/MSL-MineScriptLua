package org.lua.minescript.managers.lua;

import org.bukkit.ChatColor;
import org.lua.minescript.LuaMachine;
import org.lua.minescript.Main;
import org.lua.minescript.managers.directory.DirectoryManager;
import org.luaj.vm2.LuaValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ScriptManager {
    public static class ScriptModel {
        public ScriptModel(String Path) {
            SetPath(Path);
        }

        public ScriptModel(String Path, Boolean IsIncluded) {
            SetPath(Path);
            this.IsIncluded = IsIncluded;
        }

        private void SetPath(String Path) {
            this.Path = Path;
            this.RelativePath = Path.replaceAll("^plugins\\\\" + Main.pluginName + "\\\\addons\\\\(\\w*)\\\\", "");
        }

        public String Path;
        public String RelativePath;
        public boolean IsIncluded = false;
    }

    public static ArrayList<ScriptModel> Scripts = new ArrayList<ScriptModel>();
    public static ArrayList<ScriptModel> AutorunScripts = new ArrayList<ScriptModel>();
    public static ArrayList<ScriptModel> NotAutorunScripts = new ArrayList<ScriptModel>();
    public static ArrayList<String> AddonsDirsPath = new ArrayList<String>();

    public static void LoadAll() {
        for(String AddonPath : DirectoryManager.GetAddonsPathArray()) {
            String FullAddonPath = Paths.get(DirectoryManager.GetAddonsPath() + "/" + AddonPath).toString();
            Main.console.sendMessage(ChatColor.DARK_GRAY + " - " + FullAddonPath);
            for(String ScriptFilePath : DirectoryManager.GetAllFilesFromDirectory(FullAddonPath)) {
                ScriptModel n_Script = new ScriptModel(ScriptFilePath);

                if (n_Script.RelativePath.matches("^autorun\\\\(.*)")) {
                    n_Script.IsIncluded = true;
                    AutorunScripts.add(n_Script);
                } else {
                    NotAutorunScripts.add(n_Script);
                }

                Scripts.add(n_Script);

                if (AddonsDirsPath.stream().noneMatch(x -> x.equals(FullAddonPath))) {
                    AddonsDirsPath.add(FullAddonPath);
                }

                Main.console.sendMessage(ChatColor.DARK_GRAY + " - " + n_Script.Path);
            }
        }
    }

    public static void ScriptLoad(String FullFilePath) {
        if (Scripts.stream().noneMatch(x -> x.Path.equals(FullFilePath))) {
            ScriptModel n_Script = new ScriptModel(FullFilePath);
            Scripts.add(n_Script);

            if (n_Script.RelativePath.matches("^autorun\\\\(.*)")) {
                n_Script.IsIncluded = true;
                AutorunScripts.add(n_Script);
            } else {
                NotAutorunScripts.add(n_Script);
            }

            Main.console.sendMessage(ChatColor.DARK_GRAY + " - " + n_Script.Path);
        }
    }

    public static LuaValue ScriptExecute(String FilePath) {
        try {
            File f_Script = new File(FilePath);
            if (f_Script.exists()) {
                LuaValue L_Script = LuaMachine.vm.load(ReadFile(FilePath));
                LuaValue Result = L_Script.call();
                Main.console.sendMessage(ChatColor.GREEN + "Successful script loading - " + FilePath);
                return Result;
            }
            else
                Main.console.sendMessage(ChatColor.YELLOW + "Couldn't find file -" + FilePath);
        }
        catch (Throwable ex) {
            Main.console.sendMessage(ChatColor.RED + "Something is creating script error:\n" + ex);
        }

        return LuaValue.NIL;
    }

    public static String ReadFile(String FilePath)
    {
        StringBuilder sb = new StringBuilder();
        Path path = Paths.get(FilePath);

        // Java 8, default UTF-8
        try (BufferedReader reader = Files.newBufferedReader(path)) {

            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
