package cool.ollie.lucidchat.managers;

import cool.ollie.lucidchat.LucidChat;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TagManager {
    LucidChat plugin = LucidChat.getPlugin(LucidChat.class);
    Logger log = Logger.getLogger("Minecraft");

    List tags;
    List icons;

    public void firstTimeTags() {
        File tagsFile = new File(plugin.getDataFolder(), "tags.yml");
        File iconsFile = new File(plugin.getDataFolder(), "icons.yml");

        if(!tagsFile.exists()) {
            plugin.saveResource("tags.yml", false);
            log.info(String.format("[%s] - Created tags.yml", plugin.getDescription().getName()));
        }
        if(!iconsFile.exists()) {
            plugin.saveResource("icons.yml", false);
            log.info(String.format("[%s] - Created icons.yml", plugin.getDescription().getName()));
        }
    }

    public void reloadTags() {
        log.info(String.format("[%s] - Loading tags.yml & icons.yml", plugin.getDescription().getName()));
        File tagsFile = new File(plugin.getDataFolder(), "tags.yml");
        File iconsFile = new File(plugin.getDataFolder(), "icons.yml");
        tags = new ArrayList();
        icons = new ArrayList();
        YamlConfiguration tagsConfig = YamlConfiguration.loadConfiguration(tagsFile);
        YamlConfiguration iconsConfig = YamlConfiguration.loadConfiguration(iconsFile);
        ConfigurationSection tagsSection = tagsConfig.getConfigurationSection("tags");
        ConfigurationSection iconsSection = iconsConfig.getConfigurationSection("icons");
        for(String key : tagsSection.getKeys(false)) {
            List<String> tag = new ArrayList();
            tag.add(tagsSection.getString(key));
            tag.add(tagsSection.getString(key + ".permission"));
            tag.add(tagsSection.getString(key + ".display"));
            tags.add(tag);
        }
        for(String key : iconsSection.getKeys(false)) {
            List<String> icon = new ArrayList();
            icon.add(key);
            icon.add(iconsSection.getString(key + ".permission"));
            icon.add(iconsSection.getString(key + ".display"));
            icons.add(icon);
        }

        log.info(String.format("[%s] - Successfully loaded tags.yml & icons.yml", plugin.getDescription().getName()));
        log.info(String.format("[%s] - Loaded %s tags and %s icons", plugin.getDescription().getName(), tags.size(), icons.size()));
    }
    public String parseIcon(Player player, String key) {
        String display = null;
        for(int i = 0; i < icons.size(); i++) {
            List icon = (List) icons.get(i);
            if(icon.get(0).toString().equals(key)) {
                display = icon.get(2).toString();
            }
        }
        
        String string = PlaceholderAPI.setPlaceholders(player, display);
        
        return string;
    }
    public List getIcons() {
        return icons;
    }
}
