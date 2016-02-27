package com.teambr.bookshelf.manager

import com.teambr.bookshelf.events.ToolTipEvent
import net.minecraftforge.common.MinecraftForge

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Dyonovan
 * @since August 01, 2015
 */
object EventManager {
    def init(): Unit = {
        registerEvent(new ToolTipEvent)
    }

    def registerEvent(thing : Object) : Unit = {
        MinecraftForge.EVENT_BUS.register(thing)
    }
}
