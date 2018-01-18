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
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;
import pl.bmstefanski.tools.listener.*;
import pl.bmstefanski.tools.storage.resource.BanResourceManager;
import pl.bmstefanski.tools.type.DatabaseType;

import java.io.File;

public class Tools extends JavaPlugin implements ToolsAPI {

    private final File pluginConfigFile = new File(getDataFolder(), "config.yml");
    private final File messagesFile = new File(getDataFolder(), "messages.yml");

    private static Tools instance;

    private BanResourceManager banResource;
    private PluginConfig pluginConfig;
    private UserManager userManager;
    private Messages messages;
    private Storage storage;

    public Tools() {
        instance = this;
    }

    @Override
    public void onEnable() {

        this.pluginConfig = ConfigManager.createInstance(PluginConfig.class);
        this.messages = ConfigManager.createInstance(Messages.class);

        this.pluginConfig.bindFile(pluginConfigFile);
        this.messages.bindFile(messagesFile);

        this.pluginConfig.load();
        this.pluginConfig.save();

        this.messages.load();
        this.messages.save();

        setUpStorage();

        this.userManager = new UserManager();
        this.banResource = new BanResourceManager(storage);

        this.banResource.load();

        registerListeners(
                new PlayerCommandPreprocess(this),
                new PlayerJoin(this),
                new PlayerPreLogin(this),
                new PlayerQuit(this),
                new PlayerMove(this),
                new EntityDamage(this),
                new PlayerDeath(this)
        );

        registerCommands(
                new ToolsCommand(this),
                new WhoisCommand(this),
                new WorkbenchCommand(this),
                new SpawnCommand(this),
                new SetSpawnCommand(this),
                new ReloadCommand(this),
                new ListCommand(this),
                new HealCommand(this),
                new GodCommand(this),
                new GamemodeCommand(this),
                new FlyCommand(this),
                new FeedCommand(this),
                new EnderchestCommand(this),
                new DisableCommand(this),
                new ClearCommand(this),
                new BroadcastCommand(this),
                new BackCommand(this),
                new BanCommand(this),
                new UnbanCommand(this)
        );
    }

    @Override
    public void onDisable() {
        this.pluginConfig.save();
        this.messages.save();
        this.banResource.save();
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
    public Messages getMessages() {
        return messages;
    }

    @Override
    public BanResourceManager getBanResource() {
        return banResource;
    }

    public static Tools getInstance() {
        return instance;
    }
}
