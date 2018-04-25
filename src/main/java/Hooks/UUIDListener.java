package Hooks;

import Events.BitchClaimEvent;
import com.massivecraft.factions.event.LandClaimEvent;
import graceperiod.sotwgraceperiod.SOTWGracePeriod;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;


public class UUIDListener extends FactionsListener {


    public UUIDListener() {

        super();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClaim(LandClaimEvent e) {

        if (enabled) {
            Location location = new Location(e.getLocation().getWorld(),e.getLocation().getX(),0,e.getLocation().getZ());
            if (isBitchClaim(location, e.getfPlayer().getPlayer())) {
                BitchClaimEvent event = new BitchClaimEvent(e.getfPlayer().getName(), e.getFaction().getTag());
                Bukkit.getServer().getPluginManager().callEvent(event);
                e.setCancelled(event.isCancelled());
                if (event.isCancelled())
                    SOTWGracePeriod.sms(e.getfPlayer().getPlayer(), replacePlaceHolders());
            }
        }
    }


}

