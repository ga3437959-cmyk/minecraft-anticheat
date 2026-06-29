package com.anticheat.commands;

import com.anticheat.AntiCheatPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AntiCheatCommand implements CommandExecutor {
    private final AntiCheatPlugin plugin;

    public AntiCheatCommand(AntiCheatPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("anticheat.admin")) {
            sender.sendMessage("\u00a7cVocê não tem permissão!");
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.getConfigManager().reload();
                sender.sendMessage("\u00a7a✓ Configuração recarregada!");
                break;
            
            case "check":
                if (args.length < 2) {
                    sender.sendMessage("\u00a7cUso: /anticheat check <jogador>");
                } else {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        int violations = plugin.getPlayerData(target.getUniqueId()).getViolations();
                        sender.sendMessage("\u00a7e[AntiCheat] " + target.getName() + " - Violations: " + violations);
                    } else {
                        sender.sendMessage("\u00a7cJogador não encontrado!");
                    }
                }
                break;
            
            case "stats":
                sender.sendMessage("\u00a7e[AntiCheat] Estatísticas do Servidor");
                sender.sendMessage("\u00a7fPlayers online: \u00a7b" + Bukkit.getOnlinePlayers().size());
                sender.sendMessage("\u00a7fAggression Level: \u00a7b5/5");
                break;
            
            default:
                sendHelp(sender);
        }

        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("\u00a7e=== AntiCheat Commands ===");
        sender.sendMessage("\u00a7f/anticheat reload - Recarrega configuração");
        sender.sendMessage("\u00a7f/anticheat check <player> - Verifica violations");
        sender.sendMessage("\u00a7f/anticheat stats - Mostra estatísticas");
    }
}
