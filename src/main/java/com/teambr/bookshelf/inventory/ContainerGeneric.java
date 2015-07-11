package com.teambr.bookshelf.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerGeneric extends Container {
    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_) {
        return true;
    }
}
