package pl.bmstefanski.tools.api;

import pl.bmstefanski.tools.api.storage.Storage;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

public interface ToolsAPI {

    PluginConfig getConfiguration();

    Storage getStorage();

    UserManager getUserManager();

    Messages getMessages();
}
