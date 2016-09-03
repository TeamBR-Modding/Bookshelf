package com.teambr.bookshelf.energy;

/**
 * This file was created for Lux et Umbra
 * <p>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * The interface to produce energy
 *
 * @author Paul Davis <pauljoda>
 * @since 9/2/2016
 */
public interface IEnergyProvider {

    /**
     * The amount of power this object can provide
     * @param maxOut The max amount to extract
     * @param doDrain True to drain, false to simulate
     * @return The amount drained from internal storage (successful sent)
     */
    int providePower(int maxOut, boolean doDrain);
}
