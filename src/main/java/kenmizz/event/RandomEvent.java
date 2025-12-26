package kenmizz.event;

import kenmizz.DontTouchWhiteTile;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RandomEvent implements Listener {

    private DontTouchWhiteTile plugin;

    public RandomEvent(DontTouchWhiteTile plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if ( plugin.playerDraftingList.contains(player) ) {
            if ( event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
                if ( plugin.pointA == null ) {
                    Block block = event.getClickedBlock();
                    if ( block.getType() == Material.WHITE_WOOL ) {
                        plugin.pointA = block.getLocation();
                        player.sendMessage("set a point B, needs to be 4x5 and white wool");
                    }
                } else if ( plugin.pointB == null ) {
                    Block block = event.getClickedBlock();
                    if ( block.getType() == Material.WHITE_WOOL ) {
                        int deltaX = (int)Math.abs(block.getLocation().getX() - plugin.pointA.getX()) + 1;
                        int deltaY = (int)Math.abs(block.getLocation().getY() - plugin.pointA.getY()) + 1;
                        int deltaZ = (int)Math.abs(block.getLocation().getZ() - plugin.pointA.getZ()) + 1;
                        int actualWidth = Math.max(deltaX, deltaZ);
                        int actualHeight = deltaY;
                        if ( actualWidth == 4 && actualHeight == 5 ) {
                            //Wow what a bad code
                            plugin.pointB = block.getLocation();
                            player.sendMessage("You got it!");
                            plugin.playerDraftingList.remove(player);
                        } else {
                            player.sendMessage("Try again");
                        }
                    }
                }
            }
        }
    }
}
