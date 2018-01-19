package pl.bmstefanski.tools.storage.configuration;

import org.diorite.config.Config;

public interface SpawnConfig extends Config {

    default int getX() {
        return 0;
    }

    default int getY() {
        return 64;
    }

    default int getZ() {
        return 0;
    }

    default String getWorld() {
        return "world";
    }

    default boolean getExists() {
        return true;
    }

    void setX(int x);

    void setY(int y);

    void setZ(int z);

    void setWorld(String world);

    void setExists(boolean exists);
}
