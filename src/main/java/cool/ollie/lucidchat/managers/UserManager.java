package cool.ollie.lucidchat.managers;

import cool.ollie.lucidchat.LucidChat;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class UserManager {
    LucidChat plugin = LucidChat.getPlugin(LucidChat.class);
    Logger log = Logger.getLogger("Minecraft");
    public void initPlayer(Player player) throws IOException {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        File playerFolder = new File(plugin.getDataFolder(), "/players");
        if(!playerFolder.exists()) {
            log.info(String.format("[%s] - Creating player folder", plugin.getDescription().getName()));
            playerFolder.mkdir();
        }
        File playerFile = new File(plugin.getDataFolder(), "/players/" + player.getUniqueId().toString() + ".yml");
        if(!playerFile.exists()) {
            log.info(String.format("[%s] - Creating player file for %s", plugin.getDescription().getName(), player.getName()));
            playerFile.createNewFile();
            log.info(String.format("[%s] - Successfully created player file for %s", plugin.getDescription().getName(), player.getName()));
        }
    }

    public String getPlayerTag(Player player) {
        String tag = getValue(player, "tag");
        if(tag == null) {
            return null;
        }
        return tag;
    }

    public String getValue(Player player, String key) {
        File playerFile = getPlayerFile(player);
        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        String value = playerConfig.getString(key);
        return value;
    }
    public File getPlayerFile(Player player) {
        File playerFile = new File(plugin.getDataFolder(), "/players/" + player.getUniqueId().toString() + ".yml");
        return playerFile;
    }
}
