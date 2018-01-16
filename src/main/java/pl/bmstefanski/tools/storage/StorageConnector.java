package pl.bmstefanski.tools.storage;

import pl.bmstefanski.tools.api.storage.Storage;
import pl.bmstefanski.tools.type.DatabaseType;

public class StorageConnector {

    private final DatabaseType databaseType;
    private Storage storage;

    public StorageConnector(DatabaseType databaseType) {
        this.databaseType = databaseType;
        
        connect();
    }

    public void connect() {

        switch (databaseType) {
            case MYSQL:
                this.storage = new MySQLDatabase(
                        "root",
                        "tools",
                        "root",
                        "localhost",
                        "3306"
                );

                break;
        }
    }

    public Storage getStorage() {
        return storage;
    }
}
