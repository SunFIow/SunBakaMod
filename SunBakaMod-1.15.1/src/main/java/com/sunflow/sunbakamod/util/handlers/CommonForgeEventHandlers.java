package com.sunflow.sunbakamod.util.handlers;

import java.util.ArrayList;
import java.util.List;

import com.sunflow.sunbakamod.setup.ModDimensions;
import com.sunflow.sunbakamod.setup.ModEnchantments;
import com.sunflow.sunbakamod.util.Log;
import com.sunflow.sunbakamod.util.VersionHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonForgeEventHandlers {

	@SubscribeEvent
	public static void onBlockMined(BlockEvent.BreakEvent event) {
		PlayerEntity player = event.getPlayer();
		if (!VersionHelper.isSneaking(player)) return;
		IWorld world = event.getWorld();
		BlockPos pos = event.getPos();
		if (!(event.getState().getBlock() instanceof LogBlock)) return;
		if (world.getBlockState(pos.down()).getBlock() != Blocks.DIRT &&
				world.getBlockState(pos.down()).getBlock() != Blocks.GRASS_BLOCK)
			return;
		ItemStack axe = player.getHeldItemMainhand();
		if (!(axe.getItem() instanceof AxeItem) || EnchantmentHelper.getEnchantmentLevel(ModEnchantments.ENCHANTMENT_Timber, axe) == 0) return;
		fellTree(axe, player, world, pos, new ArrayList<>(), 0);
	}

	private static void fellTree(ItemStack axe, PlayerEntity player, IWorld world, BlockPos pos, List<BlockPos> list, int iteration) {
		list.add(pos);
		if (iteration > 10000) {
			player.sendMessage(new StringTextComponent("fellTree iteration > 10 000."));
			return;
		}
		if (axe.getCount() == 0) return;

		for (Direction dir : Direction.values()) {
			BlockPos checkingPos = pos.offset(dir);
			if (list.contains(checkingPos)) continue;
			Block checking = world.getBlockState(checkingPos).getBlock();
			if (checking instanceof LogBlock || checking instanceof LeavesBlock) {
//				world.destroyBlock(checkingPos, true);
				if (checking instanceof LogBlock)
					axe.damageItem(1, player, (entity) -> {
						entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
					});
				fellTree(axe, player, world, checkingPos, list, ++iteration);
			}
//			}
		}
		for (BlockPos p : list) {
			world.destroyBlock(p, true);
		}
		return;
	}

	@SubscribeEvent
	public static void onDimensionRegistry(RegisterDimensionsEvent event) {
		Log.info("-registerDimensions-");
		ModDimensions.DIMENSIONS.forEach((dim) -> dim.setDimensionType(DimensionManager.registerOrGetDimension(dim.getRegistryName(), dim, dim.getData(), dim.hasSkyLight())));
	}

	@SubscribeEvent
	public static void onPlayerUseItem(PlayerInteractEvent.RightClickBlock event) {
		PlayerEntity player = event.getPlayer();
		if (!player.abilities.allowEdit) return;
		if (!(event.getWorld() instanceof ServerWorld)) return;

		ServerWorld world = (ServerWorld) event.getWorld();
		BlockPos pos = event.getPos();
		BlockState state = world.getBlockState(pos);

		if (!state.canEntityDestroy(world, pos, player)) return;

		if (!(state.getBlock() instanceof CropsBlock)) return;
		CropsBlock block = (CropsBlock) state.getBlock();

		if (!block.isMaxAge(state)) return;

		List<ItemStack> drops = Block.getDrops(state, world, pos, world.getTileEntity(pos));
		drops.forEach((itemstack) -> {
			for (; itemstack.getCount() > 0; itemstack.setCount(itemstack.getCount() - 1)) {
				int i = player.inventory.storeItemStack(itemstack);
				if (i == -1) {
					i = player.inventory.getFirstEmptyStack();
				}
				ItemStack itemstack1 = itemstack.copy();
				itemstack1.setCount(1);
				if (!player.inventory.add(i, itemstack)) {
					Log.error("Can't find any space for item in the inventory");
				}
			}
		});

		if (!world.destroyBlock(pos, false)) return;

		int size = player.inventory.getSizeInventory();
		for (int j = 0; j < size; ++j) {
			ItemStack itemstack = player.inventory.getStackInSlot(j);
			if (itemstack.getItem().equals(block.getItem(world, pos, state).getItem())) {
				player.inventory.decrStackSize(j, 1);
				break;
			}
		}

		if (!world.setBlockState(pos, block.getDefaultState())) return;
	}

	@SubscribeEvent
	public static void onPlayerDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity dyingPlayer = (PlayerEntity) event.getEntity();
			BlockPos pos = dyingPlayer.getPosition();
			dyingPlayer.sendMessage(new StringTextComponent(String.format("You died at: [X: %s, Y: %s, Z: %s]", pos.getX(), pos.getY(), pos.getZ())));
		}
	}

}