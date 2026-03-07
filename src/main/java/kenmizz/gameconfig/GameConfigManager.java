package kenmizz.gameconfig;

import java.util.HashMap;
import java.util.Map;

import kenmizz.DontTouchWhiteTile;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class GameConfigManager {

    private final Map<Player, GameConfig.Holder> playerDraftingMap = new HashMap<>();

    public GameConfigManager(DontTouchWhiteTile plugin) {
        plugin.getServer().getPluginManager().registerEvents(new GameConfigDraftingListener(plugin), plugin);
    }

    public void createConfigDraft(Player player) {
        playerDraftingMap.put(player, new GameConfig.Holder());
    }

    @Nullable
    public GameConfig.Holder getConfigDraft(Player player) {
        if ( playerDraftingMap.containsKey(player) ) {
            return playerDraftingMap.get(player);
        }
        return null;
    }

    public void removeConfigDraft(Player player) {
        playerDraftingMap.remove(player);
    }
}
