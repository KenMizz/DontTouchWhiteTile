package kenmizz.gameconfig;

import kenmizz.DontTouchWhiteTile;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class GameConfigDraftingListener implements Listener {

    private final DontTouchWhiteTile plugin;

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public GameConfigDraftingListener(DontTouchWhiteTile plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        Player player = event.getPlayer();
        GameConfig.Holder configDraftHolder = plugin.getGameConfigManager().getConfigDraft(player);
        if ( configDraftHolder != null ) {
            event.setCancelled(true);
            Block destroyedBlock = event.getBlock();
            GameConfig.Holder.settingStage settingStage = configDraftHolder.getCurrentSettingStage();
            switch ( settingStage ) {

                case AREA_A -> {
                    configDraftHolder.areaA(destroyedBlock.getLocation());
                    player.sendMessage("Set up area B");
                    configDraftHolder.setCurrentSettingStage(GameConfig.Holder.settingStage.AREA_B);
                }

                case AREA_B -> {
                    configDraftHolder.areaB(destroyedBlock.getLocation());
                    player.sendMessage("Set up point A");
                    configDraftHolder.setCurrentSettingStage(GameConfig.Holder.settingStage.POINT_A);
                }

                case POINT_A -> {
                    configDraftHolder.pointA(destroyedBlock.getLocation());
                    player.sendMessage(miniMessage.deserialize("<yellow>开始设置点B，请确保大小为4x5</yellow>"));
                    configDraftHolder.setCurrentSettingStage(GameConfig.Holder.settingStage.POINT_B);
                }

                case POINT_B -> {
                    if ( Utils.checkWallBoundary(destroyedBlock.getLocation(), configDraftHolder.getPointA(), 4, 5 ) ) {
                        configDraftHolder.pointB(destroyedBlock.getLocation());
                        player.sendMessage(miniMessage.deserialize("<yellow>请设置一个木牌作为开始游戏按钮</yellow>"));
                        configDraftHolder.setCurrentSettingStage(GameConfig.Holder.settingStage.START_SIGN);
                    } else {
                        player.sendMessage(miniMessage.deserialize("<red>请确保大小为4x5</red>"));
                    }
                }

                case START_SIGN -> {
                    if ( destroyedBlock.getType().toString().contains("SIGN") ) {
                        /*Sign startGameSign = (Sign) destroyedBlock.getState();
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
                        persistentDataContainer.set(pointAxKey, PersistentDataType.DOUBLE, configDraftHolder.getPointA().getX());
                        persistentDataContainer.set(pointAyKey, PersistentDataType.DOUBLE, configDraftHolder.getPointA().getY());
                        persistentDataContainer.set(pointAzKey, PersistentDataType.DOUBLE, configDraftHolder.getPointA().getZ());
                        persistentDataContainer.set(pointBxKey, PersistentDataType.DOUBLE, configDraftHolder.getPointB().getX());
                        persistentDataContainer.set(pointByKey, PersistentDataType.DOUBLE, configDraftHolder.getPointB().getY());
                        persistentDataContainer.set(pointBzKey, PersistentDataType.DOUBLE, configDraftHolder.getPointB().getZ());
                        persistentDataContainer.set(facingKey, PersistentDataType.STRING, player.getFacing().toString());
                        //Set a new line for the sign
                        startGameSign.getTargetSide(player).line(0, miniMessage.deserialize("<yellow>别踩白块儿</yellow>"));
                        startGameSign.getTargetSide(player).line(1, miniMessage.deserialize("<light_purple>游戏状态: 点击开始</light_purple>"));
                        startGameSign.getTargetSide(player).line(2, miniMessage.deserialize("<dark_red>当前玩家: </dark_red><black>无</black>"));
                        startGameSign.update();
                        player.sendMessage(miniMessage.deserialize("<green>设置完毕，点击木牌开始游玩吧！</green>"));
                        Utils.setWall(configDraftHolder.getPointA(), 4, 5, player.getFacing());
                        plugin.getGameConfigManager().removeConfigDraft(player);*/


                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if ( plugin.getGameConfigManager().getConfigDraft(player) != null ) {
            plugin.getGameConfigManager().removeConfigDraft(player);
        }
    }
}
