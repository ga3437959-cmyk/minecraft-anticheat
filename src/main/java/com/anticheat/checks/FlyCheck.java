package com.anticheat.checks;

import com.anticheat.AntiCheatPlugin;
import com.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class FlyCheck implements Check {
    private final AntiCheatPlugin plugin;

    public FlyCheck(AntiCheatPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void check(Player player, PlayerData data, PlayerMoveEvent event) {
        if (shouldBypass(player)) return;
        if (!isEnabled()) return;
        if (player.isOnGround() || player.isInWater()) return;

        double ascension = event.getTo().getY() - event.getFrom().getY();
        double maxAscension = plugin.getConfigManager().getDouble("anticheat.checks.fly.max_ascension", 0.42);

        if (ascension > maxAscension && ascension > 0) {
            data.addViolation("FLY");
            double confidence = Math.min(100, (ascension / maxAscension) * 90);
            plugin.getNotifier().notifyStaff(player, data, "FLY", ascension, confidence);
            plugin.getPunishmentManager().handleViolation(player, data, "FLY");
        }
    }

    @Override
    public String getName() {
        return "FLY";
    }

    @Override
    public boolean isEnabled() {
        return plugin.getConfigManager().getBoolean("anticheat.checks.fly.enabled", true);
    }

    private boolean shouldBypass(Player player) {
        return player.hasPermission("anticheat.bypass") || player.isOp();
    }
}
