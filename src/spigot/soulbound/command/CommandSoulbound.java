package spigot.soulbound.command;

/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import spigot.soulbound.Main;
import spigot.soulbound.Soulbound;

public class CommandSoulbound implements CommandExecutor {
    private Main main;
    private Soulbound soulbound;

    public CommandSoulbound(Main main) {
        this.main = main;
        this.soulbound = new Soulbound(this.main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String lbl, String[] args) {
        if (command.getName().equalsIgnoreCase("soulbound")) {
            if (sender instanceof Player) {

                Player player = (Player) sender;
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                if (this.getSoulbound().isItemValid(itemStack)) {
                    if (!this.getSoulbound().hasSoulbound(itemStack)) {
                        this.getSoulbound().applySoulbound(player, itemStack);
                    }
                }
            }
        }
        return true;
    }

    public Main getMain() {
        return main;
    }

    public Soulbound getSoulbound() {
        return soulbound;
    }
}
