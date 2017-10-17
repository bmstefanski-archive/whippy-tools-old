package pl.bmstefanski.tools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import pl.bmstefanski.tools.command.ToolsCommand;
import pl.bmstefanski.tools.command.admin.DisableCommand;
import pl.bmstefanski.tools.command.admin.ReloadCommand;
import pl.bmstefanski.tools.command.normal.*;
import pl.bmstefanski.tools.database.MySQL;
import pl.bmstefanski.tools.io.Files;
import pl.bmstefanski.tools.io.MessageFile;
import pl.bmstefanski.tools.listener.*;
import pl.bmstefanski.tools.manager.CommandManager;
import pl.bmstefanski.tools.manager.DatabaseManager;

import java.io.IOException;

public final class Tools extends JavaPlugin {

    private static Tools instance;
    private DatabaseManager databaseManager;
    private MySQL mySQL;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.databaseManager = DatabaseManager.getInstance();
        this.mySQL = MySQL.getInstance();

        getLogger().info(ChatColor.YELLOW + "Enabling " + ChatColor.GRAY + " (" + getDescription().getName() + ")");

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
        getLogger().info(ChatColor.YELLOW + "Disabling" + ChatColor.GRAY + " (" + getDescription().getName() + ")");

        MessageFile.saveMessages();
        saveDatabases();

        instance = null;
    }

    private void registerCommands() {
        CommandManager.registerCommand(new ToolsCommand());
        CommandManager.registerCommand(new ListCommand());
        CommandManager.registerCommand(new GamemodeCommand());
        CommandManager.registerCommand(new ReloadCommand());
        CommandManager.registerCommand(new DisableCommand());
        CommandManager.registerCommand(new FeedCommand());
        CommandManager.registerCommand(new HealCommand());
        CommandManager.registerCommand(new FlyCommand());
        CommandManager.registerCommand(new SetSpawnCommand());
        CommandManager.registerCommand(new SpawnCommand());
        CommandManager.registerCommand(new GodCommand());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocess(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLogin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamage(), this);

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
