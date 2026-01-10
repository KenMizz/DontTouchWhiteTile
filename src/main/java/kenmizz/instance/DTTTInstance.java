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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.ThreadLocalRandom;

public class DTTTInstance {

    private final DontTouchWhiteTile plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private String UUID;
    private final Location pointA;
    private final Location pointB;
    private final Sign startGameSign;
    private final Player player;
    private DTTTPlayingEventListener playingEventListener = null;

    private final Location[][] blockPattern;

    public DTTTInstance(DontTouchWhiteTile plugin, Location pointA, Location pointB, BlockFace facing, Sign startGameSign, int width, int height, Player player) {
        this.plugin = plugin;
        this.pointA = pointA;
        this.pointB = pointB;
        this.startGameSign = startGameSign;
        this.player = player;
        blockPattern = new Location[width][height];
        initializePattern(facing);
        plugin.getLogger().info("Instance created, width: " + blockPattern.length + " height: " + blockPattern[0].length);
    }

    private void initializePattern(BlockFace facing) {
        for ( int row = 0; row < blockPattern[0].length; row++ ) {
            for ( int col = 0; col < blockPattern.length; col++ ) {
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
        for ( int row = 0; row < blockPattern[0].length; row++) {
            int randNum = ThreadLocalRandom.current().nextInt(0, blockPattern.length);
            blockPattern[randNum][row].getBlock().setType(Material.BLACK_WOOL);
        }
    }

    public void setWall(Material blockMaterial) {
        for ( int row = 0; row < blockPattern[0].length; row++ ) {
            for ( int col = 0; col < blockPattern.length; col++) {
                blockPattern[col][row].getBlock().setType(blockMaterial);
            }
        }
    }

    public void updatePattern() {
        for ( int row = 0; row < blockPattern[0].length - 1; row++ ) { //Exclude the top row first, only update the row below
            for ( int col = 0; col < blockPattern.length; col++ ) {
                Location upperBlockPattern = blockPattern[col][row + 1];
                blockPattern[col][row].getBlock().setType(upperBlockPattern.getBlock().getType());
            }
        }

        for ( int col = 0; col < blockPattern.length; col++) { //Update the top row exclusively
            blockPattern[col][blockPattern[0].length - 1].getBlock().setType(Material.WHITE_WOOL);
        }

        int randNum = ThreadLocalRandom.current().nextInt(0, blockPattern.length);
        blockPattern[randNum][blockPattern[0].length - 1].getBlock().setType(Material.BLACK_WOOL);
    }

    public boolean checkClickedBoundary(Location clickedBlock) {
        // Check if the clicked block is in row 0 of the block pattern
        // Using direct coordinate comparison for better performance
        for (int col = 0; col < blockPattern.length; col++) {
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
