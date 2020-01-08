package com.sunflow.sunbakamod.bag;

import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.items.ItemBackpack;
import com.sunflow.sunbakamod.items.ItemEnderPack;
import com.sunflow.sunbakamod.items.ModItems;
import com.sunflow.sunbakamod.util.ContainerHelper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ContainerBag extends ContainerHelper {

	public ContainerBag(int windowId, PlayerInventory inv, PlayerEntity player, boolean client) {
		super(ModItems.CONTAINER_BAG, windowId, 9 * 6);
		ItemStack itemstack = player.getHeldItemMainhand();
		Item item = itemstack.getItem();

		IItemHandler handler = new ItemStackHandler(containerSlotCount);

		if (item instanceof ItemBackpack) {
			if (!client) {
				handler = ((ItemBackpack) item).getHandler();
			} else {
				handler = ((ItemBackpack) item).createHandler(containerSlotCount);
			}
		} else if (item instanceof ItemEnderPack) {
			if (!client) {
				handler = ItemEnderPack.handler;
			}
			SunBakaMod.data.markDirty();
		}

		addSlotBox(handler, 0, 8, 18, 9, 18, 6, 18);

		layoutPlayerInventorySlots(new InvWrapper(inv), 8, 140);
	}

	@Override
	public void onContainerClosed(PlayerEntity player) {
		ItemStack itemstack = player.getHeldItemMainhand();
		Item item = itemstack.getItem();
		if (item instanceof ItemBackpack) {
			((ItemBackpack) item).closeBag(player, itemstack);
		}
		super.onContainerClosed(player);
	}
}
