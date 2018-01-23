package pl.bmstefanski.tools.command;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;

public class HatCommand implements MessageUtils {

    private final Tools plugin;
    private final Messages messages;

    public HatCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "hat",
            description = "hat command",
            userOnly = true,
            permission = "hat"
    )
    public void hat(CommandSender sender, CommandContext context) {

        Player player = (Player) sender;
        ItemStack item = player.getItemInHand().clone();
        item.setAmount(1);
        PlayerInventory playerInventory = player.getInventory();

        if (playerInventory.getItemInMainHand().getType().equals(Material.AIR)) {
            sendMessage(player, messages.getHatCantBeAir());
            return;
        }


        if (playerInventory.getHelmet() == null) {
            playerInventory.setHelmet(item);
            playerInventory.removeItem(item);
            player.updateInventory();
            sendMessage(player, messages.getHat());
            return;
        }

        playerInventory.addItem(playerInventory.getHelmet());
        playerInventory.setHelmet(item);
        playerInventory.removeItem(item);
        player.updateInventory();
        sendMessage(player, messages.getHat());

    }
}
