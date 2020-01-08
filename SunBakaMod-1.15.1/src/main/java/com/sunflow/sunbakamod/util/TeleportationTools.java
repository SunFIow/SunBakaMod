package com.sunflow.sunbakamod.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEventPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.network.play.server.SServerDifficultyPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.hooks.BasicEventHooks;
import net.minecraftforge.fml.network.NetworkHooks;

public class TeleportationTools {

	private TeleportationTools() {}

	public static void teleport(ServerPlayerEntity player, BlockPos pos) { teleportToDimension(player, player.dimension, pos); }

	public static void teleport(ServerPlayerEntity player, double x, double y, double z) { teleportToDimension(player, player.dimension, x, y, z); }

	public static void teleportToDimension(ServerPlayerEntity player, int dimensionID, BlockPos pos) { teleportToDimension(player, DimensionType.getById(dimensionID), pos); }

	public static void teleportToDimension(ServerPlayerEntity player, DimensionType dimensionType, BlockPos pos) { teleportToDimension(player, dimensionType, pos.getX(), pos.getY(), pos.getZ()); }

	public static void teleportToDimension(ServerPlayerEntity player, DimensionType dimensionType, double x, double y, double z) {
		ServerWorld world = player.getServer().getWorld(dimensionType);

		world.getChunkProvider().func_217228_a(TicketType.POST_TELEPORT, new ChunkPos(new BlockPos(x, y, z)), 1, player.getEntityId());

		if (player.isSleeping()) player.wakeUp();
		player.detach();

		player.teleport(world, x, y, z, player.rotationYaw, player.rotationPitch);
	}

	public static ServerPlayerEntity teleport(ServerPlayerEntity entity, DimensionType destinationDim, BlockPos pos) {
		if (entity.dimension.equals(destinationDim)) {
			entity.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
			return entity;
		}
		if (!ForgeHooks.onTravelToDimension(entity, destinationDim)) {
			return null;
		}
		DimensionType sourceDim = entity.dimension;
		ServerWorld sourceWorld = entity.server.getWorld(sourceDim);

		entity.dimension = destinationDim;
		ServerWorld destinationWorld = entity.server.getWorld(destinationDim);

		WorldInfo worldinfo = entity.world.getWorldInfo();
		NetworkHooks.sendDimensionDataPacket(entity.connection.netManager, entity);
		entity.connection.sendPacket(new SRespawnPacket(destinationDim, WorldInfo.func_227498_c_(worldinfo.getSeed()), worldinfo.getGenerator(), entity.interactionManager.getGameType()));
		entity.connection.sendPacket(new SServerDifficultyPacket(worldinfo.getDifficulty(), worldinfo.isDifficultyLocked()));
		PlayerList playerlist = entity.server.getPlayerList();
		playerlist.updatePermissionLevel(entity);
		sourceWorld.removeEntity(entity);
		entity.revive();
		double d0 = VersionHelper.getX(entity);
		double d1 = VersionHelper.getY(entity);
		double d2 = VersionHelper.getZ(entity);
		float f0 = entity.rotationPitch;
		float f1 = entity.rotationYaw;
		sourceWorld.getProfiler().startSection("moving");
		double movefactor = sourceWorld.getDimension().getMovementFactor() / destinationWorld.getDimension().getMovementFactor();
		d0 *= movefactor;
		d1 *= movefactor;
		entity.setLocationAndAngles(d0, d1, d2, f1, f0);
		sourceWorld.getProfiler().endSection();
		sourceWorld.getProfiler().startSection("placing");
		double d4 = Math.min(-2.999872E7D, destinationWorld.getWorldBorder().minX() + 16.0D);
		double d5 = Math.min(-2.999872E7D, destinationWorld.getWorldBorder().minZ() + 16.0D);
		double d6 = Math.min(2.999872E7D, destinationWorld.getWorldBorder().minX() - 16.0D);
		double d7 = Math.min(2.999872E7D, destinationWorld.getWorldBorder().minZ() - 16.0D);
		d0 = MathHelper.clamp(d0, d4, d6);
		d2 = MathHelper.clamp(d2, d5, d7);
		entity.setLocationAndAngles(d0, d1, d2, f1, f0);

		sourceWorld.getProfiler().endSection();
		entity.setWorld(destinationWorld);
		destinationWorld.func_217447_b(entity);
		entity.connection.setPlayerLocation(VersionHelper.getX(entity), VersionHelper.getY(entity), VersionHelper.getZ(entity), f1, f0);
		entity.interactionManager.setWorld(destinationWorld);
		entity.connection.sendPacket(new SPlayerAbilitiesPacket(entity.abilities));
		playerlist.sendWorldInfo(entity, destinationWorld);
		playerlist.sendInventory(entity);

		for (EffectInstance effect : entity.getActivePotionEffects()) {
			entity.connection.sendPacket(new SPlayEntityEffectPacket(entity.getEntityId(), effect));
		}

		entity.connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
		entity.setExperienceLevel(entity.experienceLevel);
		entity.setHealth(entity.getHealth());

		BasicEventHooks.firePlayerChangedDimensionEvent(entity, sourceDim, destinationDim);
		return entity;
	}
}
