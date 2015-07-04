package com.dyonovan.brlib.client.renderer;

import com.dyonovan.brlib.client.ClientProxy;
import com.dyonovan.brlib.common.blocks.BaseBlock;
import com.dyonovan.brlib.util.RenderUtils;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class BasicBlockRenderer implements ISimpleBlockRenderingHandler {
    public static int renderID;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        RenderUtils.render3DInventory((BaseBlock) block, renderer);
        if(((BaseBlock)block).getBlockTextures().getOverlay() != null)
            RenderUtils.render3DInventory((BaseBlock) block, ((BaseBlock)block).getBlockTextures().getOverlay(), renderer);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        if(ClientProxy.renderPass == 0)
            return renderer.renderStandardBlock(block, x, y, z);
        else if(((BaseBlock)block).getBlockTextures().getOverlay() != null)
            renderer.renderBlockUsingTexture(Blocks.cobblestone, x, y, z, ((BaseBlock)block).getBlockTextures().getOverlay());
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
