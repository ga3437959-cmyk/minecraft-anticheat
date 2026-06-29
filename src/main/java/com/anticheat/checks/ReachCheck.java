package com.anticheat.checks;

import com.anticheat.AntiCheatPlugin;
import com.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class ReachCheck implements Check {
    private final AntiCheatPlugin plugin;

    public ReachCheck(AntiCheatPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void check(Player player, PlayerData data, PlayerMoveEvent event) {
        // Reach check é acionado em PlayerInteractEvent
    }

    @Override
    public String getName() {
        return "REACH";
    }

    @Override
    public boolean isEnabled() {
        return plugin.getConfigManager().getBoolean("anticheat.checks.reach.enabled", true);
    }
}
