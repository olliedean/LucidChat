package cool.ollie.lucidchat;

import cool.ollie.lucidchat.commands.*;
import cool.ollie.lucidchat.listeners.ChatListener;
import cool.ollie.lucidchat.listeners.CommandPreprocess;
import cool.ollie.lucidchat.listeners.PlayerJoinLeaveEvents;
import cool.ollie.lucidchat.managers.TagManager;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public final class LucidChat extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    public boolean isPAPIEnabled = false;
    public boolean isMuted = false;

    public TagManager tagManager;

    @Override
    public void onEnable() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            isPAPIEnabled = true;
            log.info(String.format("[%s] - PlaceholderAPI found, enabling placeholders!", getDescription().getName()));
            log.info(String.format("[%s] - Enabling custom placeholders.", getDescription().getName()));
            new LucidChatPlaceholders(this).register();
            new InventoryAPI(this).init();
            tagManager = new TagManager();
            tagManager.firstTimeTags();
            tagManager.reloadTags();
        } else {
            log.info(String.format("[%s] - PlaceholderAPI not found, disabling placeholders!", getDescription().getName()));
        }

        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinLeaveEvents(), this);
        getServer().getPluginManager().registerEvents(new CommandPreprocess(), this);
        this.getCommand("lucidchat").setExecutor(new LCCommand());
        this.getCommand("lucidchat").setTabCompleter(new LCTabComplete());
        this.getCommand("clearchat").setExecutor(new ClearChatCommand());
        this.getCommand("mutechat").setExecutor(new MuteChatCommand());
        this.getCommand("icons").setExecutor(new IconsCommand());
        this.getCommand("tags").setExecutor(new TagsCommand());

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

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }
}
