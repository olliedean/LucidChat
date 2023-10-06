package cool.ollie.lucidchat;

import cool.ollie.lucidchat.commands.LCCommand;
import cool.ollie.lucidchat.events.ChatListener;
import cool.ollie.lucidchat.events.PlayerJoinLeaveEvents;
import cool.ollie.lucidchat.managers.UserManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class LucidChat extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    public boolean isPAPIEnabled = false;

    @Override
    public void onEnable() {
        if(!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
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

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
