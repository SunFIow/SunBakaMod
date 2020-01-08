package com.sunflow.sunbakamod.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class CraftingStickItem extends BaseItem {
	private static final ITextComponent name = new TranslationTextComponent("container.crafting");

	public CraftingStickItem(String name) {
		super(name, 1, 1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		if (!world.isRemote) {
			player.openContainer(getContainer(world, player.getPosition()));
			player.addStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
		}
		return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}

	public INamedContainerProvider getContainer(World world, BlockPos pos) {
		return new SimpleNamedContainerProvider((windowId, playerInv, player) -> {
			return new WorkbenchContainer(windowId, playerInv, IWorldPosCallable.of(world, pos)) {
				@Override
				public boolean canInteractWith(PlayerEntity playerIn) {
					return true;
				}
			};
		}, name);
	}
}
