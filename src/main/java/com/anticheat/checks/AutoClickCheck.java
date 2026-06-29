package com.anticheat.checks;

import com.anticheat.AntiCheatPlugin;
import com.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class AutoClickCheck implements Check {
    private final AntiCheatPlugin plugin;

    public AutoClickCheck(AntiCheatPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void check(Player player, PlayerData data, PlayerMoveEvent event) {
        // AutoClick check é acionado em PlayerInteractEvent
    }

    @Override
    public String getName() {
        return "AUTOCLICK";
    }

    @Override
    public boolean isEnabled() {
        return plugin.getConfigManager().getBoolean("anticheat.checks.autoclick.enabled", true);
    }
}
