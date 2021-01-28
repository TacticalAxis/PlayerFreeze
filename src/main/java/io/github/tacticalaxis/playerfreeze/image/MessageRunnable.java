package io.github.tacticalaxis.playerfreeze.image;

import io.github.tacticalaxis.playerfreeze.ConfigurationManager;
import io.github.tacticalaxis.playerfreeze.PlayerFreeze;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MessageRunnable extends BukkitRunnable {
    private final Player player;

    public MessageRunnable(final Player player) {
        this.player = player;
    }

    public void run() {
        this.sendImage();
    }

    private MessageUtil getMessage() {
        return PlayerFreeze.getInstance().getPictureUtil().createPictureMessage(ConfigurationManager.getInstance().getMainConfiguration().getStringList("messages"));
    }

    private void sendImage() {
        final MessageUtil pictureMessage = this.getMessage();
        if (pictureMessage == null) {
            return;
        }
        PlayerFreeze.getInstance().getPictureUtil().clearChat(player);
        PlayerFreeze.getInstance().getPictureUtil().sendOutPictureMessage(pictureMessage);
    }
}