package pl.bmstefanski.tools;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.diorite.config.ConfigManager;
import pl.bmstefanski.tools.api.ToolsAPI;
import pl.bmstefanski.tools.api.storage.Storage;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.command.*;
import pl.bmstefanski.tools.command.basic.BukkitCommands;
import pl.bmstefanski.tools.command.basic.Commands;
import pl.bmstefanski.tools.storage.StorageConnector;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;
import pl.bmstefanski.tools.listener.*;
import pl.bmstefanski.tools.storage.resource.UserResourceManager;
import pl.bmstefanski.tools.type.DatabaseType;

import java.io.File;

public class Tools extends JavaPlugin implements ToolsAPI {

    private final File pluginConfigFile = new File(getDataFolder(), "config.yml");

    private static Tools instance;

    private UserResourceManager userResourceManager;
    private UserManager userManager;
    private PluginConfig pluginConfig;
    private Storage storage;

    public Tools() {
        instance = this;
    }

    @Override
    public void onEnable() {


        this.pluginConfig = ConfigManager.createInstance(PluginConfig.class);
        this.pluginConfig.bindFile(pluginConfigFile);

        this.pluginConfig.load();
        this.pluginConfig.save();

        setUpStorage();

        this.userResourceManager = new UserResourceManager(storage);
        this.userManager = new UserManager();

        registerListeners(
                new PlayerCommandPreprocess(),
                new PlayerJoin(this),
                new PlayerPreLogin(this),
                new PlayerQuit(this),
                new PlayerMove(this),
                new EntityDamage(this),
                new PlayerDeath(this)
        );

        registerCommands(
                new ToolsCommand(this),
                new WhoisCommand(),
                new WorkbenchCommand(),
                new SpawnCommand(this),
                new SetSpawnCommand(this),
                new ReloadCommand(this),
                new ListCommand(),
                new HealCommand(),
                new GodCommand(),
                new GamemodeCommand(),
                new FlyCommand(),
                new FeedCommand(),
                new EnderchestCommand(),
                new DisableCommand(this),
                new ClearCommand(),
                new BroadcastCommand(),
                new BackCommand(this),
                new BanCommand(),
                new UnbanCommand(),
                new DebugCommand(this)
        );
    }

    @Override
    public void onDisable() {
        this.pluginConfig.save();
    }

    private void registerCommands(Object... commands) {
        Commands bukkitCommands = new BukkitCommands(this);

        for (Object object : commands) {
            bukkitCommands.registerCommandObject(object);
        }
    }

    private void registerListeners(Listener... listeners) {

        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    private void setUpStorage() {
        this.storage = new StorageConnector(this, DatabaseType.MYSQL).getStorage();
    }

    @Override
    public PluginConfig getConfiguration() {
        return pluginConfig;
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public UserManager getUserManager() {
        return userManager;
    }

    @Override
    public UserResourceManager getUserResource() {
        return userResourceManager;
    }

    public static Tools getInstance() {
        return instance;
    }
}
