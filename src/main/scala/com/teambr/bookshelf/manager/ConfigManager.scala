package com.teambr.bookshelf.manager

import java.io.File

import com.teambr.bookshelf.lib.Reference
import net.minecraftforge.common.config.Configuration

/**
 * This file was created for the Bookshelf
 *
 * Bookshelf if licensed under the is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 02, 2015
 *
 * Used to manage the config. For us, its helpful debug mode
 */
object ConfigManager {
    var config: Configuration = null

    var debug : Boolean = _

    def init(configFolderLocation : String) = {
        config = new Configuration(new File(configFolderLocation + File.separator + "Bookshelf.cfg"))
        config.load()

        debug         = config.get(Reference.DEBUG, "Enable Debug Mode?", false).getBoolean

        config.save()
    }

    def set(categoryName: String, propertyName: String, newValue: String) = {
        config.load()
        if (config.getCategoryNames.contains(categoryName.toLowerCase)) {
            if (config.getCategory(categoryName.toLowerCase).containsKey(propertyName)) {
                config.getCategory(categoryName.toLowerCase).get(propertyName).set(newValue)
            }
        }
        config.save()
    }

    def set(categoryName: String, propertyName: String, newValue: Boolean) = {
        config.load()
        if (config.getCategoryNames.contains(categoryName.toLowerCase)) {
            if (config.getCategory(categoryName.toLowerCase).containsKey(propertyName)) {
                config.getCategory(categoryName.toLowerCase).get(propertyName).set(newValue)
            }
        }
        config.save()
    }
}