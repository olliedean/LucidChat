package cool.ollie.lucidchat.commands;

import cool.ollie.lucidchat.LucidChat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MuteChatCommand implements CommandExecutor {
    LucidChat plugin = LucidChat.getPlugin(LucidChat.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Boolean isMuted = plugin.isMuted();
        if(isMuted) {
            plugin.setMuted(false);
            sender.sendMessage(plugin.getConfig().getString("prefix").replace("&", "§") + "§bYou have unmuted chat");
            Bukkit.broadcastMessage(plugin.getConfig().getString("prefix").replace("&", "§") + "§bChat has been unmuted by " + sender.getName());
        } else {
            plugin.setMuted(true);
            sender.sendMessage(plugin.getConfig().getString("prefix").replace("&", "§") + "§bYou have muted chat.");
            sender.sendMessage(plugin.getConfig().getString("prefix").replace("&", "§") + "§bUse /mutechat again to unmute chat.");
            Bukkit.broadcastMessage(plugin.getConfig().getString("prefix").replace("&", "§") + "§bChat has been muted by " + sender.getName());
        }
        return true;
    }
}
