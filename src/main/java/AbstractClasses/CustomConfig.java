package AbstractClasses;

import graceperiod.sotwgraceperiod.SOTWGracePeriod;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class CustomConfig {

    protected String fileName;
    protected File file;
    protected YamlConfiguration config;
    private static CustomConfig instance;


    protected CustomConfig() {
    }

    protected void loadConfig() {
        instance = this;
        file = new File(SOTWGracePeriod.getInstance().getDataFolder(),fileName);
        config = YamlConfiguration.loadConfiguration(file);
        if(!file.exists())
            createConfig();
    }

    protected abstract void createConfig();

    public void save(){
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean exists(String path){
        return config.contains(path);
    }

    public void set(String path, Object o){
        if(exists(path)){
            config.set(path,o);
        }
    }

    public int getInt(String path){
        return config.getInt(path);
    }

    public String getString(String path){
        return config.getString(path);
    }

    public List<String> getStringList(String path){
        return config.getStringList(path);
    }

    public boolean getBoolean(String path){
        return config.getBoolean(path);
    }

    public void reload(){
        loadConfig();
    }

    public void saveInv(Inventory inventory){}

    public void getInv(Inventory inventory){}


}
