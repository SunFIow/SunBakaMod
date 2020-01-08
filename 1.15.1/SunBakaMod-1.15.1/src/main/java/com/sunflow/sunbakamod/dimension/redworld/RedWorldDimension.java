package com.sunflow.sunbakamod.dimension.redworld;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;

public class RedWorldDimension extends Dimension {

	public RedWorldDimension(World worldIn, DimensionType typeIn) {
		super(worldIn, typeIn, 0.0F);
	}

	@Override
	public int getActualHeight() { return 256; }

	@Override
	public SleepResult canSleepAt(PlayerEntity player, BlockPos pos) { return SleepResult.DENY; }

	@Override
	public ChunkGenerator<?> createChunkGenerator() {
		return new RedWorldChunkGenerator(world, new RedWorldBiomeProvider());
	}

	@Override
	public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) { return null; }

	@Override
	public BlockPos findSpawn(int posX, int posZ, boolean checkValid) { return null; }

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		int i = 6000;
		double d0 = (i + partialTicks) / 24000.0D - 0.25D;

		if (d0 < 0.0f) d0 += 1.0f;
		if (d0 > 1.0f) d0 -= 1.0f;
		double d1 = d0;
		d0 = 1.0D - (Math.cos(d0 * Math.PI) + 1.0D) / 2.0D;
		d0 = d1 + (d0 - d1) / 3.0D;
		return (float) d0;

//		double d0 = MathHelper.frac(worldTime / 24000.0D - 0.25D);
//		double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
//		return (float) (d0 * 2.0D + d1) / 3.0F;
	}

	@Override
	public boolean isSurfaceWorld() { return false; }

	@Override
	public Vec3d getFogColor(float celestialAngle, float partialTicks) { return new Vec3d(0, 0, 0); }

	@Override
	public boolean canRespawnHere() { return false; }

	@Override
	public boolean doesXZShowFog(int x, int z) { return false; }

}
