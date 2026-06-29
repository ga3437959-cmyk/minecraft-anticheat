package com.anticheat;

import com.anticheat.checks.*;
import com.anticheat.commands.AntiCheatCommand;
import com.anticheat.data.PlayerData;
import com.anticheat.listeners.PlayerListener;
import com.anticheat.listeners.MovementListener;
import com.anticheat.manager.CheckManager;
import com.anticheat.manager.ConfigManager;
import com.anticheat.manager.PunishmentManager;
import com.anticheat.utils.Logger;
import com.anticheat.utils.Notifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * AntiCheat Main Plugin
 * Enterprise-grade Bukkit anti-cheat system
 * Author: ga3437959-cmyk (29+ years)
 */
public class AntiCheatPlugin extends JavaPlugin implements Listener {

    private static AntiCheatPlugin instance;
    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();
    
    private ConfigManager configManager;
    private CheckManager checkManager;
    private PunishmentManager punishmentManager;
    private Logger logger;
    private Notifier notifier;

    @Override
    public void onEnable() {
        instance = this;
        
        logger = new Logger(this);
        logger.info("===============================================");
        logger.info("  AntiCheat v1.0.0 - Enterprise Edition");
        logger.info("  Author: ga3437959-cmyk");
        logger.info("  Aggressive Detection + Smart Validation");
        logger.info("===============================================");
        
        try {
            saveDefaultConfig();
            configManager = new ConfigManager(this);
            notifier = new Notifier(this);
            punishmentManager = new PunishmentManager(this, notifier);
            checkManager = new CheckManager(this);
            
            Bukkit.getPluginManager().registerEvents(this, this);
            Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
            Bukkit.getPluginManager().registerEvents(new MovementListener(this), this);
            
            getCommand("anticheat").setExecutor(new AntiCheatCommand(this));
            
            logger.info("✓ AntiCheat ENABLED - READY FOR PRODUCTION");
            logger.info("===============================================");
            
        } catch (Exception e) {
            logger.severe("Failed to enable AntiCheat!");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        logger.info("AntiCheat DISABLED");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        playerDataMap.put(event.getPlayer().getUniqueId(), 
            new PlayerData(event.getPlayer().getUniqueId(), event.getPlayer().getName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        playerDataMap.remove(event.getPlayer().getUniqueId());
    }

    public static AntiCheatPlugin getInstance() {
        return instance;
    }

    public PlayerData getPlayerData(UUID uuid) {
        return playerDataMap.computeIfAbsent(uuid, id -> new PlayerData(id, ""));
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public CheckManager getCheckManager() {
        return checkManager;
    }

    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }

    public Logger getLogger() {
        return logger;
    }

    public Notifier getNotifier() {
        return notifier;
    }
}
