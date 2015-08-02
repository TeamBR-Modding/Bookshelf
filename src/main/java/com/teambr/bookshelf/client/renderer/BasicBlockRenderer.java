package com.teambr.bookshelf.client.renderer;

import com.teambr.bookshelf.client.ClientProxy;
import com.teambr.bookshelf.common.blocks.BlockBase;
import com.teambr.bookshelf.util.RenderUtils;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

public class BasicBlockRenderer implements ISimpleBlockRenderingHandler {
    public static int renderID;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        RenderUtils.render3DInventory((BlockBase) block, renderer);
        if(((BlockBase)block).textures().getOverlay() != null)
            RenderUtils.render3DInventory((BlockBase) block, ((BlockBase)block).textures().getOverlay(), renderer);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        if(ClientProxy.renderPass == 0)
            return renderer.renderStandardBlock(block, x, y, z);
        else if(ClientProxy.renderPass == 1 && ((BlockBase) block).textures().getOverlay() != null)
            renderer.renderBlockUsingTexture(Blocks.cobblestone, x, y, z, ((BlockBase)block).textures().getOverlay());
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return renderID;
    }
}
