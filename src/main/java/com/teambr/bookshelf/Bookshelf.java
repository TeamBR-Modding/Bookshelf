package com.teambr.bookshelf;


import com.teambr.bookshelf.common.CommonProxy;
import com.teambr.bookshelf.lib.Reference;
import com.teambr.bookshelf.manager.BlockManager;
import com.teambr.bookshelf.manager.ConfigManager;
import com.teambr.bookshelf.manager.GuiManager;
import com.teambr.bookshelf.manager.PacketManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

import java.io.File;

@SuppressWarnings("unused")
@Mod(name = Reference.MODNAME, modid = Reference.MODID, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)

public class Bookshelf {

    @Instance(Reference.MODID)
    public static Bookshelf instance;

    @SidedProxy(clientSide = "com.teambr.bookshelf.client.ClientProxy",
            serverSide = "com.teambr.bookshelf.common.CommonProxy")
    public static CommonProxy proxy;

    public static String configFolderLocation;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configFolderLocation = event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MODNAME;
        ConfigManager.init(configFolderLocation);
        BlockManager.init();
        proxy.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiManager());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        PacketManager.initPackets();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}
