package com.sunflow.sunbakamod.block.chunkloader;

import com.sunflow.sunbakamod.setup.ModBlocks;
import com.sunflow.sunbakamod.util.Log;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class ChunkLoaderTile extends TileEntity {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	private boolean powered;

	public ChunkLoaderTile() {
		super(ModBlocks.CHUNK_LOADER_TILE);
	}

	public void changeState(World world, BlockPos pos) {
		if (world.isRemote) return;
		Log.warn("1: " + powered);
		powered = !powered;
		world.getChunkProvider().forceChunk(new ChunkPos(pos), powered);
		BlockState state = world.getBlockState(pos);
		if (state.get(POWERED) != powered) world.setBlockState(pos, state.with(POWERED, powered), 3);
		Log.warn("2: " + powered);

	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putBoolean("powered", powered);
		return super.write(compound);
	}

	@Override
	public void read(CompoundNBT compound) {
		powered = compound.getBoolean("powered");
		super.read(compound);
	}
}
