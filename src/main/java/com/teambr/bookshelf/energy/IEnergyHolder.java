package com.teambr.bookshelf.energy;

/**
 * This file was created for Lux et Umbra
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * The interface for an object that can store power, not used to send or receive just store
 *
 * @author Paul Davis <pauljoda>
 * @since 9/2/2016
 */
public interface IEnergyHolder {

    /**
     * Get the amount of stored power in this object
     * @return The current power, must be positive
     */
    int getEnergyStored();

    /**
     * The max amount of energy this object can hold
     * @return The max energy stored, must be positive
     */
    int getMaxEnergyStored();
}
