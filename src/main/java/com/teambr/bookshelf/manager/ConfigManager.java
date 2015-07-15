package com.teambr.bookshelf.manager;

import com.teambr.bookshelf.lib.Reference;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigManager {

    private static Configuration config;

    public static int versionNotify, versionRetry;
    public static String lastVersion, updateURL;
    public static boolean debug;

    public static void init(String configFolderLocation) {
        config = new Configuration(new File(configFolderLocation + File.separator + "Bookshelf.cfg"));
        config.load();

        lastVersion         = config.get(Reference.VERSIONCHECK, Reference.REMOTE_VERSION, "").getString();
        versionNotify       = config.get(Reference.VERSIONCHECK, "Notify if out of Date? (0=Always, 1=Once, 2=Never)", 0).getInt();
        versionRetry        = config.get(Reference.VERSIONCHECK, "# of attempts to check for updates?", 3).getInt();
        updateURL           = config.get(Reference.VERSIONCHECK, Reference.UPDATE_URL, "").getString();

        debug               = config.get(Reference.DEBUG, "Enable Debug Mode?", false).getBoolean();

        config.save();
    }

    public static void set(String categoryName, String propertyName, String newValue) {
        config.load();
        if (config.getCategoryNames().contains(categoryName.toLowerCase())) {
            if (config.getCategory(categoryName.toLowerCase()).containsKey(propertyName)) {
                config.getCategory(categoryName.toLowerCase()).get(propertyName).set(newValue);
            }
        }
        config.save();
    }

    public static void set(String categoryName, String propertyName, Boolean newValue) {
        config.load();
        if (config.getCategoryNames().contains(categoryName.toLowerCase())) {
            if (config.getCategory(categoryName.toLowerCase()).containsKey(propertyName)) {
                config.getCategory(categoryName.toLowerCase()).get(propertyName).set(newValue);
            }
        }
        config.save();
    }
}
