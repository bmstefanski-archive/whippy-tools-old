package pl.bmstefanski.tools.storage.configuration;

import org.diorite.config.Config;
import org.diorite.config.annotations.Comment;
import org.diorite.config.annotations.CustomKey;
import org.diorite.config.annotations.Header;

@Header("Tools BETA")
public interface PluginConfig extends Config {

    @Comment("You can choose pl, en or make own file.")
    default String getLanguage() {
        return "pl";
    }

    @Comment("Available modes: MySQL, SQLite")
    default String getDatabase() {
        return "mysql";
    }

    default String getHostname() {
        return "localhost";
    }

    default String getDatabaseName() {
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

    @Comment("Join and quit format. If you don't need it, leave it blank.")
    default String getJoinFormat() {
        return "&e%player% &adolaczyl do gierki!";
    }

    default String getQuitFormat() {
        return "&e%player% &cwyszedl z gierki!";
    }

    default int getSpawnX() {
        return 0;
    }

    default int getSpawnY() {
        return 64;
    }

    default int getSpawnZ() {
        return 0;
    }

    default String getSpawnWorld() {
        return "world";
    }

    default boolean getSpawn() {
        return false;
    }

    void setSpawnX(int x);

    void setSpawnY(int y);

    void setSpawnZ(int z);

    void setSpawnWorld(String worldName);

}
