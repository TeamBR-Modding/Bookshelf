package com.teambr.bookshelf.manager;

import com.teambr.bookshelf.lib.Reference;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * This file was created for Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
public class ConfigManager {
    // Public instance of the manager
    public static Configuration config;

    // Debug
    public static boolean debug;

    /**
     * Loads the config
     * @param configFolderLocation The location for the config folder
     */
    public static void init(String configFolderLocation) {
        config = new Configuration(new File(configFolderLocation + File.separator + "Bookshelf.cfg"));
        config.load();

        debug = config.get(Reference.DEBUG, "Enable Debug Mode?", false).getBoolean();

        config.save();
    }

    /**
     * Set a value to the config
     * @param categoryName The category
     * @param propertyName The property
     * @param newValue The new value
     */
    public static void set(String categoryName, String propertyName, String newValue) {
        config.load();
        if(config.getCategoryNames().contains(categoryName.toLowerCase()))
            config.getCategory(categoryName.toLowerCase()).get(propertyName).set(newValue);
        config.save();
    }

    /**
     * Set a value to the config, used for booleanss
     * @param categoryName The category
     * @param propertyName The property
     * @param newValue The new value
     */
    public static void set (String categoryName, String propertyName, boolean newValue) {
        config.load();
        if(config.getCategoryNames().contains(categoryName.toLowerCase()))
            config.getCategory(categoryName.toLowerCase()).get(propertyName).set(newValue);
        config.save();
    }
}
