package com.sunflow.sunbakamod.block.chunkloader;

import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.setup.ModBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;

public class ChunkLoaderTile extends TileEntity {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	private boolean powered;
	private ChunkPos chunkPos;

	public ChunkLoaderTile() {
		super(ModBlocks.CHUNK_LOADER_TILE);
	}

	public void click() {
		powered = !powered;
		if (chunkPos == null) chunkPos = new ChunkPos(pos);
		forceChunk(world, pos, chunkPos, powered);
		markDirty();
	}

	@Override
	public void remove() {
		SunBakaMod.data.removeChunkLoader(world.dimension.getType(), powered, pos, chunkPos);
		super.remove();
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT tag = getTileData();

		tag.putBoolean("powered", powered);
		if (chunkPos == null) chunkPos = new ChunkPos(pos);
		tag.putLong("chunkPos", chunkPos.asLong());

		SunBakaMod.data.addChunkLoader(world.dimension.getType(), powered, pos, chunkPos);

		return super.write(compound);
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);

		CompoundNBT tag = getTileData();
		powered = tag.getBoolean("powered");
		chunkPos = new ChunkPos(tag.getLong("chunkPos"));
	}

	public static void forceChunk(IWorld world, BlockPos pos, ChunkPos chunkPos, boolean newState) {
		world.getChunkProvider().forceChunk(chunkPos, newState);

		BlockState state = world.getBlockState(pos);
		if (state.get(POWERED) != newState) world.setBlockState(pos, state.with(POWERED, newState), 3);
	}
}
