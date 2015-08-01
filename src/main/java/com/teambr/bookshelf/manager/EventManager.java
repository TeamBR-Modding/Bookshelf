package com.teambr.bookshelf.manager;

import com.teambr.bookshelf.client.itemtooltip.ToolTipEvent;
import net.minecraftforge.common.MinecraftForge;

/**
 * Modular-Systems
 * Created by Dyonovan on 01/08/15
 */
public class EventManager {

    public static void init() {
        registerEvent(new ToolTipEvent());
    }

    private static void registerEvent(Object event) {
        MinecraftForge.EVENT_BUS.register(event);
    }
}
