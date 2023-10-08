package cool.ollie.lucidchat.commands;

import cool.ollie.lucidchat.LucidChat;
import cool.ollie.lucidchat.guis.IconGUI;
import cool.ollie.lucidchat.guis.TagGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if(sender instanceof Player){
            player = (Player) sender;
        } else {
            sender.sendMessage("You must be a player to use this command!");
            return true;
        }

        new TagGUI(player).open();

        return true;
    }
}
