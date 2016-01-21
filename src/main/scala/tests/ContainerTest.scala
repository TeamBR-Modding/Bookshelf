package tests

import com.teambr.bookshelf.common.container.BaseContainer
import com.teambr.bookshelf.common.container.slots.PhantomSlot
import net.minecraft.inventory.IInventory

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 04, 2015
 */
class ContainerTest(playerInventory: IInventory, inventory: IInventory) extends BaseContainer(playerInventory, inventory) {
    addInventoryLine(10, 50, 0, 3)
    addSlotToContainer(new PhantomSlot(inventory, 3, 50, 30))
    addPlayerInventorySlots(85)
}
