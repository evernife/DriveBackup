package ratismal.drivebackup;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import ratismal.drivebackup.config.Config;
import ratismal.drivebackup.handler.CommandHandler;
import ratismal.drivebackup.handler.PlayerListener;
import ratismal.drivebackup.util.MessageUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public class DriveBackup extends JavaPlugin {

    private String newVersionTitle = "";
    private double newVersion = 0;
    private double currentVersion = 0;
    private String currentVersionTitle = "";

    private static Config pluginconfig;
    private static DriveBackup plugin;
    public Logger log = getLogger();


    /**
     * What to do when plugin is enabled (init)
     */
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        reloadConfig();

        pluginconfig = new Config(getConfig());
        pluginconfig.reload();
        //reloadLocalConfig();
        getCommand("drivebackup").setExecutor(new CommandHandler(this));
        plugin = this;

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(), this);


        currentVersionTitle = getDescription().getVersion().split("-")[0];
        currentVersion = Double.valueOf(currentVersionTitle.replaceFirst("\\.", ""));

        startThread();
    }

    /**
     * What to do when plugin is disabled
     */
    public void onDisable() {
        MessageUtil.sendConsoleMessage("Stopping plugin!");
    }

    /**
     * Gets an instance of the plugin
     *
     * @return DriveBackup plugin
     */
    public static DriveBackup getInstance() {
        return plugin;
    }

    /**
     * Starts the backup thread
     */
    public static void startThread() {
        if (Config.getBackupDelay() / 60 / 20 != -1) {
            MessageUtil.sendConsoleMessage("Starting the backup thread for every " + Config.getBackupDelay() + " ticks.");
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.runTaskTimerAsynchronously(getInstance(), new UploadThread(), Config.getBackupDelay(), Config.getBackupDelay());
        }
    }

    /**
     * Reloads config
     */
    public static void reloadLocalConfig() {
        getInstance().reloadConfig();
        pluginconfig.reload(getInstance().getConfig());
    }

}
