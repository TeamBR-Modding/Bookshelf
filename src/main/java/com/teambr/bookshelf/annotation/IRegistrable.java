package com.teambr.bookshelf.annotation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

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
public interface IRegistrable<T extends IForgeRegistryEntry<T>> {

    /**
     * Registers an object to the ForgeRegistry
     *
     * @param registry The Block Forge Registry
     */
    void registerObject(IForgeRegistry<T> registry);

    /**
     * Register the renderers for the block/item
     */
    @SideOnly(Side.CLIENT)
    void registerRender();
}
