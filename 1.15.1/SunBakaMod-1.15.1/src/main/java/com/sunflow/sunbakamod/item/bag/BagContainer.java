package com.sunflow.sunbakamod.item.bag;

import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.setup.ModItems;
import com.sunflow.sunbakamod.util.ContainerBase;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class BagContainer extends ContainerBase {
//	private ItemStackHandler handler;
//	private ItemStack itemstack;

	public BagContainer(int windowId, PlayerInventory inv, PlayerEntity player) {
		super(ModItems.CONTAINER_BAG, windowId, 9 * 6);
		this.handler = new ItemStackHandler(containerSlotCount);
//		this.itemstack = player.getHeldItemMainhand();
		for (Hand hand : Hand.values()) {
			ItemStack stack = player.getHeldItem(hand);
			if (stack.getItem() instanceof BaseBagItem) {
				itemstack = stack;
				break;
			}
		}
		boolean client = !(player instanceof ServerPlayerEntity);

		Item item = itemstack.getItem();
		if (item instanceof BackpackItem) {
			if (!client) handler = getHandler(itemstack);
		} else if (item instanceof EnderPackItem) {
			if (!client) {
				handler = EnderPackItem.handler;
				SunBakaMod.data.markDirty();
			}
		}
		playerInv = inv;
		addSlotBox(handler, 0, 8, 18, 9, 18, 6, 18);
		layoutPlayerInventorySlots(new InvWrapper(inv), 8, 140);
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickType, PlayerEntity player) {
		if (itemstack.getItem() instanceof BackpackItem) {
			if (clickType == ClickType.SWAP && inventorySlots.get(containerSlotCount + 9 * 3 + dragType).getStack().getItem() instanceof BackpackItem)
				return ItemStack.EMPTY;
			if (slotId > 0 && inventorySlots.get(slotId).getStack().getItem() instanceof BackpackItem)
				return ItemStack.EMPTY;
		}
		return super.slotClick(slotId, dragType, clickType, player);
	}

	@Override
	public void onContainerClosed(PlayerEntity player) {
		super.onContainerClosed(player);
		Item item = itemstack.getItem();
		if (item instanceof BackpackItem) {
			CompoundNBT tag = itemstack.getOrCreateTag();
			CompoundNBT invTag = handler.serializeNBT();
			tag.put("inv", invTag);
			itemstack.setTag(tag);
		}
	}

	public static ItemStackHandler getHandler(ItemStack itemstack) {
		CompoundNBT tag = itemstack.getOrCreateTag();
		CompoundNBT invTag = tag.getCompound("inv");
		ItemStackHandler handler = new ItemStackHandler(9 * 6);
		handler.deserializeNBT(invTag);
		return handler;
	}
}
