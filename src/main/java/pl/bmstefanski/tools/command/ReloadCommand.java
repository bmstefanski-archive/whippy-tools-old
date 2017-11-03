package pl.bmstefanski.tools.command;

import org.bukkit.command.CommandSender;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.io.Files;
import pl.bmstefanski.tools.io.MessageFile;
import pl.bmstefanski.tools.manager.DatabaseManager;
import pl.bmstefanski.tools.util.Utils;

import java.io.IOException;
import java.util.Collections;

public class ReloadCommand extends CommandImpl {

    public ReloadCommand() {
        super("tools-reload", "reload", "/tools-reload", "reload", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {
        long startedTime = System.currentTimeMillis();

        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Tools tools = Tools.getInstance();

        tryToCheck();

        MessageFile.loadMessages();
        MessageFile.saveMessages();

        tools.loadDatabases();
        tools.saveDatabases();

        databaseManager.establishConnection();

        long elapsedTime = (System.currentTimeMillis() - startedTime);
        float elapsedTimeSecond = elapsedTime / 1000F;

        Utils.sendMessage(commandSender, Messages.SUCCESSFULLY_RELOADED.replace("%time%", elapsedTimeSecond + ""));
    }

    private void tryToCheck() {
        try {
            Files.check();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
