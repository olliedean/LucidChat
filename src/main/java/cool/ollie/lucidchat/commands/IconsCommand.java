package cool.ollie.lucidchat.commands;

import cool.ollie.lucidchat.LucidChat;
import cool.ollie.lucidchat.guis.IconGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IconsCommand implements CommandExecutor {
    LucidChat plugin = LucidChat.getPlugin(LucidChat.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if(sender instanceof Player){
            player = (Player) sender;
        } else {
            sender.sendMessage("You must be a player to use this command!");
            return true;
        }

        new IconGUI(player).open();

        return true;
    }
}
