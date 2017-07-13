package spigot.soulbound.events;

/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import spigot.soulbound.Main;
import spigot.soulbound.Soulbound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerDeath implements Listener {
    public static HashMap<UUID, List<ItemStack>> map;
    private Main main;
    private Soulbound soulbound;

    public PlayerDeath(Main main) {
        this.main = main;
        this.soulbound = new Soulbound(this.main);
        map = new HashMap<UUID, List<ItemStack>>();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = (Player) e.getEntity();
        List<ItemStack> items = new ArrayList<ItemStack>();

        for (ItemStack dropped : new ArrayList<>(e.getDrops())) {
            if (this.getSoulbound().isItemSoulbound(dropped)) {
                this.getSoulbound().remove(dropped);

                items.add(dropped);
                e.getDrops().remove(dropped);
            }
        }

        if (!map.containsKey(player.getUniqueId()))
            map.put(player.getUniqueId(), items);

        Bukkit.getLogger().info(this.map.get(player.getUniqueId()).size() + "");
    }

    private Soulbound getSoulbound() {
        return soulbound;
    }

    private Main getMain() {
        return main;
    }
}