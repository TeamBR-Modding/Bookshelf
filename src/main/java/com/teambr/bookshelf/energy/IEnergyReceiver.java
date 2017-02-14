package com.teambr.bookshelf.energy;

/**
 * This file was created for Bookshelf
 * <p>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * The interface for an object that receiver power
 *
 * @author Paul Davis <pauljoda>
 * @since 9/2/2016
 */
public interface IEnergyReceiver {

    /**
     * Allow the object to receive power
     * @param incomingPower The amount of power sent
     * @param doFill True to fill, false to simulate
     * @return The amount taken
     */
    int receivePower(int incomingPower, boolean doFill);
}
