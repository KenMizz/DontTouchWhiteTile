package kenmizz.gameconfig;

import org.bukkit.Location;

public class GameConfig {

    static class Holder {

        enum settingStage {
            POINT_A,
            POINT_B,
            START_SIGN
        }

        private Location pointA;
        private Location pointB;
        private String startSignUUID;

        private settingStage currentSettingStage = settingStage.POINT_A;

        public Holder pointA(Location pointA) {
            this.pointA = pointA;
            return this;
        }

        public Holder pointB(Location pointB) {
            this.pointB = pointB;
            return this;
        }

        public Holder startSignUUID(String UUID) {
            this.startSignUUID = startSignUUID;
            return this;
        }

        public Location getPointA() {
            return pointA;
        }

        public Location getPointB() {
            return pointB;
        }

        public String getStartSignUUID() {
            return startSignUUID;
        }

        public settingStage getCurrentSettingStage() {
            return currentSettingStage;
        }

        public void setCurrentSettingStage(settingStage currentSettingStage) {
            this.currentSettingStage = currentSettingStage;
        }
    }
}
