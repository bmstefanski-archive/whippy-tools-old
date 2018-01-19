package pl.bmstefanski.tools.api;

import pl.bmstefanski.tools.api.storage.Storage;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;
import pl.bmstefanski.tools.storage.configuration.SpawnConfig;
import pl.bmstefanski.tools.storage.resource.BanResourceManager;

public interface ToolsAPI {

    PluginConfig getConfiguration();

    Storage getStorage();

    UserManager getUserManager();

    Messages getMessages();

    BanResourceManager getBanResource();

    SpawnConfig getSpawnConfiguration();
}
