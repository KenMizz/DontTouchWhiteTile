package kenmizz;

import kenmizz.commands.DTTTCommand;
import kenmizz.instance.event.DTTTEventListener;
import kenmizz.gameconfig.GameConfigManager;
import kenmizz.instance.DTTTInstanceManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DontTouchWhiteTile extends JavaPlugin {

    private GameConfigManager gameConfigManager = null;
    private DTTTInstanceManager dtttInstanceManager = null;

    @Override
    public void onEnable() {
        getCommand("dttt").setExecutor(new DTTTCommand(this));
        getCommand("dttt").setTabCompleter(new DTTTCommand(this));
        gameConfigManager = new GameConfigManager(this);
        dtttInstanceManager = new DTTTInstanceManager(this);
        getLogger().info("小游戏 *别踩白块儿* 开启 !");
        getLogger().info("Faithful remake by KenMizz~");
        getLogger().info("当前版本: " + getPluginMeta().getVersion());
        getServer().getPluginManager().registerEvents(new DTTTEventListener(this), this);
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

    public DTTTInstanceManager getDtttInstanceManager() {
        return dtttInstanceManager;
    }
}
