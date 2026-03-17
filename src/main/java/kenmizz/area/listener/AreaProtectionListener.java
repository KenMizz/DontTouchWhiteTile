package kenmizz.area.listener;

import kenmizz.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class AreaProtectionListener implements Listener {

    private final Location areaALocation;
    private final Location areaBLocation;

    public AreaProtectionListener(Location areaALocation, Location areaBLocation) {
        this.areaALocation = areaALocation;
        this.areaBLocation = areaBLocation;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block destroyedBlock = event.getBlock();
        if ( Utils.checkAreaBoundary(destroyedBlock.getLocation(), areaALocation, areaBLocation) ) {
            if ( !player.hasPermission("dttt.area.break") ) {
                event.setCancelled(true);
                player.sendMessage("You can't break here");
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block destroyedBlock = event.getBlock();
        if ( Utils.checkAreaBoundary(destroyedBlock.getLocation(), areaALocation, areaBLocation) ) {
            if ( !player.hasPermission("dttt.area.place") ) {
                event.setCancelled(true);
                player.sendMessage("You can't place here");
            }
        }
    }

    public void unregister() {
        BlockBreakEvent.getHandlerList().unregister(this);
        BlockPlaceEvent.getHandlerList().unregister(this);
    }
}
