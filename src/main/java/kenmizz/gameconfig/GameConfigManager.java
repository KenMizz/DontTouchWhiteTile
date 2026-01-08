package kenmizz.gameconfig;

import java.util.HashMap;
import java.util.Map;

import kenmizz.DontTouchWhiteTile;
import org.bukkit.entity.Player;

public class GameConfigManager {

    private DontTouchWhiteTile plugin;

    private final Map<Player, GameConfig.Holder> playerDraftingMap = new HashMap<>();

    public GameConfigManager(DontTouchWhiteTile plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new GameConfigDraftingListener(plugin), plugin);
    }

    public void createConfigDraft(Player player) {
        playerDraftingMap.put(player, new GameConfig.Holder());
    }

    public GameConfig.Holder getConfigDraft(Player player) {
        return playerDraftingMap.get(player);
    }

    public void removeConfigDraft(Player player) {
        playerDraftingMap.remove(player);
    }

    public boolean isPlayerDrafting(Player player) {
        return playerDraftingMap.containsKey(player);
    }
}
