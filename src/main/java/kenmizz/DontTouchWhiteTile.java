package kenmizz;

import kenmizz.commands.DTTTCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class DontTouchWhiteTile extends JavaPlugin {

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
}
