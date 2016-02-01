package com.teambr.bookshelf.client.gui.component.display

import java.awt.{Rectangle, Color}
import java.util

import com.teambr.bookshelf.client.gui.GuiBase
import com.teambr.bookshelf.client.gui.component.BaseComponent
import com.teambr.bookshelf.client.gui.component.listeners.IMouseEventListener
import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.gui.GuiScreen
import net.minecraft.inventory.Container
import net.minecraft.item.ItemStack
import org.lwjgl.opengl.GL11

import scala.collection.mutable.ArrayBuffer

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
class GuiTabCollection(parent: GuiBase[_ <: Container], x: Int) extends BaseComponent(x, 2) {
    protected var tabs = new ArrayBuffer[GuiTab]
    protected var activeTab: GuiTab = null

    setMouseEventListener(new TabMouseListener)

    /**
      * Add a standard tab to this wrapper
      *
      * @param components The components to add to the tab
      * @param maxWidth The max width of the tab
      * @param maxHeight The max height of the tab
      * @param color The color of the tab
      * @param stack What tab to display on the tab
      */
    def addTab(components: List[BaseComponent], maxWidth: Int, maxHeight: Int, color: Color, stack: ItemStack) {
        var tab: GuiTab = null
        tab = new GuiTab(parent, this.xPos - 5, this.yPos + (this.yPos + (tabs.size * 24)), maxWidth, maxHeight, color, stack)
        for (c <- components) tab.addChild(c)
        tabs += tab
    }

    /**
      * Add a reverse tab to this wrapper
      *
      * @param components The components to add to the tab
      * @param maxWidth The max width of the tab
      * @param maxHeight The max height of the tab
      * @param color The color of the tab
      * @param stack What tab to display on the tab
      */
    def addReverseTab(components: List[BaseComponent], maxWidth: Int, maxHeight: Int, color: Color, stack: ItemStack) {
        var tab: GuiTab = null
        tab = new GuiReverseTab(parent, this.xPos + 5, this.yPos + (this.yPos + (tabs.size * 24)), maxWidth, maxHeight, color, stack)
        for (c <- components) {
            tab.addChild(c)
        }
        tabs += tab
    }

    /**
      * Get the list of tabs in this wrapper
      *
      * @return
      */
    def getTabs: ArrayBuffer[GuiTab] = tabs

    def initialize(): Unit = {}

    def render(i: Int, i1: Int, mouseX: Int, mouseY: Int) {
        realignTabsVertically()
        for (tab <- tabs) {
            GL11.glPushMatrix()
            RenderUtils.prepareRenderState()
            GL11.glTranslated(tab.getXPos, tab.getYPos, 0)
            tab.render(0, 0, mouseX - tab.getXPos, mouseY - tab.getYPos)
            RenderUtils.restoreRenderState()
            GL11.glPopMatrix()
        }
    }

    /**
      * Move the tabs to fit the expansion of one
      */
    private def realignTabsVertically() {
        var y: Int = this.yPos
        for (tab <- tabs) {
            tab.setYPos(y)
            y += tab.getHeight
        }
    }

    def renderOverlay(i : Int, i1 : Int, mouseX: Int, mouseY: Int) {
        for (tab <- tabs) {
            GL11.glPushMatrix()
            RenderUtils.prepareRenderState()
            GL11.glTranslated(tab.getXPos, tab.getYPos, 0)
            tab.renderOverlay(0, 0, mouseX, mouseY)
            RenderUtils.restoreRenderState()
            GL11.glPopMatrix()
        }
    }

    /**
      * Render the tooltip if you can
      *
      * @param mouseX Mouse X
      * @param mouseY Mouse Y
      */
    override def renderToolTip(mouseX: Int, mouseY: Int, parent: GuiScreen) {
        for (tab <- tabs) {
            if (tab.isMouseOver(mouseX - this.parent.getGuiLeft, mouseY - this.parent.getGuiTop)) {
                tab.renderToolTip(mouseX, mouseY, parent)
            }
        }
    }

    /**
      * Used when a key is pressed
      *
      * @param letter The letter
      * @param keyCode The code
      */
    override def keyTyped(letter: Char, keyCode: Int) {
        for (tab <- tabs) tab.keyTyped(letter, keyCode)
    }

    override def getWidth: Int = 24

    override def getHeight: Int = 5 + (tabs.size * 24)

    override def isMouseOver(mouseX: Int, mouseY: Int): Boolean = {
        for (tab <- tabs) {
            if (tab.isMouseOver(mouseX, mouseY)) return true
        }
        false
    }

    def getAreasCovered(guiLeft : Int, guiTop : Int) : java.util.List[Rectangle] = {
        val list = new util.ArrayList[Rectangle]()
        tabs.foreach((tab : GuiTab) =>
            if(tab.isInstanceOf[GuiReverseTab])
                list.add(new Rectangle(guiLeft + tab.xPos - getWidth, guiTop + tab.yPos, tab.getWidth, tab.getHeight))
            else
                list.add(new Rectangle(guiLeft + tab.xPos, guiTop + tab.yPos, tab.getWidth, tab.getHeight))
        )
        list
    }

    private class TabMouseListener extends IMouseEventListener {
        override def onMouseDown(baseComponent: BaseComponent, x: Int, y: Int, i2: Int): Unit = {
            for (i <- tabs.indices) {
                val tab = tabs(i)
                if (tab.isMouseOver(x, y)) {
                    if (tab.getMouseEventListener == null) {
                        if (!tab.mouseDownActivated(if (tab.isInstanceOf[GuiReverseTab]) x + tab.expandedWidth - 5 else x - parent.getXSize + 5, y - (i * 24) - 2, i2)) {
                            if (activeTab ne tab) {
                                if (activeTab != null) activeTab.setActive(false)
                                activeTab = tab
                                activeTab.setActive(true)
                            }
                            else if (tab.isMouseOver(x, y)) {
                                tab.setActive(false)
                                activeTab = null
                            }
                        }

                    } else
                        tab.mouseDown(x, y, i2)
                    return
                }
            }
        }

        override def onMouseUp(baseComponent: BaseComponent, x: Int, y: Int, i2: Int): Unit = {
            for (i <- tabs.indices) {
                val tab = tabs(i)
                if (tab.isMouseOver(x, y)) {
                    tab.mouseUpActivated(if (tab.isInstanceOf[GuiReverseTab]) x + tab.expandedWidth - 5 else x - parent.getXSize + 5, y - (i * 24) - 2, i2)
                    return
                }
            }
        }

        override def onMouseDrag(baseComponent: BaseComponent, x: Int, y: Int, i2: Int, l: Long): Unit = {
            for (i <- tabs.indices) {
                val tab = tabs(i)
                if (tab.isMouseOver(x, y)) {
                    tab.mouseDragActivated(if (tab.isInstanceOf[GuiReverseTab]) x + tab.expandedWidth - 5 else x - parent.getXSize + 5, y - (i * 24) - 2, i2, l)
                    return
                }
            }
        }
    }
}