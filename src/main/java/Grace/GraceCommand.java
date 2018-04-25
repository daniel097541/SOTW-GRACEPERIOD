package Grace;

import Configurations.Configurations;
import Configurations.GracePeriodConfig;
import Enums.Commands;
import Events.GracePeriodEndEvent;
import graceperiod.sotwgraceperiod.SOTWGracePeriod;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GraceCommand implements CommandExecutor{
    private String enabledPath;

    private String permissionPath;
    private String permission;

    private String nopermissionPath;
    private String noPermissionMsg;

    private String messagePath;
    private String message;

    private String notEnabledPath;
    private String getNotEnabledMsg;

    private String enablePermission;
    private String togglePermission;

    private String graceStarted;
    private String getGraceStartedMessage;

    private String reloadPermissionPath;
    private String reloadPermission;

    private String reloadMessagePath;
    private String reloadMessage;

    private GracePeriodConfig config;

    public GraceCommand() {

        enabledPath = "Grace-Period.enabled";
        permissionPath = "Grace-Period.permission";
        nopermissionPath = "Grace-Period.no-permission";
        messagePath = "Grace-Period.message";
        notEnabledPath = "Grace-Period.not-enabled-message";
        enablePermission = "Grace-Period.command-toggle.permission";
        graceStarted = "Grace-Period.grace-enabled-message";
        reloadPermissionPath = "Grace-Period.command-reload.permission";
        reloadMessagePath = "Grace-Period.command-reload.message";
        load();
    }

    public void load(){
        config = Configurations.graceConfig;
        permission = config.getString(permissionPath);
        noPermissionMsg = config.getString(nopermissionPath);
        message = config.getString(messagePath);
        getNotEnabledMsg = config.getString(notEnabledPath);
        togglePermission = config.getString(enablePermission);
        getGraceStartedMessage = config.getString(graceStarted);
        reloadPermission = config.getString(reloadPermissionPath);
        reloadMessage = config.getString(reloadMessagePath);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (command.getName().equalsIgnoreCase(Commands.GRACE.getName())) {

            if (!commandSender.hasPermission(permission)) {
                SOTWGracePeriod.sms(commandSender,noPermissionMsg);
                return false;
            }

            if (strings.length != 1) {
                if (Configurations.graceConfig.getBoolean(enabledPath) && SOTWGracePeriod.getGraceInstance().isTaskOn()) {

                    sendGrace(commandSender);
                    return true;
                }
                SOTWGracePeriod.sms(commandSender, getNotEnabledMsg);
                return false;
            }

            if (strings[0].equalsIgnoreCase("enable")) {

                if (commandSender.hasPermission(togglePermission)) {

                    if (Configurations.graceConfig.getBoolean(enabledPath) && SOTWGracePeriod.getGraceInstance().isTaskOn()) {
                        sendGrace(commandSender);
                        return false;
                    }

                    SOTWGracePeriod.enableGrace();
                    SOTWGracePeriod.sms(commandSender,getGraceStartedMessage);
                    return true;
                }
                SOTWGracePeriod.sms(commandSender, noPermissionMsg);
                return false;
            }



            if (strings[0].equalsIgnoreCase("disable")) {



                if (!commandSender.hasPermission(togglePermission)) {
                    SOTWGracePeriod.sms(commandSender, noPermissionMsg);
                    return false;
                }

                if (!Configurations.graceConfig.getBoolean(enabledPath) || !SOTWGracePeriod.getGraceInstance().isTaskOn()) {
                    SOTWGracePeriod.sms(commandSender, getNotEnabledMsg);
                    return false;
                }

                SOTWGracePeriod.disableGrace();
                GracePeriodEndEvent event = new GracePeriodEndEvent();
                Bukkit.getServer().getPluginManager().callEvent(event);
                return true;
            }

            if(strings[0].equalsIgnoreCase("reload")){

                if(!commandSender.hasPermission(reloadPermission)){
                    SOTWGracePeriod.sms(commandSender,noPermissionMsg);
                    return false;
                }

                SOTWGracePeriod.getInstance().reload();
                SOTWGracePeriod.sms(commandSender,reloadMessage);
            }

        }

        return false;
    }


    private String replacePlaceholder(String placeholder, String text){

        String msg = message;
        return msg.replace(placeholder, text);
    }


    private void sendGrace(CommandSender commandSender){
        String timeLeft = SOTWGracePeriod.getGraceInstance().getTimeLeft();
        String placeholder = "{time_left}";
        SOTWGracePeriod.sms(commandSender, replacePlaceholder(placeholder, timeLeft));
    }
}
