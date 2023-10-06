package cool.ollie.lucidchat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class LCTabComplete implements TabCompleter {
    List<String> arguments = new ArrayList<String>();
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (arguments.isEmpty()) {
            arguments.add("reload");
        }
        List<String> result = new ArrayList<String>();
        return null;
    }
}
