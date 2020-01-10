package com.sunflow.sunbakamod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sunflow.sunbakamod.setup.ModDimensions;
import com.sunflow.sunbakamod.util.TeleportationTools;
import com.sunflow.sunbakamod.util.VersionHelper;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameType;
import net.minecraft.world.dimension.DimensionType;

public class RedWorldCommand extends BaseCommand implements Command<CommandSource> {

	@Override
	public ArgumentBuilder<CommandSource, ?> getBuilder() {
		return Commands.literal("redworld")
				.requires(cs -> cs.hasPermissionLevel(0))
				.executes(this);
	}

	@Override
	public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity player = context.getSource().asPlayer();
		CompoundNBT playerData = player.getPersistentData();

		DimensionType oldDim;
		DimensionType newDim;

		if (player.dimension.equals(ModDimensions.REDSTONEWORLD_TYPE)) {
			String fromDim = playerData.getString("fromDim");
			DimensionType type = DimensionType.byName(new ResourceLocation(fromDim));
			oldDim = ModDimensions.REDSTONEWORLD_TYPE;
			newDim = type != null ? type : DimensionType.OVERWORLD;
		} else {
			playerData.putString("fromDim", player.dimension.getRegistryName().toString());
			oldDim = player.dimension;
			newDim = ModDimensions.REDSTONEWORLD_TYPE;
		}

		changeDimension(player, playerData, oldDim, newDim);

		return 0;
	}

	public static void changeDimension(ServerPlayerEntity player, CompoundNBT playerData, DimensionType oldDim, DimensionType newDim) {
		CompoundNBT oldDimTag = playerData.getCompound(oldDim.toString());
		oldDimTag.putDouble("x", VersionHelper.getX(player));
		oldDimTag.putDouble("y", VersionHelper.getY(player));
		oldDimTag.putDouble("z", VersionHelper.getZ(player));
		oldDimTag.put("mainInv", ItemStackHelper.saveAllItems(new CompoundNBT(), player.inventory.mainInventory));
		oldDimTag.put("armorInv", ItemStackHelper.saveAllItems(new CompoundNBT(), player.inventory.armorInventory));
		oldDimTag.put("offHandInv", ItemStackHelper.saveAllItems(new CompoundNBT(), player.inventory.offHandInventory));
		oldDimTag.putFloat("pitch", player.rotationPitch);
		oldDimTag.putFloat("yaw", player.rotationYaw);
		oldDimTag.putBoolean("flying", player.abilities.isFlying);
		oldDimTag.putString("gamemode", player.interactionManager.getGameType().getName());
		oldDimTag.putInt("experience", player.experienceTotal);

		playerData.put(oldDim.toString(), oldDimTag);

		CompoundNBT newDimTag = playerData.getCompound(newDim.toString());
		double x = newDimTag.getDouble("x");
		double y = newDimTag.getDouble("y");
		double z = newDimTag.getDouble("z");
		if (x == 0 && y == 0 && z == 0) y = 70;

		player.rotationPitch = newDimTag.getFloat("pitch");
		player.rotationYaw = newDimTag.getFloat("yaw");

		TeleportationTools.teleportToDimension(player, newDim, x, y, z);

		player.inventory.clear();
		ItemStackHelper.loadAllItems(newDimTag.getCompound("mainInv"), player.inventory.mainInventory);
		ItemStackHelper.loadAllItems(newDimTag.getCompound("armorInv"), player.inventory.armorInventory);
		ItemStackHelper.loadAllItems(newDimTag.getCompound("offHandInv"), player.inventory.offHandInventory);
		player.abilities.isFlying = newDimTag.getBoolean("flying");
		GameType newGameType = GameType.getByName(newDimTag.getString("gamemode"));
		player.setGameType(newGameType != GameType.NOT_SET ? newGameType : GameType.CREATIVE);
		player.giveExperiencePoints(newDimTag.getInt("experience") - player.experienceTotal);
	}
}
