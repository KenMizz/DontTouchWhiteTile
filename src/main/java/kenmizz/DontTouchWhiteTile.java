package kenmizz;

import kenmizz.commands.DTTTCommand;
import kenmizz.event.RandomEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class DontTouchWhiteTile extends JavaPlugin {

    public List<Player> playerDraftingList = new ArrayList<>();

    public Location pointA = null;
    public Location pointB = null;
    public Location startSign = null;
    private Location[][] blockPattern = new Location[4][5];

    @Override
    public void onEnable() {
        if ( !getDataFolder().exists() ) {
            boolean result = getDataFolder().mkdir();
            if ( !result ) {
                getLogger().warning("插件根目录创建失败！");
                this.getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }
        getCommand("dttt").setExecutor(new DTTTCommand(this));
        getCommand("dttt").setTabCompleter(new DTTTCommand(this));
        getLogger().info("小游戏 *别踩白块儿* 开启 !");
        getLogger().info("Faithful remake by KenMizz~");
        getLogger().info("当前版本: " + getPluginMeta().getVersion());
        getServer().getPluginManager().registerEvents(new RandomEvent(this), this);
    }

    @Override
    public void onLoad() {
        getLogger().info("小游戏 *别踩白块儿* 正在加载 !");
    }

    @Override
    public void onDisable() {
        getLogger().info("小游戏 *别踩白块儿* 关闭 !");
    }

    public void updatePattern() {
        /**
         * Alright, I need to get this update just by a basic pattern
         */
        
    }
}
