package io.github.tacticalaxis.playerfreeze.image;

import io.github.tacticalaxis.playerfreeze.PlayerFreeze;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class PictureUtil {

    public PictureUtil() {
    }

    private BufferedImage getImage() {
        try {
            return ImageIO.read(getFinalImage());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MessageUtil createPictureMessage(final List<String> messages) {
        final BufferedImage image = this.getImage();
        if (image == null) {
            return null;
        }
        return getMessage(messages, image);
    }

    public void sendOutPictureMessage(final MessageUtil picture_message) {
        PlayerFreeze.getInstance().getServer().getOnlinePlayers().forEach(online_player -> {
            if (PlayerFreeze.frozen.containsKey(online_player)) {
                clearChat(online_player);
                picture_message.sendToPlayer(online_player);
            }
        });
    }

    public void clearChat(final Player player) {
        for (int i = 0; i < 20; ++i) {
            player.sendMessage("");
        }
    }

    public static MessageUtil getMessage(final List<String> messages, final BufferedImage image) {
        final int imageDimensions = 8;
        int count = 0;
        final MessageUtil messageUtil = new MessageUtil(image, imageDimensions, '\u2588');
        final String[] msg = new String[imageDimensions];
        for (final String message : messages) {
            if (count > msg.length) {
                break;
            }
            msg[count++] = ChatColor.translateAlternateColorCodes('&', message);
        }
        while (count < imageDimensions) {
            msg[count++] = "";
        }
        return messageUtil.appendText(msg);
    }

    public static File getFinalImage() {
        File image = new File(PlayerFreeze.getInstance().getDataFolder(), "errorimage.png");
        if (!image.exists()) {
            PlayerFreeze.getInstance().saveResource("errorimage.png", false);
        }
        return image;
    }
}