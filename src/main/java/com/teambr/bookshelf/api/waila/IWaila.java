package com.teambr.bookshelf.api.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IWaila {
    void returnWailaHead(List<String> tip);

    void returnWailaBody(List<String> tip);

    void returnWailaTail(List<String> tip);

    ItemStack returnWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config);
}