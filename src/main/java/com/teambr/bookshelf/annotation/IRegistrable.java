package com.teambr.bookshelf.annotation;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public interface IRegistrable {

    /**
     * Registers a block to the ForgeRegistry
     *
     * @param block The Block Forge Registry
     */
    void registerBlock(IForgeRegistry<Block> block);

    /**
     * Registers an item to the ForgeRegistry
     *
     * @param item The Item Forge Registry
     */
    void registerItem(IForgeRegistry<Item> item);

    /**
     * Register the renderers for the block/item
     */
    void registerRender();
}
