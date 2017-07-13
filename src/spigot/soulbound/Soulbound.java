package spigot.soulbound;

/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Soulbound
{
    private Main main;
    public boolean isSoulbound;

    public Soulbound(Main main)
    {
        this.main = main;
    }

    public boolean hasSoulbound(ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        for (String l : lore)
        {
            if (l.equals(this.getMain().getConfig().getString("Soulbound.lore")))
                this.isSoulbound = true; else this.isSoulbound = false;
        }
        return this.isSoulbound;
    }

    public Main getMain()
    {
        return main;
    }
}
