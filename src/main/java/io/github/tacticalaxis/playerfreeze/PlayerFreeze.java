package io.github.tacticalaxis.playerfreeze;

import io.github.tacticalaxis.playerfreeze.image.MessageRunnable;
import io.github.tacticalaxis.playerfreeze.image.PictureUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class PlayerFreeze extends JavaPlugin implements Listener {

    public static HashMap<Player, String> frozen;
    private static PlayerFreeze instance;
    private PictureUtil pictureUtil;

    public static PlayerFreeze getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        ConfigurationManager.getInstance().setupConfiguration();
        pictureUtil = new PictureUtil();
        frozen = new HashMap<>();
        getCommand("ss").setExecutor(new FreezeCommand());
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @EventHandler
    public void move(PlayerMoveEvent event) {
        if (frozen.containsKey(event.getPlayer())) {
            event.setTo(event.getFrom());
            sendImage(event.getPlayer());
        }
    }

    @EventHandler
    public void leave(PlayerQuitEvent event) {
        if (frozen.containsKey(event.getPlayer())) {
            for (Player player: Bukkit.getOnlinePlayers()) {
                if (frozen.containsValue(player.getName())) {
                    BaseComponent[] hoverEventComponents = new BaseComponent[]{new TextComponent(net.md_5.bungee.api.ChatColor.YELLOW + "Click to ban!")};
                    TextComponent message = new TextComponent(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', ConfigurationManager.getInstance().getMainConfiguration().getString("staff-player-disconnect-message").replace("%username%", event.getPlayer().getName())));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + ConfigurationManager.getInstance().getMainConfiguration().getString("staff-click-command").replace("%username%", event.getPlayer().getName())));
                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverEventComponents));
                    player.spigot().sendMessage(message);
                    return;
                }
            }
            if (frozen.containsValue("Console")) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigurationManager.getInstance().getMainConfiguration().getString("staff-player-disconnect-message".replace("%username%", event.getPlayer().getName()))));
            }
        }
        frozen.remove(event.getPlayer());
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent event) {
        if (frozen.containsKey(event.getPlayer())) {
            event.setCancelled(true);
            sendImage(event.getPlayer());
        }
    }

    @EventHandler
    public void attack(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (frozen.containsKey(player)) {
                event.setCancelled(true);
                sendImage(player);
            }
        }
    }

    @EventHandler
    public void attack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (frozen.containsKey(player)) {
                event.setCancelled(true);
                sendImage(player);
            }
        }
    }


    private void sendImage(Player player) {
        final MessageRunnable messageRunnable = new MessageRunnable(player);
        messageRunnable.runTask(this);
    }

    public PictureUtil getPictureUtil() {
        return this.pictureUtil;
    }
}
