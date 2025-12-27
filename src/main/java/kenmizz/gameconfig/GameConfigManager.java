package kenmizz.gameconfig;

import java.util.ArrayList;
import java.util.List;

import kenmizz.DontTouchWhiteTile;
import org.bukkit.entity.Player;

public class GameConfigManager {

    private DontTouchWhiteTile plugin;

    private List<Player> playerDraftingList = new ArrayList<>();

    public GameConfigManager(DontTouchWhiteTile plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new GameConfigDraftingListener(plugin), plugin);
    }

    public void addDraftingPlayer(Player player) {
        playerDraftingList.add(player);
    }

    public void removeDraftingPlayer(Player player) {
        playerDraftingList.remove(player);
    }

    public boolean isPlayerDrafting(Player player) {
        return playerDraftingList.contains(player);
    }
}
