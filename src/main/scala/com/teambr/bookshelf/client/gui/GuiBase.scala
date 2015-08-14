package com.teambr.bookshelf.client.gui

import codechicken.nei.VisiblityData
import codechicken.nei.api.{TaggedInventoryArea, INEIGuiHandler}
import com.teambr.bookshelf.client.gui.component.display.{GuiReverseTab, GuiComponentText, GuiTabCollection}
import com.teambr.bookshelf.client.gui.component.{BaseComponent, NinePatchRenderer}
import com.teambr.bookshelf.common.container.{SLOT_SIZE, ICustomSlot}
import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.inventory.{SlotFurnaceOutput, Slot, Container}
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector
import net.minecraftforge.fml.common.Optional
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
 *
 * This is the base GUI class. All GUIs should extend this as it handles many things for you.
 *
 * For instance, you can add tabs, use any of our components, and you don't have to render any slots or make a background!
 * This class will handle drawing all slots and the background of the GUI. It will even center the title for you and translate it
 */
@Optional.InterfaceList(Array(new Optional.Interface(iface = "codechicken.nei.cofh.api.INEIGuiHandler", modid = "NotEnoughItems")))
abstract class GuiBase[T <: Container](val inventory : T, width : Int, height: Int, name : String)
        extends GuiContainer(inventory) with INEIGuiHandler {
    this.xSize = width
    this.ySize = height

    //Set up all our tings
    var title = new GuiComponentText(StatCollector.translateToLocal(name), xSize / 2 - Minecraft.getMinecraft.fontRendererObj.getStringWidth(StatCollector.translateToLocal(name)) / 2, 6)
    protected var background: NinePatchRenderer = new NinePatchRenderer
    protected var components = new ArrayBuffer[BaseComponent]
    protected var rightTabs = new GuiTabCollection(this, xSize)
    protected var leftTabs: GuiTabCollection = new GuiTabCollection(this, 0)


    //Start adding
    components += title
    addComponents()
    addRightTabs(rightTabs)
    addLeftTabs(leftTabs)
    components += rightTabs
    components += leftTabs

    /**
     * Adds the tabs to the right. Overwrite this if you want tabs on your GUI
     * @param tabs List of tabs, put GuiTabs in
     */
    def addRightTabs(tabs: GuiTabCollection) : Unit = {}


    /**
     * Add the tabs to the left. Overwrite this if you want tabs on your GUI
     * @param tabs List of tabs, put GuiReverseTabs in
     */
    def addLeftTabs(tabs: GuiTabCollection) : Unit = {}


    /**
     * This will be called after the GUI has been initialized and should be where you add all components.
     */
    def addComponents()

    /**
     * Used to get the guiLeft
     * @return Where the gui starts
     */
    def getGuiLeft: Int = guiLeft


    /**
     * Used to get guiTop
     * @return Where the gui starts
     */
    def getGuiTop: Int = guiTop

    /**
     * For some reason this isn't in vanilla
     * @return The size of the gui
     */
    def getXSize: Int = xSize


    /**
     * For some reason this isn't in vanilla
     * @return The size of the gui
     */
    def getYSize: Int = ySize

    /**
     * Called when the mouse is clicked
     * @param x The X Position
     * @param y The Y Position
     * @param button The button pressed
     */
    protected override def mouseClicked(x: Int, y: Int, button: Int) {
        super.mouseClicked(x, y, button)
        for (component <- components) if (component.isMouseOver(x - this.guiLeft, y - this.guiTop)) component.mouseDown(x - this.guiLeft, y - this.guiTop, button)
    }

    /**
     * Called when the mouse releases a button
     * @param x The X Position
     * @param y The Y Position
     * @param button The button released
     */
    protected override def mouseReleased(x: Int, y: Int, button: Int) {
        super.mouseReleased(x, y, button)
        for (component <- components) if (component.isMouseOver(x - this.guiLeft, y - this.guiTop)) component.mouseUp(x - this.guiLeft, y - this.guiTop, button)
    }

    /**
     * Used to track when the mouse is clicked and dragged
     * @param x The Current X Position
     * @param y The Current Y Position
     * @param button The button being dragged
     * @param time How long it has been pressed
     */
    protected override def mouseClickMove(x: Int, y: Int, button: Int, time: Long) {
        super.mouseClickMove(x, y, button, time)
        for (component <- components) if (component.isMouseOver(x - this.guiLeft, y - this.guiTop)) component.mouseDrag(x - this.guiLeft, y - this.guiTop, button, time)
    }

    /**
     * Called when a key is typed
     * @param letter The letter pressed, as a char
     * @param code The Java key code
     */
    protected override def keyTyped(letter: Char, code: Int) {
        super.keyTyped(letter, code)
        for (component <- components) component.keyTyped(letter, code)
    }

    /**
     * Used to draw above the background. This will be called after the background has been drawn
     *
     * Used mostly for adding text
     *
     * @param x The mouse X Position
     * @param y The mouse Y Position
     */
    protected override def drawGuiContainerForegroundLayer(x: Int, y: Int) {
        GL11.glPushMatrix()
        RenderUtils.prepareRenderState()
        for (component <- components) {
            RenderUtils.prepareRenderState()
            component.renderOverlay(0, 0)
            RenderUtils.restoreRenderState()
        }
        RenderUtils.restoreRenderState()
        RenderHelper.enableGUIStandardItemLighting()
        GL11.glPopMatrix()
    }

    /**
     * Called to draw the background
     *
     * Usually used to create the base on which to render things
     * @param f A float?
     * @param mouseX The mouse X
     * @param mouseY The mouse Y
     */
    protected def drawGuiContainerBackgroundLayer(f: Float, mouseX: Int, mouseY: Int) {
        GL11.glPushMatrix()
        GL11.glTranslated(guiLeft, guiTop, 0)
        RenderUtils.prepareRenderState()
        background.render(this, 0, 0, xSize, ySize)

        for (component <- components) {
            RenderUtils.prepareRenderState()
            component.render(0, 0)
            RenderUtils.restoreColor()
            RenderUtils.restoreRenderState()
        }

        RenderUtils.restoreRenderState()
        RenderUtils.bindGuiComponentsSheet()
        RenderUtils.prepareRenderState()

        for (i <- 0 until inventory.inventorySlots.size()) {
            val obj = inventory.inventorySlots.get(i)
            obj match {
                case customSlot : ICustomSlot =>
                    if(customSlot.getSlotSize == SLOT_SIZE.LARGE) this.drawTexturedModalRect(customSlot.getPoint._1, customSlot.getPoint._2, 0, 38, 26, 26)
                    else this.drawTexturedModalRect(customSlot.getPoint._1, customSlot.getPoint._2, 0, 20, 18, 18)
                case slot : Slot =>
                    if(isLargeSlot(slot)) this.drawTexturedModalRect(slot.xDisplayPosition - 5, slot.yDisplayPosition - 5, 0, 38, 26, 26)
                    else this.drawTexturedModalRect(slot.xDisplayPosition - 1, slot.yDisplayPosition - 1, 0, 20, 18, 18)
                case _ => //Not a slot. Somehow...
            }
        }
        RenderUtils.restoreRenderState()
        GL11.glPopMatrix()
    }

    /**
     * The main draw call. The super will handle calling the background and foreground layers. Then our extra code will run
     *
     * Used mainly to attach tool tips as they will always be on the top
     * @param mouseX The Mouse X Position
     * @param mouseY The mouse Y Position
     * @param par3 A Float?
     */
    override def drawScreen(mouseX: Int, mouseY: Int, par3: Float) {
        super.drawScreen(mouseX, mouseY, par3)
        for (component <- components) if (component.isMouseOver(mouseX - guiLeft, mouseY - guiTop)) component.renderToolTip(mouseX, mouseY, this)
    }

    /**
     * Used internally to check if the slot should be rendered with a large background
     *
     * At the moment, the only slot that we have to check is the furnace slot. If you make a custom slot,
     * you should implement ICustomSlot as that will handle this. If you're adding vanilla slot checks here, you're probably
     * using this wrong
     * @param slot The slot to check
     * @return True if it is a big slot
     */
    private def isLargeSlot(slot: Slot): Boolean = slot.isInstanceOf[SlotFurnaceOutput]

    /*******************************************************************************************************************
      ********************************************* NEI ****************************************************************
      *******************************************************************************************************************/

    override def modifyVisiblity (guiContainer: GuiContainer, visiblityData: VisiblityData) : VisiblityData = null

    override def getItemSpawnSlots(guiContainer: GuiContainer, itemStack: ItemStack) : java.lang.Iterable[Integer] = null

    override def getInventoryAreas(guiContainer: GuiContainer) : java.util.List[TaggedInventoryArea] = null

    override def handleDragNDrop(guiContainer: GuiContainer, i: Int, i1: Int, itemStack: ItemStack, i2: Int) : Boolean = false

    @Optional.Method(modid = "NotEnoughItems")
    override def hideItemPanelSlot(gc: GuiContainer, x: Int, y: Int, w: Int, h: Int) : Boolean = {
        val xMin: Int = guiLeft + xSize
        val yMin: Int = guiTop
        var xMax: Int = xMin
        var yMax: Int = yMin
        for (tab <- rightTabs.getTabs) {
            if (!tab.isInstanceOf[GuiReverseTab]) {
                if (tab.getWidth > 24) {
                    xMax += tab.getWidth + 10
                    yMax += tab.getHeight + 20
                }
                else yMax += 24
            }
        }
        ((x + w) > xMin && (x + w) < xMax && (y + h) > yMin && (y + h) < yMax) || ((x + w) < xMin + 30 && (x + w) > xMin)
    }
}
