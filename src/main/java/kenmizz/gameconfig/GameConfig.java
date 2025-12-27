package kenmizz.gameconfig;

import org.bukkit.Location;

public class GameConfig {

    private final String name;
    private final Location pointA;
    private final Location pointB;
    private final Location startSign;

    public GameConfig(Builder builder) {
        this.name = builder.name;
        this.pointA = builder.pointA;
        this.pointB = builder.pointB;
        this.startSign = builder.startSign;
    }

    public Location getPointA() {
        return this.pointA;
    }

    public Location getPointB() {
        return this.pointB;
    }

    static class Builder {

        private String name;
        private Location pointA;
        private Location pointB;
        private Location startSign;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder pointA(Location pointA) {
            this.pointA = pointA;
            return this;
        }

        public Builder pointB(Location pointB) {
            this.pointB = pointB;
            return this;
        }

        public Builder startSign(Location startSign) {
            this.startSign = startSign;
            return this;
        }

        public GameConfig build() {
            return new GameConfig(this);
        }
    }
}
