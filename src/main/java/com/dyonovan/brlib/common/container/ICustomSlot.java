package com.dyonovan.brlib.common.container;

import com.dyonovan.brlib.lib.Couplet;

public interface ICustomSlot {
    public enum SLOT_SIZE {
        STANDARD,
        LARGE
    }

    public SLOT_SIZE getSlotSize();

    public Couplet<Integer, Integer> getPoint();
}