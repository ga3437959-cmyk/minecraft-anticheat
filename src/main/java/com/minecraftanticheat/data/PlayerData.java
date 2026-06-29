package com.minecraftanticheat.data;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * Dados de um jogador armazenados pelo AntiCheat
 * Acumula evidências e flags de suspeita
 */
public class PlayerData {
    
    private final String playerName;
    private final UUID playerUUID;
    
    // ============ SISTEMA DE FLAGS ============
    
    /** Pontos totais de suspeita */
    private int suspicionPoints = 0;
    
    /** Histórico de flags com timestamps */
    private final LinkedList<FlagRecord> flagHistory = new LinkedList<>();
    
    // ============ COMBAT ============
    
    /** Última posição registrada */
    private Vector lastPosition;
    
    /** Última velocidade registrada */
    private Vector lastVelocity;
    
    /** Último yaw (rotação horizontal) */
    private float lastYaw;
    
    /** Último pitch (rotação vertical) */
    private float lastPitch;
    
    /** Ticks desde o último hit */
    private int ticksSinceLastHit = 0;
    
    /** CPS médio do último segundo */
    private double avgCPS = 0;
    
    /** Contador de clicks no segundo atual */
    private int clicksThisSecond = 0;
    
    /** Timestamp do último click */
    private long lastClickTime = 0;
    
    /** Verificações de reach atual */
    private double currentReachDistance = 0;
    
    // ============ MOVIMENTO ============
    
    /** Últimas 5 velocidades Y para análise */
    private final LinkedList<Double> yVelocityHistory = new LinkedList<>();
    
    /** Último dano recebido */
    private double lastDamageTaken = 0;
    
    /** Último knockback aplicado */
    private Vector lastKnockbackApplied = new Vector(0, 0, 0);
    
    /** Tick do último knockback */
    private int lastKnockbackTick = 0;
    
    // ============ ESTATÍSTICAS ============
    
    /** Quando o jogador conectou */
    private final long connectTime = System.currentTimeMillis();
    
    /** Último movimento registrado */
    private long lastMovementTime = System.currentTimeMillis();
    
    /** Ping do jogador */
    private int ping = 0;
    
    /** Modo de jogo (SURVIVAL, CREATIVE, SPECTATOR, etc) */
    private String gameMode = "SURVIVAL";
    
    // ============ PUNIÇÕES ============
    
    /** Quantas vezes foi punido */
    private int punitionCount = 0;
    
    /** Status de ban */
    private boolean banned = false;
    
    /** Data de expiração do ban (0 = permanente) */
    private long banExpireTime = 0;
    
    /** Razão do ban */
    private String banReason = "";
    
    /**
     * Construtor
     */
    public PlayerData(Player player) {
        this.playerName = player.getName();
        this.playerUUID = player.getUniqueId();
        this.lastPosition = player.getLocation().toVector();
        this.ping = player.getPing();
        this.gameMode = player.getGameMode().toString();
    }
    
    /**
     * Adicionar uma flag com peso
     */
    public void addFlag(String type, int weight, String reason) {
        FlagRecord flag = new FlagRecord(type, weight, reason);
        flagHistory.add(flag);
        
        // Manter apenas os últimos 500 registros
        if (flagHistory.size() > 500) {
            flagHistory.removeFirst();
        }
        
        // Aumentar pontos de suspeita
        suspicionPoints += weight;
    }
    
    /**
     * Reduzir flags com decay
     */
    public void decayFlags(int decayAmount) {
        if (suspicionPoints > 0) {
            suspicionPoints -= decayAmount;
            if (suspicionPoints < 0) {
                suspicionPoints = 0;
            }
        }
    }
    
    /**
     * Registrar um movimento
     */
    public void updateMovement(Vector position, Vector velocity, float yaw, float pitch, int ping) {
        this.lastPosition = position;
        this.lastVelocity = velocity;
        this.lastYaw = yaw;
        this.lastPitch = pitch;
        this.ping = ping;
        this.lastMovementTime = System.currentTimeMillis();
        
        // Manter histórico de velocidades Y
        if (yVelocityHistory.size() > 10) {
            yVelocityHistory.removeFirst();
        }
        yVelocityHistory.add(velocity.getY());
    }
    
    /**
     * Registrar um hit
     */
    public void registerHit() {
        long now = System.currentTimeMillis();
        
        if (now - lastClickTime < 1000) {
            clicksThisSecond++;
        } else {
            this.avgCPS = clicksThisSecond;
            clicksThisSecond = 1;
        }
        
        this.lastClickTime = now;
        this.ticksSinceLastHit = 0;
    }
    
    /**
     * Incrementar ticks desde último hit
     */
    public void incrementTicksSinceHit() {
        ticksSinceLastHit++;
    }
    
    /**
     * Registrar dano recebido
     */
    public void registerDamageTaken(double damage, Vector knockback) {
        this.lastDamageTaken = damage;
        this.lastKnockbackApplied = knockback;
        this.lastKnockbackTick = 0;
    }
    
    /**
     * Incrementar tick de knockback
     */
    public void incrementKnockbackTick() {
        lastKnockbackTick++;
    }
    
    /**
     * Aplicar punição
     */
    public void addPunition() {
        punitionCount++;
    }
    
    /**
     * Ban jogador
     */
    public void setBanned(boolean banned, long expireTime, String reason) {
        this.banned = banned;
        this.banExpireTime = expireTime;
        this.banReason = reason;
    }
    
    /**
     * Verificar se ban expirou
     */
    public boolean isBanExpired() {
        if (!banned || banExpireTime == 0) return false;
        return System.currentTimeMillis() > banExpireTime;
    }
    
    /**
     * Limpar todas as flags
     */
    public void clearAllFlags() {
        suspicionPoints = 0;
        flagHistory.clear();
    }
    
    // ============ GETTERS ============
    
    public String getPlayerName() { return playerName; }
    public UUID getPlayerUUID() { return playerUUID; }
    
    public int getSuspicionPoints() { return suspicionPoints; }
    public LinkedList<FlagRecord> getFlagHistory() { return flagHistory; }
    
    public Vector getLastPosition() { return lastPosition; }
    public Vector getLastVelocity() { return lastVelocity; }
    public float getLastYaw() { return lastYaw; }
    public float getLastPitch() { return lastPitch; }
    
    public int getTicksSinceLastHit() { return ticksSinceLastHit; }
    public double getAvgCPS() { return avgCPS; }
    public double getCurrentReachDistance() { return currentReachDistance; }
    
    public LinkedList<Double> getYVelocityHistory() { return yVelocityHistory; }
    public double getLastDamageTaken() { return lastDamageTaken; }
    public Vector getLastKnockbackApplied() { return lastKnockbackApplied; }
    public int getLastKnockbackTick() { return lastKnockbackTick; }
    
    public long getConnectTime() { return connectTime; }
    public long getLastMovementTime() { return lastMovementTime; }
    public int getPing() { return ping; }
    public String getGameMode() { return gameMode; }
    
    public int getPunitionCount() { return punitionCount; }
    public boolean isBanned() { return banned; }
    public long getBanExpireTime() { return banExpireTime; }
    public String getBanReason() { return banReason; }
    
    // ============ SETTERS ============
    
    public void setCurrentReachDistance(double reach) { this.currentReachDistance = reach; }
    public void setGameMode(String gameMode) { this.gameMode = gameMode; }
    
    /**
     * Classe interna para registrar uma flag
     */
    public static class FlagRecord {
        public final String type;
        public final int weight;
        public final String reason;
        public final long timestamp;
        
        public FlagRecord(String type, int weight, String reason) {
            this.type = type;
            this.weight = weight;
            this.reason = reason;
            this.timestamp = System.currentTimeMillis();
        }
        
        @Override
        public String toString() {
            return String.format("[%s] %s (+%d pts): %s", 
                new Date(timestamp), type, weight, reason);
        }
    }
}
