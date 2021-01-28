package io.github.tacticalaxis.playerfreeze;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class FreezeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        String staff = "Console";
        if (sender instanceof Player) {
            Player player = (Player) sender;
            staff = player.getName();
        }
        if (args.length > 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().equals(args[0].toLowerCase())) {
                    PlayerFreeze.frozen.put(player, staff);
                }
            }
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigurationManager.getInstance().getMainConfiguration().getString("staff-player-freeze-message").replace("%username%", args[0])));
        return true;
    }
}
