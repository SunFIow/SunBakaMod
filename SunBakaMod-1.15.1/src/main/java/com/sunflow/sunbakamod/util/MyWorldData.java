package com.sunflow.sunbakamod.util;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.item.bag.EnderPackItem;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.WorldSavedData;

public class MyWorldData extends WorldSavedData {

	public static final String ID_ENDERPACK = SunBakaMod.MODID + "_enderpackstorage";
	public ArrayList<ChunkLoader> chunkloader = new ArrayList<>();

	public MyWorldData() {
		super(ID_ENDERPACK);
	}

	@Override
	public void read(CompoundNBT compound) {
		Log.debug("-readWorldData-");

		CompoundNBT tagES = compound.getCompound("enderStorage");
		EnderPackItem.handler.deserializeNBT(tagES);

		CompoundNBT tagCL = compound.getCompound("chunkloader");

		int num = tagCL.getInt("cl_num");
		for (int i = 0; i < num; i++) chunkloader.add(ChunkLoader.read(tagCL.getCompound("cl_" + i)));
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		Log.debug("-writeWorldData-");

		CompoundNBT tagES = EnderPackItem.handler.serializeNBT();
		compound.put("enderStorage", tagES);

		CompoundNBT tagCL = new CompoundNBT();
		int num = 0;
		for (ChunkLoader cl : chunkloader) tagCL.put("cl_" + num++, cl.write());
		tagCL.putInt("cl_num", num);
		compound.put("chunkloader", tagCL);

		return compound;
	}

	public void addChunkLoader(DimensionType dimension, boolean powered, BlockPos pos, ChunkPos chunkPos) {
		ChunkLoader cl = new ChunkLoader(dimension, powered, pos, chunkPos);
		if (chunkloader.contains(cl)) chunkloader.get(chunkloader.indexOf(cl)).update(dimension, powered, pos, chunkPos);
		else chunkloader.add(cl);
		markDirty();
	}

	public void removeChunkLoader(DimensionType dimension, boolean powered, BlockPos pos, ChunkPos chunkPos) {
		chunkloader.remove(new ChunkLoader(dimension, powered, pos, chunkPos));
		markDirty();
	}

	public void removeChunkLoader(ChunkLoader cl) {
		chunkloader.remove(cl);
		markDirty();
	}

	public static class ChunkLoader {
		public DimensionType dimension;
		public boolean powered;
		public BlockPos pos;
		public ChunkPos chunkPos;

		public ChunkLoader(DimensionType dimension, boolean powered, BlockPos pos, ChunkPos chunkPos) {
			this.dimension = dimension;
			this.powered = powered;
			this.pos = pos;
			this.chunkPos = chunkPos;
		}

		public void update(DimensionType dimension, boolean powered, BlockPos pos, ChunkPos chunkPos) {
			this.dimension = dimension;
			this.powered = powered;
			this.pos = pos;
			this.chunkPos = chunkPos;
		}

		@Nullable
		public static ChunkLoader read(CompoundNBT compound) {
			DimensionType dimension = DimensionType.getById(compound.getInt("dimension"));
			boolean powered = compound.getBoolean("powered");
			BlockPos pos = BlockPos.fromLong(compound.getLong("pos"));
			ChunkPos chunkPos = new ChunkPos(compound.getLong("chunkPos"));

			return new ChunkLoader(dimension, powered, pos, chunkPos);
		}

		public CompoundNBT write() {
			CompoundNBT tag = new CompoundNBT();
			tag.putBoolean("powered", powered);
			tag.putLong("pos", pos.toLong());
			tag.putLong("chunkPos", chunkPos.asLong());
			tag.putInt("dimension", dimension.getId());
			return tag;
		}

		public DimensionType getType() {
			return dimension;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ChunkLoader)) return false;
			ChunkLoader o = (ChunkLoader) obj;
			return (this.dimension.equals(o.dimension) && this.pos.equals(o.pos) && this.chunkPos.equals(o.chunkPos));
		}
	}
}