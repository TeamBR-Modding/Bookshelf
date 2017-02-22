package com.teambr.bookshelf.manager;

import com.teambr.bookshelf.events.CraftingEvents;
import com.teambr.bookshelf.events.ToolTipEvent;
import com.teambr.bookshelf.util.TimeUtils;
import net.minecraftforge.common.MinecraftForge;

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
public class EventManager {

    /**
     * Registers the events
     */
    public static void init() {
        registerEvent(new ToolTipEvent());
        registerEvent(new TimeUtils());
        registerEvent(new CraftingEvents());
    }

    /**
     * Registers an event to the event registry
     * @param event The event to register
     */
    private static void registerEvent(Object event) {
        MinecraftForge.EVENT_BUS.register(event);
    }
}
