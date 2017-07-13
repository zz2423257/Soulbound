package spigot.soulbound.events;

/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import spigot.soulbound.Main;
import spigot.soulbound.Soulbound;
import spigot.soulbound.utils.SoulboundUtil;

public class InventoryClick implements Listener
{
    private Main main;
    private Soulbound soulbound;
    private SoulboundUtil soulboundUtil;

    public InventoryClick(Main main)
    {
        this.main = main;
        this.soulbound = new Soulbound(this.main);
        this.soulboundUtil = new SoulboundUtil(this.main);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        ItemStack item = e.getCurrentItem();
        ItemStack cursor = e.getCursor();

        if (item == null || cursor == null)
            return;

        Player player = (Player) e.getWhoClicked();

        if (!this.getSoulbound().isSimilar(cursor))
            return;

        if (!this.getSoulbound().isItemValid(item))
            return;

        if (this.getSoulbound().isItemSoulbound(item))
            return;

        if (player.getGameMode() == GameMode.CREATIVE) {
            player.sendMessage(this.getSoulboundUtil().trans(
                    this.getMain().getConfig().getString("messages.wrong-gamemode")));
            return;
        }

        this.getSoulbound().apply(item);
        if (cursor.getAmount() > 1)
            cursor.setAmount(cursor.getAmount() - 1);
        else
            cursor.setAmount(0);
    }

    private SoulboundUtil getSoulboundUtil() {
        return soulboundUtil;
    }

    private Main getMain()
    {
        return main;
    }

    private Soulbound getSoulbound()
    {
        return soulbound;
    }
}
