package com.teambr.bookshelf.inventory;

import net.minecraft.inventory.IInventory;

public interface IInventoryCallback {
    public void onInventoryChanged(IInventory inventory, int slotNumber);
}
