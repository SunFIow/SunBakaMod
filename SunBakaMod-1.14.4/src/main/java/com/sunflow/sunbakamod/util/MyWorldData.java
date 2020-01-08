package com.sunflow.sunbakamod.util;

import com.sunflow.sunbakamod.items.ItemEnderPack;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.storage.WorldSavedData;

public class MyWorldData extends WorldSavedData {

	public static final String ID = Reference.MOD_ID + "_enderpackstorage";

	public MyWorldData() {
		super(ID);
	}

	@Override
	public void read(CompoundNBT nbt) {
		Reference.NEKOLOGGER.info("-readWorldData-");

		CompoundNBT tag = nbt.getCompound("enderStorage");
		ItemEnderPack.handler.deserializeNBT(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		Reference.NEKOLOGGER.info("-writeWorldData-");

		CompoundNBT tag = ItemEnderPack.handler.serializeNBT();
		compound.put("enderStorage", tag);
		return compound;
	}
}