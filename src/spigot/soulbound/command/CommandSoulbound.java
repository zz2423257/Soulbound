package spigot.soulbound.command;

/*
 * Soulbound created by MisterFantasy on 13-7-2017
 */

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import spigot.soulbound.Main;
import spigot.soulbound.Soulbound;
import spigot.soulbound.utils.SoulboundUtil;

import java.util.ArrayList;
import java.util.List;

public class CommandSoulbound implements CommandExecutor {

    private Main main;
    private Soulbound soulbound;
    private SoulboundUtil soulboundUtil;

    public CommandSoulbound(Main main) {
        this.main = main;
        this.soulbound = new Soulbound(this.main);
        this.soulboundUtil = new SoulboundUtil(this.main);
    }

    @Deprecated
    @Override
    public boolean onCommand(CommandSender sender, Command command, String lbl, String[] args) {
        if (command.getName().equalsIgnoreCase("soulbound")) {

            if (!(sender instanceof Player)) {
                Bukkit.getLogger().info("The command 'soulbound' can not be executed by console.");
                return true;
            }

            Player player = (Player) sender;
            if (!player.hasPermission("soulbound")) {
                player.sendMessage(this.getSoulboundUtil().trans(
                        this.getMain().getConfig().getString("messages.no-permission")));
                return true;
            }

            if (args.length == 0) {
                this.sendHelppage(player);
                return true;
            }

            ItemStack handItem = player.getInventory().getItemInHand();

            if (args[0].equalsIgnoreCase("apply")) {
                if (!player.hasPermission(this.getMain().getConfig().getString("permissions.apply"))) {
                    player.sendMessage(this.getSoulboundUtil().trans(
                            this.getMain().getConfig().getString("messages.no-permission")));
                    return true;
                }

                if (!this.getSoulbound().isItemValid(handItem)) {
                    player.sendMessage(this.getSoulboundUtil().trans(
                            this.getMain().getConfig().getString("messages.invalid-item")));
                    return true;
                }

                if (this.getSoulbound().isItemSoulbound(handItem)) {
                    player.sendMessage(this.getSoulboundUtil().trans(
                            this.getMain().getConfig().getString("messages.item-already-soulbound")));
                    return true;
                }

                this.getSoulbound().apply(handItem);
                player.sendMessage(this.getSoulboundUtil().trans(
                        this.getMain().getConfig().getString("messages.success.apply")));
                return true;
            }

            if (args[0].equalsIgnoreCase("remove")) {
                if (!player.hasPermission(this.getMain().getConfig().getString("permissions.remove"))) {
                    player.sendMessage(this.getSoulboundUtil().trans(
                            this.getMain().getConfig().getString("messages.no-permission")));
                    return true;
                }

                if (!this.getSoulbound().isItemValid(handItem)) {
                    player.sendMessage(this.getSoulboundUtil().trans(
                            this.getMain().getConfig().getString("messages.invalid-item")));
                    return true;
                }

                if (!this.getSoulbound().isItemSoulbound(handItem)) {
                    player.sendMessage(this.getSoulboundUtil().trans(
                            this.getMain().getConfig().getString("messages.item-no-soulbound")));
                    return true;
                }

                this.getSoulbound().remove(handItem);
                player.sendMessage(this.getSoulboundUtil().trans(
                        this.getMain().getConfig().getString("messages.success.remove")));
                player.getInventory().addItem(this.getSoulbound().getSoulboundItem(1));
                return true;
            }

            if (args[0].equalsIgnoreCase("give")) {
                if (args.length == 1) {
                    this.sendHelppage(player);
                    return true;
                }

                int amount = 1;

                if (args.length == 3)
                    amount = this.parseInt(args[2]);

                if (args[1].equalsIgnoreCase("all") ||
                        args[1].equalsIgnoreCase("*")) {
                    if (!player.hasPermission(this.getMain().getConfig().getString("permissions.give-all"))) {
                        player.sendMessage(this.getSoulboundUtil().trans(
                                this.getMain().getConfig().getString("messages.no-permission")));
                        return true;
                    }

                    for (Player each : Bukkit.getOnlinePlayers()) {
                        each.getInventory().addItem(this.getSoulbound().getSoulboundItem(
                                amount));
                        each.sendMessage(this.getSoulboundUtil().trans(this.getMain().getConfig()
                                .getString("messages.give-all.to").replace("%amount%", String.valueOf(amount))
                                .replace("%player%", player.getName())));
                    }
                    player.sendMessage(this.getSoulboundUtil().trans(this.getMain().getConfig()
                            .getString("messages.give-all.from").replace("%amount%",
                                    String.valueOf(amount))));
                    return true;
                }

                if (!player.hasPermission(this.getMain().getConfig().getString("permissions.give-other"))) {
                    player.sendMessage(this.getSoulboundUtil().trans(
                            this.getMain().getConfig().getString("messages.no-permission")));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(this.getSoulboundUtil().trans(this.getMain().getConfig()
                            .getString("messages.invalid-player")));
                    return true;
                }

                target.getInventory().addItem(this.getSoulbound().getSoulboundItem(
                        amount));
                target.sendMessage(this.getSoulboundUtil().trans(this.getMain().getConfig()
                        .getString("messages.give-other.to").replace("%amount%", String.valueOf(amount))
                        .replace("%player%", player.getName())));
                player.sendMessage(this.getSoulboundUtil().trans(this.getMain().getConfig()
                        .getString("messages.give-other.from").replace("%amount%",
                                String.valueOf(amount))
                        .replace("%player%", target.getName())));
                return true;
            }
            if (args[0].equalsIgnoreCase("reload"))
            {
                if (!player.hasPermission(this.getMain().getConfig().getString("permissions.reload"))) {
                    player.sendMessage(this.getSoulboundUtil().trans(
                            this.getMain().getConfig().getString("messages.no-permission")));
                    return true;
                }

                this.getMain().reloadConfig();
                player.sendMessage(this.getSoulboundUtil().trans(
                        this.getMain().getConfig().getString("messages.config-reloaded")));
            }
        }
        return true;
    }

    private void sendHelppage(Player player) {
        List<String> list = new ArrayList<String>();
        list.add("&8&m--------------------------------------------------");
        list.add(" &6Soulbound &eapply &7| &eApplies the &6Soulbound Enchantment &efrom an item.");
        list.add(" &6Soulbound &eremove &7| &eRemoves the &6Soulbound Enchantment &efrom an item.");
        list.add(" &6Soulbound &ereload &7| &eReload the &6config.yml&e in-game.");
        list.add(" &6Soulbound &egive <(all | *) | player> (amount) &7| " +
                "&eGives all or one player a specified amount of the &6Soulbound Enchantment&e.");
        list.add("&8&m--------------------------------------------------");
        for (String l : list)
            player.sendMessage(this.getSoulboundUtil().trans(l));
    }

    private int parseInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            Bukkit.getLogger().warning("The amount must be numeric.");
        }
        return Integer.parseInt(s);
    }

    private Main getMain() {
        return main;
    }

    private Soulbound getSoulbound() {
        return soulbound;
    }

    private SoulboundUtil getSoulboundUtil() {
        return soulboundUtil;
    }
}
