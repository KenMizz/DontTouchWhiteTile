package kenmizz.gameconfig;

import kenmizz.DontTouchWhiteTile;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class GameConfigDraftingListener implements Listener {

    /**
     * This listener is responsible for listening game config creation
     */

    private final DontTouchWhiteTile plugin;

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public GameConfigDraftingListener(DontTouchWhiteTile plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if ( plugin.getGameConfigManager().isPlayerDrafting(player) ) {
            GameConfig.Holder configHolder = plugin.getGameConfigManager().getConfigDraft(player);
            Block destroyedBlock = event.getBlock();
            GameConfig.Holder.settingStage settingStage = configHolder.getCurrentSettingStage();
            event.setCancelled(true);
            switch ( settingStage ) {

                case POINT_A -> {
                    configHolder.pointA(destroyedBlock.getLocation());
                    player.sendMessage(miniMessage.deserialize("<yellow>开始设置点B，请确保大小为4x5</yellow>"));
                    configHolder.setCurrentSettingStage(GameConfig.Holder.settingStage.POINT_B);
                }

                case POINT_B -> {
                    if ( Utils.checkWallBoundary(destroyedBlock.getLocation(), configHolder.getPointA(), 4, 5 ) ) {
                        configHolder.pointB(destroyedBlock.getLocation());
                        player.sendMessage(miniMessage.deserialize("<yellow>请设置一个木牌作为开始游戏按钮</yellow>"));
                        configHolder.setCurrentSettingStage(GameConfig.Holder.settingStage.START_SIGN);
                    } else {
                        player.sendMessage(miniMessage.deserialize("<red>请确保大小为4x5</red>"));
                    }
                }

                case START_SIGN -> {
                    if ( destroyedBlock.getType().toString().contains("SIGN") ) {
                        Sign startGameSign = (Sign) destroyedBlock.getState();
                        PersistentDataContainer persistentDataContainer = startGameSign.getPersistentDataContainer();
                        //Create Namespaced key to store inside the sign
                        UUID signUUID = UUID.randomUUID();
                        NamespacedKey uuidKey = new NamespacedKey(plugin, "UUID");
                        NamespacedKey pointAxKey = new NamespacedKey(plugin, "pointAx");
                        NamespacedKey pointAyKey = new NamespacedKey(plugin, "pointAy");
                        NamespacedKey pointAzKey = new NamespacedKey(plugin, "pointAz");
                        NamespacedKey pointBxKey = new NamespacedKey(plugin, "pointBx");
                        NamespacedKey pointByKey = new NamespacedKey(plugin, "pointBy");
                        NamespacedKey pointBzKey = new NamespacedKey(plugin, "pointBz");
                        NamespacedKey facingKey = new NamespacedKey(plugin, "facing");
                        persistentDataContainer.set(uuidKey, PersistentDataType.STRING, signUUID.toString());
                        persistentDataContainer.set(pointAxKey, PersistentDataType.DOUBLE, configHolder.getPointA().getX());
                        persistentDataContainer.set(pointAyKey, PersistentDataType.DOUBLE, configHolder.getPointA().getY());
                        persistentDataContainer.set(pointAzKey, PersistentDataType.DOUBLE, configHolder.getPointA().getZ());
                        persistentDataContainer.set(pointBxKey, PersistentDataType.DOUBLE, configHolder.getPointB().getX());
                        persistentDataContainer.set(pointByKey, PersistentDataType.DOUBLE, configHolder.getPointB().getY());
                        persistentDataContainer.set(pointBzKey, PersistentDataType.DOUBLE, configHolder.getPointB().getZ());
                        persistentDataContainer.set(facingKey, PersistentDataType.STRING, player.getFacing().toString());
                        //Set a new line for the sign
                        startGameSign.getTargetSide(player).line(0, miniMessage.deserialize("<yellow>别踩白块儿</yellow>"));
                        startGameSign.getTargetSide(player).line(1, miniMessage.deserialize("<light_purple>游戏状态: 点击开始</light_purple>"));
                        startGameSign.getTargetSide(player).line(2, miniMessage.deserialize("<dark_red>当前玩家: </dark_red><black>无</black>"));
                        startGameSign.update();
                        player.sendMessage(miniMessage.deserialize("<green>设置完毕，点击木牌开始游玩吧！</green>"));
                        Utils.setWall(configHolder.getPointA(), 4, 5, player.getFacing());
                        plugin.getGameConfigManager().removeConfigDraft(player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if ( plugin.getGameConfigManager().isPlayerDrafting(player) ) {
            plugin.getGameConfigManager().removeConfigDraft(player);
        }
    }
}
