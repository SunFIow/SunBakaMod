package com.sunflow.sunbakamod.util;

import java.util.LinkedList;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerHelper extends Container {

	protected int containerSlotCount;

	public ContainerHelper(ContainerType<?> type, int id, int containerSlotCount) {
		super(type, id);
		this.containerSlotCount = containerSlotCount;
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return true;
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickType, PlayerEntity player) {
		this.dragType = dragType;
		return super.slotClick(slotId, dragType, clickType, player);
	}

	private int dragType;

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			itemstack = stack.copy();
			if (index < containerSlotCount) {
				if (ItemStack.areItemsEqualIgnoreDurability(stack, player.inventory.getItemStack())) {
					if (dragType == 0) {
						// Swap all items from type == stack.item from this inventory to player
						// inventory
						LinkedList<Slot> slots = getAllItemStacksOf(stack, 0, containerSlotCount);
						for (Slot s : slots) {
							ItemStack newStack = s.getStack();
							ItemStack newStackCopy = newStack.copy();
							if (!this.mergeItemStack(newStack, containerSlotCount, containerSlotCount + 9 * 4, true)) {
								return ItemStack.EMPTY;
							}
							slot.onSlotChange(newStack, newStackCopy);
						}
						return ItemStack.EMPTY;

					} else if (dragType == 1) {
						// Swap all items from type == stack.item from player inventory to this
						// inventory
						LinkedList<Slot> slots = getAllItemStacksOf(stack, containerSlotCount,
								containerSlotCount + 9 * 4);

						for (Slot s : slots) {
							ItemStack newStack = s.getStack();
							ItemStack newStackCopy = newStack.copy();
							if (!this.mergeItemStack(newStack, 0, containerSlotCount, false)) {
								return ItemStack.EMPTY;
							}
							slot.onSlotChange(newStack, newStackCopy);
						}
						return ItemStack.EMPTY;
					}
				}
				if (!this.mergeItemStack(stack, containerSlotCount, containerSlotCount + 9 * 4, true)) {
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(stack, itemstack);
			} else {
				if (!this.mergeItemStack(stack, 0, containerSlotCount, false)) {
					if (index < containerSlotCount + 9 * 3) {
						if (!this.mergeItemStack(stack, containerSlotCount + 9 * 3, containerSlotCount + 9 * 4,
								false)) {
							return ItemStack.EMPTY;
						}
					} else if (index < containerSlotCount + 9 * 4
							&& !this.mergeItemStack(stack, containerSlotCount, containerSlotCount + 9 * 3, false)) {
						return ItemStack.EMPTY;
					}
				}
			}

			if (stack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (stack.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, stack);
		}

		return itemstack;
		// return super.transferStackInSlot(playerIn, index);
	}

	private LinkedList<Slot> getAllItemStacksOf(ItemStack compareStack, int startIndex, int endIndex) {
		LinkedList<Slot> returnList = new LinkedList<>();
		for (int index = startIndex; index < endIndex; index++) {
			// ItemStack slotItemStack = ItemStack.EMPTY;
			Slot slot = this.inventorySlots.get(index);
			// if (slot != null && slot.getHasStack()) {
			// ItemStack stack = slot.getStack();
			// slotItemStack = stack.copy();
			// }
			if (ItemStack.areItemsEqualIgnoreDurability(slot.getStack(), compareStack)) {
				returnList.add(slot);
			}
		}

		return returnList;
	}

	protected int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
		for (int i = 0; i < amount; i++) {
			addSlot(new SlotItemHandler(handler, index, x, y));
			x += dx;
			index++;
		}
		return index;
	}

	protected int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount,
			int dy) {
		for (int j = 0; j < verAmount; j++) {
			index = addSlotRange(handler, index, x, y, horAmount, dx);
			y += dy;
		}
		return index;
	}

	protected void layoutPlayerInventorySlots(IItemHandler handler, int leftCol, int topRow) {
		// Player inventory
		addSlotBox(handler, 9, leftCol, topRow, 9, 18, 3, 18);

		// Hotbar
		topRow += 58;
		addSlotRange(handler, 0, leftCol, topRow, 9, 18);
	}
}
