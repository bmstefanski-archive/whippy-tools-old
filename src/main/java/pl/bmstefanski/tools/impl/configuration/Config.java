package pl.bmstefanski.tools.impl.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import pl.bmstefanski.tools.Tools;

public class Config {

    private static Config instance;

    public String mysqlHostname;
    public String mysqlDatabase;
    public String mysqlUsername;
    public String mysqlPassword;
    public int mysqlPort;
    public int mysqlRefresh;

    private Config() {
        FileConfiguration fileConfiguration = Tools.getInstance().getConfig();

        this.mysqlHostname = fileConfiguration.getString("mysql.hostname");
        this.mysqlDatabase = fileConfiguration.getString("mysql.database");
        this.mysqlUsername = fileConfiguration.getString("mysql.username");
        this.mysqlPassword = fileConfiguration.getString("mysql.password");
        this.mysqlPort = fileConfiguration.getInt("mysql.port");
        this.mysqlRefresh = fileConfiguration.getInt("mysql.refresh");
    }

    public static Config getInstance() {
        if (instance == null) instance = new Config();
        return instance;
    }
}
