package spigot.soulbound;

/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        this.soulboundLore = this.getMain().getConfig().getString("Soulbound.lore");
    }

    private boolean hasSoulbound(ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        for (String l : lore)
        {

            if (l.equals(this.soulboundLore))
                isSoulbound = true;
            else
                isSoulbound = false;
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

        if (isItemValid(itemStack))
        {
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = itemMeta.getLore();
            lore.add(this.getMain().trans(this.soulboundLore));

            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            player.setItemInHand(itemStack);

            this.getMain().throwException("Soulbound has been applied.");
        }
    }

    private boolean isItemValid(ItemStack itemStack)
    {
        Material material = itemStack.getType();
        List<String> whitelist = this.getMain().getConfig().getStringList("Soulbound.item-whitelist");
        if (whitelist.contains(String.valueOf(material)))
        {
            isValid = true;
            this.getMain().throwException("Item is valid.");
        }
        else
        {
            isValid = false;
            this.getMain().throwException("Item is invalid.");
        }
        return isValid;
    }

    public Main getMain()
    {
        return main;
    }
}
