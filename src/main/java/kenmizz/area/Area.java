package kenmizz.area;

import kenmizz.area.listener.AreaProtectionListener;
import kenmizz.gameconfig.GameConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class Area {

    public enum WallPattern {
        RACING_FLAG,
        RED,
        WHITE
    }

    private final Location areaA;
    private final Location areaB;
    private final Location pointA;
    private final Location pointB;
    private final Location[][] blockPattern;

    private final AreaProtectionListener areaProtectionListener;

    public Area(GameConfig gameConfig, AreaProtectionListener areaProtectionListener) {
        this.areaA = gameConfig.getAreaA();
        this.areaB = gameConfig.getAreaB();
        this.pointA = gameConfig.getPointA();
        this.pointB = gameConfig.getPointB();
        this.areaProtectionListener = areaProtectionListener;
        blockPattern = new Location[4][5];
        initializePattern(gameConfig.getBlockFacing());
        setWall(WallPattern.RACING_FLAG);
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

    public void setWall(WallPattern pattern) {
        switch (pattern) {

            case RED -> {
                for ( int row = 0; row < blockPattern[0].length; row++ ) {
                    for ( int col = 0; col < blockPattern.length; col++ ) {
                        blockPattern[col][row].getBlock().setType(Material.RED_WOOL);
                    }
                }
            }

            case WHITE -> {
                for ( int row = 0; row < blockPattern[0].length; row++ ) {
                    for ( int col = 0; col < blockPattern.length; col++ ) {
                        blockPattern[col][row].getBlock().setType(Material.WHITE_WOOL);
                    }
                }
            }

            case RACING_FLAG -> {
                for ( int row = 0; row < blockPattern[0].length; row++ ) {
                    for ( int col = 0; col < blockPattern.length; col++ ) {
                        Material woolType = (row + col) % 2 == 0 ? Material.BLACK_WOOL : Material.WHITE_WOOL;
                        blockPattern[col][row].getBlock().setType(woolType);
                    }
                }
            }
        }
    }
}
