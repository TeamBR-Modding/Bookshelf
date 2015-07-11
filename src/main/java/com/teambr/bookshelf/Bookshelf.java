package com.teambr.bookshelf;


import com.teambr.bookshelf.common.CommonProxy;
import com.teambr.bookshelf.lib.Constants;
import com.teambr.bookshelf.manager.GuiManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@SuppressWarnings("unused")
@Mod(name = Constants.MODNAME, modid = Constants.MODID, version = Constants.VERSION, dependencies = Constants.DEPENDENCIES)

public class Bookshelf {

    @Instance(Constants.MODID)
    public static Bookshelf instance;

    @SidedProxy(clientSide = "com.teambr.bookshelf.client.ClientProxy",
            serverSide = "com.teambr.bookshelf.common.CommonProxy")
    public static CommonProxy proxy;

    public static final PacketPipeline packetPipeline = new PacketPipeline();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiManager());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        packetPipeline.initalise();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        packetPipeline.postInitialise();
    }
}
