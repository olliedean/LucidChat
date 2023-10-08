package cool.ollie.lucidchat;

import cool.ollie.lucidchat.managers.TagManager;
import cool.ollie.lucidchat.managers.UserManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class LucidChatPlaceholders extends PlaceholderExpansion {
    LucidChat plugin;

    public LucidChatPlaceholders(LucidChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "olliedean";
    }

    @Override
    public String getIdentifier() {
        return "lucidchat";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        UserManager userManager = new UserManager();
        TagManager tagManager = plugin.tagManager;
        if(params.equalsIgnoreCase("tag")) {
            return tagManager.parseTag(player.getPlayer(), userManager.getPlayerTag(player.getPlayer()));
        }
        if(params.equalsIgnoreCase("chatcolor")) {
            return userManager.getPlayerChatColor(player.getPlayer());
        }
        if(params.equalsIgnoreCase("icon")) {
            return tagManager.parseIcon(player.getPlayer(), userManager.getPlayerIcon(player.getPlayer()));
        }
        return null;
    }
}
