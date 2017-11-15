package com.teambr.bookshelf.events;

import com.teambr.bookshelf.annotation.IRegistrable;
import com.teambr.bookshelf.common.CommonProxy;
import com.teambr.bookshelf.common.IRegistersOreDictionary;
import com.teambr.bookshelf.common.blocks.IRegistersTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@SuppressWarnings({"ConstantConditions", "unchecked"})
public class RegistrationEvents {

    /**
     * Adds the items to the registry, including block ItemBlocks
     *
     * @param event
     */
    @SubscribeEvent
    public void addItems(RegistryEvent.Register<Item> event) {
        // Create ItemBlocks
        CommonProxy.BLOCKS.forEach(block ->
                event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName())));

        // Add Items
        CommonProxy.ITEMS.forEach(item -> {
            // Register the item itself
            ((IRegistrable<Item>) item).registerObject(event.getRegistry());

            // If has ore dict, register
            if(item instanceof IRegistersOreDictionary)
                OreDictionary.registerOre(((IRegistersOreDictionary)item).getOreDictionaryTag(), item);
        });

        if (FMLCommonHandler.instance().getSide().isClient()) {
            CommonProxy.BLOCKS.forEach(block -> ((IRegistrable<?>) block).registerRender());
            CommonProxy.ITEMS.forEach(item -> ((IRegistrable<?>) item).registerRender());
        }
    }

    @SubscribeEvent
    public void addBlocks(RegistryEvent.Register<Block> event) {
        CommonProxy.BLOCKS.forEach(block -> {
            // Register the block itself
            ((IRegistrable<Block>) block).registerObject(event.getRegistry());

            // Register the tile, if present
            if (block instanceof IRegistersTileEntity)
                GameRegistry.registerTileEntity(((IRegistersTileEntity) block).getTileEntityClass(),
                        block.getRegistryName().toString());

            // Add ore dict tag if present
            if(block instanceof IRegistersOreDictionary)
                OreDictionary.registerOre(((IRegistersOreDictionary)block).getOreDictionaryTag(), block);
        });
    }
}
