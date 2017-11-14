package com.teambr.bookshelf.common.tiles.nbt.java;

import com.teambr.bookshelf.common.tiles.nbt.INBTHandler;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class IntegerNBTHandler implements INBTHandler<Integer> {

    @Override
    public boolean isClassValid(Class<?> aClass) {
        return int.class.isAssignableFrom(aClass) || Integer.class.isAssignableFrom(aClass);
    }

    @Override
    public boolean storeToNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, @Nonnull Integer object) {
        compound.setInteger(name, object);
        return true;
    }

    @Override
    public Integer readFromNBT(@Nonnull NBTTagCompound compound, @Nullable Field field, @Nonnull String name, @Nullable Integer object) {
        return compound.hasKey(name) ? compound.getInteger(name) : (object != null ? object : 0);
    }
}
