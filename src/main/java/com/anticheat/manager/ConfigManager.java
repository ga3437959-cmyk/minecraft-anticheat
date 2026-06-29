package com.anticheat.manager;

import com.anticheat.AntiCheatPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final AntiCheatPlugin plugin;
    private FileConfiguration config;

    public ConfigManager(AntiCheatPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public String getString(String path, String def) {
        return config.getString(path, def);
    }

    public int getInt(String path, int def) {
        return config.getInt(path, def);
    }

    public double getDouble(String path, double def) {
        return config.getDouble(path, def);
    }

    public boolean getBoolean(String path, boolean def) {
        return config.getBoolean(path, def);
    }
}
