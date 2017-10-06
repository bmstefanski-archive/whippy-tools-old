package pl.bmstefanski.tools.manager;

import org.bukkit.configuration.file.FileConfiguration;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.configuration.Config;
import pl.bmstefanski.tools.util.Utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseManager {

    private static DatabaseManager instance;
    private Config config;
    private Connection connection;

    private DatabaseManager() {
        this.config = Config.getInstance();
    }


    public void establishConnection() {
        try {
            String connectionString = "jdbc:mysql://" + config.mysqlHostname
                                    + ":" + config.mysqlPort
                                    + "/" + config.mysqlDatabase
                                    + "?user=" + config.mysqlUsername
                                    + "&password=" + config.mysqlPassword;

            if (databaseType() == DatabaseType.MYSQL) {
                Class.forName("com.mysql.jdbc.Driver");
                this.connection = DriverManager.getConnection(connectionString);
            }
            else if (databaseType() == DatabaseType.SQLITE) {
                Class.forName("orq.sqlite.JDBC");
                this.connection = DriverManager.getConnection("jdbc:sqlite:plugins/"
                        + Tools.getInstance().getName() + "/players.db");
            }

            Utils.sendMessageToConsole("&ePomyslnie polaczono z baza danych! &7("
                    + Tools.getInstance().getDescription().getName() + ")" +
                    " (" + DatabaseType.MYSQL.name() + ")");

        } catch (Exception ex) {
            Utils.sendMessageToConsole("&cBlad podczas laczenia z baza danych! &7("
                    + Tools.getInstance().getDescription().getName() + ")" +
                    " (" + DatabaseType.MYSQL.name() + ")");

            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public DatabaseType databaseType() {
        FileConfiguration fileConfiguration = Tools.getInstance().getConfig();
        String databaseType = fileConfiguration.getString("database");

        if (databaseType.equalsIgnoreCase("mysql")) return DatabaseType.MYSQL;
        else if (databaseType.equalsIgnoreCase("sqlite")) return DatabaseType.SQLITE;
        return null;
    }

    public enum DatabaseType {
        MYSQL,
        SQLITE
    }

    public static DatabaseManager getInstance() {
        if (instance == null) instance = new DatabaseManager();
        return instance;
    }
}
