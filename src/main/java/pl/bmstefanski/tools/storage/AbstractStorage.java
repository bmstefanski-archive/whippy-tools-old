package pl.bmstefanski.tools.storage;

import pl.bmstefanski.tools.api.storage.Storage;

public abstract class AbstractStorage {

    private final Storage storage;

    protected AbstractStorage(Storage storage) {
        this.storage = storage;
    }

    protected AbstractDatabase getStorage() {
        return (AbstractDatabase) storage;
    }
}
