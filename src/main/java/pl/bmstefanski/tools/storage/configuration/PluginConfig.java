package pl.bmstefanski.tools.storage.configuration;

import org.diorite.config.Config;
import org.diorite.config.ConfigManager;
import org.diorite.config.annotations.CustomKey;

public interface PluginConfig extends Config {

    default String getLanguage() {
        return "pl";
    }

    default String getJoinFormat() {
        return "&e%player% &adolaczyl do gierki!";
    }

    default String getQuitFormat() {
        return "&e%player% &cwyszedl z gierki!";
    }

    @CustomKey("spawn")
    default Spawn getSpawnSection() {
        return ConfigManager.createInstance(Spawn.class);
    }

    @CustomKey("mysql")
    default MySQL getMySQLSection() {
        return ConfigManager.createInstance(MySQL.class);
    }

    interface Spawn extends Config {

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

    interface MySQL extends Config {

        default String getHostname() {
            return "localhost";
        }

        default String getDatabase() {
            return "tools";
        }

        default String getUsername() {
            return "root";
        }

        default String getPassword() {
            return "root";
        }

        default int getPort() {
            return 3306;
        }
    }

}
