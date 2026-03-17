package kenmizz.gameconfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import kenmizz.DontTouchWhiteTile;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class GameConfigManager {

    private final DontTouchWhiteTile plugin;

    private final File configFolder;
    private final Map<Player, GameConfig.Builder> configDraftMap = new HashMap<>();

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
        configDraftMap.put(player, new GameConfig.Builder());
    }

    @Nullable
    public GameConfig.Builder getConfigDraft(Player player) {
        if ( configDraftMap.containsKey(player) ) {
            return configDraftMap.get(player);
        }
        return null;
    }

    public void removeConfigDraft(Player player) {
        configDraftMap.remove(player);
    }

    public void saveConfig(String UUID, GameConfig gameConfig) {
        Path configFile = Paths.get(configFolder.getPath(), UUID + ".yml");
        try {
            Files.writeString(configFile, gameConfig.toYaml());
        } catch ( IOException exception ) {
            plugin.getLogger().warning("Unable to save gameconfig \nUUID: " + UUID + "\nreason: " + exception.getMessage());
        }
    }

    public boolean loadConfig(String UUID) {
        File configFile = Paths.get(configFolder.getPath(), UUID + ".yml").toFile();
        if ( configFile.exists() ) {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

            if ( plugin.getServer().getWorld(yamlConfiguration.getString("coordinates.areaA.world")) == null ) {
                plugin.getLogger().warning("无法加载配置文件 '" + UUID + "'. 世界不存在");
                return false;
            }

            Location areaALocation = new Location(
                    plugin.getServer().getWorld(yamlConfiguration.getString("coordinates.areaA.world")),
                    yamlConfiguration.getDouble("coordinates.areaA.x"),
                    yamlConfiguration.getDouble("coordinates.areaA.y"),
                    yamlConfiguration.getDouble("coordinates.areaA.z")
            );
            Location areaBLocation = new Location(
                    plugin.getServer().getWorld(yamlConfiguration.getString("coordinates.areaB.world")),
                    yamlConfiguration.getDouble("coordinates.areaB.x"),
                    yamlConfiguration.getDouble("coordinates.areaB.y"),
                    yamlConfiguration.getDouble("coordinates.areaB.z")
            );
            Location pointALocation = new Location(
                    plugin.getServer().getWorld(yamlConfiguration.getString("coordinates.pointA.world")),
                    yamlConfiguration.getDouble("coordinates.pointA.x"),
                    yamlConfiguration.getDouble("coordinates.pointA.y"),
                    yamlConfiguration.getDouble("coordinates.pointA.z")
            );
            Location pointBLocation = new Location(
                    plugin.getServer().getWorld(yamlConfiguration.getString("coordinates.pointB.world")),
                    yamlConfiguration.getDouble("coordinates.pointB.x"),
                    yamlConfiguration.getDouble("coordinates.pointB.y"),
                    yamlConfiguration.getDouble("coordinates.pointB.z")
            );
            GameConfig.Builder builder = new GameConfig.Builder()
                    .areaA(areaALocation)
                    .areaB(areaBLocation)
                    .pointA(pointALocation)
                    .pointB(pointBLocation)
                    .facing(BlockFace.valueOf(yamlConfiguration.getString("settings.blockFacing")))
                    .timer(yamlConfiguration.getInt("settings.timer"));
            plugin.getAreaManager().createArea(UUID, builder.build());
            return true;
        }
        return false;
    }
}
