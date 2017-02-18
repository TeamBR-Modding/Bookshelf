package com.teambr.bookshelf.client.gui.component.control;

import com.google.common.collect.Lists;
import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.misc.SidePicker;
import com.teambr.bookshelf.client.gui.misc.TrackballWrapper;
import com.teambr.bookshelf.util.ClientUtils;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.List;

/**
 * This file was created for Bookshelf
 * <p>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/13/2017
 */
public abstract class GuiComponentSideSelector extends BaseComponent {
    // Variables
    protected double scale;
    protected IBlockState blockState;
    protected TileEntity tile;
    protected boolean highListSelectedSides, renderTile;
    protected int diameter;
    protected boolean isInInitialPosition = false;
    protected TrackballWrapper trackball;
    protected EnumFacing lastSideHovered;

    /**
     * Creates the side selector object
     * @param parent The parent GUI
     * @param x The x pos
     * @param y The y pos
     * @param scaleValue The scale
     * @param state The block state of the block to render
     * @param tileEntity The tile entity of the block to render
     * @param doHighlights Render the highlights when selecting
     * @param doRenderTile Render the tile?
     */
    public GuiComponentSideSelector(GuiBase<?> parent, int x, int y,
                                    double scaleValue, @Nullable IBlockState state, @Nullable TileEntity tileEntity,
                                    boolean doHighlights, boolean doRenderTile) {
        super(parent, x, y);
        this.scale = scaleValue;
        this.blockState = state;
        this.tile = tileEntity;
        this.highListSelectedSides = doHighlights;
        this.renderTile = doRenderTile;

        this.diameter = MathHelper.ceiling_double_int(scale * Math.sqrt(3));
        trackball = new TrackballWrapper(1, 40);
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * This is called any time the user presses a side to toggle the mode. You should change your variable
     * here to the next value as well as handle any changes to the Tile or whatever.
     *
     * @param side The side that was selected
     * @param modifier 0 : Normal Click (you should toggle to the next mode)
     *                 1 : Shift Click (you should set to default or disabled)
     *                 2 : Control Click (you should go backward)
     */
    protected abstract void onSideToggled(EnumFacing side, int modifier);

    /**
     * This is used to color the highlight. Use whatever mode you have for each side and get the color that should
     * be displayed.
     * As a general rule:
     *     NULL    : Disabled
     *     BLUE    : Input
     *     ORANGE  : OUTPUT
     *     GREEN   : BOTH
     *
     * However you are free to use your own implementation as you feel needed
     *
     * @param side The side that needs a color
     * @return The color that should be rendered, or null for no color
     */
    @Nullable
    protected abstract Color getColorForMode(EnumFacing side);

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called when the mouse is pressed
     *
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    @Override
    public void mouseDown(int x, int y, int button) {
        lastSideHovered = null;
    }

    /**
     * Called when the mouse button is over the component and released
     *
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    @Override
    public void mouseUp(int x, int y, int button) {
        if(button == 0 && lastSideHovered != null)
            onSideToggled(lastSideHovered, ClientUtils.isShiftPressed() ? 1 : (ClientUtils.isCtrlPressed() ? 2 : 0));
    }

    /**
     * Called to render the component
     */
    @Override
    public void render(int guiLeft, int guiTop, int mouseX, int mouseY) {
        if(!isInInitialPosition || Mouse.isButtonDown(2)) {
            Entity renderViewEntity = Minecraft.getMinecraft().getRenderViewEntity();
            trackball.setTransform(RenderUtils.createEntityRotateMatrix(renderViewEntity));
            isInInitialPosition = true;
        }

        int width = getWidth();
        int height = getHeight();

        GlStateManager.pushMatrix();
        GlStateManager.translate(xPos + width / 2, yPos + height / 2, (float) diameter);
        GlStateManager.scale(scale, -scale, scale);
        trackball.update(mouseX - width, -(mouseY - height));

        if(blockState != null)
            drawBlock();

        if(tile != null && renderTile)
            TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, -0.5F, -0.5F, -0.5F, 0.0F);

        SidePicker picker = new SidePicker(0.5);

        List<Pair<SidePicker.Side, Color>> selections = Lists.newArrayListWithCapacity(6 + 1);
        SidePicker.HitCoord hitCoord = picker.getNearestHit();
        if(hitCoord != null)
            selections.add(Pair.of(hitCoord.side, getColorForMode(hitCoord.side.toEnumFacing())));

        if(highListSelectedSides) {
            for(EnumFacing dir : EnumFacing.values()) {
                selections.add(Pair.of(SidePicker.Side.fromEnumFacing(dir),
                        getColorForMode(dir) != null ? getColorForMode(dir) : new Color(0, 0, 0, 0)));
            }
        }

        if(selections != null)
            drawHighlights(selections);

        lastSideHovered = hitCoord == null ? null : hitCoord.side.toEnumFacing();

        GlStateManager.popMatrix();
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(int guiLeft, int guiTop, int mouseX, int mouseY) {
       // No Op
    }

    /**
     * Used to find how wide this is
     *
     * @return How wide the component is
     */
    @Override
    public int getWidth() {
        return diameter;
    }

    /**
     * Used to find how tall this is
     *
     * @return How tall the component is
     */
    @Override
    public int getHeight() {
        return diameter;
    }

    /*******************************************************************************************************************
     * GuiComponentSideSelector                                                                                        *
     *******************************************************************************************************************/

    /**
     * Draws our stored block
     */
    private void drawBlock() {
        Tessellator tessellator = Tessellator.getInstance();
        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.5, -0.5, -0.5);

        VertexBuffer vertexBuffer = tessellator.getBuffer();
        vertexBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(blockState);
        dispatcher.getBlockModelRenderer().renderModel(tile.getWorld(), model,
                blockState, new BlockPos(0, 0, 0), vertexBuffer, false);
        vertexBuffer.setTranslation(0.0, 0.0, 0.0);
        tessellator.draw();
        GlStateManager.popMatrix();
    }

    /**
     * Draws the highlights onto the block
     * @param selections The colors to render
     */
    private void drawHighlights(List<Pair<SidePicker.Side, Color>> selections) {
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();

        GL11.glBegin(GL11.GL_QUADS);
        for(Pair<SidePicker.Side, Color> pair : selections) {
            if(pair.getRight() != null)
                RenderUtils.setColor(pair.getRight());

            switch (pair.getLeft()) {
                case XPos :
                    GL11.glVertex3d(0.5, -0.5, -0.5);
                    GL11.glVertex3d(0.5, 0.5, -0.5);
                    GL11.glVertex3d(0.5, 0.5, 0.5);
                    GL11.glVertex3d(0.5, -0.5, 0.5);
                    break;
                case YPos :
                    GL11.glVertex3d(-0.5, 0.5, -0.5);
                    GL11.glVertex3d(-0.5, 0.5, 0.5);
                    GL11.glVertex3d(0.5, 0.5, 0.5);
                    GL11.glVertex3d(0.5, 0.5, -0.5);
                    break;
                case ZPos :
                    GL11.glVertex3d(-0.5, -0.5, 0.5);
                    GL11.glVertex3d(0.5, -0.5, 0.5);
                    GL11.glVertex3d(0.5, 0.5, 0.5);
                    GL11.glVertex3d(-0.5, 0.5, 0.5);
                    break;
                case XNeg :
                    GL11.glVertex3d(-0.5, -0.5, -0.5);
                    GL11.glVertex3d(-0.5, -0.5, 0.5);
                    GL11.glVertex3d(-0.5, 0.5, 0.5);
                    GL11.glVertex3d(-0.5, 0.5, -0.5);
                    break;
                case YNeg :
                    GL11.glVertex3d(-0.5, -0.5, -0.5);
                    GL11.glVertex3d(0.5, -0.5, -0.5);
                    GL11.glVertex3d(0.5, -0.5, 0.5);
                    GL11.glVertex3d(-0.5, -0.5, 0.5);
                    break;
                case ZNeg :
                    GL11.glVertex3d(-0.5, -0.5, -0.5);
                    GL11.glVertex3d(-0.5, 0.5, -0.5);
                    GL11.glVertex3d(0.5, 0.5, -0.5);
                    GL11.glVertex3d(0.5, -0.5, -0.5);
                    break;
                default:
            }
        }

        GL11.glEnd();

        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
    }

    /*******************************************************************************************************************
     * Accessors/Mutators                                                                                              *
     *******************************************************************************************************************/

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
        this.diameter = MathHelper.ceiling_double_int(scale * Math.sqrt(3));
    }

    public IBlockState getBlockState() {
        return blockState;
    }

    public void setBlockState(IBlockState blockState) {
        this.blockState = blockState;
    }

    public TileEntity getTile() {
        return tile;
    }

    public void setTile(TileEntity tile) {
        this.tile = tile;
    }

    public boolean isHighListSelectedSides() {
        return highListSelectedSides;
    }

    public void setHighListSelectedSides(boolean highListSelectedSides) {
        this.highListSelectedSides = highListSelectedSides;
    }

    public boolean isRenderTile() {
        return renderTile;
    }

    public void setRenderTile(boolean renderTile) {
        this.renderTile = renderTile;
    }
}
