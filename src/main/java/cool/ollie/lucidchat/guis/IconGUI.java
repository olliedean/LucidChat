package cool.ollie.lucidchat.guis;

import cool.ollie.lucidchat.LucidChat;
import cool.ollie.lucidchat.managers.UserManager;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import mc.obliviate.inventory.pagination.PaginationManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class IconGUI extends Gui {
    LucidChat plugin = LucidChat.getPlugin(LucidChat.class);
    Logger log = Logger.getLogger("Minecraft");
    public IconGUI(Player player) {
        super(player, "icon-gui", "Icons", 5);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        List icons = plugin.tagManager.getIcons();
        for(int i = 0; i < icons.size(); i++) {
            boolean perm;
            List icon = (List) icons.get(i);
            String permission = (String) icon.get(1);
            String display = "§f" + (String) icon.get(2);
            perm = player.hasPermission(permission);
            Icon iconItem = new Icon(Material.PLAYER_HEAD)
                    .setName(PlaceholderAPI.setPlaceholders(player, display))
                    .setLore(perm ? "§7Click to select this icon" : "§cLocked")
                    .onClick(e -> {
                        if(perm) {
                            try {
                                new UserManager().setPlayerIcon(player, (String) icon.get(0));
                                log.info(String.format("[%s] - %s has selected the icon %s", plugin.getDescription().getName(), player.getName(), icon.get(0).toString()));
                            } catch (IOException ex) {
                                log.severe(String.format("[%s] - Error setting player icon for %s", plugin.getDescription().getName(), player.getName()));
                            }
                            player.closeInventory();
                        }
                    });
            addItem(i, iconItem);
        }
    }
}
