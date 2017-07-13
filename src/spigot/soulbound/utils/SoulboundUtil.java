package spigot.soulbound.utils;
/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.ChatColor;
import spigot.soulbound.Main;

public class SoulboundUtil {
    private Main main;

    public SoulboundUtil(Main main) {
        this.main = main;
    }

    public String trans(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    private Main getMain() {
        return main;
    }
}
