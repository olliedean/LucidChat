package cool.ollie.lucidchat;

import cool.ollie.lucidchat.managers.UserManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class LucidChatPlaceholders extends PlaceholderExpansion {
    private final LucidChat plugin;

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
        if(params.equalsIgnoreCase("tag")) {
            UserManager userManager = new UserManager();
            String tag = userManager.getPlayerTag(player.getPlayer());
            if(tag == null) {
                return "";
            }
            return userManager.getPlayerTag(player.getPlayer());
        }
        return null;
    }
}
