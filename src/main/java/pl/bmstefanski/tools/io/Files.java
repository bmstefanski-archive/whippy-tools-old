package pl.bmstefanski.tools.io;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.bmstefanski.tools.Tools;

import java.io.File;
import java.io.IOException;

public class Files {

    private static final File mainFolder = Tools.getInstance().getDataFolder();
    private static final File messageFile = new File(mainFolder, "messages_" + Tools.getInstance().getConfig().getString("language") + ".yml");
    private static final File commandsFile = new File(mainFolder, "commands.yml");

    private static YamlConfiguration yamlConfiguration;

    public static void check() throws IOException {
        if (!mainFolder.exists()) mainFolder.mkdirs();
        else if (!messageFile.exists()) messageFile.createNewFile();
        else if (!commandsFile.exists()) Tools.getInstance().saveResource("commands.yml", true);

        yamlConfiguration = YamlConfiguration.loadConfiguration(commandsFile);
    }

    public static FileConfiguration getMessageFileConfiguration() {
        return YamlConfiguration.loadConfiguration(messageFile);
    }

    public static File getMessageFile() {
        return messageFile;
    }

    public static YamlConfiguration getCommandsFile() {
        return yamlConfiguration;
    }
}
