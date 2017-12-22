package pl.bmstefanski.tools;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.bmstefanski.tools.basic.util.BanUtils;
import pl.bmstefanski.tools.command.*;
import pl.bmstefanski.tools.command.basic.BukkitCommands;
import pl.bmstefanski.tools.command.basic.Commands;
import pl.bmstefanski.tools.database.MySQL;
import pl.bmstefanski.tools.io.MessageFile;
import pl.bmstefanski.tools.listener.*;
import pl.bmstefanski.tools.manager.DatabaseManager;

public final class Tools extends JavaPlugin {

    private static Tools instance;
    private DatabaseManager databaseManager;
    private MySQL mySQL;

    @Override
    public void onLoad() {
        instance = this;

        this.databaseManager = DatabaseManager.getInstance();
        this.mySQL = MySQL.getInstance();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.databaseManager.establishConnection();
        this.mySQL.checkUser();
        this.mySQL.checkBan();
        BanUtils.loadBans();

        MessageFile.loadMessages();

        registerListeners(
                new PlayerCommandPreprocess(),
                new PlayerJoin(),
                new PlayerPreLogin(),
                new PlayerQuit(),
                new PlayerMove(),
                new EntityDamage(),
                new PlayerDeath()
        );

        registerCommands(
                new ToolsCommand(),
                new WhoisCommand(),
                new WorkbenchCommand(),
                new SpawnCommand(),
                new SetSpawnCommand(),
                new ReloadCommand(),
                new ListCommand(),
                new HealCommand(),
                new GodCommand(),
                new GamemodeCommand(),
                new FlyCommand(),
                new FeedCommand(),
                new EnderchestCommand(),
                new DisableCommand(),
                new ClearCommand(),
                new BroadcastCommand(),
                new BackCommand(),
                new BanCommand(),
                new UnbanCommand()
        );
    }

    @Override
    public void onDisable() {
        MessageFile.saveMessages();

        instance = null;
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

    public static Tools getInstance() {
        return instance;
    }
}
