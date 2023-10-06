package cool.ollie.lucidchat;

import cool.ollie.lucidchat.commands.LCCommand;
import cool.ollie.lucidchat.events.ChatListener;
import cool.ollie.lucidchat.events.PlayerJoinLeaveEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public final class LucidChat extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    public boolean isPAPIEnabled = false;

    @Override
    public void onEnable() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            isPAPIEnabled = true;
            log.info(String.format("[%s] - PlaceholderAPI found, enabling placeholders!", getDescription().getName()));
            log.info(String.format("[%s] - Enabling custom placeholders.", getDescription().getName()));
            new LucidChatPlaceholders(this).register();
        } else {
            log.info(String.format("[%s] - PlaceholderAPI not found, disabling placeholders!", getDescription().getName()));
        }

        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinLeaveEvents(), this);
        this.getCommand("lucidchat").setExecutor(new LCCommand());

        // auto announcer
        int delay = getConfig().getInt("auto-announcer.interval");
        if(delay > 0) {
            if(getConfig().getList("auto-announcer.messages").size() == 0) {
                log.info(String.format("[%s] - Auto announcer is enabled but no messages are set!", getDescription().getName()));
                log.info(String.format("[%s] - Disabling auto announcer.", getDescription().getName()));
                getConfig().set("auto-announcer.interval", 0);
                saveConfig();
                return;
            }
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    List messages = getConfig().getList("auto-announcer.messages");
                    int random = new Random().nextInt(messages.size());
                    String message = messages.get(random).toString();
                    message = getConfig().getString("auto-announcer.prefix") + message;
                    message = message.replace("&", "§");
                    // still need to add support for placeholders
                    Bukkit.broadcastMessage(message);
                }
            }, 0, delay * 20L);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
