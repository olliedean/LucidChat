package cool.ollie.lucidchat.listeners;

import cool.ollie.lucidchat.LucidChat;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    LucidChat plugin = LucidChat.getPlugin(LucidChat.class);
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(plugin.isMuted() && !player.hasPermission("lucidchat.bypassmute")){
            player.sendMessage(plugin.getConfig().getString("chat-muted").replace("&", "§"));
            event.setCancelled(true);
            return;
        }
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
