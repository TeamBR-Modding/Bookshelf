package com.teambr.bookshelf.client.gui.component.control

import com.teambr.bookshelf.client.gui.component.BaseComponent
import com.teambr.bookshelf.client.gui.component.display.GuiTab
import net.minecraft.inventory.Slot

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 1/31/2016
  */
class GuiComponentTabSlotHolder(x : Int, y : Int, width : Int, height : Int, tab : GuiTab, slot : Slot, slotX : Int, slotY : Int)
    extends BaseComponent(x, y) {

    /**
      * Called from constructor. Set up anything needed here
      */
    override def initialize(): Unit = {}


    /**
      * Called to render the component
      */
    override def render(guiLeft: Int, guiTop: Int, mouseX: Int, mouseY: Int): Unit = {
        if(tab.areChildrenActive) {
            slot.xPos = slotX
            slot.yPos = slotY
        } else {
            slot.xPos = -10000
            slot.yPos = -10000
        }
    }

    /**
      * Called after base render, is already translated to guiLeft and guiTop, just move offset
      */
    override def renderOverlay(guiLeft: Int, guiTop: Int, mouseX: Int, mouseY: Int): Unit = {}

    /**
      * Used to find how wide this is
      *
      * @return How wide the component is
      */
    override def getWidth: Int = width

    /**
      * Used to find how tall this is
      *
      * @return How tall the component is
      */
    override def getHeight: Int = height
}
