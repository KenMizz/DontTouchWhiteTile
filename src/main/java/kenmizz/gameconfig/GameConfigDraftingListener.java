package kenmizz.gameconfig;

import kenmizz.DontTouchWhiteTile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GameConfigDraftingListener implements Listener {

    /**
     * This listener is responsible for listening game config creation
     */

    private DontTouchWhiteTile plugin;

    public GameConfigDraftingListener(DontTouchWhiteTile plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if ( plugin.getGameConfigManager().isPlayerDrafting(player) ) {

        }
    }
}
