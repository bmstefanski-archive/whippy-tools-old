package pl.bmstefanski.tools.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.bmstefanski.commands.Arguments;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class HatCommand implements Messageable {

    private final Tools plugin;
    private final Messages messages;

    public HatCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "hat")
    @Permission("tools.command.hat")
    @GameOnly
    public void command(Arguments arguments) {

        Player player = (Player) arguments.getSender();

        PlayerInventory playerInventory = player.getInventory();
        ItemStack item = playerInventory.getItemInMainHand().clone();

        item.setAmount(1);

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
