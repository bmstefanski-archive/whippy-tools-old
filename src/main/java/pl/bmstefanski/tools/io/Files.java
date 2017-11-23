package pl.bmstefanski.tools.io;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.configuration.Config;

import java.io.File;

public class Files {

    private final Config config = Config.getInstance();
    private final File mainFolder = Tools.getInstance().getDataFolder();
    private final File messageFile = new File(mainFolder, "messages_" + config.language + ".yml");

    public FileConfiguration getMessageFileConfiguration() {
        return YamlConfiguration.loadConfiguration(messageFile);
    }

    public File getMessageFile() {
        return messageFile;
    }
}
