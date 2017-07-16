package spigot.soulbound;

/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import spigot.soulbound.utils.SoulboundUtil;

import java.util.ArrayList;
import java.util.List;

public class Soulbound {
    private Main main;
    private String soulboundLore;
    private SoulboundUtil soulboundUtil;

    public Soulbound(Main main) {
        this.main = main;
        this.soulboundUtil = new SoulboundUtil(this.main);
        this.soulboundLore = this.soulboundUtil.trans(
                this.getMain().getConfig().getString("Soulbound.lore"));
    }

    public boolean isItemValid(ItemStack itemStack) {
        if (this.getMain().getConfig().getBoolean("Soulbound.disable-whitelist"))
            return true;
        else
        {
            Material material = itemStack.getType();
            List<String> whitelist = this.getMain().getConfig().getStringList("Soulbound.item-whitelist");
            if (whitelist.contains(String.valueOf(material)))
                return true;
        }
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

    public void apply(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore;

        if (itemMeta.hasLore())
            lore = itemMeta.getLore();
        else
            lore = new ArrayList<String>();

        lore.add(this.getSoulboundUtil().trans(this.soulboundLore));

        for (String l : lore)
            this.getSoulboundUtil().trans(l);

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    public boolean remove(ItemStack itemStack) {
        if (this.isItemSoulbound(itemStack)) {
            ItemMeta itemMeta = itemStack.getItemMeta();

            List<String> newlore = new ArrayList<String>();
            List<String> lore = itemMeta.getLore();

            for (String l : lore) {
                if (!l.contains(this.soulboundLore))
                    newlore.add(l);
            }

            itemMeta.setLore(newlore);
            itemStack.setItemMeta(itemMeta);
            return true;
        }
        return false;
    }

    public boolean isSimilar(ItemStack itemStack)
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
        soulboundMeta.setDisplayName(this.getSoulboundUtil().trans(displayname));
        List<String> newlore = new ArrayList<String>();
        List<String> lore = this.getMain().getConfig().getStringList("Soulbound.item.lore");
        for (String l : lore)
            newlore.add(this.getSoulboundUtil().trans(l));
        soulboundMeta.setLore(newlore);

        soulboundItem.setItemMeta(soulboundMeta);

        return soulboundItem;
    }

    private SoulboundUtil getSoulboundUtil() {
        return soulboundUtil;
    }

    private Main getMain()
    {
        return main;
    }
}
