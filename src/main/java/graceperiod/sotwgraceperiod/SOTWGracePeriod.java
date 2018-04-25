package graceperiod.sotwgraceperiod;

import Configurations.GracePeriodConfig;
import Enums.Commands;
import Grace.GraceCommand;
import Grace.GracePeriod;
import Hooks.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import Configurations.Configurations;

import java.util.logging.Level;

public final class SOTWGracePeriod extends JavaPlugin {


    private static SOTWGracePeriod instance;
    private static GracePeriod graceInstance;
    private static FactionsHook factionsHook;
    private FactionsListener fListener;
    private GraceCommand graceCommand;

    public static GracePeriod getGraceInstance() {
        return graceInstance;
    }

    public static FactionsHook getHookedFactions() {
        return factionsHook;
    }

    private void hookInFactionsPlugin(){

        Plugin factions = getServer().getPluginManager().getPlugin("Factions");
        Plugin legacy = getServer().getPluginManager().getPlugin("LegacyFactions");

        if (factions == null && legacy == null) {
            return;
        }

        String factionsPluginHooked;

        if(factions != null){
            String version = factions.getDescription().getVersion();
            if (version.startsWith("1.6.9.5")) {
                factionsHook = new FactionsUUIDHook();
                factionsPluginHooked = "FactionsUUID";
                this.getServer().getLogger().log(Level.INFO, "GracePeriod hooked into {0}", factionsPluginHooked);
            }
            if (version.startsWith("2.13.6")) {
                factionsPluginHooked = "MassiveCoreFactions";
                this.getServer().getLogger().log(Level.INFO, "GracePeriod hooked into {0}", factionsPluginHooked);
            }
        }
        if(legacy != null){

            factionsHook = new LegacyFactionsHook();
            factionsPluginHooked = "LegacyFactions";
            this.getServer().getLogger().log(Level.INFO, "GracePeriod hooked into {0}", factionsPluginHooked);

        }
    }

    @Override
    public void onEnable() {

        instance = this;
        hookInFactionsPlugin();
        initGrace();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SOTWGracePeriod getInstance() {
        return instance;
    }

    public static void sms(CommandSender commandSender, String string) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',string));
    }

    public static void sms(Player commandSender, String string) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',string));
    }

    public static void enableGrace() {
        graceInstance.enableGrace();
    }
    public static void disableGrace() {
        graceInstance.disableGrace();
    }

    private void initGrace(){
        Configurations.graceConfig = new GracePeriodConfig();
        graceInstance = new GracePeriod();
        this.getServer().getPluginManager().registerEvents(graceInstance,this);
        graceCommand = new GraceCommand();
        this.getCommand(Commands.GRACE.getName()).setExecutor(graceCommand);

        if(factionsHook instanceof FactionsUUIDHook)
            fListener = new UUIDListener();
        if(factionsHook instanceof LegacyFactionsHook)
            fListener = new LegacyListener();
        if(fListener != null)
            this.getServer().getPluginManager().registerEvents(fListener,this);
    }


    public void reload(){
        Configurations.graceConfig.reload();
        graceCommand.load();
        fListener.load();
    }
}
