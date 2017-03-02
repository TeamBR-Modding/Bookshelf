package com.teambr.bookshelf.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * This file was created for NeoTech
 * <p>
 * NeoTech is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Helper class to help manage energy, based off TeslaUtils but with Forge Energy
 *
 * @author Paul Davis - pauljoda
 * @since 3/1/2017
 */
public class EnergyUtils {

    /**
     * Converts the given number into a readable energy number. Also adds the suffix
     * @param energy The number
     * @return A readable number
     */
    @SideOnly(Side.CLIENT)
    public static String getEnergyDisplay(int energy) {
        // If shift is press, give normal amount
        if(ClientUtils.isShiftPressed())
            return ClientUtils.formatNumber(energy) + " E";

        // No formatting, just add E
        if(energy < 1000)
            return energy + " E";

        // Exponent of 1000
        final int exp = (int) (Math.log(energy) / Math.log(1000));
        // Converts into the right prefix
        final char unitType = "KMGTPE".charAt(exp - 1);
        // Returns string with energy trimmed below the 1000, and adding the energy unit
        return String.format("%.1f %sE", energy / Math.pow(1000, exp), unitType);
    }

    /**
     * Transfers power from one storage to another, either can be null if you are not sure if it is capable
     * @param source      The source energy storage
     * @param destination The destination energy storage
     * @param maxAmount   Max amount to transfer
     * @param simulate    True to only simulate, not actually transfer
     * @return The amount moved or would be moved
     */
    public static int transferPower(@Nullable IEnergyStorage source, @Nullable IEnergyStorage destination,
                                    int maxAmount, boolean simulate) {
        if(source == null || destination == null)
            return 0;

        int amount = source
                .extractEnergy(destination.receiveEnergy(maxAmount, true), true);
        // Try move power
        return destination
                .receiveEnergy(source
                        .extractEnergy(amount, simulate), simulate);
    }

    /**
     * Gets a list of all capabilities that touch a BlockPos. This will search for tile
     * entities touching the BlockPos and then query them for access to their capabilities.
     *
     * @param capability The capability you want to retrieve.
     * @param world The world that this is happening in.
     * @param pos The position to search around.
     * @return A list of all capabilities that are being held by connected blocks.
     */
    public static <T> List<T> getConnectedCapabilities (Capability<T> capability, World world, BlockPos pos) {

        final List<T> capabilities = new ArrayList<T>();

        for (final EnumFacing side : EnumFacing.values()) {

            final TileEntity tile = world.getTileEntity(pos.offset(side));

            if (tile != null && !tile.isInvalid() && tile.hasCapability(capability, side.getOpposite()))
                capabilities.add(tile.getCapability(capability, side.getOpposite()));
        }

        return capabilities;
    }

    /**
     * Sends power to all faces connected
     * @param source        The energy source
     * @param world         The world
     * @param pos           The position
     * @param amountPerFace How much per face
     * @param simulated     True to just simulate
     * @return How much energy consumed
     */
    public static int distributePowerToFaces(IEnergyStorage source, World world, BlockPos pos, int amountPerFace, boolean simulated) {
        int consumedPower = 0;

        for(EnumFacing dir : EnumFacing.values()) {
            TileEntity tile = world.getTileEntity(pos.offset(dir));
            if (tile != null && !tile.isInvalid() && tile.hasCapability(CapabilityEnergy.ENERGY, dir.getOpposite()))
                consumedPower += transferPower(source, tile.getCapability(CapabilityEnergy.ENERGY, dir.getOpposite()), amountPerFace, simulated);
        }

        return consumedPower;
    }

    /**
     * Sends power to all faces connected
     * @param source        The energy source
     * @param world         The world
     * @param pos           The position
     * @param amountPerFace How much per face
     * @param simulated     True to just simulate
     * @return How much energy consumed
     */
    public static int consumePowerFromFaces(IEnergyStorage source, World world, BlockPos pos, int amountPerFace, boolean simulated) {
        int receivedPower = 0;

        for(EnumFacing dir : EnumFacing.values()) {
            TileEntity tile = world.getTileEntity(pos.offset(dir));
            if (tile != null && !tile.isInvalid() && tile.hasCapability(CapabilityEnergy.ENERGY, dir.getOpposite()))
                receivedPower += transferPower(tile.getCapability(CapabilityEnergy.ENERGY, dir.getOpposite()), source, amountPerFace, simulated);
        }

        return receivedPower;
    }

    /**
     * Adds the info needed to display held energy
     * @param stack   The stack
     * @param toolTip The tip list
     */
    @SideOnly(Side.CLIENT)
    public static void addToolTipInfo(ItemStack stack, List<String> toolTip) {
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            toolTip.add(ChatFormatting.YELLOW + ClientUtils.translate("bookshelfapi.energy.energyStored"));
            toolTip.add("  " + getEnergyDisplay(energyStorage.getEnergyStored()) + " / " + getEnergyDisplay(energyStorage.getMaxEnergyStored()));
            if(!ClientUtils.isShiftPressed()) {
                toolTip.add("");
                toolTip.add(ChatFormatting.ITALIC + ClientUtils.translate("bookshelfapi.text.shiftInfo"));
            }
        }
    }
}
