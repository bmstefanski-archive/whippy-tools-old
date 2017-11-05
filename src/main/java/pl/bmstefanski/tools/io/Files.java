package pl.bmstefanski.tools.io;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.bmstefanski.tools.Tools;

import java.io.File;
import java.io.IOException;

public class Files {

    private static final File mainFolder = Tools.getInstance().getDataFolder();
    private static final File messageFile = new File(mainFolder, "messages_" + Tools.getInstance().getConfig().getString("language") + ".yml");

    public static void check() throws IOException {
        if (!mainFolder.exists()) mainFolder.mkdirs();
        else if (!messageFile.exists()) messageFile.createNewFile();
    }

    public static FileConfiguration getMessageFileConfiguration() {
        return YamlConfiguration.loadConfiguration(messageFile);
    }

    public static File getMessageFile() {
        return messageFile;
    }
}
