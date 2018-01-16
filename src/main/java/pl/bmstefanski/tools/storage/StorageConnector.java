package pl.bmstefanski.tools.storage;

import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.storage.Storage;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;
import pl.bmstefanski.tools.type.DatabaseType;

public class StorageConnector {

    private final DatabaseType databaseType;
    private final Tools plugin;
    private Storage storage;

    public StorageConnector(Tools plugin, DatabaseType databaseType) {
        this.plugin = plugin;
        this.databaseType = databaseType;

        connect();
    }

    public void connect() {

        PluginConfig config = plugin.getConfiguration();

        switch (databaseType) {
            case MYSQL:
                this.storage = new MySQLDatabase(
                        config.getMySQLSection().getUsername(),
                        config.getMySQLSection().getDatabase(),
                        config.getMySQLSection().getPassword(),
                        config.getMySQLSection().getHostname(),
                        config.getMySQLSection().getPort()
                );

                break;
        }
    }

    public Storage getStorage() {
        return storage;
    }
}
