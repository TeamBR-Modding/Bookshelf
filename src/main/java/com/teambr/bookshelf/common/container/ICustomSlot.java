package com.teambr.bookshelf.common.container;

import com.teambr.bookshelf.collections.Couplet;

public interface ICustomSlot {
    enum SLOT_SIZE {
        STANDARD,
        LARGE
    }

    SLOT_SIZE getSlotSize();

    Couplet<Integer, Integer> getPoint();
}