package spigot.soulbound;

/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Soulbound
{
    private Main main;
    private String soulboundLore;

    private boolean isSoulbound;
    private boolean isValid;

    public Soulbound(Main main)
    {
        this.main = main;
        this.isSoulbound = false;
        this.isValid = false;
        this.soulboundLore = this.getMain().getConfig().getString("Soulbound.lore");
    }

    private boolean isItemValid(ItemStack itemStack)
    {
        Material material = itemStack.getType();
        List<String> whitelist = this.getMain().getConfig().getStringList("Soulbound.item-whitelist");
        if (whitelist.contains(String.valueOf(material))) {
            isValid = true;
            this.getMain().throwException("Item is valid.");
        }
        return isValid;
    }

    private boolean hasSoulbound(ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (this.isItemValid(itemStack))
        {
            List<String> lore;

            if (itemMeta.hasLore())
            {
                lore = itemMeta.getLore();
                if (lore.contains(this.soulboundLore))
                    this.isSoulbound = true;
            }
        }
        return isSoulbound;
    }

    public void applySoulbound(Player player, ItemStack itemStack)
    {
        if (hasSoulbound(itemStack))
        {
            this.getMain().throwException("This item already has soulbound.");
            return;
        }


        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore;

        if (itemMeta.hasLore())
            lore = itemMeta.getLore();
        else
            lore = new ArrayList<String>();

        lore.add(this.soulboundLore);

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        player.setItemInHand(itemStack);
        player.updateInventory();

        this.getMain().throwException("Soulbound has been applied.");
    }

    public Main getMain()
    {
        return main;
    }
}
