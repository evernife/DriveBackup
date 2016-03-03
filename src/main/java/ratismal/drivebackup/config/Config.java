package ratismal.drivebackup.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import ratismal.drivebackup.util.MessageUtil;

import java.util.HashMap;

public class Config {

    private FileConfiguration pluginconfig;

    /**
     * General
     */
    private static long backupDelay;
    private static int keepCount;

    /**
     * Metrics
     */
    private static boolean metrics;

    /**
     * Backups
     */
    private static String dir;
    private static boolean backup;
    private static HashMap<String, HashMap<String, String>> backupList;

    /**
     * Uploading
     */
    private static String destination;

    /**
     * Google Drive
     */
    private static boolean googleEnabled;

    /**
     * OneDrive
     */
    private static boolean onedriveEnabled;

    /**
     * Messages
     */

    private static String noPerms;

    /**
     * config constructor
     *
     * @param pluginconfig - Plugin config
     */
    public Config(FileConfiguration pluginconfig) {
        this.pluginconfig = pluginconfig;
    }

    public void reload(FileConfiguration pluginconfig) {
        this.pluginconfig = pluginconfig;
        reload();
    }

    @SuppressWarnings("unchecked")
    public void reload() {
        metrics = pluginconfig.getBoolean("metrics");

        destination = pluginconfig.getString("destination");

        dir = pluginconfig.getString("dir");
        noPerms = pluginconfig.getString("no-perm");

        backup = pluginconfig.getBoolean("backup");

        googleEnabled = pluginconfig.getBoolean("googledrive.enabled");

        onedriveEnabled = pluginconfig.getBoolean("onedrive.enabled");


        backupDelay = pluginconfig.getLong("delay") * 60 * 20;
        keepCount = pluginconfig.getInt("keep-count");
        //MessageUtil.sendConsoleMessage("Scheduling backups for every " + backupDelay + " ticks.");

        HashMap<String, HashMap<String, String>> temp = new HashMap<>();
        ConfigurationSection groupSection = pluginconfig.getConfigurationSection("backup-list");
        if (groupSection != null) {
            for (String name : groupSection.getKeys(false)) {
                HashMap<String, String> temp2 = new HashMap<>();
                ConfigurationSection subSection = groupSection.getConfigurationSection(name);
                for (String name2 : subSection.getKeys(false)) {
                    String value = subSection.getString(name2);
                    temp2.put(name2, value);
                }
                temp.put(name, temp2);
            }
        }
        backupList = (HashMap<String, HashMap<String, String>>) temp.clone();
    }


    /**
     * Returns
     */

    public static boolean isMetrics() {
        return metrics;
    }

    public static String getDir() {
        return dir;
    }

    public static String getNoPerms() {
        return noPerms;
    }

    public static String getDestination() {
        return destination;
    }

    public static boolean isBackup() {
        return backup;
    }

    public static boolean isGoogleEnabled() {
        return googleEnabled;
    }

    public static boolean isOnedriveEnabled() {
        return onedriveEnabled;
    }

    public static HashMap<String, HashMap<String, String>> getBackupList() {
        return backupList;
    }

    public static long getBackupDelay() {
        return backupDelay;
    }

    public static int getKeepCount() {
        return keepCount;
    }
}

