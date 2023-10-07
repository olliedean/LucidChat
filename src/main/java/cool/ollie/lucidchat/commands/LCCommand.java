package cool.ollie.lucidchat.commands;

import cool.ollie.lucidchat.LucidChat;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LCCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args[0].equalsIgnoreCase("reload")) {
            LucidChat.getPlugin(LucidChat.class).reloadConfig();
            LucidChat.getPlugin(LucidChat.class).tagManager.reloadTags();
            sender.sendMessage(ChatColor.GREEN + "Reloaded config!");
            return true;
        }
        return true;
    }
}
