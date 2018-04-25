package Hooks;

import Events.BitchClaimEvent;
import graceperiod.sotwgraceperiod.SOTWGracePeriod;
import net.redstoneore.legacyfactions.entity.FPlayer;
import net.redstoneore.legacyfactions.event.EventFactionsLandChange;
import net.redstoneore.legacyfactions.locality.Locality;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;

public class LegacyListener extends FactionsListener{


    public LegacyListener() {
        super();
    }

    @EventHandler
    public void onLandClaim(EventFactionsLandChange e){

        if(enabled){
            if(e.getCause().equals(EventFactionsLandChange.LandChangeCause.Claim)){

                FPlayer fPlayer = e.getFPlayer();
                for(Locality locality : e.transactions().keySet()){
                    if (isBitchClaim(getLocationFromLocality(locality),fPlayer.getPlayer())){

                        BitchClaimEvent event = new BitchClaimEvent(fPlayer.getName(), fPlayer.getFaction().getTag());
                        Bukkit.getServer().getPluginManager().callEvent(event);
                        e.setCancelled(event.isCancelled());
                        if (event.isCancelled())
                            SOTWGracePeriod.sms(fPlayer.getPlayer(), replacePlaceHolders());
                        return;
                    }
                }
            }
        }
    }

    private Location getLocationFromLocality(Locality locality){
        return locality.getLocation();
    }


}
