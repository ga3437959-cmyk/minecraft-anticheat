package com.anticheat.manager;

import com.anticheat.AntiCheatPlugin;
import com.anticheat.data.PlayerData;
import com.anticheat.utils.Notifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PunishmentManager {
    private final AntiCheatPlugin plugin;
    private final Notifier notifier;

    public PunishmentManager(AntiCheatPlugin plugin, Notifier notifier) {
        this.plugin = plugin;
        this.notifier = notifier;
    }

    public void handleViolation(Player player, PlayerData data, String checkType) {
        int violations = data.getViolations();
        int warnThreshold = plugin.getConfigManager().getInt("anticheat.punishment.warn_threshold", 10);
        int kickThreshold = plugin.getConfigManager().getInt("anticheat.punishment.kick_threshold", 25);
        int banThreshold = plugin.getConfigManager().getInt("anticheat.punishment.ban_threshold", 50);

        if (violations >= banThreshold) {
            banPlayer(player);
        } else if (violations >= kickThreshold) {
            kickPlayer(player);
        } else if (violations >= warnThreshold) {
            warnPlayer(player);
        }
    }

    private void warnPlayer(Player player) {
        player.sendMessage("\u00a7c[AntiCheat] \u26a0 Você recebeu um aviso!");
    }

    private void kickPlayer(Player player) {
        player.kickPlayer("\u00a7c[AntiCheat] Você foi kickado por suspeita de cheat!");
    }

    private void banPlayer(Player player) {
        if (plugin.getConfigManager().getBoolean("anticheat.punishment.announce_ban", true)) {
            Bukkit.broadcastMessage("\u00a7c[AntiCheat] \u00a7e" + player.getName() + " \u00a7cfoi banido por cheating!");
        }
        player.kickPlayer("\u00a7c[AntiCheat] Você foi banido por cheating!");
    }
}
