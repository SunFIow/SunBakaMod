package com.sunflow.sunbakamod.dimension.redworld;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Maps;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraft.world.gen.WorldGenRegion;

public class RedWorldChunkGenerator extends FlatChunkGenerator {

	private static final int HEIGHT = 64;
	private static final BlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
	private static final BlockState SANDSTONE = Blocks.SANDSTONE.getDefaultState();

	private static final FlatGenerationSettings SETTINGS = getSettings(Biomes.DESERT, Collections.emptyList(), new FlatLayerInfo(52, Blocks.SANDSTONE), new FlatLayerInfo(3, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));

	public RedWorldChunkGenerator(IWorld world, BiomeProvider biomeProvider) { super(world, biomeProvider, SETTINGS); }

	@Override
//	public void generateSurface(IChunk chunk) {
	public void func_225551_a_(WorldGenRegion worldGenRegion, IChunk chunk) {

		BlockPos.Mutable pos = new BlockPos.Mutable();

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				chunk.setBlockState(pos.setPos(x, 0, z), BEDROCK, false);
				for (int y = 1; y < HEIGHT; y++) {
					chunk.setBlockState(pos.setPos(x, y, z), SANDSTONE, false);
				}
			}
		}

	}

	private static FlatGenerationSettings getSettings(Biome biome, List<String> options, FlatLayerInfo... layers) {
		FlatGenerationSettings settings = ChunkGeneratorType.FLAT.createSettings();

		for (int i = layers.length - 1; i >= 0; --i) {
			settings.getFlatLayers().add(layers[i]);
		}

		settings.setBiome(biome);
		settings.updateLayers();

		for (String s : options) {
			settings.getWorldFeatures().put(s, Maps.newHashMap());
		}
		return settings;
	}
}
