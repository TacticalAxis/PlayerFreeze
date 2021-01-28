package io.github.tacticalaxis.playerfreeze;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@SuppressWarnings("NullableProblems")
public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        ConfigurationManager.getInstance().reloadConfiguration();
        ConfigurationManager.getInstance().setupConfiguration();
        commandSender.sendMessage(ChatColor.GREEN + "Player freeze plugin has been reloaded!");
        return true;
    }
}
