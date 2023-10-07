package cool.ollie.lucidchat.commands;

import cool.ollie.lucidchat.LucidChat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ClearChatCommand implements CommandExecutor {
    LucidChat plugin = LucidChat.getPlugin(LucidChat.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for(int i = 0; i < 100; i++) {
            Bukkit.broadcastMessage("");
        }
        String prefix = plugin.getConfig().getString("prefix").replace("&", "§");
        Bukkit.broadcastMessage(prefix + "§bChat cleared by " + sender.getName());
        return false;
    }
}
