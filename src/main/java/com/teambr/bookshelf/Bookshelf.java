package com.teambr.bookshelf;


import com.teambr.bookshelf.common.CommonProxy;
import com.teambr.bookshelf.common.blocks.TestBlock;
import com.teambr.bookshelf.common.tiles.TileTestBlock;
import com.teambr.bookshelf.lib.Constants;
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
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

@SuppressWarnings("unused")
@Mod(name = Constants.MODNAME, modid = Constants.MODID, version = Constants.VERSION, dependencies = Constants.DEPENDENCIES)

public class Bookshelf {

    Block testBlock;

    @Instance(Constants.MODID)
    public static Bookshelf instance;

    @SidedProxy(clientSide = "com.teambr.bookshelf.client.ClientProxy",
            serverSide = "com.teambr.bookshelf.common.CommonProxy")
    public static CommonProxy proxy;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.init();

        //register test block
        //GameRegistry.registerBlock(testBlock = new TestBlock(Material.iron, "testBlock", TileTestBlock.class), "testBlock");
        //GameRegistry.registerTileEntity(TileTestBlock.class, "testBlock");

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
