package spigot.soulbound.events;

/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import spigot.soulbound.Main;

import java.util.ArrayList;

public class PlayerRespawn implements Listener {
    private Main main;
    private PlayerDeath playerDeath;

    public PlayerRespawn(Main main) {
        this.main = main;
        this.playerDeath = new PlayerDeath(this.main);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        if (this.getPlayerDeath().map.get(player.getUniqueId()) != null) {
            for (ItemStack itemStack : new ArrayList<>(this.getPlayerDeath().map.get(player.getUniqueId())))
                player.getInventory().addItem(itemStack);
        }
    }

    private PlayerDeath getPlayerDeath() {
        return playerDeath;
    }

    private Main getMain() {
        return main;
    }
}
