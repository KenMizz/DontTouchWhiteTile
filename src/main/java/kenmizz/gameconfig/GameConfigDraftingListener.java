package kenmizz.gameconfig;

import kenmizz.DontTouchWhiteTile;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
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
        GameConfig.Builder configDraftHolder = plugin.getGameConfigManager().getConfigDraft(player);
        if ( configDraftHolder != null ) {
            event.setCancelled(true);
            Block destroyedBlock = event.getBlock();
            GameConfig.Builder.settingStage settingStage = configDraftHolder.getCurrentSettingStage();
            switch ( settingStage ) {

                case AREA_A -> {
                    configDraftHolder.areaA(destroyedBlock.getLocation());
                    player.sendMessage("Set up area B");
                    configDraftHolder.setCurrentSettingStage(GameConfig.Builder.settingStage.AREA_B);
                }

                case AREA_B -> {
                    configDraftHolder.areaB(destroyedBlock.getLocation());
                    player.sendMessage("Set up point A");
                    configDraftHolder.setCurrentSettingStage(GameConfig.Builder.settingStage.POINT_A);
                }

                case POINT_A -> {
                    configDraftHolder.pointA(destroyedBlock.getLocation());
                    player.sendMessage(miniMessage.deserialize("<yellow>开始设置点B，请确保大小为4x5</yellow>"));
                    configDraftHolder.setCurrentSettingStage(GameConfig.Builder.settingStage.POINT_B);
                }

                case POINT_B -> {
                    if ( Utils.checkWallBoundary(destroyedBlock.getLocation(), configDraftHolder.getPointA(), 4, 5 ) ) {
                        configDraftHolder.pointB(destroyedBlock.getLocation());
                        player.sendMessage(miniMessage.deserialize("<yellow>请设置一个木牌作为开始游戏按钮</yellow>"));
                        configDraftHolder.setCurrentSettingStage(GameConfig.Builder.settingStage.START_SIGN);
                    } else {
                        player.sendMessage(miniMessage.deserialize("<red>请确保大小为4x5</red>"));
                    }
                }

                case START_SIGN -> {
                    if ( destroyedBlock.getType().toString().contains("SIGN") ) {
                        Sign startGameSign = (Sign) destroyedBlock.getState();
                        PersistentDataContainer dataContainer = startGameSign.getPersistentDataContainer();
                        // Create uuid key
                        NamespacedKey uuidKey = new NamespacedKey(plugin, "uuid");
                        String gameUUID = UUID.randomUUID().toString();
                        try {
                            if ( dataContainer.get(uuidKey, PersistentDataType.STRING ) == null ) {
                                // Save configuration to disk
                                plugin.getGameConfigManager().saveConfig(gameUUID, configDraftHolder.build());
                                dataContainer.set(uuidKey, PersistentDataType.STRING, gameUUID);
                                SignSide side = startGameSign.getTargetSide(player);
                                side.line(0, miniMessage.deserialize("<yellow>别踩白块儿</yellow>"));
                                side.line(1, miniMessage.deserialize("<light_purple>游戏状态: 点击开始</light_purple>"));
                                side.line(2, miniMessage.deserialize("<dark_red>当前玩家: </dark_red><black>无</black>"));
                                startGameSign.update();
                                player.sendMessage("设置完毕！");
                            }
                        } catch ( IllegalArgumentException exception ) {
                            player.sendMessage(miniMessage.deserialize("<red>无法创建游戏区域</red>"));
                        }
                        plugin.getGameConfigManager().removeConfigDraft(player);
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
