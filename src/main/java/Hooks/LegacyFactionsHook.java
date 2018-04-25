package Hooks;

import net.redstoneore.legacyfactions.Relation;
import net.redstoneore.legacyfactions.entity.Board;
import net.redstoneore.legacyfactions.entity.FPlayer;
import net.redstoneore.legacyfactions.entity.FPlayerColl;
import net.redstoneore.legacyfactions.entity.Faction;
import net.redstoneore.legacyfactions.locality.Locality;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class LegacyFactionsHook extends FactionsHook{

    public LegacyFactionsHook( ) {
        super();
    }


    @Override
    public String getFactionAt(String world, int x, int z) {
        Location location = new Location(plugin.getServer().getWorld(world), x, 0, z);
        Locality lclt = Locality.of(location);
        return Board.get().getFactionAt(lclt).getTag();
    }

    @Override
    public String getFactionAtLocation(Location loc) {
        return null;
    }

    @Override
    public String getFaction(Player player) {


        FPlayer fp = FPlayerColl.get(player);
        return fp.getFaction().getTag();

    }

    @Override
    public boolean nearbyEnemies(Player player, double radius) {


        List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
        Boolean b = false;
        FPlayer f = FPlayerColl.get(player);
        for(Entity ent : nearbyEntities){
            if(ent instanceof Player && !ent.hasPermission(undetectablePermission)){
                FPlayer fp = FPlayerColl.get(ent);
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
        FPlayer fp = FPlayerColl.get(player);
        return fp.isInOthersTerritory();
    }

    @Override
    public boolean locationInEnemyTerritory(Player player, Location location) {
        FPlayer fp = FPlayerColl.get(player);
        Faction faction = fp.getFaction();
        Locality lclt = Locality.of(location);
        Faction fAt = Board.get().getFactionAt(lclt);
        return fAt.getRelationWish(faction).equals(Relation.ENEMY);
    }

    @Override
    public boolean locationInOthersTerritory(Player player, Location location) {

        FPlayer fp = FPlayerColl.get(player);
        Faction faction = fp.getFaction();
        Locality lclt = Locality.of(location);
        Faction fAt = Board.get().getFactionAt(lclt);
        return !fAt.getTag().equals(faction.getTag());

    }

    @Override
    public void broadCastPlayerLoggedIn(Player player, String message) {

        FPlayer fp = FPlayerColl.get(player);
        Faction faction = fp.getFaction();
        for(FPlayer fPlayer: faction.getWhereOnline(true))
            fPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    @Override
    public boolean playerInAllyTerritory(Player player) {
        FPlayer fp = FPlayerColl.get(player);
        return fp.isInAllyTerritory();
    }

    @Override
    public boolean playerInOwnTerritory(Player player) {
        FPlayer fp = FPlayerColl.get(player);
        return fp.isInOwnTerritory();
    }

    @Override
    public boolean playerInEnemyTerritory(Player player) {
        FPlayer fp = FPlayerColl.get(player);
        return fp.isInEnemyTerritory();
    }

    @Override
    public boolean playerInWildernessTerritory(Player player) {
        Locality lclt = Locality.of(player.getLocation());
        Faction fAt = Board.get().getFactionAt(lclt);
        return fAt.isWilderness();}

    @Override
    public boolean playerInWarzoneTerritory(Player player) {
        Locality lclt = Locality.of(player.getLocation());
        Faction fAt = Board.get().getFactionAt(lclt);
        return fAt.isWarZone();    }

    @Override
    public boolean playerInSafezoneTerritory(Player player) {
        Locality lclt = Locality.of(player.getLocation());
        Faction fAt = Board.get().getFactionAt(lclt);
        return fAt.isSafeZone();    }

    @Override
    public boolean locationInOwnLand(Player player, Location location) {
        return Board.get().getFactionAt(Locality.of(location)).equals(FPlayerColl.get(player).getFaction());
    }

}
