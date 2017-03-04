package com.teambr.bookshelf.events;

import com.teambr.bookshelf.common.ICraftingListener;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * This file was created for NeoTech
 *
 * NeoTech is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/20/2017
 */
public class CraftingEvents {
    @SubscribeEvent
    public void onCrafting(PlayerEvent.ItemCraftedEvent event) {
        if((event.crafting.getItem() instanceof ItemBlock &&
                Block.getBlockFromItem(event.crafting.getItem()) instanceof ICraftingListener) ||
                event.crafting.getItem() instanceof ICraftingListener) {
            ItemStack[] craftingList = new ItemStack[event.craftMatrix.getSizeInventory()];
            for(int x = 0; x < craftingList.length; x++)
                craftingList[x] = event.craftMatrix.getStackInSlot(x);

            if(!(event.crafting.getItem() instanceof ItemBlock)) // Is a block class
                ((ICraftingListener)Block.getBlockFromItem(event.crafting.getItem())).onCrafted(craftingList, event.crafting);
            else // Is an item class
                ((ICraftingListener)event.crafting.getItem()).onCrafted(craftingList, event.crafting);
        }
    }
}
