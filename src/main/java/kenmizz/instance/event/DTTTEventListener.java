package kenmizz.instance.event;

import kenmizz.DontTouchWhiteTile;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DTTTEventListener implements Listener {

    private final DontTouchWhiteTile plugin;

    public DTTTEventListener(DontTouchWhiteTile plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        //This handler is for detecting when player interact with a start game sign
        Player player = event.getPlayer();
        if ( event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
            Block clickedBlock = event.getClickedBlock();
            if ( clickedBlock.getType().toString().contains("SIGN") ) {
                event.setCancelled(true);
                Sign startGameSign = (Sign) clickedBlock.getState();
                PersistentDataContainer persistentDataContainer = startGameSign.getPersistentDataContainer();
                NamespacedKey uuidKey = new NamespacedKey(plugin, "UUID");
                if ( persistentDataContainer.get(uuidKey, PersistentDataType.STRING) != null ) {
                    //It's an startGame sign
                    NamespacedKey pointAxKey = new NamespacedKey(plugin, "pointAx");
                    NamespacedKey pointAyKey = new NamespacedKey(plugin, "pointAy");
                    NamespacedKey pointAzKey = new NamespacedKey(plugin, "pointAz");
                    NamespacedKey pointBxKey = new NamespacedKey(plugin, "pointBx");
                    NamespacedKey pointByKey = new NamespacedKey(plugin, "pointBy");
                    NamespacedKey pointBzKey = new NamespacedKey(plugin, "pointBz");
                    NamespacedKey facingKey = new NamespacedKey(plugin, "facing");
                    Double pointAx = persistentDataContainer.get(pointAxKey, PersistentDataType.DOUBLE);
                    Double pointAy = persistentDataContainer.get(pointAyKey, PersistentDataType.DOUBLE);
                    Double pointAz = persistentDataContainer.get(pointAzKey, PersistentDataType.DOUBLE);
                    Double pointBx = persistentDataContainer.get(pointBxKey, PersistentDataType.DOUBLE);
                    Double pointBy = persistentDataContainer.get(pointByKey, PersistentDataType.DOUBLE);
                    Double pointBz = persistentDataContainer.get(pointBzKey, PersistentDataType.DOUBLE);
                    Location pointA = new Location(player.getWorld(), pointAx, pointAy, pointAz);
                    Location pointB = new Location(player.getWorld(), pointBx, pointBy, pointBz);
                    BlockFace blockFacing = BlockFace.valueOf(persistentDataContainer.get(facingKey, PersistentDataType.STRING));
                    plugin.getDtttInstanceManager().createInstance(pointA, pointB, blockFacing, startGameSign, 4, 5, player).start();
                }
            }
        }
    }
}
