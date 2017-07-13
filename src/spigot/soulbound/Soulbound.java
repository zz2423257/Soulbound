package spigot.soulbound;

/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Soulbound
{
    private Main main;
    private String soulboundLore;

    private HashMap<ItemStack, Boolean> isSoulbound;
    private HashMap<ItemStack, Boolean> isValid;

    public Soulbound(Main main)
    {
        this.main = main;
        this.soulboundLore = this.getMain().trans(
                this.getMain().getConfig().getString("Soulbound.lore"));
        this.isSoulbound = new HashMap<ItemStack, Boolean>();
        this.isValid = new HashMap<ItemStack, Boolean>();
    }

    public boolean isItemValid(ItemStack itemStack)
    {
        Material material = itemStack.getType();
        List<String> whitelist = this.getMain().getConfig().getStringList("Soulbound.item-whitelist");
        if (whitelist.contains(String.valueOf(material)))
            this.isValid.put(itemStack, true);

        if (this.isValid.get(itemStack) == null)
            return false;
        else
            return this.isValid.get(itemStack);
    }

    public boolean hasSoulbound(ItemStack itemStack)
    {
        if (itemStack.getType() != Material.AIR)
        {
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta.hasLore())
            {
                List<String> lore = itemMeta.getLore();
                for (String l : lore)
                {
                    if (l.contains(this.soulboundLore))
                        this.isSoulbound.put(itemStack, true);
                }
            }
        }

        if (this.isSoulbound.get(itemStack) == null)
            return false;
        else
            return this.isSoulbound.get(itemStack);
    }

    public void applySoulbound(Player player, ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore;

        if (itemMeta.hasLore())
            lore = itemMeta.getLore();
        else
            lore = new ArrayList<String>();

        lore.add(this.getMain().trans(this.soulboundLore));

        for (String l : lore)
            this.getMain().trans(l);

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    public Main getMain()
    {
        return main;
    }
}
