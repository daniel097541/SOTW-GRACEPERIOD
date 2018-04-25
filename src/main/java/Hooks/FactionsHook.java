package Hooks;

import graceperiod.sotwgraceperiod.SOTWGracePeriod;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class FactionsHook {
    protected final String undetectablePermission;
    protected final SOTWGracePeriod plugin;

    public FactionsHook(){
        this.plugin = SOTWGracePeriod.getInstance();
        this.undetectablePermission = plugin.getConfig().getString("Anti-Spawner-Mine.undetectable-permission");
    }

    public abstract String getFactionAt(String world, int x, int z);

    public abstract String getFactionAtLocation(Location loc);

    public abstract String getFaction(Player player);

    public abstract boolean nearbyEnemies(Player player,double radius);

    public abstract boolean playerIsInOthersTerritory(Player player);

    public abstract boolean locationInEnemyTerritory(Player player, Location location);

    public abstract boolean locationInOthersTerritory(Player player, Location location);

    public abstract void broadCastPlayerLoggedIn(Player player,String message);

    public abstract boolean playerInAllyTerritory(Player player);

    public abstract boolean playerInOwnTerritory(Player player);

    public abstract boolean playerInEnemyTerritory(Player player);

    public abstract boolean playerInWildernessTerritory(Player player);

    public abstract boolean playerInWarzoneTerritory(Player player);

    public abstract boolean playerInSafezoneTerritory(Player player);

    public abstract boolean locationInOwnLand(Player player, Location location);

}

