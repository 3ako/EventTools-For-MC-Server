package mst.eventtools;

import mst.eventtools.commands.ClearNear;
import mst.eventtools.commands.EffectNear;
import mst.eventtools.commands.EffectNearTabCompleter;
import mst.eventtools.files.ConfigurationManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class EventTools extends JavaPlugin {
    public static EventTools instance;
    private  ConfigurationManager configurationManage;
    @Override
    public void onEnable() {
        instance = this;

        configurationManage = new ConfigurationManager();
        checkConfig();

        getCommand("effectNear").setExecutor(new EffectNear());
        getCommand("effectNear").setTabCompleter(new EffectNearTabCompleter());

        getCommand("clearNear").setExecutor(new ClearNear());
        getLogger().info("Инструментарий для ивентов загружен");
    }
    private void checkConfig() {
        try {
            if (!(new File(getDataFolder(), "config.yml")).exists()) {
                getLogger().info("Looks like there is no config. Creating the default one.");
                this.configurationManage.createFile("config.yml");
            }
            if (!(new File(getDataFolder(), "lang.yml")).exists()) {
                getLogger().info("Looks like there is no lang. Creating the default one.");
                this.configurationManage.createFile("lang.yml");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  ConfigurationManager getConfigurationManage() {
        return configurationManage;
    }
}
