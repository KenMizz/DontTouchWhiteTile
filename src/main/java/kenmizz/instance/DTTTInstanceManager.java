package kenmizz.instance;

import kenmizz.DontTouchWhiteTile;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DTTTInstanceManager {

    private final DontTouchWhiteTile plugin;

    private final Map<Player, DTTTInstance> dtttInstanceMap = new HashMap<>();

    public DTTTInstanceManager(DontTouchWhiteTile plugin) {
        this.plugin = plugin;
    }

    public DTTTInstance createInstance(Location pointA, Location pointB, BlockFace facing, Sign startGameSign, int width, int height, Player player) {
        DTTTInstance instance = new DTTTInstance(plugin, pointA, pointB, facing, startGameSign, width, height, player);
        dtttInstanceMap.put(player, instance);
        return instance;
    }

    public DTTTInstance getInstance(Player player) {
        return dtttInstanceMap.get(player);
    }

    public void removeInstance(Player player) {
        dtttInstanceMap.remove(player);
    }
}
