package com.sunflow.sunbakamod.dimension.redworld;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableSet;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.feature.structure.Structure;

public class RedWorldBiomeProvider extends BiomeProvider {
	private static final Biome BIOME = Biomes.DESERT;
	private static final List<Biome> SPAWNABLE = Collections.singletonList(BIOME);

	public RedWorldBiomeProvider() { super(ImmutableSet.of(BIOME)); }

	@Override
	public List<Biome> getBiomesToSpawnIn() { return SPAWNABLE; }

	@Override
	public Biome func_225526_b_(int x, int y, int z) { return BIOME; }

	@Override
	public boolean hasStructure(Structure<?> structureIn) { return false; }

//	@Override
//	public Biome getBiome(int x, int y) { return biome; }

//	@Override
//	public Biome[] getBiomes(int x, int z, int width, int length, boolean cacheFlag) {
//		Biome[] biomes = new Biome[width * length];
//		Arrays.fill(biomes, biome);
//		return biomes;
//	}

//	@Override
//	public Set<Biome> getBiomesInSquare(int centerX, int centerZ, int sideLength) {
//		return Collections.singleton(biome);
//	}

//	@Override
//	public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random) {
//		return new BlockPos(x, 65, z);
//	}

}
