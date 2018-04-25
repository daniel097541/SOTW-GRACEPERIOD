package Configurations;

import AbstractClasses.CustomConfig;

import java.io.IOException;

public class GracePeriodConfig extends CustomConfig {


    public GracePeriodConfig(){
        fileName = "GraceConfig.yml";
        loadConfig();
    }

    @Override
    public void createConfig() {
        config.createSection("Grace-Period.enabled");
        config.set("Grace-Period.enabled",false);
        config.createSection("Grace-Period.permission");
        config.set("Grace-Period.permission","btf.grace.command");
        config.createSection("Grace-Period.no-permission");
        config.set("Grace-Period.no-permission","&4&l(!) &cYou don´t have permission!");
        config.createSection("Grace-Period.not-enabled-message");
        config.set("Grace-Period.not-enabled-message","&4&l(!) &cGrace is not enabled!");
        config.createSection("Grace-Period.message");
        config.createSection("Grace-Period.grace-enabled-message");
        config.set("Grace-Period.grace-enabled-message","&aGrace period has started! Type &d/grace &ato see how much time lefts!");
        config.set("Grace-Period.message","&d&lGrace &7&f> &eGrace period will end in: &d&l{time_left}");
        config.createSection("Grace-Period.left");
        config.set("Grace-Period.left",604800);
        config.createSection("Grace-Period.end-message");
        config.set("Grace-Period.end-message","&d&lGrace &e> &fGrace period has ended, now you can use TNT and BitchClaims!");
        config.createSection("Grace-Period.Bitch-Claim.disabled");
        config.set("Grace-Period.Bitch-Claim.disabled",true);
        config.createSection("Grace-Period.Bitch-Claim.radius");
        config.set("Grace-Period.Bitch-Claim.radius",20);
        config.createSection("Grace-Period.Bitch-Claim.message");
        config.set("Grace-Period.Bitch-Claim.message","&4(!) &d{grace} &clefts until the &eSOTW &cends! Meanwhile you can`t claim at less than &e{radius_in_chunks} &cchunks near a faction!! &7( The faction &d{near_faction} &7is &d{distance} &7blocks away from you! )");
        config.createSection("Grace-Period.Explosions.disabled");
        config.set("Grace-Period.Explosions.disabled",true);
        config.createSection("Grace-Period.Auto-Save-Interval");
        config.set("Grace-Period.Auto-Save-Interval",300);

        config.createSection("Grace-Period.command-reload.permission");
        config.set("Grace-Period.command-reload.permission", "btf.grace.reload.permission");

        config.createSection("Grace-Period.command-reload.message");
        config.set("Grace-Period.command-reload.message", "&aPlugin reloaded!");

        config.createSection("Grace-Period.command-toggle.permission");
        config.set("Grace-Period.command-toggle.permission", "btf.grace.toggle.permission");


        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getGraceInSeconds(){
        return config.getInt("Grace-Period.left");
    }

    public void setGrace(int grace){
        config.set("Grace-Period.left", grace);
    }



}
