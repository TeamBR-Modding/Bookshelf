package com.teambr.bookshelf.client

import java.io.File

import com.teambr.bookshelf.Bookshelf
import com.teambr.bookshelf.common.CommonProxy
import com.teambr.bookshelf.helper.KeyInputHelper
import com.teambr.bookshelf.notification.{NotificationKeyBinding, NotificationTickHandler}
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.common.event.FMLInterModComms

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 02, 2015
 */
class ClientProxy extends CommonProxy {

    /**
     * Called on preInit
     */
    override def preInit() = {
        MinecraftForge.EVENT_BUS.register(new NotificationTickHandler())

        Bookshelf.notificationConfig = new Configuration(new File(Bookshelf.configFolderLocation + "/NotificationsSettings" + ".cfg"))
        Bookshelf.notificationXPos = Bookshelf.notificationConfig.getInt("notification xpos", "notifications", 1, 0, 2, "0: Left\n1: Center\n2: Right")
        Bookshelf.notificationConfig.save()
    }

    /**
     * Called on init
     */
    override def init() = {
        NotificationKeyBinding.init()
        MinecraftForge.EVENT_BUS.register(new KeyInputHelper())

        MinecraftForge.EVENT_BUS.register(new ModelFactory)
        MinecraftForge.EVENT_BUS.register(TextureManager)

        FMLInterModComms.sendMessage("Waila", "register", "com.teambr.bookshelf.api.waila.WailaDataProvider.callBackRegisterClient")
    }

    /**
     * Called on postInit
     */
    override def postInit() = {}
}
