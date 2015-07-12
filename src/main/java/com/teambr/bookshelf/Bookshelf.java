package com.teambr.bookshelf;


import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.common.CommonProxy;
import com.teambr.bookshelf.common.tiles.BaseTile;
import com.teambr.bookshelf.common.tiles.IOpensGui;
import com.teambr.bookshelf.inventory.ContainerGeneric;
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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

@SuppressWarnings("unused")
@Mod(name = Constants.MODNAME, modid = Constants.MODID, version = Constants.VERSION, dependencies = Constants.DEPENDENCIES)

public class Bookshelf {

    @Instance(Constants.MODID)
    public static Bookshelf instance;

    @SidedProxy(clientSide = "com.teambr.bookshelf.client.ClientProxy",
            serverSide = "com.teambr.bookshelf.common.CommonProxy")
    public static CommonProxy proxy;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiManager());

        GameRegistry.registerBlock(new BlockTest(), "Test");
        GameRegistry.registerTileEntity(TileTest.class, "test");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        PacketManager.initPackets();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    private class BlockTest extends Block {
        protected BlockTest() {
            super(Material.rock);
            setCreativeTab(CreativeTabs.tabBlock);
        }
    }

    private class TileTest extends BaseTile implements IOpensGui {

        @Override
        public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
            return new ContainerGeneric();
        }

        @Override
        public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
            return new GuiTest(new ContainerGeneric(), 100, 100, "Testing");
        }
    }

    private class GuiTest extends GuiBase {
        /**
         * Constructor for All Guis
         *
         * @param container Inventory Container
         * @param width     XSize
         * @param height    YSize
         * @param name      The inventory title
         */
        public GuiTest(Container container, int width, int height, String name) {
            super(container, width, height, name);
        }

        @Override
        public void addComponents() {

        }
    }

}
