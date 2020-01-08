package com.sunflow.sunbakamod.util.interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public interface IHasInventory {
	public void openBag(PlayerEntity player, Hand hand);

	public void closeBag(PlayerEntity player, ItemStack itemstack);
}
