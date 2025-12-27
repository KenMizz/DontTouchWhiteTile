package kenmizz.event;

import kenmizz.DontTouchWhiteTile;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class RandomEvent implements Listener {

    private DontTouchWhiteTile plugin;

    public RandomEvent(DontTouchWhiteTile plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if ( plugin.playerDraftingList.contains(player) ) {
            if ( plugin.pointA == null ) {
                Block block = event.getBlock();
                if ( block.getType().equals(Material.WHITE_WOOL) ) {
                    event.setCancelled(true);
                    plugin.pointA = block.getLocation();
                    player.sendMessage("set a point B, needs to be 4x5 and white wool");
                }
            } else if ( plugin.pointB == null ) {
                Block block = event.getBlock();
                if ( block.getType().equals(Material.WHITE_WOOL)) {
                    event.setCancelled(true);
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
                        for ( int row = 0; row < 5; row++ ) {
                            for ( int column = 0; column < 4; column++) {
                                plugin.blockPattern[column][row] = plugin.pointA.getBlock().getRelative(-column, row, 0).getLocation();
                            }
                        }
                    } else {
                        player.sendMessage("Try again");
                    }
                }
            }
        }

        if ( plugin.playingPlayer == player ) {
            //Playing that!
            Block destroyedBlock = event.getBlock();
            if ( destroyedBlock.getLocation().getY() == plugin.pointA.getY()) { // destroyed block's Y has to match the pointA's Y since the wall relatives is based on point A
                event.setCancelled(true);
                //make sure the upper row comes down
                if (destroyedBlock.getType() == Material.BLACK_WOOL) {
                    //Win condition
                    plugin.updatePattern();
                } else if (destroyedBlock.getType() == Material.WHITE_WOOL ) {
                    player.sendMessage("You lose!");
                    plugin.resetWall();
                    plugin.playingPlayer = null;
                }
            }
        }
    }
}
