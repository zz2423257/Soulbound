package spigot.soulbound.events;
/*
 * Soulbound created by MisterFantasy on 24-7-2017
 */

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import spigot.soulbound.Main;
import spigot.soulbound.Soulbound;
import spigot.soulbound.command.CommandSoulbound;
import spigot.soulbound.utils.SoulboundUtil;

public class PlayerInteract implements Listener {
    private final Main main;
    private final SoulboundUtil soulboundUtil;
    private final Soulbound soulbound;

    public PlayerInteract(Main main) {
        this.main = main;
        this.soulboundUtil = new SoulboundUtil(this.main);
        this.soulbound = new Soulbound(this.main);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (CommandSoulbound.list.contains(player.getUniqueId())) {
                ItemStack handItem = player.getInventory().getItemInHand();
                if (handItem.getType() == Material.AIR) {
                    player.sendMessage(this.getSoulboundUtil().trans(
                            this.getMain().getConfig().getString("messages.invalid-item")));
                    return;
                }

                if (!this.getSoulbound().isItemValid(handItem)) {
                    player.sendMessage(this.getSoulboundUtil().trans(
                            this.getMain().getConfig().getString("messages.invalid-item")));
                    return;
                }

                if (this.getSoulbound().isItemSoulbound(handItem)) {
                    player.sendMessage(this.getSoulboundUtil().trans(
                            this.getMain().getConfig().getString("messages.item-already-soulbound")));
                    return;
                }

                this.getSoulbound().apply(handItem);
                player.sendMessage(this.getSoulboundUtil().trans(
                        this.getMain().getConfig().getString("messages.success.apply")));
                CommandSoulbound.list.remove(player.getUniqueId());
            }
        }
    }

    public Main getMain() {
        return main;
    }

    public SoulboundUtil getSoulboundUtil() {
        return soulboundUtil;
    }

    public Soulbound getSoulbound() {
        return soulbound;
    }
}
