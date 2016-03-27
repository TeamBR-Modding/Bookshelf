package com.teambr.bookshelf.events

import com.teambr.bookshelf.traits.HasToolTip
import net.minecraft.block.Block
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 04, 2015
 */
class ToolTipEvent {
    @SubscribeEvent def onToolTip(event: ItemTooltipEvent) {
        var itemWithTip : HasToolTip = null

        Block.getBlockFromItem(event.getItemStack.getItem) match {
            case tip: HasToolTip => itemWithTip = tip
            case _ => //Not a block, what about an item?
                event.getItemStack.getItem match {
                    case tip : HasToolTip => itemWithTip = tip
                    case _ => return //We can't use this, just leave
                }
        }

        if(itemWithTip != null)
            for(tip <- itemWithTip.getToolTip())
                event.getToolTip.add(tip)

    }
}
