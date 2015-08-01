package com.teambr.bookshelf.client.itemtooltip;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

/**
 * Modular-Systems
 * Created by Dyonovan on 01/08/15
 */
public class ToolTipEvent {
    @SubscribeEvent
    public void onToolTip(ItemTooltipEvent event) {
        IItemTooltip item = null;
        if (Block.getBlockFromItem(event.itemStack.getItem()) instanceof IItemTooltip)
            item = (IItemTooltip) Block.getBlockFromItem(event.itemStack.getItem());
        else if (event.itemStack.getItem() instanceof IItemTooltip)
            item = (IItemTooltip)event.itemStack.getItem();

        if (item != null) {
            List<String> tips = item.returnTooltip();
            for (String tip : tips)
                event.toolTip.add(tip);
        }
    }
}
