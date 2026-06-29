package com.anticheat.listeners;

import com.anticheat.AntiCheatPlugin;
import com.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final AntiCheatPlugin plugin;

    public PlayerListener(AntiCheatPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getLogger().debug("[+] " + player.getName() + " joined");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getLogger().debug("[-] " + player.getName() + " left");
    }
}
