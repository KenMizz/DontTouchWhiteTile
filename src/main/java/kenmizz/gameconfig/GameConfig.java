package kenmizz.gameconfig;

import org.bukkit.Location;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.Map;

public class GameConfig {

    private final Location areaA;
    private final Location areaB;
    private final Location pointA;
    private final Location pointB;

    private final int timer;

    private GameConfig(Builder builder) {
        areaA = builder.areaA;
        areaB = builder.areaB;
        pointA = builder.pointA;
        pointB = builder.pointB;
        timer = builder.timer;
    }

    public Location getAreaA() {
        return areaA;
    }

    public Location getAreaB() {
        return areaB;
    }

    public Location getPointA() {
        return pointA;
    }

    public Location getPointB() {
        return pointB;
    }

    public int getTimer() {
        return timer;
    }

    public String toYaml() {
        Map<String, Object> configMap = new LinkedHashMap<>();
        Map<String, Object> coordinatesMap = new LinkedHashMap<>();
        Map<String, Object> settingsMap = new LinkedHashMap<>();
        coordinatesMap.put("areaA", areaA.serialize());
        coordinatesMap.put("areaB", areaB.serialize());
        coordinatesMap.put("pointA", pointA.serialize());
        coordinatesMap.put("pointB", pointB.serialize());
        settingsMap.put("timer", timer); // seconds
        configMap.put("coordinates", coordinatesMap);
        configMap.put("settings", settingsMap);
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        return yaml.dump(configMap);
    }

    static class Builder {

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

        private final int timer = 30;

        private settingStage currentSettingStage = settingStage.AREA_A;

        public Builder areaA(Location areaA) {
            this.areaA = areaA;
            return this;
        }

        public Builder areaB(Location areaB) {
            this.areaB = areaB;
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

        public Location getAreaA() {
            return areaA;
        }

        public Location getAreaB() {
            return areaB;
        }

        public Location getPointA() {
            return pointA;
        }

        public Location getPointB() {
            return pointB;
        }

        public int getTimer() {
            return timer;
        }

        public settingStage getCurrentSettingStage() {
            return currentSettingStage;
        }

        public void setCurrentSettingStage(settingStage currentSettingStage) {
            this.currentSettingStage = currentSettingStage;
        }

        public GameConfig build() {
            return new GameConfig(this);
        }
    }
}
