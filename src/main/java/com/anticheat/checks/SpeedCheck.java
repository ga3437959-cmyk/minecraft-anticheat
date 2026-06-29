package com.anticheat.checks;

import com.anticheat.AntiCheatPlugin;
import com.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class SpeedCheck implements Check {
    private final AntiCheatPlugin plugin;

    public SpeedCheck(AntiCheatPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void check(Player player, PlayerData data, PlayerMoveEvent event) {
        if (shouldBypass(player)) return;
        if (!isEnabled()) return;

        double distance = event.getFrom().distance(event.getTo());
        if (distance < 0.01) return;

        double threshold = plugin.getConfigManager().getDouble("anticheat.checks.speed.threshold", 7.5);
        
        if (player.isSprinting()) threshold *= 1.3;
        if (distance > threshold) {
            data.addViolation("SPEED");
            double confidence = Math.min(100, (distance / threshold) * 85);
            plugin.getNotifier().notifyStaff(player, data, "SPEED", distance, confidence);
            plugin.getPunishmentManager().handleViolation(player, data, "SPEED");
        }
        
        data.recordSpeed(distance);
    }

    @Override
    public String getName() {
        return "SPEED";
    }

    @Override
    public boolean isEnabled() {
        return plugin.getConfigManager().getBoolean("anticheat.checks.speed.enabled", true);
    }

    private boolean shouldBypass(Player player) {
        return player.hasPermission("anticheat.bypass") || player.isOp();
    }
}
