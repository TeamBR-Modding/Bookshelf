package com.teambr.bookshelf.client.gui;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.component.display.GuiComponentText;
import com.teambr.bookshelf.client.gui.component.display.GuiTabCollection;
import com.teambr.bookshelf.util.ClientUtils;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This file was created for Bookshelf
 * <p>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/12/2017
 */
public abstract class GuiBase<T extends Container> extends GuiContainer {
    // Variables
    protected String name;
    protected GuiComponentText titleComponent;

    public ResourceLocation textureLocation;

    protected List<BaseComponent> components = new ArrayList<>();
    protected GuiTabCollection rightTabs;
    protected GuiTabCollection leftTabs;

    /**
     * Main constructor for Guis
     * @param inventory The container
     * @param width The width of the gui
     * @param height The height of the gui
     * @param title The title of the gui
     * @param texture The location of the background texture
     */
    public GuiBase(T inventory, int width, int height, String title, ResourceLocation texture) {
        super(inventory);
        this.xSize = width;
        this.ySize = height;
        this.name = title;
        this.textureLocation = texture;

        rightTabs = new GuiTabCollection(this, xSize + 1);
        leftTabs = new GuiTabCollection(this, -1);

        titleComponent = new GuiComponentText(this,
                xSize / 2 - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(ClientUtils.translate(name)) / 2),
                3, name, null);
        components.add(titleComponent);

        addComponents();
        addRightTabs(rightTabs);
        addLeftTabs(leftTabs);

        components.add(rightTabs);
        components.add(leftTabs);
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * This will be called after the GUI has been initialized and should be where you add all components.
     */
    protected abstract void addComponents();

    /*******************************************************************************************************************
     * GuiBase Methods                                                                                                 *
     *******************************************************************************************************************/

    /**
     * Adds the tabs to the right. Overwrite this if you want tabs on your GUI
     *
     * @param tabs List of tabs, put GuiTabs in
     */
    protected void addRightTabs(GuiTabCollection tabs) {}

    /**
     * Add the tabs to the left. Overwrite this if you want tabs on your GUI
     *
     * @param tabs List of tabs, put GuiReverseTabs in
     */
    protected void addLeftTabs(GuiTabCollection tabs) {}

    /*******************************************************************************************************************
     * Gui                                                                                                             *
     *******************************************************************************************************************/

    /**
     * Called when the mouse is clicked
     *
     * @param mouseX The X Position
     * @param mouseY The Y Position
     * @param mouseButton The button pressed
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        components.forEach((baseComponent -> {
            if(baseComponent.isMouseOver(mouseX - guiLeft, mouseY - guiTop)) {
                baseComponent.mouseDown(mouseX - guiLeft, mouseY - guiTop, mouseButton);
            }
        }));
    }

    /**
     * Called when the mouse releases a button
     *
     * @param mouseX The X Position
     * @param mouseY The Y Position
     * @param state The button released
     */
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        components.forEach((baseComponent -> {
            if(baseComponent.isMouseOver(mouseX - guiLeft, mouseY - guiTop)) {
                baseComponent.mouseUp(mouseX - guiLeft, mouseY - guiTop, state);
            }
        }));
    }

    /**
     * Used to track when the mouse is clicked and dragged
     *
     * @param mouseX The Current X Position
     * @param mouseY The Current Y Position
     * @param clickedMouseButton The button being dragged
     * @param timeSinceLastClick How long it has been pressed
     */
    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        components.forEach((baseComponent -> {
            if(baseComponent.isMouseOver(mouseX - guiLeft, mouseY - guiTop)) {
                baseComponent.mouseDrag(mouseX - guiLeft, mouseY - guiTop, clickedMouseButton, timeSinceLastClick);
            }
        }));
    }

    /**
     * Handle the mouse input
     */
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int scrollDirection = Mouse.getEventDWheel();
        if(scrollDirection != 0)
            components.forEach((baseComponent -> baseComponent.mouseScrolled(scrollDirection > 0 ? 1 : -1)));
    }

    /**
     * Called when a key is typed
     *
     * @param typedChar The letter pressed, as a char
     * @param keyCode The Java key code
     */
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        components.forEach((baseComponent -> baseComponent.keyTyped(typedChar, keyCode)));
    }

    /**
     * Used to draw above the background. This will be called after the background has been drawn
     *
     * Used mostly for adding text
     *
     * @param mouseX The mouse X Position
     * @param mouseY The mouse Y Position
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        RenderUtils.prepareRenderState();
        RenderUtils.bindTexture(textureLocation);
        components.forEach((baseComponent -> {
            RenderUtils.prepareRenderState();
            baseComponent.renderOverlay(0, 0, mouseX - guiLeft, mouseY - guiTop);
            RenderUtils.restoreRenderState();
            RenderUtils.restoreColor();
        }));
        RenderUtils.restoreRenderState();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.popMatrix();
    }

    /**
     * Called to draw the background
     *
     * Usually used to create the base on which to render things
     *
     * @param partialTicks partial ticks
     * @param mouseX The mouse X
     * @param mouseY The mouse Y
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        RenderUtils.prepareRenderState();
        GlStateManager.translate(guiLeft, guiTop, 0);

        RenderUtils.bindTexture(textureLocation);
        drawTexturedModalRect(0, 0, 0, 0, xSize + 1, ySize + 1);

        components.forEach((baseComponent -> {
            RenderUtils.prepareRenderState();
            baseComponent.render(0, 0, mouseX - guiLeft, mouseY - guiTop);
            RenderUtils.restoreRenderState();
            RenderUtils.restoreColor();
        }));
        RenderUtils.restoreRenderState();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.popMatrix();
    }

    /**
     * The main draw call. The super will handle calling the background and foreground layers. Then our extra code will run
     *
     * Used mainly to attach tool tips as they will always be on the top
     *
     * @param mouseX The Mouse X Position
     * @param mouseY The mouse Y Position
     * @param partialTicks partial ticks
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        components.forEach((baseComponent -> {
            if(baseComponent.isMouseOver(mouseX - guiLeft, mouseY - guiTop))
                baseComponent.renderToolTip(mouseX, mouseY);
        }));
    }

    /*******************************************************************************************************************
     * Accessors/Mutators                                                                                              *
     *******************************************************************************************************************/

    /**
     * Used to get the guiLeft
     *
     * @return Where the gui starts
     */
    public int getGuiLeft() {
        return guiLeft;
    }

    /**
     * Used to get guiTop
     *
     * @return Where the gui starts
     */
    public int getGuiTop() {
        return guiTop;
    }

    /**
     * For some reason this isn't in vanilla
     *
     * @return The size of the gui
     */
    public int getXSize() {
        return xSize;
    }

    /**
     * For some reason this isn't in vanilla
     *
     * @return The size of the gui
     */
    public int getYSize() {
        return ySize;
    }

    /*******************************************************************************************************************
     * JEI                                                                                                             *
     *******************************************************************************************************************/

    /**
     * Returns a list of Rectangles that represent the areas covered by the GUI
     *
     * @return A list of covered areas
     */
    public List<Rectangle> getCoveredAreas() {
        List<Rectangle> areas = new ArrayList<>();
        areas.add(new Rectangle(guiLeft, guiTop, xSize, ySize));
        components.forEach((baseComponent -> {
            if(baseComponent instanceof GuiTabCollection) {
                GuiTabCollection tabCollection = (GuiTabCollection) baseComponent;
                areas.addAll(tabCollection.getAreasCovered(guiLeft, guiTop));
            } else
                areas.add(new Rectangle(baseComponent.getArea(guiLeft, guiTop)));
        }));
        return areas;
    }
}
