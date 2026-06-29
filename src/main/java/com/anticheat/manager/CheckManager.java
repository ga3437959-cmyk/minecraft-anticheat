package com.anticheat.manager;

import com.anticheat.AntiCheatPlugin;
import com.anticheat.checks.*;
import com.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.*;

public class CheckManager {
    private final AntiCheatPlugin plugin;
    private final List<Check> checks = new ArrayList<>();

    public CheckManager(AntiCheatPlugin plugin) {
        this.plugin = plugin;
        checks.add(new SpeedCheck(plugin));
        checks.add(new FlyCheck(plugin));
        checks.add(new ReachCheck(plugin));
        checks.add(new AutoClickCheck(plugin));
        checks.add(new BadPacketsCheck(plugin));
    }

    public void performChecks(Player player, PlayerData data, PlayerMoveEvent event) {
        if (shouldBypass(player)) return;
        
        for (Check check : checks) {
            if (!check.isEnabled()) continue;
            try {
                check.check(player, data, event);
            } catch (Exception e) {
                plugin.getLogger().severe("Error in " + check.getName() + ": " + e.getMessage());
            }
        }
    }

    private boolean shouldBypass(Player player) {
        return player.hasPermission("anticheat.bypass") || player.isOp() || 
               player.getGameMode().name().equals("CREATIVE") ||
               player.getGameMode().name().equals("SPECTATOR");
    }
}
