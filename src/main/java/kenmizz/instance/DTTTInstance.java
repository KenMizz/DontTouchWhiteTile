package kenmizz.instance;

import kenmizz.DontTouchWhiteTile;
import kenmizz.instance.event.DTTTPlayingEventListener;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.ThreadLocalRandom;

public class DTTTInstance {

    private final DontTouchWhiteTile plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private String UUID; //database
    private final Location pointA;
    private final Location pointB;
    private final Sign startGameSign;
    private Player player;
    private DTTTPlayingEventListener playingEventListener = null;

    private Location[][] blockPattern = null;
    public int clickedWool = 0;

    public DTTTInstance(DontTouchWhiteTile plugin, Location pointA, Location pointB, BlockFace facing, Sign startGameSign, Player player) {
        this.plugin = plugin;
        this.pointA = pointA;
        this.pointB = pointB;
        this.startGameSign = startGameSign;
        this.player = player;
        initializePattern(facing);
    }

    private void initializePattern(BlockFace facing) {
        blockPattern = new Location[4][5];
        for ( int row = 0; row < 5; row++ ) {
            for ( int col = 0; col < 4; col++ ) {
                switch (facing) {

                    case SOUTH -> {
                        blockPattern[col][row] = pointA.getBlock().getRelative(-col, row, 0).getLocation();
                    }

                    case WEST -> {
                        blockPattern[col][row] = pointA.getBlock().getRelative(0, row, -col).getLocation();
                    }

                    case NORTH -> {
                        blockPattern[col][row] = pointA.getBlock().getRelative(col, row, 0).getLocation();
                    }

                    case EAST -> {
                        blockPattern[col][row] = pointA.getBlock().getRelative(0, row, col).getLocation();
                    }
                }
            }
        }
    }

    public void shufflePattern() {
        setWall(Material.WHITE_WOOL);
        for ( int row = 0; row < 5; row++) {
            int randNum = ThreadLocalRandom.current().nextInt(0, 4);
            blockPattern[randNum][row].getBlock().setType(Material.BLACK_WOOL);
        }
    }

    public void setWall(Material blockMaterial) {
        for ( int row = 0; row < 5; row++ ) {
            for ( int col = 0; col < 4; col++) {
                blockPattern[col][row].getBlock().setType(blockMaterial);
            }
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

    public boolean checkBoundary(Location clickedBlock) {
        // Check if the clicked block is in row 0 of the block pattern
        // Using direct coordinate comparison for better performance
        for (int col = 0; col < 4; col++) {
            Location blockLoc = blockPattern[col][0];
            if (blockLoc.getBlockX() == clickedBlock.getBlockX() &&
                blockLoc.getBlockY() == clickedBlock.getBlockY() &&
                blockLoc.getBlockZ() == clickedBlock.getBlockZ()) {
                return true;
            }
        }
        return false;
    }

    public void start() {
        //shuffle pattern
        shufflePattern();
        //Change sign line
        NamespacedKey statusKey = new NamespacedKey(plugin, "status");
        startGameSign.getPersistentDataContainer().set(statusKey, PersistentDataType.BOOLEAN, true);
        startGameSign.getTargetSide(player).line(2, miniMessage.deserialize("<dark_red>当前玩家: </dark_red><black>" + player.getName() + "</black>"));
        startGameSign.getTargetSide(player).line(1, miniMessage.deserialize("<light_purple>游戏状态: 游戏中</light_purple>"));
        startGameSign.update();
        playingEventListener = new DTTTPlayingEventListener(plugin, player, this);
        plugin.getServer().getPluginManager().registerEvents(playingEventListener, plugin);
    }

    public void stop() {
        setWall(Material.RED_WOOL);
        PlayerInteractEvent.getHandlerList().unregister(playingEventListener);
        startGameSign.getTargetSide(player).line(1, miniMessage.deserialize("<light_purple>游戏状态: 点击开始</light_purple>"));
        startGameSign.getTargetSide(player).line(2, miniMessage.deserialize("<dark_red>当前玩家: </dark_red><black>无</black>"));
        startGameSign.update();
        plugin.getDtttInstanceManager().removeInstance(player);
    }
}
