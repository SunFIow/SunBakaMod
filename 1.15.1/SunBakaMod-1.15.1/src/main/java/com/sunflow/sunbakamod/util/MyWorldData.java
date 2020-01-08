package com.sunflow.sunbakamod.util;

import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.item.bag.EnderPackItem;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.storage.WorldSavedData;

public class MyWorldData extends WorldSavedData {

	public static final String ID_ENDERPACK = SunBakaMod.MODID + "_enderpackstorage";

	public MyWorldData() {
		super(ID_ENDERPACK);
	}

	@Override
	public void read(CompoundNBT nbt) {
		Log.debug("-readWorldData-");

		CompoundNBT tag = nbt.getCompound("enderStorage");
		EnderPackItem.handler.deserializeNBT(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		Log.debug("-writeWorldData-");

		CompoundNBT tag = EnderPackItem.handler.serializeNBT();
		compound.put("enderStorage", tag);
		return compound;
	}
}