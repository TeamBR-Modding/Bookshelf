package com.teambr.bookshelf.common.blocks;

import com.teambr.bookshelf.Bookshelf;
import com.teambr.bookshelf.common.tiles.TileTestBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TestBlock extends BaseBlock {

    public TestBlock(Material mat, String name, Class<? extends TileEntity> tile) {
        super(mat, name, tile);

        this.setBlockName(name);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        TileTestBlock tile = (TileTestBlock) world.getTileEntity(x, y, z);
        if (tile != null) {
            player.openGui(Bookshelf.instance, 0, world, x, y, z);
            return true;
        }
        return true;
    }
}