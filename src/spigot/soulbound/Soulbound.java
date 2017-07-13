package spigot.soulbound;

/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Soulbound {
    private Main main;
    private String soulboundLore;

    public Soulbound(Main main) {
        this.main = main;
        this.soulboundLore = this.getMain().trans(
                this.getMain().getConfig().getString("Soulbound.lore"));
    }

    public boolean isItemValid(ItemStack itemStack) {
        Material material = itemStack.getType();
        List<String> whitelist = this.getMain().getConfig().getStringList("Soulbound.item-whitelist");
        if (whitelist.contains(String.valueOf(material)))
            return true;

        return false;
    }

    public boolean isItemSoulbound(ItemStack itemStack) {
        if (itemStack.getType() != Material.AIR) {
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta.hasLore()) {
                List<String> lore = itemMeta.getLore();
                for (String l : lore) {
                    if (l.contains(this.soulboundLore))
                        return true;
                }
            }
        }

        return false;
    }

    public void apply(ItemStack itemStack)
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

    public boolean isSoulboundItem(ItemStack itemStack)
    {
        if (itemStack.isSimilar(this.getSoulboundItem(1)))
            return true;

        return false;
    }

    public ItemStack getSoulboundItem(int amount)
    {
        String material = this.getMain().getConfig().getString("Soulbound.item.material");
        short data = (short) this.getMain().getConfig().getInt("Soulbound.item.data");

        ItemStack soulboundItem = new ItemStack(Material.valueOf(material), amount, data);
        ItemMeta soulboundMeta = soulboundItem.getItemMeta();

        String displayname = this.getMain().getConfig().getString("Soulbound.item.displayname");
        soulboundMeta.setDisplayName(this.getMain().trans(displayname));
        List<String> newlore = new ArrayList<String>();
        List<String> lore = this.getMain().getConfig().getStringList("Soulbound.item.lore");
        for (String l : lore)
            newlore.add(this.getMain().trans(l));
        soulboundMeta.setLore(newlore);

        soulboundItem.setItemMeta(soulboundMeta);

        return soulboundItem;
    }

    public Main getMain()
    {
        return main;
    }
}
