package test.com.teambr.bookshelf.manager;

import com.teambr.bookshelf.manager.ConfigManager;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;
import test.com.teambr.bookshelf.common.blocks.TestBlock;
import test.com.teambr.bookshelf.common.tiles.TileTestBlock;

public class BlockManager {

    public static Block testBlock;

    public static void init() {
        if (ConfigManager.debug) {
            registerBlock(testBlock = new TestBlock(), "testBlock", TileTestBlock.class);
        }
    }


    public static void registerBlock(Block block, String name, Class<? extends TileEntity> tileEntity, String oreDict) {
        GameRegistry.registerBlock(block, name);
        if(tileEntity != null)
            GameRegistry.registerTileEntity(tileEntity, name);
        if(oreDict != null)
            OreDictionary.registerOre(oreDict, block);
    }

    private static void registerBlock(Block block, String name, Class<? extends TileEntity> tileEntity) {
        registerBlock(block, name, tileEntity, null);
    }
}