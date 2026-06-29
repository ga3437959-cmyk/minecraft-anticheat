package com.anticheat.listeners;

import com.anticheat.AntiCheatPlugin;
import com.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementListener implements Listener {
    private final AntiCheatPlugin plugin;

    public MovementListener(AntiCheatPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        if (!plugin.getConfigManager().getBoolean("anticheat.enabled", true)) return;
        if (event.getFrom().equals(event.getTo())) return;

        Player player = event.getPlayer();
        PlayerData data = plugin.getPlayerData(player.getUniqueId());
        
        plugin.getCheckManager().performChecks(player, data, event);
    }
}
