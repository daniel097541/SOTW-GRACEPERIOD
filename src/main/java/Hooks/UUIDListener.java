package Hooks;

import Events.BitchClaimEvent;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.event.LandClaimEvent;
import graceperiod.sotwgraceperiod.SOTWGracePeriod;
import Configurations.Configurations;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;


public class UUIDListener implements Listener {


    private int radius;
    private FactionsHook hook;
    private String bitchClaimCancelled;
    private boolean enabled;
    private String nearestFactionName;
    private int distanceToNearestFaction;

    public UUIDListener() {

        load();

    }

    public void load(){
        enabled = Configurations.graceConfig.getBoolean("Grace-Period.Bitch-Claim.disabled");
        radius = Configurations.graceConfig.getInt("Grace-Period.Bitch-Claim.radius");
        hook = SOTWGracePeriod.getHookedFactions();
        bitchClaimCancelled = Configurations.graceConfig.getString("Grace-Period.Bitch-Claim.message");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClaim(LandClaimEvent e) {

        if (enabled && isBitchClaim(e.getLocation(), e.getfPlayer().getPlayer())) {
            BitchClaimEvent event = new BitchClaimEvent(e.getfPlayer().getName(), e.getFaction().getTag());
            Bukkit.getServer().getPluginManager().callEvent(event);
            e.setCancelled(event.isCancelled());
            if (event.isCancelled())
                SOTWGracePeriod.sms(e.getfPlayer().getPlayer(), replacePlaceHolders());
        }
    }

    private boolean isBitchClaim(FLocation loc, Player player) {

        List<Chunk> chunks = getChunksAroudLocation(new Location(loc.getWorld(), loc.getX(), 0, loc.getZ()), radius);
        double distanceX;
        double distanceZ;
        Location playerLocation = player.getLocation();

        for (Chunk chunk : chunks) {

            Location location = getLocationFromChunk(chunk);

            if (hook.locationInOthersTerritory(player, location)) {

                nearestFactionName = hook.getFactionAtLocation(location);
                distanceX = location.getX() - playerLocation.getX();
                distanceZ = location.getZ() - playerLocation.getZ();
                distanceToNearestFaction = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceZ, 2));
                return true;
            }
        }
        return false;
    }


    private List<Chunk> getChunksAroudLocation(Location loc, int radius) {

        List<Chunk> chunks = new ArrayList<>();
        World world = loc.getWorld();
        int baseX = loc.getChunk().getX();
        int baseZ = loc.getChunk().getZ();

        for (int x = baseX; x <= radius; x++) {
            for (int z = baseZ; z <= radius; z++) {
                chunks.add(world.getChunkAt(x, z));
            }
        }
        for (int x = baseX; x >= -radius; x--) {
            for (int z = baseZ; z >= radius; z--) {
                chunks.add(world.getChunkAt(x, z));
            }
        }


        return chunks;
    }


    private Location getLocationFromChunk(Chunk chunk) {
        return chunk.getBlock(0, 0, 0).getLocation();
    }


    private String replacePlaceHolders() {

        String replaced = bitchClaimCancelled;

        String gracePlaceholder = "{grace}";
        if (replaced.contains(gracePlaceholder))
            replaced = replaced.replace(gracePlaceholder, SOTWGracePeriod.getGraceInstance().getTimeLeft());
        String radiusPlaceholder = "{radius_in_chunks}";
        if (replaced.contains(radiusPlaceholder))
            replaced = replaced.replace(radiusPlaceholder, (Configurations.graceConfig.getInt("Grace-Period.Bitch-Claim.radius")) + "");
        String nearFaction = "{near_faction}";
        if (replaced.contains(nearFaction))
            replaced = replaced.replace(nearFaction, nearestFactionName);
        String distance = "{distance}";
        if (replaced.contains(distance))
            replaced = replaced.replace(distance, distanceToNearestFaction + "");

        return replaced;
    }

}

