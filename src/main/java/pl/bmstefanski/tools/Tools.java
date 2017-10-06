package pl.bmstefanski.tools;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import pl.bmstefanski.tools.command.ToolsCommand;
import pl.bmstefanski.tools.command.admin.DisableCommand;
import pl.bmstefanski.tools.command.admin.ReloadCommand;
import pl.bmstefanski.tools.command.normal.GamemodeCommand;
import pl.bmstefanski.tools.command.normal.ListCommand;
import pl.bmstefanski.tools.database.MySQL;
import pl.bmstefanski.tools.io.Files;
import pl.bmstefanski.tools.io.MessageFile;
import pl.bmstefanski.tools.listener.PlayerCommandPreprocess;
import pl.bmstefanski.tools.listener.PlayerJoin;
import pl.bmstefanski.tools.listener.PlayerLogin;
import pl.bmstefanski.tools.listener.PlayerQuit;
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

        databaseManager.establishConnection();
        MessageFile.loadMessages();
        tryToCheck();
        registerListeners();
        registerCommands();
        loadDatabases();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.YELLOW + "Disabling" + ChatColor.GRAY + " (" + getDescription().getName() + ")");

        MessageFile.saveMessages();
        saveDatabases();

        instance = null;
    }

    public void registerCommands() {
        CommandManager.registerCommand(new ToolsCommand());
        CommandManager.registerCommand(new ListCommand());
        CommandManager.registerCommand(new GamemodeCommand());
        CommandManager.registerCommand(new ReloadCommand());
        CommandManager.registerCommand(new DisableCommand());
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerCommandPreprocess(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerLogin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
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
