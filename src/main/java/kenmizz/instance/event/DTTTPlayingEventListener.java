package kenmizz.instance.event;

import kenmizz.DontTouchWhiteTile;
import kenmizz.instance.DTTTInstance;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class DTTTPlayingEventListener implements Listener {

    private final DontTouchWhiteTile plugin;

    private final Player playingPlayer;
    private final DTTTInstance playingInstance;

    public DTTTPlayingEventListener(DontTouchWhiteTile plugin, Player player, DTTTInstance instance) {
        this.plugin = plugin;
        this.playingPlayer = player;
        this.playingInstance = instance;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if ( player == playingPlayer ) {
            //Player is indeed playing
            if ( event.getAction() == Action.LEFT_CLICK_BLOCK ) {
                Block clickedBlock = event.getClickedBlock();
                /**
                 * Few Goals
                 * 1. prevent player from clicking any line besides the lowest one (make sure player don't go out of bounds)
                 * 2. Only interfere within the boundary
                 */
                if ( playingInstance.checkClickedBoundary(clickedBlock.getLocation()) ) {
                    event.setCancelled(true);
                    if ( clickedBlock.getType() == Material.BLACK_WOOL ) {
                        playingInstance.updatePattern();
                    } else if (clickedBlock.getType() == Material.WHITE_WOOL ) {
                        player.sendMessage("你输了");
                        playingInstance.stop();
                    }
                }
            }
        }
    }
}
