package spigot.soulbound.events;

/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.inventory.ItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import spigot.soulbound.Main;
import spigot.soulbound.Soulbound;

public class InventoryClick implements Listener
{
    private Main main;
    private Soulbound soulbound;

    public InventoryClick(Main main)
    {
        this.main = main;
        this.soulbound = new Soulbound(this.main);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        Inventory inventory = e.getClickedInventory();

        ItemStack item = e.getCurrentItem();
        ItemStack cursor = e.getCursor();

        if (item == null || cursor == null)
            return;



    }


    public Main getMain()
    {
        return main;
    }

    public Soulbound getSoulbound()
    {
        return soulbound;
    }
}
