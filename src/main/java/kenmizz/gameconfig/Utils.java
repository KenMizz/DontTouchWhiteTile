package kenmizz.gameconfig;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class Utils {

    public static boolean checkWallBoundary(Location pointA, Location pointB, int width, int height ) {
        int deltaX = (int)Math.abs(pointB.getX() - pointA.getX()) + 1;
        int actualHeight = (int)Math.abs(pointB.getY() - pointA.getY()) + 1;
        int deltaZ = (int)Math.abs(pointB.getZ() - pointA.getZ()) + 1;
        int actualWidth = Math.max(deltaX, deltaZ);
        return actualWidth == width && actualHeight == height;
    }

    public static void setWall(Location pointA, int width, int height, BlockFace facing) {
        Location[][] blockPattern = new Location[width][height];
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
        //Set wall
        for ( int row = 0; row < 5; row++ ) {
            for ( int col = 0; col < 4; col++) {
                blockPattern[col][row].getBlock().setType(Material.WHITE_WOOL);
            }
        }
    }
}
