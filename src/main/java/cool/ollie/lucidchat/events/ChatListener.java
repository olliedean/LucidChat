package cool.ollie.lucidchat.events;

import cool.ollie.lucidchat.LucidChat;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    LucidChat plugin = LucidChat.getPlugin(LucidChat.class);
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        String format = plugin.getConfig().getString("format");
        if(plugin.isPAPIEnabled) {
            format = PlaceholderAPI.setPlaceholders(player, format);
            // reformatting the message incase any placeholders hold placeholders
            format = PlaceholderAPI.setPlaceholders(player, format);
        }
        format = format.replace("%", "%%");
        format = format.replace("{player_name}", player.getName());
        format = format.replace("&", "§");
        if(!player.hasPermission("lucidchat.color")) {
            message = message.replace("§", "&");
        } else {
            message = message.replace("&", "§");
        }
        format = format.replace("{message}", message);
        event.setFormat(format);
    }
}
