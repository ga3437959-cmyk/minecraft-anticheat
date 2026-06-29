package com.anticheat.data;

import java.util.*;

public class PlayerData {
    private final UUID uuid;
    private final String name;
    private int violations = 0;
    private long lastMoveTime = 0;
    private long lastClickTime = 0;
    private boolean inCombat = false;
    private final Map<String, Integer> violationsByType = new HashMap<>();
    private double avgSpeed = 0;
    private int sampleCount = 0;

    public PlayerData(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public void addViolation(String type) {
        this.violations++;
        this.violationsByType.put(type, this.violationsByType.getOrDefault(type, 0) + 1);
    }

    public void resetViolations() {
        this.violations = 0;
        this.violationsByType.clear();
    }

    public void recordSpeed(double speed) {
        avgSpeed = (avgSpeed * sampleCount + speed) / (sampleCount + 1);
        sampleCount++;
    }

    // Getters
    public UUID getUuid() { return uuid; }
    public String getName() { return name; }
    public int getViolations() { return violations; }
    public long getLastMoveTime() { return lastMoveTime; }
    public void setLastMoveTime(long time) { this.lastMoveTime = time; }
    public long getLastClickTime() { return lastClickTime; }
    public void setLastClickTime(long time) { this.lastClickTime = time; }
    public boolean isInCombat() { return inCombat; }
    public void setInCombat(boolean combat) { this.inCombat = combat; }
    public Map<String, Integer> getViolationsByType() { return violationsByType; }
    public double getAvgSpeed() { return avgSpeed; }
}
