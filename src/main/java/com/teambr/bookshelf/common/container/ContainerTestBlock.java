package com.teambr.bookshelf.common.container;

import com.teambr.bookshelf.common.tiles.TileTestBlock;
import com.teambr.bookshelf.inventory.BaseContainer;
import net.minecraft.inventory.IInventory;

public class ContainerTestBlock extends BaseContainer {
    public ContainerTestBlock(IInventory playerInventory, TileTestBlock tileEntity) {
        super(playerInventory, tileEntity);
    }
}
