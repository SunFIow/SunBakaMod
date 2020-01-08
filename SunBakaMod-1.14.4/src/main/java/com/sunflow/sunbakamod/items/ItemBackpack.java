package com.sunflow.sunbakamod.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ItemBackpack extends ItemBagBase {

	private IItemHandler handler = createHandler(9 * 6);

	public ItemBackpack(String name) {
		super(name);
	}

	public ItemBackpack(String name, String customName) {
		super(name);
		setCustomName(customName);
	}

	public IItemHandler createHandler(int num) {
		return new ItemStackHandler(num) {
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				return !(stack.getItem() instanceof ItemBackpack);
			}
		};
	}

	@Override
	public void openBag(PlayerEntity player, Hand hand) {
		// if (!listOfOpenNames.contains(this.getRegistryName().getPath())) {
		this.readFromNBT(player.getHeldItem(hand).getOrCreateTag());
		super.openBag(player, hand);
		// listOfOpenNames.add(this.getRegistryName().getPath());
		// }
	}

	@Override
	public void closeBag(PlayerEntity player, ItemStack itemstack) {
		itemstack.setTag(this.writeToNBT(itemstack.getOrCreateTag()));
		// listOfOpenNames.remove(this.getRegistryName().getPath());
	}

	public IItemHandler getHandler() {
		return handler;
	}

	@SuppressWarnings("unchecked")
	public void readFromNBT(CompoundNBT tag) {
		CompoundNBT invTag = tag.getCompound("inv");
		((INBTSerializable<CompoundNBT>) handler).deserializeNBT(invTag);
	}

	@SuppressWarnings("unchecked")
	public CompoundNBT writeToNBT(CompoundNBT tag) {
		CompoundNBT invTag = ((INBTSerializable<CompoundNBT>) handler).serializeNBT();
		tag.put("inv", invTag);
		return tag;
	}
}
