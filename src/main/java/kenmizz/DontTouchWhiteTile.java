package kenmizz;

import kenmizz.area.AreaManager;
import kenmizz.commands.DTTTCommand;
import kenmizz.gameconfig.GameConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DontTouchWhiteTile extends JavaPlugin {

    private GameConfigManager gameConfigManager = null;
    private AreaManager areaManager = null;

    @Override
    public void onEnable() {
        if ( !getDataFolder().exists() ) {
            boolean result = getDataFolder().mkdir();
            if ( !result ) {
                getLogger().warning("Unable to create plugin folder, disabling plugin...");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }
        getCommand("dttt").setExecutor(new DTTTCommand(this));
        getCommand("dttt").setTabCompleter(new DTTTCommand(this));
        gameConfigManager = new GameConfigManager(this);
        areaManager = new AreaManager(this);
        getLogger().info("小游戏 *别踩白块儿* 开启 !");
        getLogger().info("Faithful remake by KenMizz~");
        getLogger().info("当前版本: " + getPluginMeta().getVersion());
    }

    @Override
    public void onLoad() {
        getLogger().info("小游戏 *别踩白块儿* 正在加载 !");
    }

    @Override
    public void onDisable() {
        getLogger().info("小游戏 *别踩白块儿* 关闭 !");
    }

    public GameConfigManager getGameConfigManager() {
        return gameConfigManager;
    }

    public AreaManager getAreaManager() {
        return areaManager;
    }
}
