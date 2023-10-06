package cool.ollie.lucidchat.events;

import cool.ollie.lucidchat.LucidChat;
import cool.ollie.lucidchat.managers.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.Logger;

public class PlayerJoinLeaveEvents implements Listener {
    LucidChat plugin = LucidChat.getPlugin(LucidChat.class);
    Logger log = Logger.getLogger("Minecraft");

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UserManager userManager = new UserManager();
        try {
            userManager.initPlayer(player);
        } catch (Exception e) {
            log.info(String.format("[%s] - Error initialising player file for %s", plugin.getDescription().getName(), player.getName()));
        }
    }
}
