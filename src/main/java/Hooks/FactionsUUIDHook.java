package Hooks;

import com.massivecraft.factions.*;
import com.massivecraft.factions.struct.Relation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionsUUIDHook extends FactionsHook{

    public FactionsUUIDHook() {
        super();
    }


    @Override
    public String getFactionAt(String world, int x, int z) {
        FLocation floc = new FLocation(world, x, z);
        return Board.getInstance().getFactionAt(floc).getId();
    }

    @Override
    public String getFactionAtLocation(Location loc) {
        FLocation location = new FLocation(loc);
        return Board.getInstance().getFactionAt(location).getTag();
    }

    @Override
    public String getFaction(Player player) {
        return FPlayers.getInstance().getByPlayer(player).getFaction().getTag();
    }

    @Override
    public boolean nearbyEnemies(Player player,double radius) {

        List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
        Boolean b = false;
        FPlayer f = FPlayers.getInstance().getByPlayer(player);
        for(Entity ent : nearbyEntities){
            if(ent instanceof Player && !ent.hasPermission(undetectablePermission)){
                FPlayer fp = FPlayers.getInstance().getByPlayer((Player) ent);
                if(!fp.getFactionId().equals(f.getFactionId()) && !fp.getFaction().getRelationWish(f.getFaction()).isAlly()){

                    b = true;
                    break;

                }
            }

        }
        return b;


    }

    @Override
    public boolean playerIsInOthersTerritory(Player player) {


        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        return fPlayer.isInOthersTerritory();


    }

    @Override
    public boolean locationInEnemyTerritory(Player player, Location location) {
        FPlayer fp = FPlayers.getInstance().getByPlayer(player);
        Faction playersFaction = fp.getFaction();
        Faction fAt = Board.getInstance().getFactionAt(new FLocation(location));
        boolean enemy = fAt.getRelationWish(playersFaction).equals(Relation.ENEMY);
        boolean wild = fAt.isWilderness();
        boolean warzone = fAt.isWarZone();
        boolean safezone = fAt.isSafeZone();
        return enemy && !warzone &&!safezone &&!wild;
    }

    @Override
    public boolean locationInOthersTerritory(Player player, Location location) {

        FPlayer fp = FPlayers.getInstance().getByPlayer(player);
        Faction playersFaction = fp.getFaction();
        Faction fAt = Board.getInstance().getFactionAt(new FLocation(location));
        return !fAt.isWilderness() && !fAt.isWarZone() && !fAt.isSafeZone() && !playersFaction.getTag().equals(fAt.getTag());
    }

    @Override
    public void broadCastPlayerLoggedIn(Player player, String message) {
        FPlayer fp = FPlayers.getInstance().getByPlayer(player);
        Faction faction = fp.getFaction();
        for(FPlayer fPlayer : faction.getFPlayersWhereOnline(true))
            fPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

    }

    @Override
    public boolean playerInAllyTerritory(Player player) {
        FPlayer fp = FPlayers.getInstance().getByPlayer(player);
        return fp.isInAllyTerritory();
    }

    @Override
    public boolean playerInOwnTerritory(Player player) {
        FPlayer fp = FPlayers.getInstance().getByPlayer(player);
        return fp.isInOwnTerritory();    }

    @Override
    public boolean playerInEnemyTerritory(Player player) {
        FPlayer fp = FPlayers.getInstance().getByPlayer(player);
        return fp.isInEnemyTerritory();    }

    @Override
    public boolean playerInWildernessTerritory(Player player) {
        Faction faction = Board.getInstance().getFactionAt(new FLocation(player.getLocation()));
        return faction.isWilderness();
    }

    @Override
    public boolean playerInWarzoneTerritory(Player player) {
        Faction faction = Board.getInstance().getFactionAt(new FLocation(player.getLocation()));
        return faction.isWarZone();    }

    @Override
    public boolean playerInSafezoneTerritory(Player player) {
        Faction faction = Board.getInstance().getFactionAt(new FLocation(player.getLocation()));
        return faction.isSafeZone();    }

    @Override
    public boolean locationInOwnLand(Player player, Location location) {
        FLocation fLocation = new FLocation(location);
        return Board.getInstance().getFactionAt(fLocation).equals(FPlayers.getInstance().getByPlayer(player).getFaction());
    }


}