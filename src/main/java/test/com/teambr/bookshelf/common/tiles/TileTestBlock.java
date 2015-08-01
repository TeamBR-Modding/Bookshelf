package test.com.teambr.bookshelf.common.tiles;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import com.teambr.bookshelf.api.waila.IWaila;
import com.teambr.bookshelf.collections.InventoryTile;
import com.teambr.bookshelf.common.tiles.BaseTile;
import com.teambr.bookshelf.common.tiles.IOpensGui;
import com.teambr.bookshelf.helpers.GuiHelper;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import test.com.teambr.bookshelf.client.gui.GuiTestBlock;
import test.com.teambr.bookshelf.common.container.ContainerTestBlock;

import java.util.List;

public class TileTestBlock extends BaseTile implements IOpensGui, IInventory, IWaila, IEnergyHandler {

    protected EnergyStorage energy;
    protected InventoryTile inventory;

    public TileTestBlock() {
        energy = new EnergyStorage(32000);
        inventory = new InventoryTile(4);
    }
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerTestBlock(player.inventory, this);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GuiTestBlock(player.inventory, this);
    }
    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack returnStack = inventory.getStackInSlot(slot).copy();
        inventory.getStackInSlot(slot).stackSize -= amount;
        if(inventory.getStackInSlot(slot).stackSize <= 0)
            inventory.setStackInSlot(null, slot);
        return returnStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory.setStackInSlot(stack, slot);
    }

    /**
     * Returns the name of the inventory
     */
    public String getInventoryName() {
        return null;
    }

    /**
     * Returns if the inventory is named
     */
    public boolean hasCustomInventoryName() {
        return false;
    }


    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    /**
     * Do not give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public void returnWailaHead(List<String> tip) {
        tip.add(GuiHelper.GuiColor.LIGHTBLUE.toString() + GuiHelper.GuiTextFormat.ITALICS + "Shift+Click to access GUI");
    }

    @Override
    public void returnWailaBody(List<String> tip) {

    }

    @Override
    public void returnWailaTail(List<String> tip) {

    }

    @Override
    public ItemStack returnWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return new ItemStack(Blocks.anvil);
    }

    @Override
    public NBTTagCompound returnNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return 0;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return false;
    }

    @Override
    public void readFromNBT (NBTTagCompound tags) {
        super.readFromNBT(tags);
        energy.readFromNBT(tags);
    }

    @Override
    public void writeToNBT (NBTTagCompound tags) {
        super.writeToNBT(tags);
        energy.writeToNBT(tags);
    }
}
