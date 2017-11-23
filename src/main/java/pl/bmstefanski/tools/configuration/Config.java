package pl.bmstefanski.tools.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import pl.bmstefanski.tools.Tools;

public class Config {

    private static Config instance;

    public final String language;

    public final String mysqlHostname;
    public final String mysqlDatabase;
    public final String mysqlUsername;
    public final String mysqlPassword;
    public final int mysqlPort;

    public final String joinFormat;
    public final String quitFormat;

    public final int spawnBlockX;
    public final int spawnBlockY;
    public final int spawnBlockZ;
    public final String spawnWorld;
    public final boolean spawnSet;

    private Config() {
        FileConfiguration fileConfiguration = Tools.getInstance().getConfig();

        this.language = fileConfiguration.getString("language");

        this.mysqlHostname = fileConfiguration.getString("mysql.hostname");
        this.mysqlDatabase = fileConfiguration.getString("mysql.database");
        this.mysqlUsername = fileConfiguration.getString("mysql.username");
        this.mysqlPassword = fileConfiguration.getString("mysql.password");
        this.mysqlPort = fileConfiguration.getInt("mysql.port");

        this.joinFormat = fileConfiguration.getString("join-format");
        this.quitFormat = fileConfiguration.getString("quit-format");

        this.spawnBlockX = fileConfiguration.getInt("spawn.x");
        this.spawnBlockY = fileConfiguration.getInt("spawn.y");
        this.spawnBlockZ = fileConfiguration.getInt("spawn.z");
        this.spawnWorld = fileConfiguration.getString("spawn.world");
        this.spawnSet = fileConfiguration.getBoolean("spawn.set");
    }

    public static Config getInstance() {
        if (instance == null) instance = new Config();
        return instance;
    }
}
