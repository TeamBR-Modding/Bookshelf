package com.dyonovan.brlib.common.container;

import com.dyonovan.brlib.collections.Couplet;

public interface ICustomSlot {
    enum SLOT_SIZE {
        STANDARD,
        LARGE
    }

    SLOT_SIZE getSlotSize();

    Couplet<Integer, Integer> getPoint();
}