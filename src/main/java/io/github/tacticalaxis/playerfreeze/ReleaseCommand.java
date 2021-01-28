package io.github.tacticalaxis.playerfreeze;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class ReleaseCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length > 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().equals(args[0].toLowerCase())) {
                    PlayerFreeze.frozen.remove(player);
                }
            }
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigurationManager.getInstance().getMainConfiguration().getString("staff-player-release-message").replace("%username%", args[0])));
        return true;
    }
}