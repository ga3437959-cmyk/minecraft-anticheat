package com.anticheat.utils;

import com.anticheat.AntiCheatPlugin;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final AntiCheatPlugin plugin;
    private final File logFile;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Logger(AntiCheatPlugin plugin) {
        this.plugin = plugin;
        this.logFile = new File(plugin.getDataFolder(), "anticheat.log");
        
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void info(String message) {
        plugin.getServer().getConsoleSender().sendMessage("[AntiCheat] " + message);
        logMessage("INFO", message);
    }

    public void warn(String message) {
        plugin.getServer().getConsoleSender().sendMessage("\u00a7e[AntiCheat] \u00a7f" + message);
        logMessage("WARN", message);
    }

    public void severe(String message) {
        plugin.getServer().getConsoleSender().sendMessage("\u00a7c[AntiCheat] \u00a7f" + message);
        logMessage("SEVERE", message);
    }

    public void debug(String message) {
        if (!plugin.getConfigManager().getBoolean("anticheat.debug", false)) return;
        plugin.getServer().getConsoleSender().sendMessage("\u00a75[AntiCheat DEBUG] \u00a7f" + message);
        logMessage("DEBUG", message);
    }

    private void logMessage(String level, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = "[" + timestamp + "] [" + level + "] " + message;
        
        try {
            java.nio.file.Files.write(
                logFile.toPath(),
                (logEntry + "\n").getBytes(),
                java.nio.file.StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
