package pl.bmstefanski.tools;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.bmstefanski.tools.command.*;
import pl.bmstefanski.tools.command.ToolsCommand;
import pl.bmstefanski.tools.command.ReloadCommand;
import pl.bmstefanski.tools.command.basic.BukkitCommands;
import pl.bmstefanski.tools.command.basic.Commands;
import pl.bmstefanski.tools.database.MySQL;
import pl.bmstefanski.tools.io.Files;
import pl.bmstefanski.tools.io.MessageFile;
import pl.bmstefanski.tools.listener.*;
import pl.bmstefanski.tools.manager.DatabaseManager;

import java.io.IOException;

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

        databaseManager.establishConnection();
        MessageFile.loadMessages();
        tryToCheck();
        registerListeners();
        registerCommands();
        loadDatabases();
    }

    @Override
    public void onDisable() {
        MessageFile.saveMessages();
        saveDatabases();

        instance = null;
    }

    private void registerCommands() {
        Commands commands = new BukkitCommands(this);

        commands.registerCommandObject(new ToolsCommand());
        commands.registerCommandObject(new WhoisCommand());
        commands.registerCommandObject(new WorkbenchCommand());
        commands.registerCommandObject(new SpawnCommand());
        commands.registerCommandObject(new SetSpawnCommand());
        commands.registerCommandObject(new ReloadCommand());
        commands.registerCommandObject(new ListCommand());
        commands.registerCommandObject(new HealCommand());
        commands.registerCommandObject(new GodCommand());
        commands.registerCommandObject(new GamemodeCommand());
        commands.registerCommandObject(new FlyCommand());
        commands.registerCommandObject(new FeedCommand());
        commands.registerCommandObject(new EnderchestCommand());
        commands.registerCommandObject(new DisableCommand());
        commands.registerCommandObject(new ClearCommand());
        commands.registerCommandObject(new BroadcastCommand());
        commands.registerCommandObject(new BackCommand());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocess(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLogin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamage(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);
    }

    private void tryToCheck() {
        try {
            Files.check();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDatabases() {
        if (databaseManager.databaseType() == DatabaseManager.DatabaseType.MYSQL) {
            mySQL.checkTable();
            mySQL.loadData();
        } else if (databaseManager.databaseType() == DatabaseManager.DatabaseType.SQLITE) {
            getLogger().info("blank");
            // todo
        }
    }

    public void saveDatabases() {
        if (databaseManager.databaseType() == DatabaseManager.DatabaseType.MYSQL) {
            mySQL.saveData();
        } else if (databaseManager.databaseType() == DatabaseManager.DatabaseType.SQLITE) {
            getLogger().info("blank");
            // todo
        }
    }

    public static Tools getInstance() {
        return instance;
    }
}
