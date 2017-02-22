package com.teambr.bookshelf.helper;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;

/**
 * This file was created for Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
public class BlockHelper {

    /**
     * Used to get non-meta specific string
     * @param block Block to check
     * @return "modid:name:-1"
     */
    public static String getBlockString(Block block){
        return getBlockString(block, -1);
    }

    /**
     * Get the string for this block
     * @param block Block to check
     * @param meta Block meta data
     * @return 'modid:name:meta' string representation
     */
    public static String getBlockString(Block block, int meta) {
        return block.getRegistryName().toString() + ":" + meta;
    }

    /**
     * Get the block and meta from the string
     * @param blockString Block string
     * @return The block and meta
     */
    public static Tuple<Block, Integer> getBlockFromString(String blockString) {
        String[] name = blockString.split(":");
        switch (name.length) {
            case 3 :
                return new Tuple<>(Block.REGISTRY.getObject(new ResourceLocation(name[0], name[1])),
                        ((Integer.valueOf(name[2]) == -1)) ? 0 : Integer.valueOf(name[2]));
            case 2 :
                return new Tuple<>(Block.REGISTRY.getObject(new ResourceLocation(name[0], name[1])), 0);
            default :
                return new Tuple<>(Blocks.AIR, 0);
        }
    }
}
