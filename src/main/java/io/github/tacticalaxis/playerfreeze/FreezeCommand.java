package io.github.tacticalaxis.playerfreeze;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        String staff = "Console";
        if (sender instanceof Player) {
            Player player = (Player) sender;
            staff = player.getName();
        }
        if (args.length >= 2) {
            boolean found = false;
            if (args[0].equalsIgnoreCase("-f")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getName().toLowerCase().equals(args[1].toLowerCase())) {
                        if (!isFrozen(player)) {
                            found = true;
                            PlayerFreeze.frozen.put(player, staff);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigurationManager.getInstance().getMainConfiguration().getString("staff-player-freeze-message").replace("%username%", args[1])));
                        } else {
                            found = true;
                            PlayerFreeze.frozen.remove(player);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigurationManager.getInstance().getMainConfiguration().getString("staff-player-release-message").replace("%username%", args[1])));
                        }
                    }
                }
            }
            if (!found) {
                sender.sendMessage(ChatColor.GOLD + args[1] + ChatColor.RED + " is not a valid player!");
            }
        } else if (args.length == 1){
            if (args[0].equalsIgnoreCase("reload")) {
                ConfigurationManager.getInstance().reloadConfiguration();
                ConfigurationManager.getInstance().setupConfiguration();
                sender.sendMessage(ChatColor.GREEN + "PlayerFreeze plugin has been reloaded!");
            }
        }
        return true;
    }

    private boolean isFrozen(Player player) {
        return PlayerFreeze.frozen.containsKey(player);
    }
}
