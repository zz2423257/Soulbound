package spigot.soulbound;

/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.soulbound.command.CommandSoulbound;

public class Main extends JavaPlugin
{
    private Soulbound soulbound;

    public void onEnable()
    {
        this.registerCommand();
        this.registerEvents();
    }

    private void registerCommand()
    {
        this.getCommand("soulbound").setExecutor(new CommandSoulbound(this));
    }

    private void registerEvents()
    {
        PluginManager manager = this.getServer().getPluginManager();
        //manager.registerEvents();
    }


}