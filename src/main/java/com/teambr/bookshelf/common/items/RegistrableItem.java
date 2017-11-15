package com.teambr.bookshelf.common.items;

import com.teambr.bookshelf.annotation.IRegistrable;
import com.teambr.bookshelf.client.ModelHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * This file was created for Bookshelf
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Buuz135 + Paul Davis
 * @since 11/14/2019
 */
public class RegistrableItem extends Item implements IRegistrable<Item> {

    /**
     * Registers an object to the ForgeRegistry
     *
     * @param registry The Block Forge Registry
     */
    @Override
    public void registerObject(IForgeRegistry<Item> registry) {
        registry.register(this);
    }

    /**
     * Register the renderers for the block/item
     */
    @Override
    public void registerRender() {
        ModelHelper.registerSimpleRenderItem(this);
    }
}
