package pl.bmstefanski.tools.storage;

import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.storage.Database;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;
import pl.bmstefanski.tools.type.DatabaseType;

public class DatabaseStorageConnector {

    private final DatabaseType databaseType;
    private final Tools plugin;
    private Database database;

    public DatabaseStorageConnector(Tools plugin, DatabaseType databaseType) {
        this.plugin = plugin;
        this.databaseType = databaseType;

        connect();
    }

    public void connect() {

        PluginConfig config = plugin.getConfiguration();

        switch (databaseType) {
            case MYSQL:
                this.database = new MySQLDatabase(
                        config.getMySQLSection().getHostname(),
                        config.getMySQLSection().getPort(),
                        config.getMySQLSection().getDatabase(),
                        config.getMySQLSection().getUsername(),
                        config.getMySQLSection().getPassword()
                );
        }

    }

    public Database getDatabase() {
        return database;
    }
}
