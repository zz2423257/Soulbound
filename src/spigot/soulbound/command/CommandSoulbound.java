package spigot.soulbound.command;
/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import spigot.soulbound.Main;

public class CommandSoulbound implements CommandExecutor
{
    private Main main;

    public CommandSoulbound(Main main)
    {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String lbl, String[] args)
    {
        return true;
    }
}
