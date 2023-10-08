package cool.ollie.lucidchat.guis;

import cool.ollie.lucidchat.LucidChat;
import cool.ollie.lucidchat.managers.UserManager;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class TagGUI extends Gui {
    LucidChat plugin = LucidChat.getPlugin(LucidChat.class);
    Logger log = Logger.getLogger("Minecraft");
    public TagGUI(Player player) {
        super(player, "tag-gui", "Tags", 5);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        List tags = plugin.tagManager.getTags();
        for(int i = 0; i < tags.size(); i++) {
            boolean perm;
            List tag = (List) tags.get(i);
            String permission = (String) tag.get(1);
            String display = "§f" + (String) tag.get(2);
            display = display.replace("&", "§");
            perm = player.hasPermission(permission);
            Icon iconItem = new Icon(Material.NAME_TAG)
                    .setName(PlaceholderAPI.setPlaceholders(player, display))
                    .setLore(perm ? "§7Click to select this tag" : "§cLocked")
                    .onClick(e -> {
                        if(perm) {
                            try {
                                new UserManager().setPlayerTag(player, (String) tag.get(0));
                                log.info(String.format("[%s] - %s has selected the tag %s", plugin.getDescription().getName(), player.getName(), tag.get(0).toString()));
                                player.sendMessage(String.format("%s§aYou have selected the tag: %s",
                                        plugin.getConfig().get("prefix"),
                                        tag.get(0).toString()
                                        ).replace("&", "§")
                                );
                            } catch (IOException ex) {
                                log.severe(String.format("[%s] - Error setting player tag for %s", plugin.getDescription().getName(), player.getName()));
                                player.sendMessage("§cAn error occurred whilst setting your tag");
                            }
                            player.closeInventory();
                        } else {
                            player.sendMessage(String.format("%s§cYou do not have permission to use this tag", plugin.getConfig().get("prefix")).replace("&", "§"));
                        }
                    });
            addItem(i, iconItem);
        }
    }
}
