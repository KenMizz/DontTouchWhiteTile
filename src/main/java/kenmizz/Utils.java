package kenmizz;

import org.bukkit.Location;

public class Utils {

    public static boolean checkWallBoundary(Location pointA, Location pointB, int width, int height ) {
        int deltaX = (int)Math.abs(pointB.getX() - pointA.getX()) + 1;
        int actualHeight = (int)Math.abs(pointB.getY() - pointA.getY()) + 1;
        int deltaZ = (int)Math.abs(pointB.getZ() - pointA.getZ()) + 1;
        int actualWidth = Math.max(deltaX, deltaZ);
        return actualWidth == width && actualHeight == height;
    }

    public static boolean checkAreaBoundary(Location blockLocation, Location areaALocation, Location areaBLocation) {
        double MinAreaX = Math.min(areaALocation.getX(), areaBLocation.getX());
        double MinAreaZ = Math.min(areaALocation.getZ(), areaBLocation.getZ());
        double MaxAreaX = Math.max(areaALocation.getX(), areaBLocation.getX());
        double MaxAreaZ = Math.max(areaALocation.getZ(), areaBLocation.getZ());

        return blockLocation.getX() >= MinAreaX && blockLocation.getX() <= MaxAreaX &&
                blockLocation.getZ() >= MinAreaZ && blockLocation.getZ() <= MaxAreaZ;
    }
}
