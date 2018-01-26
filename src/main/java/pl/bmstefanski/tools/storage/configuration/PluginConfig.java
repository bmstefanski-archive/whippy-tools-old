/*
 MIT License

 Copyright (c) 2018 Whippy Tools

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

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

    default int getSpawnDelay() {
        return 5;
    }

    default int getBackDelay() {
        return 3;
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
