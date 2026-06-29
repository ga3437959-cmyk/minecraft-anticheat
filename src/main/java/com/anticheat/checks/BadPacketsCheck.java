package com.anticheat.checks;

import com.anticheat.AntiCheatPlugin;
import com.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class BadPacketsCheck implements Check {
    private final AntiCheatPlugin plugin;

    public BadPacketsCheck(AntiCheatPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void check(Player player, PlayerData data, PlayerMoveEvent event) {
        if (event.getTo() == null) return;
        
        double distance = event.getFrom().distance(event.getTo());
        if (distance > 50) {
            data.addViolation("BADPACKETS");
            plugin.getPunishmentManager().handleViolation(player, data, "BADPACKETS");
        }
    }

    @Override
    public String getName() {
        return "BADPACKETS";
    }

    @Override
    public boolean isEnabled() {
        return plugin.getConfigManager().getBoolean("anticheat.checks.badpackets.enabled", true);
    }
}
