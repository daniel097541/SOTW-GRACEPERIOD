package Grace;

import Events.BitchClaimEvent;
import Events.GracePeriodEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import Configurations.Configurations;

public class GracePeriod implements Listener {


    private final String disableExplosionsPath;
    private final String disableBithcClaimPath;
    private GraceTask graceTask;
    private String graceEndMessagePath;
    private final String denyPlacedBlocksList;

    public GracePeriod() {
        graceTask = new GraceTask();
        disableBithcClaimPath = "Grace-Period.Bitch-Claim.disabled";
        disableExplosionsPath = "Grace-Period.Explosions.disabled";
        graceEndMessagePath = "Grace-Period.end-message";
        denyPlacedBlocksList = "Grace-Period.Block-Placing.denied-blocks";
        String enabledPath = "Grace-Period.enabled";
        if(Configurations.graceConfig.getBoolean(enabledPath)) startTask();
    }


    public String getTimeLeft(){
        return graceTask.getGraceFormatted();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGraceEnd(GracePeriodEndEvent e){
        String graceEndMessage = Configurations.graceConfig.getString(graceEndMessagePath);
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', graceEndMessage));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExplosion(EntityExplodeEvent e){


        if(!isTaskOn()) return;

        e.setCancelled(Configurations.graceConfig.getBoolean(disableExplosionsPath));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBitchClaim(BitchClaimEvent e){


        if(!isTaskOn()) return;

        e.setCancelled(Configurations.graceConfig.getBoolean(disableBithcClaimPath));

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent e){
        if(!isTaskOn()) return;
        e.setCancelled(Configurations.graceConfig.getStringList(denyPlacedBlocksList).contains(e.getBlock().getType().name()));
    }

    private void startTask(){
        graceTask.startTask();
    }

    public boolean isTaskOn() {
        return graceTask.isOn();
    }

    public void enableGrace(){
        startTask();
    }

    public void disableGrace() {
        graceTask.disable();
    }
}