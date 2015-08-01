package com.teambr.bookshelf.client.itemtooltip;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

/**
 * Modular-Systems
 * Created by Dyonovan on 01/08/15
 */
public class ToolTipEvent {
    @SubscribeEvent
    public void onToolTip(ItemTooltipEvent event) {
        if (event.itemStack.getItem() instanceof IItemTooltip) {
            Item item = event.itemStack.getItem();
            List<String> tips = ((IItemTooltip) item).returnTooltip();
            for (String tip : tips)
                event.toolTip.add(tip);
        }
    }
}
