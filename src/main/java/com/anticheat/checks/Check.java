package com.anticheat.checks;

import com.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public interface Check {
    void check(Player player, PlayerData data, PlayerMoveEvent event);
    String getName();
    boolean isEnabled();
}
