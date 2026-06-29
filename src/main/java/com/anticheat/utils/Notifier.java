package com.anticheat.utils;

import com.anticheat.AntiCheatPlugin;
import com.anticheat.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Notifier {
    private final AntiCheatPlugin plugin;

    public Notifier(AntiCheatPlugin plugin) {
        this.plugin = plugin;
    }

    public void notifyStaff(Player player, PlayerData data, String checkType, double value, double confidence) {
        if (!plugin.getConfigManager().getBoolean("anticheat.punishment.notify_staff", true)) {
            return;
        }

        String message = "\u00a7c[\u26a0 AntiCheat] \u00a77" + player.getName() + " \u00a7cSUSPEITA\n" +
                         "  \u00a7fCheck: \u00a7e" + checkType + "\n" +
                         "  \u00a7fValor: \u00a7e" + String.format("%.2f", value) + "\n" +
                         "  \u00a7fViolations: \u00a7e" + data.getViolations() + "\n" +
                         "  \u00a7fConfiança: \u00a7e" + String.format("%.0f", confidence) + "%";

        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("anticheat.notify")) {
                staff.sendMessage(message);
            }
        }

        plugin.getLogger().warn("[" + checkType + "] " + player.getName() + " | Value: " + value + " | Confidence: " + confidence + "%");
    }
}
