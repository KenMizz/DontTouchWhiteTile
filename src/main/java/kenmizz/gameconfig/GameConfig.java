package kenmizz.gameconfig;

import org.bukkit.Location;

public class GameConfig {

    static class Holder {

        enum settingStage {
            AREA_A,
            AREA_B,
            POINT_A,
            POINT_B,
            START_SIGN
        }

        private Location areaA;
        private Location areaB;
        private Location pointA;
        private Location pointB;

        private settingStage currentSettingStage = settingStage.AREA_A;

        public Holder areaA(Location areaA) {
            this.areaA = areaA;
            return this;
        }

        public Holder areaB(Location areaB) {
            this.areaB = areaB;
            return this;
        }

        public Holder pointA(Location pointA) {
            this.pointA = pointA;
            return this;
        }

        public Holder pointB(Location pointB) {
            this.pointB = pointB;
            return this;
        }

        public Location getPointA() {
            return pointA;
        }

        public Location getPointB() {
            return pointB;
        }

        public settingStage getCurrentSettingStage() {
            return currentSettingStage;
        }

        public void setCurrentSettingStage(settingStage currentSettingStage) {
            this.currentSettingStage = currentSettingStage;
        }
    }
}
