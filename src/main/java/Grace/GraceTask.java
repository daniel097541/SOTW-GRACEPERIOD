package Grace;

import Events.GracePeriodEndEvent;
import graceperiod.sotwgraceperiod.SOTWGracePeriod;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import Configurations.Configurations;
import java.util.concurrent.TimeUnit;

public class GraceTask implements Runnable{



    private int grace;
    private BukkitTask task;
    private int savePeriod;
    private int autosaveInterval;
    private boolean on;
    GraceTask() {

        grace = Configurations.graceConfig.getGraceInSeconds();
        savePeriod = 0;
        autosaveInterval = Configurations.graceConfig.getInt("Grace-Period.Auto-Save-Interval");
        on = false;
    }




    @Override
    public void run() {



        savePeriod ++;

        if(savePeriod == autosaveInterval) {
            saveGrace();
            savePeriod = 0;
        }

        if(grace > 0) {
            grace--;
            return;
        }
        if(grace == 0) {
            GracePeriodEndEvent event = new GracePeriodEndEvent();
            Bukkit.getServer().getPluginManager().callEvent(event);
            disable();
        }
    }


    private void saveGrace(){
        Configurations.graceConfig.setGrace(grace);
        Configurations.graceConfig.save();
    }



    public String getGraceFormatted(){
        int durationSeconds = grace;
        if(durationSeconds <= 0)
            return  Configurations.graceConfig.getString("Grace-Period.finished");
        else{
            int day = (int) TimeUnit.SECONDS.toDays(durationSeconds);
            long hours = TimeUnit.SECONDS.toHours(durationSeconds) - (day *24);
            long minute = TimeUnit.SECONDS.toMinutes(durationSeconds) - (TimeUnit.SECONDS.toHours(durationSeconds)* 60);
            long second = TimeUnit.SECONDS.toSeconds(durationSeconds) - (TimeUnit.SECONDS.toMinutes(durationSeconds) *60);
            return day + " d, " + hours + " h, " + minute + " m, " + second + " s ";
        }

    }

    public void disable(){
        task.cancel();
        on = false;
        Configurations.graceConfig.set("Grace-Period.enabled", false);
        Configurations.graceConfig.save();

    }
    public void startTask(){
        on = true;
        Configurations.graceConfig.set("Grace-Period.enabled", true);
        Configurations.graceConfig.save();
        task = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(SOTWGracePeriod.getInstance(), this, 20L, 20L);
    }

    public boolean isOn() {
        return on;
    }

}
