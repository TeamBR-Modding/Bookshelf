package test.com.teambr.bookshelf.common.container;

import test.com.teambr.bookshelf.common.tiles.TileTestBlock;
import com.teambr.bookshelf.inventory.BaseContainer;
import net.minecraft.inventory.IInventory;

public class ContainerTestBlock extends BaseContainer {
    public ContainerTestBlock(IInventory playerInventory, TileTestBlock tileEntity) {
        super(playerInventory, tileEntity);
        addInventoryLine(10, 10, 0, 3);
        addPlayerInventorySlots(10, 70);
    }
}
