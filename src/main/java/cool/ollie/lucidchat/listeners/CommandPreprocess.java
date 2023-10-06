package cool.ollie.lucidchat.listeners;

import cool.ollie.lucidchat.LucidChat;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class CommandPreprocess implements Listener {
    LucidChat plugin = LucidChat.getPlugin(LucidChat.class);
    @EventHandler
    public void onCommandPreprocess(org.bukkit.event.player.PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        Configuration config = plugin.getConfig();
        List<?> commands = config.getList("override-commands.commands");
        int x = commands.size();
        for(int i = 0; i < x; i++) {
            String command = commands.get(i).toString();
            if(message.startsWith("/" + command)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(config.getString("override-commands.message").replace("&", "§"));
            }
        }



    }
}
