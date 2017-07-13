package spigot.soulbound;

/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.soulbound.command.CommandSoulbound;
import spigot.soulbound.events.InventoryClick;
import spigot.soulbound.events.PlayerDeath;
import spigot.soulbound.events.PlayerRespawn;

public class Main extends JavaPlugin {
    private Soulbound soulbound;

    public void onEnable() {
        this.registerCommand();
        this.registerEvents();
        this.saveDefaultConfig();
    }

    private void registerCommand() {
        this.getCommand("soulbound").setExecutor(new CommandSoulbound(this));
    }

    private void registerEvents() {
        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new InventoryClick(this), this);
        manager.registerEvents(new PlayerDeath(this), this);
        manager.registerEvents(new PlayerRespawn(this), this);
    }
}
