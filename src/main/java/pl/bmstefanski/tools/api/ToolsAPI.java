package pl.bmstefanski.tools.api;

import pl.bmstefanski.tools.api.storage.Storage;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;
import pl.bmstefanski.tools.storage.resource.UserResourceManager;

public interface ToolsAPI {

    PluginConfig getConfiguration();

    Storage getStorage();

    UserResourceManager getUserResource();

    UserManager getUserManager();
}
