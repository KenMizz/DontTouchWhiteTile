package kenmizz;

import kenmizz.commands.DTTTCommand;
import kenmizz.event.RandomEvent;
import kenmizz.gameconfig.GameConfigManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class DontTouchWhiteTile extends JavaPlugin {

    public List<Player> playerDraftingList = new ArrayList<>();
    public Player playingPlayer = null;

    public Location pointA = null;
    public Location pointB = null;
    public Location[][] blockPattern = new Location[4][5];

    private final GameConfigManager gameConfigManager = new GameConfigManager(this);

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

    public GameConfigManager getGameConfigManager() {
        return gameConfigManager;
    }

    public void shufflePattern() {
        //shuffle its pattern
        for ( int row = 0; row < 5; row++) {
            int randNum = ThreadLocalRandom.current().nextInt(0, 4);
            blockPattern[randNum][row].getBlock().setType(Material.BLACK_WOOL);
        }
    }

    public void updatePattern() {
        for ( int row = 0; row < 4; row++ ) {
            for ( int col = 0; col < 4; col++ ) {
                Location upperBlockPattern = blockPattern[col][row + 1];
                blockPattern[col][row].getBlock().setType(upperBlockPattern.getBlock().getType());
                //It's updating recursively
            }
        }
        for ( int col = 0; col < 4; col++) {
            blockPattern[col][4].getBlock().setType(Material.WHITE_WOOL);
        }
        int randNum = ThreadLocalRandom.current().nextInt(0, 4);
        blockPattern[randNum][4].getBlock().setType(Material.BLACK_WOOL);
    }

    public void resetWall() {
        for ( int row = 0; row < 5; row++ ) {
            for ( int col = 0; col < 4; col++) {
                blockPattern[col][row].getBlock().setType(Material.WHITE_WOOL);
            }
        }
    }
}
