package com.teambr.bookshelf;

import com.teambr.bookshelf.common.CommonProxy;
import com.teambr.bookshelf.lib.Reference;
import com.teambr.bookshelf.manager.ConfigManager;
import com.teambr.bookshelf.manager.EventManager;
import com.teambr.bookshelf.manager.GuiManager;
import com.teambr.bookshelf.network.PacketManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

/**
 * This file was created for com.teambr.bookshelf.Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
@Mod(
        name = Reference.MOD_NAME,
        modid = Reference.MOD_ID,
        version = Reference.VERSION,
        dependencies = Reference.DEPENDENCIES
)
public class Bookshelf {

    /**
     * Public INSTANCE of this mod
     */
    @Mod.Instance
    public static Bookshelf INSTANCE;

    /**
     * The INSTANCE of the proxy
     */
    @SidedProxy(clientSide = "com.teambr.bookshelf.client.ClientProxy",
                serverSide = "com.teambr.bookshelf.common.CommonProxy")
    public static CommonProxy proxy;

    /**
     * The location of the config folder
     */
    public static String configFolderLocation;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        // Create Config Folder Location
        configFolderLocation = event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MOD_NAME;

        // Load the Config Manager
        ConfigManager.init(configFolderLocation);

        // Send to proxy
        proxy.preInit(event);

        // Register GUI Handler
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiManager());
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        // Init Network Packets
        PacketManager.initPackets();

        // Register Events
        EventManager.init();

        // Send to proxy
        proxy.init(event);
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        // Send to proxy
        proxy.postInit(event);
    }
}
