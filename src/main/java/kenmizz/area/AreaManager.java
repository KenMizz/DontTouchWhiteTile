package kenmizz.area;

import kenmizz.DontTouchWhiteTile;
import kenmizz.area.listener.AreaProtectionListener;
import kenmizz.gameconfig.GameConfig;

import java.util.HashMap;
import java.util.Map;

public class AreaManager {

    private final DontTouchWhiteTile plugin;

    private final Map<String, Area> areaMap = new HashMap<>();

    public AreaManager(DontTouchWhiteTile plugin) {
        this.plugin = plugin;
    }

    public void createArea(String UUID, GameConfig gameConfig) {
        AreaProtectionListener areaProtectionListener = new AreaProtectionListener(gameConfig.getAreaA(), gameConfig.getAreaB());
        plugin.getServer().getPluginManager().registerEvents(areaProtectionListener, plugin);
        Area area = new Area(gameConfig, areaProtectionListener);
        areaMap.put(UUID, area);
    }
}
