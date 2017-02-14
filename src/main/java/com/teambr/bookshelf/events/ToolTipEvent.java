package com.teambr.bookshelf.events;

import com.teambr.bookshelf.common.IHasToolTip;
import net.minecraft.block.Block;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This file was created for Bookshelf - Java
 * <p>
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
public class ToolTipEvent {
    @SubscribeEvent
    public void onToolTip(ItemTooltipEvent event) {
        IHasToolTip itemWithTip = null;

        if(Block.getBlockFromItem(event.getItemStack().getItem()) instanceof IHasToolTip)
            itemWithTip = (IHasToolTip) Block.getBlockFromItem(event.getItemStack().getItem());
        else if(event.getItemStack().getItem() instanceof IHasToolTip)
            itemWithTip = (IHasToolTip) event.getItemStack().getItem();

        if(itemWithTip != null)
            for(String tip : itemWithTip.getToolTip(event.getItemStack()))
                event.getToolTip().add(tip);
    }
}
