package kenmizz.gameconfig;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import kenmizz.DontTouchWhiteTile;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class GameConfigManager {

    private final DontTouchWhiteTile plugin;

    private File configFolder;
    private final Map<Player, GameConfig.Builder> playerDraftingMap = new HashMap<>();

    public GameConfigManager(DontTouchWhiteTile plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new GameConfigDraftingListener(plugin), plugin);
        configFolder = new File(plugin.getDataFolder(), "gameconfig");
        if ( !configFolder.exists() ) {
            boolean result = configFolder.mkdir();
            if ( !result ) {
                plugin.getLogger().warning("Unable to create gameconfig folder!");
            }
        }
    }

    public void createConfigDraft(Player player) {
        playerDraftingMap.put(player, new GameConfig.Builder());
    }

    @Nullable
    public GameConfig.Builder getConfigDraft(Player player) {
        if ( playerDraftingMap.containsKey(player) ) {
            return playerDraftingMap.get(player);
        }
        return null;
    }

    public void removeConfigDraft(Player player) {
        playerDraftingMap.remove(player);
    }

    public void saveConfig(String UUID, GameConfig gameConfig) {
        Path configFile = Paths.get(configFolder.getPath(), UUID + ".yml");
        try {
            Files.writeString(configFile, gameConfig.toYaml());
        } catch ( IOException exception ) {
            plugin.getLogger().warning("Unable to save gameconfig! \nUUID: " + UUID + "\nreason: " + exception.getMessage());
        }
    }
}
