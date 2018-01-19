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

    @CustomKey("mysql")
    default MySQL getMySQLSection() {
        return ConfigManager.createInstance(MySQL.class);
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
