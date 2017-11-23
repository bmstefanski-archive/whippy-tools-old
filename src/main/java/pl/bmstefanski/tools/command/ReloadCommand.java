package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.io.Files;
import pl.bmstefanski.tools.io.MessageFile;
import pl.bmstefanski.tools.manager.DatabaseManager;
import pl.bmstefanski.tools.util.MessageUtils;

import java.io.IOException;

public class ReloadCommand {

    private final DatabaseManager database = DatabaseManager.getInstance();
    private final Tools plugin = Tools.getInstance();

    @CommandInfo (
            name = "tools-reload",
            description = "reload command",
            permission = "reload",
            userOnly = true
    )

    public void reload(CommandSender commandSender, CommandContext context) {
        long startedTime = System.currentTimeMillis();

        tryToCheck();

        MessageFile.loadMessages();
        MessageFile.saveMessages();

        database.establishConnection();

        long elapsedTime = (System.currentTimeMillis() - startedTime);
        float elapsedTimeSecond = elapsedTime / 1000F;

        MessageUtils.sendMessage(commandSender, StringUtils.replace(Messages.SUCCESSFULLY_RELOADED, "%time%", elapsedTimeSecond + ""));
    }

    private void tryToCheck() {
        try {
            Files.check();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
