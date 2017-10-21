package pl.bmstefanski.tools.command.admin;

import org.bukkit.command.CommandSender;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.util.Utils;

import java.util.Collections;

public class DisableCommand extends CommandImpl {

    public DisableCommand() {
        super("tools-disable", "disable plugin", "/tools-disable", "disable", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {
        Tools.getInstance().getServer().getPluginManager().disablePlugin(Tools.getInstance());
        Utils.sendMessage(commandSender, Messages.SUCCESSFULLY_DISABLED);
    }
}
