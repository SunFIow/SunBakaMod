package com.sunflow.sunbakamod.setup;

import java.util.ArrayList;
import java.util.List;

import com.sunflow.sunbakamod.block.chunkloader.ChunkLoaderBlock;
import com.sunflow.sunbakamod.block.chunkloader.ChunkLoaderTile;
import com.sunflow.sunbakamod.util.TileEntityHelper;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<>();
	public static final List<TileEntityType<? extends TileEntity>> TILE_TYPES = new ArrayList<TileEntityType<? extends TileEntity>>();

	public static final Block CHUNK_LOADER = new ChunkLoaderBlock();
	public static final TileEntityType<ChunkLoaderTile> CHUNK_LOADER_TILE = TileEntityHelper.createType(ChunkLoaderTile::new, CHUNK_LOADER);
}
